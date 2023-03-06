import Vue from 'vue'
import Router from 'vue-router'
import Home from '@/Home'
/*import Index from '@/views/Index'
import Login from '@/views/Login'
import Register from '@/views/Register'
import Log from '@/views/Log'
import MessageBoard from '@/views/MessageBoard'
import BlogWrite from '@/views/blog/BlogWrite'
import BlogView from '@/views/blog/BlogView'
import BlogAllCategoryTag from '@/views/blog/BlogAllCategoryTag'
import BlogCategoryTag from '@/views/blog/BlogCategoryTag'*/

import {Message} from 'element-ui';


import store from '@/store'

import {getToken} from '@/request/token'

Vue.use(Router)

const router = new Router({
  routes: [
    {
      path: '/write/:id?',
      component: r => require.ensure([], () => r(require('@/views/blog/BlogWrite')), 'blogwrite'),
      meta: {
        requireLogin: true
      },
    },
    {
      path: '/',
      redirect: '/home',
      name: 'Home',
      component: Home,
      children: [
        {
          path: '/home',
          component: r => require.ensure([], () => r(require('@/views/Index')), 'index')
        },
        {
          path: '/log',
          component: r => require.ensure([], () => r(require('@/views/Log')), 'log')
        },
        {
          path: '/archives/:year?/:month?',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogArchive')), 'archives')
        },
        {
          path: '/messageBoard',
          component: r => require.ensure([], () => r(require('@/views/MessageBoard')), 'messageBoard')
        },
        {
          path: '/view/:id',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogView')), 'blogView')
        },
        // {
        //   path: '/category/all',
        //   component: r => require.ensure([], () => r(require('@/views/blog/BlogAllCategory')), 'blogAllCategory')
        // },
        {
          path: '/tag/:id?',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogTag')), 'blogTag')
        },
        {
          path: '/category/:id?',
          component: r => require.ensure([], () => r(require('@/views/blog/BlogCategory')), 'blogCategory')
        },
        {
          path: '/search/:queryString',
          component: r => require.ensure([], () => r(require('@/views/blog/SearchResult')), 'searchResult')
        },
        {
          path: '/about',
          component: r => require.ensure([], () => r(require('@/views/AboutMe')), 'aboutMe')
        }
        // {
        //   path: '/tag/:id',
        //   component: r => require.ensure([], () => r(require('@/views/blog/BlogTag')), 'blogTag')
        // }
      ]
    },
    {
      path: '/login',
      component: r => require.ensure([], () => r(require('@/views/Login')), 'login')
    },
    {
      path: '/register',
      component: r => require.ensure([], () => r(require('@/views/Register')), 'register')
    }

  ],
  scrollBehavior(to, from, savedPosition) {
    return {x: 0, y: 0}
  }
})

router.beforeEach((to, from, next) => {

  if (getToken()) {

    if (to.path === '/login') {
      next({path: '/'})
    } else {
      if (store.state.account.length === 0) {
        store.dispatch('getUserInfo').then(data => { //获取用户信息
            next()
        }).catch(() => {
          Message({
            type: 'warning',
            showClose: true,
            message: '登录已过期'
          })
          next({path: '/'})
        })
      } else {

        next()
      }
    }
  } else {
    if (to.matched.some(r => r.meta.requireLogin)) {
      Message({
        type: 'warning',
        showClose: true,
        message: '请先登录哦'
      })

    }
    else {
      next();
    }
  }
})


export default router
