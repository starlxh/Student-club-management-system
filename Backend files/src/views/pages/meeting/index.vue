<template>
  <div style="background-color: #ff7575;">
    <div class="nav-box">
      <navs />
    </div>
    <span class="visible-nav" />
    <div class="search-container">
      <div class="shell">
        <input v-model="meetingUrl" type="text" placeholder="输入会议名加入会议">
        <a :href="'#/'+meetingUrl">
          <i class="fa fa-hand-o-right" />
          <i class="fa fa-search" />
        </a>
      </div>
    </div>
    <foot footer-class="club-meeting"/>
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import 'font-awesome/css/font-awesome.min.css'

export default {
  name: 'Index',
  components: { navs, foot },
  data() {
    return {
      meetingUrl: ''
    }
  }
}
</script>

<style scoped>
@import "../../../assets/css/templatemo-edu-meeting.css";

.nav-box{
  position: absolute;
  z-index: 999;
  top: 0px;
}

.search-container,
.search-container * {
  outline: none;
  padding: 0;
  margin: 0;
  text-decoration: none;
}

.search-container {
  position: absolute;
  top: 0px;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #ffdcdc;
}

.shell {
  position: relative;
  width: 1000px;
  padding: 33px;
  background-color: #ff7575;
  border-radius: 20px;
  box-shadow: 0 10px 50px #ff7575, 0 0 0 20px #fff;
}

.shell input {
  width: 80%;
  height: 100px;
  color: #fff;
  font: 300 80px '优设标题黑';
  border: 0;
  background-color: transparent;
}

.shell input::placeholder {
  color: #8f36367e;
}

.shell a {
  display: flex;
  font-size: 120px;
  position: absolute;
  right: 60px;
  top: 10px;
  color: #fff;
  width: 250px;
  height: 150px;
}

.shell a .fa {
  margin: 10px 28px;
  transition: .3s;
}

.shell a .fa-search {
  transform: translateX(-80px);
  opacity: 1;
}

.shell a .fa-hand-o-right {
  transform: translateX(-100px);
  opacity: 0;
}

.shell a:hover .fa-search {
  transform: translateX(0);
  opacity: 0;
}

.shell a:hover .fa-hand-o-right {
  transform: translateX(110px);
  opacity: 1;
}

.shell a::before {
  content: 'GO !';
  position: absolute;
  display: block;
  font-size: 50px;
  background-color: #ff7575;
  padding: 5px 30px;
  top: 50px;
  right: -25px;
  border-radius: 10px;
  transition: .3s;
  opacity: 0;
  animation: box 1s infinite ease;
}

.shell a:hover::before {
  top: -115px;
  opacity: 1;
}

@keyframes box {
  0% {
    transform: rotate(0deg);
  }

  33% {
    transform: rotate(8deg);
  }

  66% {
    transform: rotate(-8deg);
  }

  100% {
    transform: rotate(0deg);
  }
}

.footer-blank{
  height: 110vh;
  background-color: #ffdcdc;
}
</style>
