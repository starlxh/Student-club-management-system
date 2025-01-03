<template>
  <div>
    <div id="meet" class="meet-container" />
  </div>
</template>

<script>
import request from '@/utils/request'

export default {
  name: 'Index',
  data() {
    return {
      userInfo: {},
      meetingInfo: {}
    }
  },
  mounted() {
    this.getUserInfo()
  },
  methods: {
    getUserInfo() {
      request.get('user/getUserInfoById').then((res) => {
        if (res.code === 20000) {
          this.userInfo = res.data
          this.getMeetingInfo()
        } else {
          this.$router.push({ path: '/meet' })
          this.$message.error('核对用户信息失败，请重试！')
        }
      })
    },
    getMeetingInfo() {
      if (this.$route.query.miId) {
        request
          .get('meeting/getMeetingInfoById', {
            params: {
              miId: this.$route.query.miId
            }
          })
          .then((res) => {
            if (res.code === 20000) {
              this.meetingInfo = res.data
              this.loadJitsiScript()
            }
          })
      } else if (this.$route.query.name) {
        request
          .get('meeting/getMeetingInfoByName', {
            params: {
              name: this.$route.query.name
            }
          })
          .then((res) => {
            if (res.code === 20000) {
              this.meetingInfo = res.data
              this.loadJitsiScript()
            }
          })
      } else {
        this.$router.push({ path: '/meet' })
      }
    },
    loadJitsiScript() {
      const script = document.createElement('script')
      script.src = 'https://shantouliu.site/external_api.js' // 确保此地址可访问
      script.async = true

      script.onload = () => {
        // 脚本加载完成后初始化 Jitsi
        this.initializeJitsi()
      }

      script.onerror = () => {
        this.$message({
          message: '加载脚本失败，请刷新重试',
          type: 'warning'
        })
      }

      document.body.appendChild(script)
    },

    initializeJitsi() {
      const domain = 'shantouliu.site'
      const options = {
        roomName: this.meetingInfo.name,
        width: '100%',
        height: '100%',
        parentNode: document.querySelector('#meet'), // 容器 ID
        configOverwrite: {
          disableDeepLinking: true // 禁用移动设备强制下载 App
        },
        userInfo: {
          displayName: this.userInfo.userName
        }
      }

      try {
        const api = new window.JitsiMeetExternalAPI(domain, options)

        api.addEventListener('videoConferenceJoined', () => {
          if (this.meetingInfo.userId !== this.userInfo.userId) {
            if (this.meetingInfo.password) {
              api.executeCommand('password', this.meetingInfo.password)
            }
          }
        })

        api.addEventListener('passwordRequired', () => {
          if (this.meetingInfo.userId !== this.userInfo.userId) {
            const password = prompt('请输入会议密码：')
            if (password) {
              api.executeCommand('password', password)
            } else {
              this.$message.error('密码不能为空，请重新输入！')
            }
          }
        })

        // 监听会议结束事件
        api.addEventListener('videoConferenceLeft', () => {
          this.$router.push({ path: '/meet' })
        })

        // 监听用户加入
        api.addEventListener('participantJoined', (event) => {
          console.log(`新用户加入：${event.id}`)
        })

        // 监听用户离开
        api.addEventListener('participantLeft', (event) => {
          if (this.role === 'User') {
            this.$router.push({ path: '/meet' })
          }
        })
      } catch (error) {
        this.$message.error('初始化会议失败，请检查网络连接!')
      }
    }
  }
}
</script>

<style scoped>
.meet-container {
  position: relative;
  width: 100vw;
  height: 100vh;
}
</style>
