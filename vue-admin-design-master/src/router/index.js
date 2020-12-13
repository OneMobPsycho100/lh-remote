import Vue from 'vue'
import Router from 'vue-router'
import Layout from '../layout'
import { asyncRoutes } from './routes'

Vue.use(Router)

/**
 * hidden: true                  如果设置为 true，该项菜单将不会显示在菜单栏中(默认为 false)
 * meta : {
    title: 'title'               菜单名
    icon: 'icon-name'            图标名
    fixed: true                  如果设置为 true，该项 tag 将一直存在 tag 栏中(默认为 false)
  }
 * */

export const constantRoutes = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login'),
    hidden: true,
    meta: { title: '登录' }
  },
  {
    path: '401',
    name: '401',
    component: () => import('../views/error-page/401'),
    hidden: true,
    meta: { title: '401' }
  },
  {
    path: '404',
    name: '404',
    component: () => import('../views/error-page/404'),
    hidden: true,
    meta: { title: '404' }
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/home',
    children: [{
      path: 'home',
      name: 'Home',
      component: () => import('../views/Home'),
      meta: {
        title: '首页',
        icon: 'vue-dsn-icon-index',
        fixed: true
      }
    }]
  },
  {
    path: '/shell',
    name: 'doc',
    component: Layout,
    redirect: '/shell',
    children: [{
      path: 'shell',
      name: 'shell',
      component: () => import('../views/form-table/shellList'),
      meta: {
        title: '脚本',
        icon: 'vue-dsn-icon-wendang',
        fixed: true
      }
    }]
  },
  {
    path: '/record',
    name: 'doc',
    component: Layout,
    redirect: '/record',
    children: [{
      path: 'record',
      name: 'record',
      component: () => import('../views/form-table/TableClassic'),
      meta: {
        title: '执行记录',
        icon: 'vue-dsn-icon-wendang',
        fixed: true
      }
    }]
  },
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/user-center',
    hidden: true,
    children: [{
      path: 'user-center',
      name: 'UserCenter',
      component: () => import('../views/UserCenter'),
      meta: {
        title: '个人中心'
      }
    }]
  }
]

const routes = [...constantRoutes, ...asyncRoutes]

export default new Router({
  routes
})

