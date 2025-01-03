import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },
  {
    path: '/auth-redirect',
    component: () => import('@/views/login/auth-redirect'),
    hidden: true
  },
  {
    path: '/404',
    component: () => import('@/views/error-page/404'),
    hidden: true
  },
  {
    path: '/401',
    component: () => import('@/views/error-page/401'),
    hidden: true
  },
  // 前台前端页面的路由配置
  {
    path: '/',
    component: () => import('@/views/main/index'),
    hidden: true
  },
  {
    path: '/clublist',
    component: () => import('@/views/pages/clublist/index'),
    hidden: true
  },

  {
    path: '/clubinfo',
    component: () => import('@/views/pages/clubinfo/index'),
    hidden: true
  },
  {
    path: '/myclub',
    component: () => import('@/views/pages/myclub/index'),
    hidden: true
  },
  {
    path: '/usercenter',
    component: () => import('@/views/pages/usercenter/index'),
    hidden: true
  },
  {
    path: '/frontactivity',
    component: () => import('@/views/pages/activity/index'),
    hidden: true
  },
  {
    path: '/clubnotice',
    component: () => import('@/views/pages/clubnotice/index'),
    hidden: true
  },
  {
    path: '/noticeinfo',
    component: () => import('@/views/pages/noticeinfo/index'),
    hidden: true
  },
  {
    path: '/meet',
    component: () => import('@/views/pages/meeting/index'),
    hidden: true
  },
  {
    path: '/clubmeeting',
    component: () => import('@/views/pages/jitsimeet/index'),
    hidden: true
  }
]

/**
 * asyncRoutes
 * the routes that need to be dynamically loaded based on user roles
 */
export const asyncRoutes = [
  {
    path: '/welcome',
    component: Layout,
    redirect: '/welcome/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/welcome/index'),
        name: 'Welcome',
        meta: {
          title: '欢迎',
          icon: 'dashboard',
          noCache: true,
          roles: ['Super', 'Admin']
        }
      }
    ]
  },
  {
    path: '/users',
    component: Layout,
    redirect: '/users/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/users/index'),
        name: 'Users',
        meta: {
          title: '用户信息管理',
          icon: 'el-icon-user-solid',
          noCache: true,
          roles: ['Super']
        }
      }
    ]
  },
  {
    path: '/admin',
    component: Layout,
    redirect: '/admin/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/admin/index'),
        name: 'Admin',
        meta: {
          title: '管理员管理',
          icon: 'el-icon-s-custom',
          noCache: true,
          roles: ['Super']
        }
      }
    ]
  },
  {
    path: '/category',
    component: Layout,
    redirect: '/category/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/category/index'),
        name: 'Category',
        meta: {
          title: '社团类型管理',
          icon: 'list',
          noCache: true,
          roles: ['Super']
        }
      }
    ]
  },
  {
    path: '/club',
    component: Layout,
    redirect: '/club/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/club/index'),
        name: 'Club',
        meta: {
          title: '社团信息管理',
          icon: 'form',
          noCache: true,
          roles: ['Super', 'Admin']
        }
      }
    ]
  },
  {
    path: '/apply',
    component: Layout,
    redirect: '/apply/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/apply/index'),
        name: 'Apply',
        meta: {
          title: '社团申请管理',
          icon: 'el-icon-s-claim',
          noCache: true,
          roles: ['Super', 'Admin']
        }
      }
    ]
  },
  {
    path: '/clubmember',
    component: Layout,
    redirect: '/clubmember/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/clubmember/index'),
        name: 'Clubember',
        meta: {
          title: '社团成员管理',
          icon: 'peoples',
          noCache: true,
          roles: ['Super', 'Admin']
        }
      }
    ]
  },
  {
    path: '/cost',
    component: Layout,
    redirect: '/cost/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/cost/index'),
        name: 'Cost',
        meta: {
          title: '社团费用管理',
          icon: 'money',
          noCache: true,
          roles: ['Admin']
        }
      }
    ]
  },
  {
    path: '/activity',
    component: Layout,
    redirect: '/activity/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/activity/index'),
        name: 'Activity',
        meta: {
          title: '社团活动管理',
          icon: 'nested',
          noCache: true,
          roles: ['Super', 'Admin']
        }
      }
    ]
  },
  {
    path: '/meeting',
    component: Layout,
    redirect: '/meeting/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/meeting/index'),
        name: 'Meeting',
        meta: {
          title: '会议记录管理',
          icon: 'el-icon-phone',
          noCache: true,
          roles: ['Super', 'Admin']
        }
      }
    ]
  },
  {
    path: '/leave',
    component: Layout,
    redirect: '/leave/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/leave/index'),
        name: 'Leave',
        meta: {
          title: '在线留言管理',
          icon: 'message',
          noCache: true,
          roles: ['Super']
        }
      }
    ]
  },
  {
    path: '/notice',
    component: Layout,
    redirect: '/notice/index',
    children: [
      {
        path: 'index',
        component: () => import('@/views/notice/index'),
        name: 'Notice',
        meta: {
          title: '公告管理',
          icon: 'el-icon-s-order',
          noCache: true,
          roles: ['Super']
        }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
