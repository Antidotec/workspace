<template>
  <el-menu
    :default-active="this.$route.path"
    router
    mode="horizontal"
    background-color="white"
    text-color="#222"
    active-text-color="red"
    style="min-width: 1300px">
    <el-menu-item v-for="(item,i) in navList" :key="i" :index="item.name">
      {{ item.navItem }}
    </el-menu-item>
    <el-button icon="el-icon-switch-button" @click="Logout" style="float: right;margin-top: 10px"></el-button>
    <!--    <i class="el-icon-menu" style="float:right;font-size: 45px;color: #222;padding-top: 8px"></i>-->
    <span
      style="position: absolute;padding-top: 20px;right: 43%;font-size: 20px;font-weight: bold">戛然而止的故事，总好过狗尾续貂</span>
    <div style="position: absolute;padding-top: 20px;right: 20%;">
      <span v-if="!this.$store.state.user.vip" style="font-weight: bold;font-family: 微软雅黑">用户:</span>
      <span v-if="this.$store.state.user.vip" style="color: red;font-weight: bold;font-family: 微软雅黑">尊贵的会员:</span>
      <span>{{this.$store.state.user.username}}</span>
      <el-button v-if="!this.$store.state.user.vip" type="danger" @click="becomeVip" size="small"
                 style="margin-left: 60px;margin-bottom:10px ">成为会员
      </el-button>
    </div>
  </el-menu>
</template>

<script>
  export default {
    name: 'NavMenu',
    data() {
      //使用return创建的是变量副本不会造成全局的污染
      return {
        navList: [
          // {name: '/index', navItem: '商城简介'},
          {name: '/market', navItem: '网上书店'},
          // {name: '/library', navItem: '我的图书馆'},
          {name: '/info', navItem: '个人中心'},
        ],
      }
    },
    methods: {
      becomeVip() {
        var that = this;
        this.$confirm("是否加入vip?", "提示", {
          confirmButtonText: "支付",
          cancelButtonText: "取消"
        }).then(() =>
          this.$axios.post('/becomeVip?id=' + this.$store.state.user.id).then(resp => {
            if (resp && resp.status === 200) {
              that.$message({
                type: "success",
                message: "恭喜你成为会员!"
              })
              that.$store.commit('becomeVip')
            }
          })
        )
      },
      Logout() {
        var that = this;
        this.$store.commit('userLogout')
        this.$confirm("退出该账号?", "提示", {
          confirmButtonText: "确定",
          cancelButtonText: "取消"
        }).then(() => that.$router.replace({path: '/login'})
        )
      }
    }
  }
</script>

<style scoped>
  a {
    text-decoration: none;
  }

  span {
    pointer-events: none;
  }
</style>

