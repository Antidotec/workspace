<template>
  <div>
    <el-card>
      <el-table :data="orders"
                stripe
                style="width: 100%;">
        <el-table-column
          prop="title"
          label="图书名"
          width="150"
          :resizable="false">
        </el-table-column>
        <el-table-column
          prop="author"
          label="作者"
          width="150"
          :resizable="false">
        </el-table-column>
        <el-table-column
          prop="address"
          label="收货地址"
          width="200"
          :resizable="false">
        </el-table-column>
        <el-table-column
          label="单价"
          width="100"
          prop="price"
          :resizable="false">
        </el-table-column>
        <el-table-column
          label="购买数量"
          width="100"
          prop="number"
          :resizable="false">
        </el-table-column>
        <el-table-column
          prop="total"
          label="总金额"
          width="150"
          :resizable="false">
        </el-table-column>
        <el-table-column
          prop="status"
          label="订单状态"
          width="100"
          :resizable="false">
        </el-table-column>
        <el-table-column
          width="200"
          :resizable="false">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="orderDetails(scope.row)">查看详情
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="delOrder(scope.row)">删除订单
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <pay-form @changeStatus="loadOrders()" ref="pay"></pay-form>
  </div>
</template>

<script>
  import PayForm from "./PayForm";

  export default {
    name: "Purchase",
    components: {PayForm},
    data() {
      return {
        orders: [],
        value: ''
      }
    },
    mounted() {
      this.loadOrders();
    },
    methods: {
      loadOrders() {
        var that = this;
        //清零
        this.$axios.get('/getOrder?uid=' + this.$store.state.user.id).then(resp => {
          if (resp && resp.status === 200) {
            that.orders = resp.data;
          }
        })
      },
      orderDetails(item) {
        this.$refs.pay.dialogFormVisible = true
        this.$refs.pay.id = item.id
        this.$refs.pay.numOfBooks = item.number
        this.$refs.pay.defaultAdd = item.address
        this.$refs.pay.successful = item.successful
        this.$refs.pay.refund = item.refund
        this.$refs.pay.paidAll = item.paidAll
        this.$refs.pay.finish = item.finish
        this.$refs.pay.delivered = item.delivered
        this.$refs.pay.onSale = item.sale;
        this.$axios.get('getBook?id=' + item.bid).then(resp => {
          if (resp && resp.status === 200) {
            const book = resp.data
            this.$refs.pay.form = {
              id: book.id,
              cover: book.cover,
              title: book.title,
              author: book.author,
              date: book.date,
              press: book.press,
              abs: book.abs,
              price: this.$store.state.user.vip ? (item.price * 0.8).toFixed(2) : item.price, //这里直接处理单价信息
              category: {
                id: book.category.id.toString(),
                name: book.category.name
              },
              deposit:(item.price * 0.2).toFixed(2)
            }
          }
        })
        //获取用户的地址信息 后续可能会放到个人信息界面中
        var that = this;
        this.$axios.get('/address?uid=' + this.$store.state.user.id).then(resp => {
          if (resp && resp.status === 200) {
            //对地址进行格式化
            var addData = [];
            for (var i = 0; i < resp.data.length; i++) {
              addData.push(
                {
                  aid: resp.data[i].id,
                  key: i,
                  label: resp.data[i].format
                }
              )
            }
            that.$refs.pay.address = addData;//获取用户的地址信息
          }
        })
      },
      delOrder(item) {
        var that = this
        this.$axios.post('/delOrder?id=' + item.id).then(resp => {
          if (resp && resp.status === 200) {
            that.loadOrders();
          }
        })
      },
    }
  }


</script>

<style scoped>
  .intro {
    line-height: 24px;
    padding: 1px;
    font-size: 14px;
    text-align: left;
  }


  .divider {
    margin-bottom: 6px;
    margin-top: 0;
  }

  img {
    width: 150px;
    height: 200px;
    /*margin: 0 auto;*/
  }
</style>

