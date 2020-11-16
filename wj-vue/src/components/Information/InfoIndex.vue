<template>
  <div>
    <el-container>
      <el-aside>
        <SideMenu ref="sideMenu"></SideMenu>
      </el-aside>
      <el-main>
        <router-view></router-view>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import SideMenu from "./SideMenu";

  export default {
    name: "InfoIndex",
    components: {SideMenu}
  }
</script>

<style scoped>

</style>
