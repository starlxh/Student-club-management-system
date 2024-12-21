<template>
  <div ref="snailContainer" class="animation" />
</template>

<script>
import lottie from 'lottie-web'

export default {
  name: 'SnailAnimation',
  props: {
    snailAnimationPath: {
      type: String,
      required: true
    }
  },
  mounted() {
    // 初始化 Lottie 动画
    lottie.loadAnimation({
      container: this.$refs.snailContainer, // DOM 节点
      renderer: 'svg', // 渲染类型
      loop: true, // 是否循环
      autoplay: true, // 是否自动播放
      path: this.snailAnimationPath // 动画文件的路径
    })
  }
}
</script>

<style scoped>
    .animation{
        width: 100%;
        height: auto;
    }
</style>
