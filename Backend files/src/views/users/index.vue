<template>
  <div>
    <h1>社团在线会议Demo</h1>
    <div id="meet" style="height: 500px;" />
  </div>
</template>

<script>
export default {
  mounted() {
    // 动态加载 external_api.js
    const script = document.createElement('script')
    script.src = 'https://shantouliu.site/external_api.js' // 确保此地址可访问
    script.async = true

    script.onload = () => {
      // 脚本加载完成后初始化 Jitsi
      this.initializeJitsi()
    }

    script.onerror = () => {
      console.error('无法加载 external_api.js 脚本')
    }

    document.body.appendChild(script)
  },
  methods: {
    initializeJitsi() {
      // 确保 external_api.js 脚本加载后再调用 JitsiMeetExternalAPI
      const domain = 'shantouliu.site'
      const options = {
        roomName: 'MyFreeMeetingRoom', // 自定义会议室名称
        width: '100%',
        height: 500,
        parentNode: document.querySelector('#meet'),
        configOverwrite: {
          disableDeepLinking: true // 禁用移动设备强制下载 App
        }
      }

      try {
        // Use window.JitsiMeetExternalAPI to reference the global object
        const api = new window.JitsiMeetExternalAPI(domain, options)
        api.addEventListener('videoConferenceJoined', () => {
          console.log('成功加入会议')
        })
      } catch (error) {
        console.error('初始化 JitsiMeetExternalAPI 失败', error)
      }
    }
  }
}
</script>

<style>
#meet {
  border: 1px solid #ddd;
}
</style>
