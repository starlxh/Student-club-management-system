<template>
  <div ref="sunContainer" class="animation" />
</template>

<script>
import lottie from 'lottie-web'

export default {
  name: 'SunAnimation',
  props: {
    sunAnimationPath: {
      type: String,
      required: true
    }
  },
  mounted() {
    // 初始化 Lottie 动画
    lottie.loadAnimation({
      container: this.$refs.sunContainer, // DOM 节点
      renderer: 'svg', // 渲染类型
      loop: true, // 是否循环
      autoplay: true, // 是否自动播放
      path: this.sunAnimationPath // 动画文件的路径
    })
  }
}
</script>

  <style scoped>
  </style>
