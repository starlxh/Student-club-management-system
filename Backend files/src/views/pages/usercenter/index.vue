<template>
  <div>
    <!--导航-->
    <navs />
    <div>
      <!-- <h3>第一屏</h3> -->
      <div id="section1">
        <!-- 背景圆点 -->
        <div class="sec1_circle1" />
        <div class="sec1_circle2" />
        <div class="sec1_circle3" />
        <!-- 内容区域 -->
        <main>
          <nav class="main_left">
            <div class="myPhoto current">
              <div class="head-container">
                <img src="@/assets/images/person/myphoto.jpg" alt="">
                <h3>Weirry Lu</h3>
                <p>唯有落叶🍂知湫意</p>
              </div>
            </div>
            <div>
              <a href="#content1">
                <p />
                <span>首页</span>
              </a>
            </div>
            <div>
              <a href="#content2">
                <p />
                <span>联系</span>
              </a>
            </div>
            <div>
              <a href="#content3">
                <p />
                <span>信息</span>
              </a>
            </div>
            <div>
              <a href="#content4">
                <p />
                <span>更多</span>
              </a>
            </div>
          </nav>
          <section class="main_right">
            <div id="content1" class="content hidden">
              <img src="@/assets/images/person/1-1.jpg">
              <p>在最黑暗的那段人生，是我自己把我自己拉出深渊</p>
              <p>没有那个人，我就做那个人——中岛美嘉</p>
            </div>

            <div id="content2" class="content hidden">
              <img src="@/assets/images/person/1-2.jpg">
              <p>生命是有关光的，在我熄灭以前</p>
              <p>能够照亮你一点，就是我所有能做的的了</p>
              <p>我爱你，你要记得我——《云边有个小卖部》</p>
            </div>

            <div id="content3" class="content hidden">
              <img src="@/assets/images/person/1-3.png">
              <p>带我回家</p>
              <p>待我回家</p>
            </div>

            <div id="content4" class="content hidden">
              <img src="@/assets/images/person/1-4.jpg">
              <p>去年夏天，我遇到一个女孩，他的出现让我放下了从前</p>
              <p>我真的很喜欢她，虽然和她没有以后了</p>
              <p>但依旧祝她岁岁平安</p>
            </div>
          </section>
        </main>
      </div>

    </div>

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

@import "../../../assets/css/templatemo-edu-meeting.css";
@import "../../../assets/css/person/base.css";
@import "../../../assets/css/person/style-1.css";
@import "//at.alicdn.com/t/font_2935587_97eo1d0furr.css";

</style>
