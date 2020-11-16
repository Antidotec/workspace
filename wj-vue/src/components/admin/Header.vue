<template>
  <el-card class="admin-header">
    <img src="../../assets/img/icon/banben.jpg" alt="" width="90px"
         style="float: left;margin-top: -18px;margin-left: -18px">
    <span style="font-size: 20px;font-weight: bold">图书管理系统</span>
    <!--    <span style="font-size: 32px;font-weight: bold;position:absolute;left: 100px">白  卷</span>-->
    <i class="el-icon-switch-button" v-on:click="logout" style="font-size: 40px;float: right"></i>
  </el-card>
</template>

<script>
  import {createRouter} from '../../router'

  export default {
    name: 'Header',
    methods: {
      logout() {
        var _this = this
        _this.$store.commit('logout')
        const newRouter = createRouter();
        _this.$router.replace('/adminLogin')
        _this.$router.go(0);//解决不刷新的问题
      }
    }
  }
</script>

<style scoped>
  .admin-header {
    height: 80px;
    opacity: 0.85;
    line-height: 40px;
    min-width: 900px;
  }

  .el-icon-switch-button {
    cursor: pointer;
    outline: 0;
  }
</style>
