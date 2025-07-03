<template>
  <div id="jitsi-container" ref="jitsiContainer" />
</template>

<script>
import { encodeWAVFromFloat32 } from '@/utils/encode-wav'
import {
  getMeetingInfoById,
  getMeetingInfoByName,
  getJitsiToken,
  uploadAudio
} from '@/api/meeting'

export default {
  name: 'JitsiWrapper',
  data() {
    return {
      meetingInfo: {},
      jitsiApi: null,
      mediaStream: null,
      audioContext: null,
      processorNode: null,
      isRecording: false,
      silentCounter: 0,
      audioChunks: []
    }
  },
  async mounted() {
    await this.getMeetingInfo()
  },
  beforeUnmount() {
    this.cleanUp()
  },
  async beforeRouteLeave(to, from, next) {
    await this.cleanUp()
    window.location.href = to.fullPath
  },
  methods: {
    getMeetingInfo() {
      if (this.$route.query.miId) {
        getMeetingInfoById(this.$route.query.miId).then((res) => {
          if (res.code === 20000) {
            this.meetingInfo = res.data
            this.loadJitsiScript()
          }
        })
      } else if (this.$route.query.name) {
        getMeetingInfoByName(this.$route.query.name).then((res) => {
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
      script.src = 'https://liudeproject.online/external_api.js'
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

    async initializeJitsi() {
      // 获取token
      let token

      try {
        token = (
          await getJitsiToken(
            this.meetingInfo,
            this.meetingInfo.userId === this.$store.getters.id
          )
        ).data
      } catch (error) {
        console.error('获取token失败:', error)
      }

      if (!token) return

      try {
        // 初始化选项
        const options = {
          roomName: this.meetingInfo.name,
          width: '100%',
          height: '100%',
          parentNode: this.$refs.jitsiContainer,
          userInfo: {
            id: this.$store.getters.id,
            displayName: this.$store.getters.name
          },
          configOverwrite: {
            startWithAudioMuted: false,
            disableModeratorIndicator: false,
            startScreenSharing: false,
            prejoinPageEnabled: false
          },
          jwt: token
        }

        // 初始化API
        this.jitsiApi = new window.JitsiMeetExternalAPI('liudeproject.online', options)

        // 初始化监听器
        this.setupEventListeners()
      } catch (error) {
        this.$message({
          message: '脚本初始化失败，请重试',
          type: 'warning'
        })
        console.error('脚本初始化失败，请重试', error)
      }
    },
    setupEventListeners() {
      if (this.jitsiApi) {
        // 麦克风状态变更（某人开始说话）
        this.jitsiApi.addEventListener(
          'audioMuteStatusChanged',
          this.audioMuteStatusChanged
        )

        // 监听会议结束事件
        this.jitsiApi.addEventListener('videoConferenceLeft', this.exitMeeting)
      }
    },
    audioMuteStatusChanged(event) {
      if (!event.muted) {
        this.setupAudioCapture()
      } else {
        this.stopAudioCapture()
      }
    },
    exitMeeting() {
      console.log('会议结束')
    },
    async setupAudioCapture() {
      try {
        this.audioChunks = []
        this.silentCounter = 0
        this.isRecording = false

        this.mediaStream = await navigator.mediaDevices.getUserMedia({ audio: true })

        this.audioContext = new (window.AudioContext || window.webkitAudioContext)()
        const source = this.audioContext.createMediaStreamSource(this.mediaStream)
        this.processorNode = this.audioContext.createScriptProcessor(4096, 1, 1)

        this.processorNode.onaudioprocess = (event) => {
          const audioData = event.inputBuffer.getChannelData(0)
          this.processAudioData(audioData)
        }

        source.connect(this.processorNode)
        this.processorNode.connect(this.audioContext.destination)
      } catch (error) {
        console.error('Error setting up audio capture:', error)
      }
    },
    processAudioData(audioData) {
      const volume = this.calculateVolume(audioData)
      const audioArray = Array.from(audioData)
      if (volume > 0.03) {
        // 正在说话，累积数据
        this.isRecording = true
        this.silentCounter = 0
        this.audioChunks.push(...audioArray)
      } else if (this.isRecording) {
        // 静音中，但之前在说话：统计静音次数
        this.silentCounter = (this.silentCounter || 0) + 1

        if (this.silentCounter >= 5) {
          this.isRecording = false

          if (this.audioChunks.length > 16000) {
            const floatData = new Float32Array(this.audioChunks)
            const wavBlob = encodeWAVFromFloat32(floatData, 44100)
            const formData = new FormData()
            formData.append('audio', wavBlob, 'audio.wav')
            formData.append('room', this.meetingInfo.name)
            formData.append('userId', this.$store.getters.id)
            formData.append('name', this.$store.getters.name)
            uploadAudio(formData)
            this.audioChunks = []
          }
          this.audioChunks = []
          this.silentCounter = 0
        } else {
          this.audioChunks.push(...audioArray)
        }
      }
    },
    calculateVolume(audioData) {
      let sum = 0.0
      for (let i = 0; i < audioData.length; i++) {
        sum += audioData[i] * audioData[i] // 平方
      }
      const rms = Math.sqrt(sum / audioData.length) // 均方根音量
      return rms
    },
    stopAudioCapture() {
      if (this.processorNode) {
        this.processorNode.disconnect()
        this.processorNode = null
      }
      if (this.audioContext) {
        this.audioContext.close()
        this.audioContext = null
      }
      if (this.mediaStream) {
        this.mediaStream.getTracks().forEach((track) => track.stop())
        this.mediaStream = null
      }
      this.isRecording = false
      this.audioChunks = []
      this.silentCounter = 0
    },
    cleanUp() {
      if (this.jitsiApi) {
        this.jitsiApi.removeEventListener(
          'audioMuteStatusChanged',
          this.audioMuteStatusChanged
        )
        this.jitsiApi.removeEventListener('videoConferenceLeft', this.exitMeeting)
        this.jitsiApi.dispose()
        this.jitsiApi = null
      }
      this.stopAudioCapture()
    }
  }
}
</script>

<style scoped>
#jitsi-container {
  width: 100%;
  height: 500px;
}
</style>
