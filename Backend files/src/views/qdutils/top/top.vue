<template>
  <div>

    <!-- Sub Header -->
    <div class="sub-header">
      <div class="container">
        <div class="row">
          <div class="col-lg-8 col-sm-8">
            <div class="left-content">
              <p>东方大学社团</p>
            </div>
          </div>
          <div class="col-lg-4 col-sm-4">
            <div class="right-icons">
              <ul>
                <li> <router-link :to="{path:'/login'}"> 登录</router-link> </li>
                <li> <a @click="logout()">退出</a></li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

</template>

<script>

export default {
  name: 'Top',
  methods: {
    logout() {
      debugger
      // 如果登录了
      if (localStorage.getItem('type') != null) {
        // 调用退出功能
        this.$store.dispatch('user/logout')
        // 跳转到主页功能
        this.$router.push({ path: '/indexs' })
      } else {
        this.$alert('未登录，不能退出')
      }
    }
  }
}
</script>

<style scoped>

</style>
