<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>收藏列表</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-card style="margin-top: 20px">
      <el-table
        :data="collections"
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
          prop="uid"
          label="用户id"
          width="100">
        </el-table-column>
        <el-table-column
          prop="username"
          label="用户名"
          width="100">
        </el-table-column>
        <el-table-column
          label="封面"
          width="100">
          <template slot-scope="scope">
            <img :src="scope.row.cover" style="width: 50px;height: 50px"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          width="100">
        </el-table-column>
        <el-table-column
          width="100"
          :resizable="false">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="danger"
              @click="delCollection(scope.row)">删除收藏
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
  </div>
</template>

<script>

  export default {
    name: "CollectList",
    data() {
      return {
        collections: [],
      }
    },
    mounted() {
      this.loadCollections();
    },
    methods: {
      loadCollections() {
        this.$axios.get('/collectionList').then(resp => {
          if (resp && resp.status === 200) {
            this.collections = resp.data;
          }
        })
      },
      delCollection(collection) {
        var that = this;
        this.$axios.post("/delCollection?id=" + collection.id).then(resp => {
          if (resp && resp.status === 200) {
            that.$message({
              type:"success",
              message:"删除成功"
            })
            that.loadCollections();
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
