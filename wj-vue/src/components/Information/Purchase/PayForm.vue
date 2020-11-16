<template>
  <div>
    <el-dialog
      title="图书详情"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-row :gutter="10">
        <el-col :span="10">
          <img :src="form.cover" alt="封面">
        </el-col>
        <el-col :span="8" :offset="2">
          <div class="intro">
            <span>书名&emsp;{{form.title}}</span>
            <el-divider class="divider"></el-divider>
            <span>作者&emsp;{{form.author}}</span>
            <el-divider class="divider"></el-divider>
            <span>出版日期&emsp;{{form.date}}</span>
            <el-divider class="divider"></el-divider>
            <span>出版社&emsp;{{form.press}}</span>
            <el-divider class="divider"></el-divider>
            <span>分类&emsp;{{form.category.name}}</span>
            <el-divider class="divider"></el-divider>
          </div>
        </el-col>
      </el-row>
      <el-row :gutter="10" style="margin-top: 30px">
        <el-col :span="10">
          <el-card style="text-align: left">
             <span slot="header">
               图书摘要
             </span>
            <div class="abstract">{{form.abs}}</div>
          </el-card>
        </el-col>
        <el-col :span="10" :offset="2">
          <el-card style="text-align: left">
            <span>价格:&nbsp</span>
            <span style="color: #ff0000">￥{{form.price}}</span>
            <i class="el-icon-close" style="font-size: 10px"></i>
            <span style="color: #505458">{{numOfBooks}}</span>
          </el-card>
          <el-card style="text-align:left;margin-top: 15px">
            <span>收货地址:&nbsp;</span>
            <span style="color: #333333;font-weight: 600;">{{defaultAdd}}</span>
          </el-card>
          <el-card style="text-align:left;margin-top: 15px">
            <span v-if="onSale">需支付总金额:&nbsp;</span>
            <span v-if="!onSale">需支付定金总金额:&nbsp;</span>
            <span
              style="color: red">{{onSale?form.price*numOfBooks:form.deposit*numOfBooks}}</span>
          </el-card>
        </el-col>
      </el-row>
      <el-dialog :visible.sync="addVisible" :append-to-body="true">
        <el-form :model="form" style="text-align: left">
          <el-form-item>
            <el-select v-model="value" value-key="key" :placeholder="defaultAdd" @change="getAid">
              <el-option
                v-for="item in address"
                :key="item.key"
                :label="item.label"
                :value="item">
              </el-option>
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="modifyAdd">确认修改</el-button>
          <el-button type="info" @click="addVisible=false">取 消</el-button>
        </div>
      </el-dialog>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" v-if="paidAll&&!delivered&&!refund" @click="">未发货</el-button>
        <el-button type="info" v-if="successful&&!onSale&&!refund">等待商品上架</el-button>
        <el-button type="info" v-if="refund&&!finish">退款中</el-button>
        <el-button type="info" v-if="paidAll&&!refund&&finish">已收货</el-button>
        <el-button type="info" v-if="refund&&finish">已退款</el-button>
        <el-button type="danger" v-if="!paidAll&&!refund" @click="addVisible=true">修改地址</el-button>
        <el-button type="primary" v-if="!successful" @click="onSubmit">支付订单</el-button>
        <el-button type="primary" v-if="successful&&onSale&&!paidAll&&!refund" @click="payRest">支付尾款</el-button>
        <el-button type="primary" v-if="delivered&&!refund&&!finish" @click="finishDeliver">收货</el-button>
        <el-button type="danger" v-if="successful&&!refund" @click="onSubmit2">申请退款</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  export default {
    name: 'PayForm',
    data() {
      return {
        id: '',
        dialogFormVisible: false,
        addVisible: false,
        form: {
          id: '',
          title: '',
          author: '',
          date: '',
          press: '',
          cover: '',
          abs: '',
          price: '',
          deposit: '',
          category: {
            id: '',
            name: ''
          },
        },
        delivered:false,
        successful: false,
        refund: false,
        finish: false,
        onSale: '',
        paidAll: false,
        region: '',
        numOfBooks: 1,
        address: [],
        defaultAdd: '',
        formLabelWidth: '120px',
        value: '',
        aid: '',
        temp: {
          addr: '',
          aid: ''
        },
      }
    },
    methods: {
      clear() {
        this.form = {
          id: '',
          title: '',
          author: '',
          date: '',
          press: '',
          cover: '',
          abs: '',
          category: ''
        }
      },
      getAid(data) {
        //这里只是暂时改变
        this.temp.aid = data.aid
        this.temp.addr = data.label
      },
      modifyAdd() {
        //这里就是确定修改了
        this.defaultAdd = this.temp.addr;
        this.aid = this.temp.aid;
        var that = this;
        this.$axios.post('updateOrder', {
          id: this.id,
          aid: this.aid
        }).then(resp => {
          if (resp && resp.status === 200) {
            that.$emit("changeStatus")
          }
        })
        this.$message({
          type: "success",
          message: "地址修改成功"
        })
        this.addVisible = false;
      },
      onSubmit() {
        var that = this
        this.$confirm("确定支付订单" + (this.onSale ? this.form.price * this.numOfBooks : this.form.deposit * this.numOfBooks).toFixed(2) + "元?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消"
          }
        ).then(() => {
          this.$axios.post("/updatePayment?id=" + this.id).then(resp => {
            if (resp && resp.status === 200) {
              that.$emit("changeStatus")
              that.successful = true
              if(that.onSale) {
                that.$axios.post("/paidAll?id=" + that.id).then(resp => {
                  if (resp && resp.status === 200) {
                    that.$emit("changeStatus")
                    that.paidAll = true
                  }
                })
              }
            }
          })
          this.$message({
            type: "success",
            message: "订单支付成功"
          })
        })
      },
      onSubmit2() {
        var that = this
        this.$axios.post("/updateRefund?id=" + this.id).then(resp => {
          if (resp && resp.status === 200) {
            that.$emit("changeStatus")
            that.refund = true;
            that.finish = false;
          }
        })
        this.$message({
          type: "success",
          message: "申请退款中!"
        })
      },
      payRest() {
        var that = this
        this.$confirm("是否支付尾款" + ((this.form.price - this.form.deposit) * this.numOfBooks).toFixed(2) + "元?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        }).then(() => {
          this.$axios.post("/paidAll?id=" + this.id).then(resp => {
            if (resp && resp.status === 200) {
              that.$emit("changeStatus")
              that.paidAll = true
              that.$message({
                type: "success",
                message: "支付成功!"
              })
            }
          })
        })
      },
      finishDeliver() {
        var that = this
        this.$axios.post("/finish?id=" + this.id).then(resp => {
          if (resp && resp.status === 200) {
            that.$emit("changeStatus")
            that.finish = true
            that.$message({
              type: "success",
              message: "已确认收货"
            })
          }
        })
      }
    }
  }
</script>

<style scoped>
  .el-icon-circle-plus-outline {
    margin: 50px 0 0 20px;
    font-size: 100px;
    float: left;
    cursor: pointer;
  }

  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  .intro {
    line-height: 24px;
    padding: 1px;
    margin-top: 2px;
    font-size: 14px;
    text-align: left;
  }

  .abstract {
    display: block;
    line-height: 24px;
  }

  .divider {
    margin-bottom: 5px;
    margin-top: 10px;
  }

  img {
    width: 200px;
    height: 200px;
    /*margin: 0 auto;*/
  }
</style>


