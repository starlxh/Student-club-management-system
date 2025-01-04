import request from '@/utils/request'

export function login(data) {
  return request({
    url: '/login/loginIn',
    method: 'post',
    data
  })
}

export function getInfo(token) {
  return request({
    url: '/login/queryUserInfo',
    method: 'get',
    params: { token }
  })
}

export function logout() {
  return request({
    url: '/login/loginOut',
    method: 'post'
  })
}
