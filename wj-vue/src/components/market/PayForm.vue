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
        <el-col :span="8" :offset="2">
          <el-card style="text-align: left" v-if="onSale">
            <span v-if="this.$store.state.user.vip">会员价</span>
            <span v-if="!this.$store.state.user.vip">价格</span>
            <span style="color: #ff0000">￥{{form.price}}</span>
            <i v-if="this.$store.state.user.vip"> &nbsp;&nbsp; &nbsp;8折优惠</i>
          </el-card>
          <el-card v-if="!onSale" style="text-align: left">
            <span v-if="this.$store.state.user.vip">到手会员价</span>
            <span v-if="!this.$store.state.user.vip">到手价</span>
            <span style="color: #ff0000">￥{{form.price}}</span>
            <i v-if="this.$store.state.user.vip"> &nbsp;&nbsp; &nbsp;8折优惠</i>
          </el-card>
          <el-card v-if="!onSale" style="text-align: left;margin-top: 10px">
            <span>定金</span>
            <span style="color: red">￥{{form.deposit}}</span>
          </el-card>
          <el-form :model="form" red="address_form" style="margin-top: 10px;text-align: left">
            <el-form-item>
              <el-select v-model="value" value-key="key" placeholder="请选择收货地址" @change="getAid">
                <el-option
                  v-for="item in address"
                  :key="item.key"
                  :label="item.label"
                  :value="item">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-input-number v-model="numOfBooks" :min="1" :max="10" label="描述文字"></el-input-number>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-button type="info" class="el-icon-shopping-cart-2">&nbsp;加入购物车</el-button>
        <el-button type="primary" @click="onSubmit">提交订单</el-button>
        <el-button type="danger" v-if="!collected" icon="el-icon-star-off" @click="Collect()">收 藏</el-button>
        <el-button type="danger" v-if="collected" icon="el-icon-star-on" @click="DisCollect()">取消收藏</el-button>
      </div>
    </el-dialog>
    <el-dialog>

    </el-dialog>
  </div>
</template>

<script>

  export default {
    name: 'PayForm',
    data() {
      return {
        dialogFormVisible: false,
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
        region: '',
        numOfBooks: 1,
        address: [],
        formLabelWidth: '120px',
        collected: false,
        value: '',
        aid: '',
        onSale: true
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
        this.aid = data.aid;
        console.log(this.aid)
      },
      onSubmit() {
        var payMoney = this.onSale ? this.numOfBooks * this.form.price : this.numOfBooks * this.form.deposit
        if (this.value === '') {
          this.$message({
            type: "warning",
            message: "请填写收货地址!"
          })
          return;
        }
        this.$confirm((this.onSale ? '请确定是否支付' : '请确定是否支付定金') + payMoney.toString() + "元？", '提示', {
            confirmButtonText: '支付',
            cancelButtonText: '取消',
            type: 'warning',
          }
        ).then(() => {
            this.$axios.post('/submitOrder',
              {
                uid: this.$store.state.user.id,
                bid: this.form.id,
                aid: this.aid,
                price: this.form.price,
                number: this.numOfBooks,
                successful: true,
                paidAll: this.onSale,
                refund: false
              })
            this.$message({
              type: "success",
              message: '订单支付成功!'
            })
          }
        ).catch(() => {
          this.$axios.post('/submitOrder',
            {
              id: -1,
              uid: this.$store.state.user.id,
              bid: this.form.id,
              aid: this.aid,
              price: this.form.price,
              number: this.numOfBooks,
              successful: 0,
              refund: 0,
              paidAll: 0
            })
          this.$message({
            type: "warning",
            message: "您未支付该订单!"
          })
        })
      },
      Collect() {
        //添加一条收藏信息到表格中
        var that = this;
        this.$axios.post('/collect',
          {
            uid: this.$store.state.user.id,
            bid: this.form.id
          }).then(resp => {
          if (resp.status === 200) {
            that.collected = true;
          }
        })
        this.collected = true;
      },
      //取消收藏
      DisCollect() {
        var that = this;
        this.$axios.post('/disCollect',
          {
            uid: this.$store.state.user.id,
            bid: this.form.id
          }).then(resp => {
          if (resp.status === 200) {
            that.collected = false;
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


