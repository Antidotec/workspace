<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>用户管理</el-breadcrumb-item>
        <el-breadcrumb-item>用户列表</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <div align="left" style="margin-top: 20px">
      <el-input
        @keyup.enter.native="searchClick"
        placeholder="通过用户名搜索..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px;"
        v-model="keywords">
      </el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    </div>
    <el-card style="margin-top: 20px">
      <el-table
        :data="users"
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
          prop="username"
          label="用户名"
          fit>
        </el-table-column>
        <el-table-column
          prop="password"
          label="密码"
          fit>
        </el-table-column>
        <el-table-column
          prop="name"
          label="昵称"
          fit>
        </el-table-column>
        <el-table-column
          prop="phone"
          label="电话"
          fit>
        </el-table-column>
        <el-table-column
          prop="email"
          label="邮箱"
          fit>
        </el-table-column>
        <el-table-column
          prop="vip"
          label="会员等级"
          fit>
        </el-table-column>
        <el-table-column
          width="150"
          :resizable="false">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="editUser(scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="delUser(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <edit-form @submit="loadUsers" ref="edit"></edit-form>
  </div>
</template>

<script>
  import EditForm from "./EditForm";

  export default {
    name: "UserList",
    components: {EditForm},
    data() {
      return {
        dialogFormVisible: false,
        users: [],
        keywords: '',
        vipDegree: 0,
        selectItem: ''
      }
    },
    mounted() {
      this.loadUsers();
    },
    methods: {
      loadUsers() {
        this.$axios.get('/userList').then(resp => {
          if (resp && resp.status === 200) {
            this.users = resp.data;
          }
        })
      },
      editUser(user) {
        this.$refs.edit.form = {
          id: user.id,
          username: user.username,
          password: user.password,
          name: user.name,
          phone: user.phone,
          email: user.email,
          enabled: user.enabled,
          vip: user.vip
        }
        this.$refs.edit.dialogFormVisible = true;
      },
      delUser(user) {
        var that = this;
        this.$axios.post("/delUser?id=" + user.id).then(resp => {
          if (resp && resp.status === 200) {
            that.$message({
              type: "success",
              message: "删除成功"
            })
            that.loadUsers();
          }
        })
      },
      searchClick() {
        this.$axios.get('admin/user/search?keywords=' + this.keywords).then(resp => {
          if (resp && resp.status === 200) {
            this.users = resp.data
          }
        })
      }
    }
  }
</script>

<style scoped>

</style>
