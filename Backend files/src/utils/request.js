import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import { getToken, setToken } from '@/utils/auth'

// 创建一个axios实例
const service = axios.create({
  // baseURL: 'http://localhost:8888/'
  baseURL: 'http://www.shantouliu.site:4485/'
})

// 请求拦截器
service.interceptors.request.use(
  config => {
    if (store.getters.token) {
      config.headers['token'] = getToken()
    }
    return config
  },
  error => {
    console.log(error)
    return Promise.reject(error)
  }
)

// 响应拦截器
service.interceptors.response.use(
  response => {
    const res = response.data
    const headers = response.headers

    if (res.code !== 20000) {
      if (headers.code === '50008' || headers.code === '500014' || headers.code === '500020') {
        // 提醒重新登录
        MessageBox.confirm('您已退出，您可以取消留在该页面，或重新登录', '确认退出', {
          confirmButtonText: '重新登录',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          store.dispatch('user/resetToken').then(() => {
            location.reload()
          })
        })
      } else {
        Message({
          message: res.message || 'Error',
          type: 'error',
          duration: 5 * 1000
        })
      }
      return Promise.reject(new Error(headers.message || 'Error'))
    } else {
      if (store.getters.token) { // 更新token
        store.commit('user/SET_TOKEN', headers.token)
        setToken(headers.token)
      }
      return res
    }
  },
  error => {
    console.log('err' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
