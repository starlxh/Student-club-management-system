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

    <foot footer-class="normal" />
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
@import "../../../assets/css/custom/base.css";
@import "//at.alicdn.com/t/font_2935587_97eo1d0furr.css";

/* -------背景圆点------ */
.sec1_circle1 {
    background: white;
    background: linear-gradient(to right bottom,
            rgba(255, 255, 255, 0.8),
            rgba(255, 255, 255, 0.3));
    height: 20rem;
    width: 20rem;
    position: absolute;
    border-radius: 50%;
    top: 5%;
    right: 15%;
}

.sec1_circle2 {
    background: white;
    background: linear-gradient(to right bottom,
            rgba(255, 255, 255, 0.8),
            rgba(247, 255, 174, 0.3));
    height: 20rem;
    width: 20rem;
    position: absolute;
    border-radius: 50%;
    bottom: 10%;
    left: 5%;
}

.sec1_circle3 {
    background: white;
    background: linear-gradient(to right bottom,
            rgba(248, 202, 202, 0.8),
            rgba(255, 255, 255, 0.3));
    height: 20rem;
    width: 20rem;
    position: absolute;
    border-radius: 50%;
    bottom: -10%;
    right: 35%;
}

/* 第一栏 nav区域*/

/* 内容区域 */
main {
    display: flex;
    justify-content: space-around;
    width: 1200px;
    height: 600px;
    border-radius: 25px;
    background-image: linear-gradient(45deg, rgba(163, 255, 231, 0.6), rgba(255, 193, 179, 0.6));
    position: relative;
    overflow: hidden;
    margin: 0 auto;
    top: calc(50vh - 300px);
    backdrop-filter: blur(0.2rem);
}

.main_left {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    flex: 3;
    height: 600px;
    background-color: rgba(255, 255, 255, 0.5);
    border-radius: 25px;
}

.main_left div {
    width: 100%;
    flex: 1;
    text-align: center;
    transition: 0.5s ease-in-out;
}

.myPhoto {
    flex: 3;
    width: 100%;
    padding: 15px auto;
    text-align: center;
}

.myPhoto a {
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    line-height: 100%;
    color: #426696;
    font-size: 16px;
}

.myPhoto img {
    width: 100px;
    height: 100px;
    border-radius: 100%;
    margin: 10px auto;
}

.myPhoto p {
    margin: 10px auto;
}

.main_left div a {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    line-height: 100%;
    color: #426696;
    font-size: 14px;
    font-weight: 700;
}

.head-container{
    color: #426696;
}

.head-container h3:hover,
.head-container p:hover{
    font-weight: bold;
}

.main_left div:hover {
    background-color: rgba(255, 255, 255, 0.4);
    border-radius: 25px;
}

.main_left div:hover a {
    color: #000;
}

.main_left div:nth-child(2) a p::before {
    content: "\e608";
    font-family: "iconfont";
    font-size: 30px;
    margin-right: 20px;
    color: #2867ce;
}

.main_left div:nth-child(3) a p::before {
    content: "\e614";
    font-family: "iconfont";
    font-size: 30px;
    margin-right: 20px;
    color: #4fffff;
}

.main_left div:nth-child(4) a p::before {
    content: "\e600";
    font-family: "iconfont";
    font-size: 30px;
    margin-right: 20px;
    color: #f44336;
}

.main_left div:nth-child(5) a p::before {
    content: "\e67c";
    font-family: "iconfont";
    font-size: 30px;
    margin-right: 20px;
    color: #93a8ff;
}

/* ------------------------------------------------------ */
.main_right {
    flex: 7;
    height: 600px;
    background-color: rgba(255, 255, 255, 0);
    border-radius: 0 25px 25px 0;
    overflow: hidden;
}

.content {
    float: right;
    width: 98%;
    height: 600px;
    background-color: rgba(255, 255, 255, 0.3);
    border-radius: 25px;
    display: flex;
    flex-direction: column;
    align-items: center;
    text-align: center;
}

.hidden {
    display: none;
}

.content img {
    width: 640px;
    height: 360px;
    border-radius: 25px;
    /* background-size: 50%; */
    margin: 40px auto;
}

.content p {
    color: #426696;
    font-size: 16px;
    font-weight: 700;
    margin: 20px auto 0;
}

.current {
    background-color: rgba(255, 255, 255, 0.7);
    border-radius: 25px;
    /* 添加过度效果 */
    transition: all 0.5s;
}

#content1 {
    display: block;
}

#content2:target {
    display: block;
}

#content3:target {
    display: block;
}

#content4:target {
    display: block;
}

#content5:target {
    display: block;
}

</style>
