<template>
  <div>
    <h1>社团在线会议Demo</h1>
    <div id="meet" style="height: 500px;" />
  </div>
</template>

<script>
export default {
  name: 'Index',
  data() {
    return {
      role: '',  // 假设主持人是通过某种方式来设置的，这里通过 isModerator 来区分
    }
  },
  mounted() {
    this.role = this.$store.getters.roles[0]
    this.loadJitsiScript()
  },
  methods: {
    loadJitsiScript() {
      const script = document.createElement('script')
      script.src = 'https://shantouliu.site/external_api.js' // 确保此地址可访问
      script.async = true

      script.onload = () => {
        // 脚本加载完成后初始化 Jitsi
        this.initializeJitsi()
      }

      script.onerror = () => {
        console.error('无法加载 external_api.js 脚本')
        alert('加载 Jitsi Meet 脚本失败，无法初始化会议')
      }

      document.body.appendChild(script)
    },

    initializeJitsi() {
      const domain = 'shantouliu.site' // Jitsi Meet 服务器的域名
      const options = {
        roomName: 'MyFreeMeetingRoom', // 自定义会议室名称
        width: '100%',
        height: 500,
        parentNode: document.querySelector('#meet'), // 容器 ID
        configOverwrite: {
          disableDeepLinking: true // 禁用移动设备强制下载 App
        },
      }

      try {
        // 使用 window.JitsiMeetExternalAPI 来初始化 Jitsi API
        const api = new window.JitsiMeetExternalAPI(domain, options)

        // 监听会议加入事件
        api.addEventListener('videoConferenceJoined', () => {
          console.log('会议已成功加入')

          // 判断是否为主持人，设置主持人权限
          if (this.role !== 'User') {
            console.log('当前用户是主持人')
          } else {
            console.log('当前用户是普通参与者')
          }

          // 当会议要求密码时，弹出提示让用户输入密码
          api.addEventListener('passwordRequired', () => {
            const password = prompt('请输入会议密码：')
            if (password) {
              api.executeCommand('password', password)
            }
          })
        })

        // 监听会议结束事件
        api.addEventListener('videoConferenceLeft', () => {
            this.$router.push({ path: '/meeting'})
        })

        api.addEventListener('participantJoined', (event) => {
          console.log(`新用户加入：${this.role}`)
        })

        api.addEventListener('participantLeft', (event) => {
          if(this.role === 'User'){
            this.$router.push({ path: '/meeting'})
          }
        })
        
      } catch (error) {
        console.error('初始化 JitsiMeetExternalAPI 失败', error)
        alert('初始化 Jitsi Meet 失败，请检查网络连接或服务器配置')
      }
    }
  }
}
</script>

<style scoped>
#meet {
  border: 1px solid #ddd;
}
</style>
