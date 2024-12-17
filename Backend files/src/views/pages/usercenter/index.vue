<template>
  <div class="main-view">
    <!--导航-->
    <navs />
    <div style="text-align: center; height: 200px; line-height: 200px;">个人中心</div>
    <foot />
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
export default {
  name: 'Index',
  components: { navs, foot },
  data() {
    return {

    }
  },
  mounted() {
  }
}
</script>

<style scoped>

@import "../../../vendor/bootstrap/css/bootstrap.min.css";
@import "../../../assets/css/templatemo-edu-meeting.css";
</style>
