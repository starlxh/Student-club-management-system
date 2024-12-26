<template>
  <div style="text-align: center; margin-top: 15vh">
    <h1>欢迎</h1>
    <h2 style="margin-bottom: 80px;">{{ $store.getters.roles.includes('Super')? '系统管理员' : '社团管理员' }}</h2>
    <div id="user-info">
      <el-avatar :size="150" :src="userImgUrl" />
      <h3>{{ userInfo.realName }}</h3>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import { getImgUrlHeader } from '@/utils/imagespath'

export default {
  name: 'Welcome',
  data() {
    return {
      userInfo: {},
      userImgUrl: undefined
    }
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    getImgUrlHeader,
    getUserInfo() {
      request.get('user/getUserInfoById').then((res) => {
        if (res.code === 20000) {
          this.userInfo = res.data
          this.userImgUrl = this.getImgUrlHeader() + this.userInfo.image
        }
      })
    }
  }
}
</script>
