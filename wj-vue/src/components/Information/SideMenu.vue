<template>
  <el-menu
    router
    class="categories"
    active-text-color="red">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      <i :class="item.icon"></i>
      <span slot="title">{{item.navItem}}</span>
    </el-menu-item>
  </el-menu>
</template>

<script>
  export default {
    name: "SideMenu",
    data() {
      return {
        navList: [
          {name: '/info/pur', navItem: '全部订单', icon: "el-icon-tickets"},
          {name: '/info/detail', navItem: '个人信息', icon: "el-icon-user"},
          {name: '/info/collections', navItem: '收藏夹', icon: "el-icon-collection"},
          {name: '/info/address', navItem: '收获地址', icon: "el-icon-office-building"}
        ]
      }
    }
  }
</script>

<style scoped>
  .categories {
    position: fixed;
    margin-left: 50%;
    left: -600px;
    top: 100px;
    width: 150px;
  }
</style>
