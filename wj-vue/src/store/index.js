import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// 状态寄存器
export default new Vuex.Store({
  state: {
    user: {
      username: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).username,
      vip: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).vip,
      id: window.sessionStorage.getItem('user' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('user' || '[]')).id,
    },
    adminUser: {
      username: window.sessionStorage.getItem('adminUser' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('adminUser' || '[]')).username,
      id: window.sessionStorage.getItem('adminUser' || '[]') == null ? '' : JSON.parse(window.sessionStorage.getItem('adminUser' || '[]')).id,
    },
    adminMenu: []
  },
  // 状态改变函数
  mutations: {
    login(state, user) {
      state.user = user
      window.sessionStorage.setItem('user', JSON.stringify(user))
    },
    adminLogin(state, adminUser) {
      state.adminUser = adminUser;
      window.sessionStorage.setItem('adminUser', JSON.stringify(adminUser))
    },
    initAdminMenu(state, menus) {
      state.adminMenus = menus
    },
    logout(state) {
      state.adminUser.username = '';
      state.adminUser.id = '';
      window.sessionStorage.removeItem('adminUser')
      state.adminMenu = [];
    },
    userLogout(state){
      state.user.username = '';
      state.user.id = '';
      state.user.vip='';
      window.sessionStorage.removeItem('user')
    },
    vipChange(state,vip){
      state.user.vip = vip;
      window.sessionStorage.setItem('user', JSON.stringify(state.user))
    }
  }
})
