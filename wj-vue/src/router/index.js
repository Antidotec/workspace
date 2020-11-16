import Vue from 'vue'
import Router from 'vue-router'
import AppIndex from '../components/home/AppIndex'
import Login from '../components/Login'
import Home from '../components/Home'
import LibraryIndex from "../components/library/LibraryIndex"
import Register from "../components/Register";
import MarketIndex from "../components/market/MarketIndex";
import InfoIndex from "../components/Information/InfoIndex";
import Purchase from "../components/Information/Purchase/Purchase";
import Detail from "../components/Information/Detail";
import CollectionIndex from "../components/Information/Collection/CollectionIndex";
import Address from "../components/Information/Address";
import AdminIndex from "../components/admin/AdminIndex";
import BooksList from "../components/admin/book/BookList";
import BookAppend from "../components/admin/book/BookAppend";
import UserList from "../components/admin/user/UserList";
import AdminLogin from "../components/admin/AdminLogin";

Vue.use(Router)
let defaultRoutes = [
  {
    path: '/home',
    name: 'Home',
    component: Home,
    // home页面并不需要被访问
    redirect: '/market',
    children: [
      {
        path: '/index',
        name: 'AppIndex',
        component: AppIndex,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/library',
        name: 'Library',
        component: LibraryIndex,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/market',
        name: 'Market',
        component: MarketIndex,
        meta: {
          requireAuth: true
        }
      },
      {
        path: '/info',
        name: 'Information',
        component: InfoIndex,
        redirect: '/info/pur',
        meta: {
          requireAuth: true
        },
        children: [
          {
            //创建子路由的时候不需要再加 / 了
            path: 'pur',
            name: 'Purchase',
            component: Purchase,
            meta: {
              requireAuth: true
            }
          },
          {
            path: 'detail',
            name: 'Detail',
            component: Detail,
            meta: {
              requireAuth: true
            }
          },
          {
            path: 'collections',
            name: 'Collections',
            component: CollectionIndex,
            meta: {
              requireAuth: true
            }
          },
          {
            path: 'address',
            name: 'Address',
            component: Address,
            meta: {
              requireAuth: true
            }
          }
        ]
      },
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/adminLogin',
    name: 'AdminLogin',
    component: AdminLogin
  }
]
export default new Router({
  mode: 'history',
  routes: defaultRoutes
})
export const createRouter = routes => new Router({
  mode: 'history',
  routes: defaultRoutes
})
