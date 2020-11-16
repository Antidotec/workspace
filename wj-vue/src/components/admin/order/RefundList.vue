<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>订单管理</el-breadcrumb-item>
        <el-breadcrumb-item>商品退货</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-card style="margin-top: 20px">
      <el-table
        :data="orders"
        stripe
        :default-sort="{prop: 'id', order: 'ascending'}"
        style="width: 100%">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="id"
          sortable
          width="80">
        </el-table-column>
        <el-table-column
          prop="bid"
          label="图书id"
          width="80">
        </el-table-column>
        <el-table-column
          prop="uid"
          label="用户id"
          width="80">
        </el-table-column>
        <el-table-column
          prop="title"
          label="图书名"
          width="120">
        </el-table-column>
        <el-table-column
          prop="author"
          label="作者"
          width="120">
        </el-table-column>
        <el-table-column
          prop="address"
          label="收货地址"
          width="220">
        </el-table-column>
        <el-table-column
          prop="price"
          label="单价"
          width="80">
        </el-table-column>
        <el-table-column
          prop="number"
          label="购买数量"
          width="80">
        </el-table-column>
        <el-table-column
          prop="total"
          label="总金额"
          width="100">
        </el-table-column>
<!--        <el-table-column-->
<!--          prop="status"-->
<!--          label="订单状态"-->
<!--          width="100">-->
<!--        </el-table-column>-->
        <el-table-column
          label="操作"
          width="150"
          :resizable="false">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="refund(scope.row)">退款
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>

  export default {
    name: "RefundList",
    data() {
      return {
        orders: [],
      }
    },
    mounted() {
      this.loadOrders();
    },
    methods: {
      loadOrders() {
        var that = this
        this.$axios.get("/getAllRefunds").then(resp => {
          if (resp && resp.status === 200) {
            this.orders = resp.data;
          }
        })
      },
      refund(item) {
        let that = this;
        this.$axios.post('/refundOrder?id='+item.id).then(resp => {
          if (resp && resp.status === 200) {
            that.$message({
              type:"success",
              message:"退款成功!"
            })
            that.loadOrders();
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
