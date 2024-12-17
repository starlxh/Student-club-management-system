<template>
  <div>
    <!--导航-->
    <navs />

    <!--核心内容start-->
    <section id="top" class="heading-page header-text">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <h6>或许这里就有你心仪的社团</h6>
            <h2>社团列表</h2>
          </div>
        </div>
      </div>
    </section>

    <!--核心内容start-->

    <section id="meetings" class="meetings-page">
      <div class="container">
        <div class="row">

          <div class="col-lg-12">
            <div class="row">
              <div class="col-lg-12">
                <div class="row grid">
                  <div v-for="club in clubList" :key="club.clubId" class="col-lg-4 templatemo-item-col all soon">
                    <div class="meeting-item">
                      <div class="thumb">
                        <!-- <a href="#/teaminfo"><img :src="team.images" width="200px" height="200px" alt=""></a>-->
                        <router-link :to="{path:'/clubinfo',query:{clubId:club.clubId}}"><img :src="club.images" width="200px" height="200px" alt=""></router-link>
                      </div>
                      <div class="down-content">
                        <div class="date">
                          <h6>Nov </h6>
                        </div>
                        <h4>{{ club.clubName }}</h4>
                        <p>社长：{{ club.captainName }}</p>
                      </div>
                    </div>
                  </div>

                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

    </section>
    <br>
    <br>
    <br>
    <!--底部-->
    <foot />
    <!--核心内容end-->

  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import request from '@/utils/request'
export default {
  name: 'Index',
  components: { navs, foot },
  // 初始数据
  data() {
    return {
      clubList: []
    }
  },

  // 获取数据信息
  created() {
    this.getList()
  },

  // 方法列表
  methods: {
    // 获取方法
    getList() {
      request({
        url: 'club/queryClubList',
        method: 'get',
        params: ''
      }).then(res => {
        this.clubList = res.data
      })
    }
  }

}
</script>

<style scoped>

  @import "../../../vendor/bootstrap/css/bootstrap.min.css";
  @import "../../../assets/css/templatemo-edu-meeting.css";
</style>
