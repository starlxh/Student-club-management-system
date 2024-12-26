<template>
  <div ref="bgContainer" :class="classBind" />
</template>

<script>
import lottie from 'lottie-web'

export default {
  name: 'BgAnimation',
  props: {
    bgAnimationPath: {
      type: String,
      required: true
    },
    speed: {
      type: Number,
      required: true
    },
    classBind: {
      type: String,
      required: true
    }
  },
  mounted() {
    // 初始化 Lottie 动画
    const animation = lottie.loadAnimation({
      container: this.$refs.bgContainer, // DOM 节点
      renderer: 'svg', // 渲染类型
      loop: true, // 是否循环
      autoplay: true, // 是否自动播放
      path: this.bgAnimationPath // 动画文件的路径
    })
    animation.setSpeed(this.speed)
  }
}
</script>

<style scoped>
.animation {
  width: 100vw;
  height: 100vh;
  z-index: -1;
}

.clubList{
  width: 100%;
  height: 100%;
  z-index: -10;

}
</style>
