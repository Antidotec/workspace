<template>
  <div>
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
        style="position: absolute;padding-top: 20px;right: 50%;font-size: 20px;font-weight: bold">戛然而止的故事，总好过狗尾续貂</span>
    </el-menu>
    <div style="position: absolute;margin-top: -40px;right: 20%;">
      <span v-if="!this.vip" style="font-weight: bold;font-family: 微软雅黑">用户:</span>
      <span v-if="this.vip" style="color: red;font-weight: bold;font-family: 微软雅黑">尊贵的{{vip}}级会员:</span>
      <span>{{this.$store.state.user.username}}</span>
      <el-button type="danger" @click="dialogFormVisible=true" size="small"
                 style="margin-left: 60px;margin-bottom:10px ">{{this.vip?"提升会员等级":"成为会员"}}
      </el-button>
    </div>
    <el-dialog :visible.sync="dialogFormVisible" style="align-content: flex-start">
      <span>请选择会员等级: </span>
      <el-input-number v-model="vipTemp" :min="this.vip" :max="5" label="选择会员等级"
                       style="margin-left:10px;margin-right:10px "></el-input-number>
      <el-button @click="vipChange" type="primary">确认支付</el-button>
      <el-button @click="dialogFormVisible=false">取消</el-button>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: 'NavMenu',
    mounted() {
      this.vip = this.$store.state.user.vip;
      this.vipTemp = this.vip;
    },
    data() {
      //使用return创建的是变量副本不会造成全局的污染
      return {
        navList: [
          // {name: '/index', navItem: '商城简介'},
          {name: '/market', navItem: '网上书店'},
          // {name: '/library', navItem: '我的图书馆'},
          {name: '/info', navItem: '个人中心'},
        ],
        vip: '',
        vipTemp: '',
        dialogFormVisible: false
      }
    },
    methods: {
      vipChange() {
        var that = this;
        if (this.vip === this.vipTemp) {
          this.$alert("vip等级未发生变化!")
        } else {
          this.$axios.post('/becomeVip?id=' + this.$store.state.user.id + "&degree=" + this.vipTemp).then(resp => {
            if (resp && resp.status === 200) {
              this.vip = this.vipTemp
              that.$message({
                type: "success",
                message: "恭喜你成为" + this.vip + "级会员!"
              })
              that.$store.commit('becomeVip')
            }
          })
          this.$store.commit('vipChange', this.vip)
        }
        this.dialogFormVisible = false;
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

