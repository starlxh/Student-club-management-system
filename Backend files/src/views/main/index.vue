<template>
  <div class="main-view">
    <div class="animation">
      <bgAnimation bg-animation-path="./animation/BgAnimation.json" />
    </div>
    <div class="main-box">
      <navs />
      <section id="top" class="section main-banner" data-section="section1">
        <video id="bg-video" class="styled-video" autoplay muted loop>
          <source src="@/assets/images/club-video.mp4" type="video/mp4">
        </video>

        <div class="header-text">
          <div class="container">
            <div class="row">
              <div class="col-lg-12">
                <div class="caption">
                  <h6>你好学生</h6>
                  <h2>欢迎来到社团招新</h2>
                  <p>体验大学丰富生活，可以来社团</p>
                  <div class="main-button-red">
                    <div><a href="#/clublist">加入我们！</a></div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </section>
      <foot />
    </div>
  </div>
</template>

<script>
import bgAnimation from '../qdutils/bg/bg.vue'
import navs from '../qdutils/navs/index'
import foot from '../qdutils/footer/index'
export default {
  name: 'Index',
  components: { navs, foot, bgAnimation }
}
</script>

<style scoped>
@import "../../vendor/bootstrap/css/bootstrap.min.css";
@import "../../assets/css/templatemo-edu-meeting.css";

.main-view{
  position: relative;
}

.animation{
  position: absolute;
  z-index: 0;
  margin-top: -40px;
}

.animation-footer{
  position: absolute;
  z-index: 0;
  height: 300px;
}

</style>
