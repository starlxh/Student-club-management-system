<template>
  <div class="nav-main">

    <span class="trigger trigger-is-hidden" @click.prevent="handleClick">
      <svg-icon icon-class="list" />
    </span>
    <a href="#/" class="logo">
      大学社团
    </a>

    <div id="custom-nav" class="custom-body nav-is-hidden">
      <div class="custom-nav custom-nav-1">
        <div class="custom-nav-ul">
          <ul>
            <li><a href="#/">主页</a></li>
            <li><a href="#/clublist">社团列表</a></li>
            <li><a href="#/myclub">我的社团</a></li>
            <li><a href="#/leave">在线留言</a></li>
            <li><a href="#/usercenter">个人中心</a></li>
            <li v-if="!role"><a href="#/login">登录</a></li>
            <li v-if="role && role !== 'User'"><a href="#/users">后台管理</a></li>
            <div class="nav-box" />
            <div class="ani-is-hidden animation">
              <bgAnimation bg-animation-path="./animation/SunAnimation.json" />
            </div>
          </ul>
        </div>
        <!-- ***** Menu End ***** -->
      </div>
    </div>
  </div>
</template>

<script>
import bgAnimation from '../../qdutils/bg/bg.vue'
export default {
  name: 'Index',
  components: { bgAnimation },
  data() {
    return {
      type: null,
      nav: null,
      video: null,
      role: null
    }
  },
  mounted() {
    this.role = this.$store.getters.roles[0]
    this.nav = document.querySelector('#custom-nav')
  },
  methods: {
    handleClick() {
      if (this.nav.classList.contains('nav-is-hidden')) {
        this.nav.classList.add('move-nav-left')
        this.nav.classList.remove('nav-is-hidden')
      } else {
        this.nav.classList.remove('move-nav-left')
        this.nav.classList.add('move-nav-right')
        setTimeout(() => {
          this.nav.classList.add('nav-is-hidden')
          this.nav.classList.remove('move-nav-right')
        }, 2000)
      }
    }
  }
}
</script>

<style scoped>
.animation {
  position: absolute;
  top: 0px;
  left: 0px;
  height: 420px;
  background-color: rgb(253, 242, 221);
}

</style>
