<template>
  <router-link :to="backUrl">
    <div
      ref="backBtnContainer"
      :class="btnClass"
      @mouseover="handleMouseOver"
      @mouseleave="handleMouseLeave"
    />
  </router-link>
</template>

<script>
import lottie from 'lottie-web'

export default {
  name: 'BackBtnAnimation',
  props: {
    backBtnAnimationPath: {
      type: String,
      required: true
    },
    backUrl: {
      type: String,
      required: true
    },
    btnClass: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      animation: null
    }
  },
  mounted() {
    this.animation = lottie.loadAnimation({
      container: this.$refs.backBtnContainer,
      renderer: 'svg',
      loop: false,
      autoplay: false,
      path: this.backBtnAnimationPath
    })
  },
  methods: {
    handleMouseOver() {
      this.animation.play()
    },
    handleMouseLeave() {
      this.animation.stop()
    }
  }
}
</script>

<style scoped>

.login-bakck-btn {
  display: block;
  width: 400px;
  position: absolute;
  top: 2vh;
  left: calc(50vw - 200px)
}
</style>
