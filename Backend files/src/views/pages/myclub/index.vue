<template>
  <div>
    <!--导航-->
    <navs />
    <div class="body">
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
                <img v-if="userInfo.image" :src="getImgUrlHeader() + userInfo.image">
                <img v-else src="@/assets/images/person/myphoto.jpg">
                <p class="hidden-mobie">{{ userInfo.userName }}</p>
              </div>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-home" />
                <span>我加入的社团</span>
              </button>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-tachometer" />
                <span>我管理的社团</span>
              </button>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-envelope-open-o" />
                <span>申请入团记录</span>
              </button>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-group" />
                <span>我参加的活动</span>
              </button>
            </div>
          </nav>
          <section class="main_right">
            <div class="content-box">
              <section class="header">
                <h1>我加入的社团</h1>
                <div class="input-group">
                  <input
                    type="search"
                    placeholder="输入社团名筛选"
                    class="search-input"
                  >
                </div>
              </section>
              <section class="table-shell">
                <table class="styled-table">
                  <thead>
                    <tr>
                      <th>社团名称</th>
                      <th>社团类型</th>
                      <th>创建时间</th>
                      <th>状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in tempjoinedClubList" :key="item.clubId">
                      <td>{{ item.clubName }}</td>
                      <td>{{ item.categoryName }}</td>
                      <td>{{ item.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}</td>
                      <td>
                        <span v-if="item.status == 1" style="color: #4caf50">活跃中</span>
                        <span
                          v-else-if="item.status == 0"
                          style="color: #f44336"
                        >封禁中</span>
                      </td>
                      <td>
                        <button class="button" @click.prevent="toClubInfo(item.clubId)">
                          详情
                        </button>
                      </td>
                    </tr>
                    <button v-if="joinedClubList == 0" class="empty-words-club">
                      <span>+</span>
                      前去加入社团
                    </button>
                  </tbody>
                </table>
              </section>
            </div>

            <div class="content-box hidden">
              <section class="header">
                <h1>我管理的社团</h1>
                <div class="input-group">
                  <input
                    type="search"
                    placeholder="输入社团名筛选"
                    class="search-input"
                  >
                </div>
              </section>
              <section class="table-shell">
                <table class="styled-table">
                  <thead>
                    <tr>
                      <th>社团名称</th>
                      <th>社团类型</th>
                      <th>创建时间</th>
                      <th>状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in tempManagedClubList" :key="item.clubId">
                      <td>{{ item.clubName }}</td>
                      <td>{{ item.categoryName }}</td>
                      <td>{{ item.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}</td>
                      <td>
                        <span v-if="item.status == 0" style="color: #4caf50">活跃中</span>
                        <span
                          v-else-if="item.status == 1"
                          style="color: #f44336"
                        >封禁中</span>
                      </td>
                      <td>
                        <button class="button" @click.prevent="toClubInfo(item.clubId)">
                          详情
                        </button>
                      </td>
                    </tr>
                    <button v-if="userInfo.type == 0" class="empty-words-club">
                      <span>+</span>
                      请求成为社团管理员
                    </button>
                    <button v-if="managedClubList" class="empty-words-club">
                      <span>+</span>
                      去创建一个社团
                    </button>
                  </tbody>
                </table>
              </section>
            </div>

            <div class="content-box hidden">
              <section class="header">
                <h1>申请入团记录</h1>
                <div class="input-group">
                  <input
                    type="search"
                    placeholder="输入社团名筛选"
                    class="search-input"
                  >
                </div>
              </section>
              <section class="table-shell">
                <table class="styled-table">
                  <thead>
                    <tr>
                      <th>社团名称</th>
                      <th>申请内容</th>
                      <th>申请时间</th>
                      <th>状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in tempApplyInfoList" :key="item.applyInfoId">
                      <td>{{ item.club.clubName }}</td>
                      <td class="table-content">{{ item.content }}</td>
                      <td>{{ item.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}</td>
                      <td>
                        <span v-if="item.status == 0" style="color: orange">审核中</span>
                        <span
                          v-else-if="item.status == 1"
                          style="color: #4caf50"
                        >审核通过</span>
                        <span
                          v-else-if="item.status == 2"
                          style="color: #f44336"
                        >被拒绝</span>
                      </td>
                      <td>
                        <button class="button" @click.prevent="deleteApplyInfo(item)">
                          删除
                        </button>
                      </td>
                    </tr>
                    <button v-if="applyInfoList == 0" class="empty-words-club">
                      <span>+</span>
                      前去加入社团
                    </button>
                  </tbody>
                </table>
              </section>
            </div>

            <div class="content-box hidden">
              <section class="header">
                <h1>我参加的活动</h1>
                <div class="input-group">
                  <input
                    type="search"
                    placeholder="输入社团名筛选"
                    class="search-input"
                  >
                </div>
              </section>
              <section class="table-shell">
                <table class="styled-table">
                  <thead>
                    <tr>
                      <th>社团名称</th>
                      <th>活动名称</th>
                      <th>活动地址</th>
                      <th>活动时间</th>
                      <th>活动状态</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in tempActivityList" :key="item.activityId">
                      <td>{{ item.clubName }}</td>
                      <td>{{ item.name }}</td>
                      <td>{{ item.acAddress }}</td>
                      <td>{{ item.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}</td>
                      <td>
                        <span v-if="item.status == 1" style="color: #4caf50">进行中</span>
                        <span
                          v-else-if="item.status == 0"
                          style="color: #f44336"
                        >已结束</span>
                      </td>
                      <td>
                        <button
                          class="button"
                          @click.prevent="toActivity(item.activityId)"
                        >
                          详情
                        </button>
                      </td>
                    </tr>
                    <div v-if="activityList == 0" class="empty-words-activity" />
                  </tbody>
                </table>
              </section>
            </div>
          </section>
        </main>
      </div>
    </div>

    <foot footer-class="user-center" />
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import 'font-awesome/css/font-awesome.min.css'
import request from '@/utils/request'
import { getImgUrlHeader } from '@/utils/imagespath'
import { parseTime } from '@/utils'
export default {
  name: 'Index',
  components: { navs, foot },
  data() {
    return {
      curIndex: null,
      userInfo: {},
      joinedClubList: null,
      managedClubList: null,
      applyInfoList: null,
      activityList: null,
      tempjoinedClubList: null,
      tempManagedClubList: null,
      tempApplyInfoList: null,
      tempActivityList: null,
      searchContent: ''
    }
  },
  mounted() {
    const contentBoxs = document.querySelectorAll('.content-box')
    const contentBtns = document.querySelectorAll('.content-btn')

    const changeDisplay = (index) => {
      for (let i = 0; i < contentBoxs.length; i++) {
        if (i === index) {
          contentBoxs[i].classList.remove('hidden')
          if (i < this.curIndex) {
            contentBoxs[i].classList.add('content-move-down-2')
            setTimeout(() => {
              contentBoxs[i].classList.remove('content-move-down-2')
            }, 2000)
          } else if (i > this.curIndex) {
            contentBoxs[i].classList.add('content-move-up-2')
            setTimeout(() => {
              contentBoxs[i].classList.remove('content-move-up-2')
            }, 2000)
          }
          this.curIndex = index
        } else {
          if (!contentBoxs[i].classList.contains('hidden')) {
            if (i < index) {
              contentBoxs[i].classList.add('content-move-up')
              setTimeout(() => {
                contentBoxs[i].classList.remove('content-move-up')
                contentBoxs[i].classList.add('hidden')
              }, 1500)
            } else if (i > index) {
              contentBoxs[i].classList.add('content-move-down')
              setTimeout(() => {
                contentBoxs[i].classList.remove('content-move-down')
                contentBoxs[i].classList.add('hidden')
              }, 1500)
            }
          }
        }
      }
    }
    for (let i = 0; i < contentBtns.length; i++) {
      contentBtns[i].addEventListener('click', () => changeDisplay(i))
    }

    const searchInputs = document.querySelectorAll('.search-input')
    searchInputs[0].addEventListener('input', () => {
      this.searchContent = searchInputs[0].value
      this.filteredItems(0, this.joinedClubList)
    })

    searchInputs[1].addEventListener('input', () => {
      this.searchContent = searchInputs[1].value
      this.filteredItems(1, this.managedClubList)
    })

    searchInputs[2].addEventListener('input', () => {
      this.searchContent = searchInputs[2].value
      this.filteredItems(2, this.applyInfoList)
    })

    searchInputs[3].addEventListener('input', () => {
      this.searchContent = searchInputs[3].value
      this.filteredItems(3, this.activityList)
    })
  },
  created() {
    this.getUserInfo()
    this.getJoinedClubList()
    this.getManagedClubList()
    this.getApplyInfoList()
    this.getActivityList()
  },
  methods: {
    parseTime,
    getImgUrlHeader,
    getUserInfo() {
      request.get('user/getUserInfoById').then((res) => {
        if (res.code === 20000) {
          this.userInfo = res.data
        }
      })
    },
    getJoinedClubList() {
      request.get('club/queryJoinedClubListByUserId').then((res) => {
        if (res.code === 20000) {
          this.joinedClubList = res.data
          this.tempjoinedClubList = this.joinedClubList
        }
      })
    },
    getManagedClubList() {
      request.get('club/queryManagedClubListByUserId').then((res) => {
        if (res.code === 20000) {
          this.managedClubList = res.data
          this.tempManagedClubList = this.managedClubList
        }
      })
    },
    getApplyInfoList() {
      request.get('apply/getApplyInfoListByUserId').then((res) => {
        if (res.code === 20000) {
          this.applyInfoList = res.data
          this.tempApplyInfoList = this.applyInfoList
        }
      })
    },
    getActivityList() {
      request.get('activity/queryActivityListByUserId').then((res) => {
        if (res.code === 20000) {
          this.activityList = res.data
          this.tempActivityList = this.activityList
        }
      })
    },
    filteredItems(index, list) {
      if (index === 0) {
        this.tempjoinedClubList = list
        this.tempjoinedClubList = this.tempjoinedClubList.filter(
          (item) => item.clubName.toLowerCase().includes(this.searchContent.toLowerCase()) // 根据 name 筛选
        )
      } else if (index === 1) {
        this.tempManagedClubList = list
        this.tempManagedClubList = this.tempManagedClubList.filter(
          (item) => item.clubName.toLowerCase().includes(this.searchContent.toLowerCase()) // 根据 name 筛选
        )
      } else if (index === 2) {
        this.tempApplyInfoList = list
        this.tempApplyInfoList = this.tempApplyInfoList.filter(
          (item) =>
            item.club.clubName.toLowerCase().includes(this.searchContent.toLowerCase()) // 根据 name 筛选
        )
      } else if (index === 3) {
        this.tempActivityList = list
        this.tempActivityList = this.tempActivityList.filter(
          (item) => item.clubName.toLowerCase().includes(this.searchContent.toLowerCase()) // 根据 name 筛选
        )
      }
    },
    toActivity(id) {
      this.$router.push({
        path: '/frontactivity',
        query: {
          activityId: id
        }
      })
    },
    toClubInfo(id) {
      this.$router.push({
        path: '/clubinfo',
        query: {
          clubId: id
        }
      })
    },
    deleteApplyInfo(item) {
      this.$confirm(`该申请入团记录将被永久删除, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          if (item.status === 0) {
            this.$message.error(`申请还在审核中，删除失败！`)
            return
          }
          request
            .delete('apply/deleteById', {
              params: {
                applyInfoId: item.id
              }
            })
            .then(() => {
              this.$message({
                message: '删除成功!',
                type: 'success'
              })
            })
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
    }
  }
}
</script>

<style scoped>
@import "../../../assets/css/templatemo-edu-meeting.css";
@import "../../../assets/css/custom/base.css";

/* -------背景圆点------ */
.sec1_circle1 {
  background: white;
  background: linear-gradient(
    to right bottom,
    rgba(255, 255, 255, 0.8),
    rgba(255, 255, 255, 0.3)
  );
  height: 20rem;
  width: 20rem;
  position: absolute;
  border-radius: 50%;
  top: 5%;
  right: 15%;
}

.sec1_circle2 {
  background: white;
  background: linear-gradient(
    to right bottom,
    rgba(255, 255, 255, 0.8),
    rgba(247, 255, 174, 0.3)
  );
  height: 20rem;
  width: 20rem;
  position: absolute;
  border-radius: 50%;
  bottom: 30%;
  left: 5%;
}

.sec1_circle3 {
  background: white;
  background: linear-gradient(
    to right bottom,
    rgba(248, 202, 202, 0.8),
    rgba(255, 255, 255, 0.3)
  );
  height: 20rem;
  width: 20rem;
  position: absolute;
  border-radius: 50%;
  bottom: 20%;
  right: 35%;
}

/* 第一栏 nav区域*/

/* 内容区域 */
.body {
  height: 100vh;
  background-image: linear-gradient(
    45deg,
    rgba(163, 255, 231, 0.6),
    rgba(255, 193, 179, 0.6)
  );
}

main {
  top: 80px;
  display: flex;
  justify-content: space-around;
  width: 90vw;
  height: 90vh;
  margin: 0 auto;
  border-radius: 25px;
  background-color: transparent;
  position: relative;
  overflow: hidden;
  backdrop-filter: blur(0.2rem);
}

.main_left {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-left: 5vw;
  width: 30%;
  height: 78vh;
  background-color: rgba(255, 255, 255, 0.5);
  border-radius: 25px;
}

.main_left div {
  width: 100%;
  height: calc(78vh / 4 * 3 / 4);
  text-align: center;
  transition: 0.5s ease-in-out;
}

.main_left div:nth-child(1) {
  height: calc(78vh / 4);
}

.myPhoto {
  width: 100%;
  height: 20vh;
  padding: 15px auto;
  text-align: center;
  position: relative;
}

.myPhoto img {
  width: 25%;
  height: auto;
  border-radius: 100%;
  position: relative;
  top: 25%;
}

.myPhoto p {
  margin: 0 auto;
  position: relative;
  top: 25%;
}

.main_left div button {
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 100%;
  line-height: 100%;
  color: #426696;
  font-size: 14px;
  font-weight: 700;
  background-color: transparent;
}

.head-container {
  color: #426696;
}

.head-container p:hover {
  font-weight: bold;
}

.main_left > div:hover {
  background-color: rgba(215, 245, 233, 1);
}

.main_left:hover {
  transition: 0.5s;
  transform: scale(1.01);
}

.main_left div:hover button {
  color: #6c7ae0;
}

.main_left div button:hover i {
  animation: box 0.8s infinite ease;
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

@keyframes moveUp {
  0% {
    transform: translateY(0) scaleX(1);
    opacity: 1;
  }

  100% {
    transform: translateY(-78vh) scaleX(0.5);
    opacity: 0;
  }
}

@keyframes moveUp2 {
  0% {
    transform: translateY(78vh) scaleX(0.5);
    opacity: 0;
  }

  100% {
    transform: translateY(0) scaleX(1);
    opacity: 1;
  }
}

@keyframes moveDown {
  0% {
    transform: translateY(0) scaleX(1);
    opacity: 1;
  }

  100% {
    transform: translateY(78vh) scaleX(0.5);
    opacity: 0;
  }
}

@keyframes moveDown2 {
  0% {
    transform: translateY(-78vh) scaleX(0.5);
    opacity: 0;
  }

  100% {
    transform: translateY(0) scalex(1);
    opacity: 1;
  }
}

.content-move-up {
  animation: moveUp 1.5s ease;
}

.content-move-up-2 {
  animation: moveUp2 2s ease;
}

.content-move-down {
  animation: moveDown 1.5s ease;
}

.content-move-down-2 {
  animation: moveDown2 2s ease;
}

.main_left div:nth-child(2) button i {
  font-size: 30px;
  margin-right: 20px;
  color: #2867ce;
}

.main_left div:nth-child(3) button i {
  font-size: 30px;
  margin-right: 20px;
  color: #4fffff;
}

.main_left div:nth-child(4) button i {
  font-size: 30px;
  margin-right: 20px;
  color: #f44336;
}

.main_left div:nth-child(5) button i {
  font-size: 30px;
  margin-right: 20px;
  color: #93a8ff;
}

/* ------------------------------------------------------ */
.main_right {
  padding-left: 2%;
  width: 70%;
  height: 78vh;
  background-color: rgba(255, 255, 255, 0);
  border-radius: 0 25px 25px 0;
}

.content-box {
  position: absolute;
  top: 0px;
  width: calc(80vw * 0.7 * 0.98);
  height: 78vh;
  background-color: rgba(255, 255, 255, 0.3);
  border-radius: 25px;
  box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  display: block;
}

.hidden {
  display: none;
}

.current {
  background-color: rgba(255, 255, 255, 0.7);
  border-radius: 25px;
  transition: all 0.5s;
}

.container {
  display: grid;
  grid-template-columns: repeat(1, 1fr);
  gap: 30px;
  margin: 0 auto;
  padding: 10px;
}

.item {
  height: calc((78vh - 30px * 3 - 20px) / 4);
  background: #ffffff;
  border-radius: 15px;
  box-shadow: 0 6px 20px rgba(0, 0, 0, 0.15);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.main_right .content-box:nth-child(1) .item {
  height: calc((78vh - 30px * 4 - 20px) / 5);
}

.item:hover {
  transform: translateY(-10px);
  box-shadow: 0 10px 25px rgba(0, 0, 0, 0.2);
}

.tag {
  background-color: rgb(111, 69, 239);
  color: #ffffff;
  font-size: 14px;
  height: 40px;
  font-weight: bold;
  text-align: center;
  padding: 10px 0;
  text-transform: uppercase;
  letter-spacing: 1px;
}

.content {
  position: relative;
  height: calc(100% - 40px);
  display: flex;
  align-items: right;
  flex-direction: column;
  position: relative;
}

.label {
  position: absolute;
  width: calc(80vw * 0.7 * 0.98);
  height: 50%;
  line-height: calc(((78vh - 30px * 4 - 20px) / 5 - 40px) * 0.5);
  top: 25%;
  text-align: center;
  font-size: 18px;
  font-weight: 600;
  color: #555;
}

.jelly-btn {
  position: relative;
  top: 25%;
  left: calc(100% - 18%);
  width: 15%;
  height: 50%;
  background: rgb(111, 69, 239);
  color: #fff;
  border: none;
  border-radius: 25px;
  padding: 10px 20px;
  font-size: 14px;
  font-weight: bold;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
}

.main_right .content-box:nth-last-child(1) .jelly-btn {
  left: 35%;
  width: 30%;
}

.jelly-btn:hover {
  transform: scale(1.1) rotate(-2deg);
  box-shadow: 0 8px 15px rgba(0, 0, 0, 0.25);
}

.jelly-btn:active {
  transform: scale(0.95);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
}

/* 表格 */
.table-shell {
  width: 95%;
  background-color: rgba(255, 255, 255, 0.479);
  margin: 8px auto;
  border-radius: 10px;
  border: 2px solid #6c7ae0;
  overflow: auto;
  overflow: overlay;
}

.table-shell::-webkit-scrollbar-thumb {
  background: rgba(115, 119, 223, 0.5);
  /* 滚动条颜色，带透明度 */
  border-top-right-radius: 4px;
  border-bottom-right-radius: 4px;
}

.table-shell::-webkit-scrollbar-thumb:hover {
  background: rgba(115, 119, 223, 0.8);
  /* 悬停时颜色更深 */
}

.table-shell::-webkit-scrollbar-track {
  background: rgba(108, 122, 224, 0.2);
  /* 滑轨颜色，带透明度 */
  border-top-right-radius: 2px;
  border-bottom-right-radius: 2px;
}

.header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h1 {
  color: #6c7ae0;
}

.header .input-group {
  background-color: rgba(115, 119, 223, 0.2);
  display: flex;
  justify-content: center;
  align-items: center;
  transition: 0.2s;
}

.header .input-group input {
  width: 100%;
  background-color: transparent;
  border: none;
  outline: none;
}

.styled-table {
  width: 100%;
  border-collapse: collapse;
  color: #333;
}

.styled-table thead tr {
  background-color: #6c7ae0;
  color: #fff;
  text-align: center;
}

.styled-table tbody {
  text-align: center;
}

.styled-table tbody tr:nth-child(even) {
  background-color: #f3f3f3;
}

.styled-table tbody tr:nth-child(odd):hover {
  background-color: rgba(203, 231, 169, 0.4);
  transition: 0.3s;
}

.styled-table tbody tr:nth-child(even):hover {
  background-color: rgba(127, 188, 234, 0.4);
  transition: 0.3s;
}

.button {
  display: inline-block;
  border-radius: 4px;
  background-color: #6c7ae0;
  color: #ffffff;
  text-decoration: none;
  text-align: center;
  cursor: pointer;
}

.styled-table tbody tr:nth-child(odd) .button:hover {
  background-color: #7abdeb;
  transition: 0.3s;
}

.styled-table tbody tr:nth-child(even) .button:hover {
  background-color: #c0e294cb;
  transition: 0.3s;
}

@keyframes box-scale {
  0% {
    transform: rotate(0deg) scale(1);
    opacity: 0;
  }

  10% {
    transform: rotate(-5deg) scale(1.1);
    opacity: 0.8;
  }

  50% {
    transform: rotate(0deg) scale(1.5);
    opacity: 1;
  }

  90% {
    transform: rotate(5deg) scale(1.1);
    opacity: 0.8;
  }

  100% {
    transform: rotate(0deg) scale(1);
    opacity: 0;
  }
}

.empty-words-club {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  padding: 12px 24px;
  font-size: 16px;
  font-weight: bold;
  color: #ffffff;
  background-color: rgb(115, 119, 223);
  border: none;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
  position: absolute;
}

.empty-words-club {
  width: 220px;
  height: 48px;
  left: calc(50% - 110px);
  top: calc(60% - 24px);
}

.empty-words-club span {
  font-size: 20px;
  margin-right: 8px;
  transition: transform 0.3s ease-in-out;
}

.empty-words-club:hover {
  background-color: rgb(115, 119, 223);
  box-shadow: 0 6px 10px rgba(0, 0, 0, 0.2);
  transform: scale(1.05);
}

.empty-words-club:hover span {
  transform: rotate(90deg);
}

.empty-words-club:active {
  transform: scale(0.95);
  box-shadow: 0 3px 6px rgba(0, 0, 0, 0.2);
}

.empty-words-activity::before {
  content: "好像还没有什么社团活动发布!";
  position: absolute;
  display: block;
  font-size: 12px;
  background-color: transparent;
  color: #6c7ae0;
  padding: 5px;
  width: 300px;
  top: 50%;
  right: calc(50% - 150px);
  border-radius: 10px;
  animation: box-scale 10s infinite ease;
}

.header {
  width: 100%;
  height: 10%;
  padding: 0 30px;
}

.header .input-group {
  width: 35%;
  height: 50%;
  padding: 0 20px;
  border-radius: 10px;
}

.table-shell {
  height: calc(90% - 30px);
}

.header .input-group:hover {
  width: 45%;
  box-shadow: 0 5px 40px #0002;
}

.table-content {
  overflow: hidden;
  max-width: 250px;
}

@media (min-width: 1800px) {
  .table-shell::-webkit-scrollbar {
    width: 20px;
    height: 20px;
  }

  .styled-table th,
  .styled-table td {
    padding: 12px 15px;
  }

  .styled-table {
    font-size: 16px;
  }

  .button {
    padding: 6px 12px;
  }
}

@media (max-width: 1800px) {
  .styled-table th,
  .styled-table td {
    padding: 12px 15px;
  }

  .styled-table {
    font-size: 16px;
  }

  .button {
    padding: 6px 12px;
  }
}

@media (max-width: 1200px) {
  .table-shell {
    height: 80%;
    padding: 0;
  }

  .header {
    width: 100%;
    height: 12%;
    padding: 0 12px;
  }

  .header h1 {
    font-size: 18px;
  }

  .header .input-group {
    width: 45%;
    height: 50%;
    padding: 0 5px;
    border-radius: 10px;
  }

  .header .input-group:hover {
    width: 55%;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  }

  .table-shell::-webkit-scrollbar {
    width: 15px;
    height: 15px;
  }

  .styled-table th,
  .styled-table td {
    padding: 4px 0px;
  }

  .styled-table {
    font-size: 10px;
  }

  .button {
    font-size: 10px;
    padding: 5px 5px;
  }
}

@media (min-width: 767px) {
  .main_left {
    flex-direction: column;
    box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
  }
}

@media (max-width: 767px) {
  main {
    width: 100%;
    overflow: visible;
  }

  .hidden-mobie {
    display: none;
  }

  .myPhoto img {
    width: 40px;
    height: 40px;
    margin: 0;
    border-radius: 100%;
    top: 0px;
  }

  .main_left {
    position: absolute;
    top: -10px;
    left: 5%;
    width: 90%;
    height: 50px;
    background-color: rgba(255, 255, 255, 1);
    z-index: 100;
    margin: 0;
    overflow: hidden;
  }

  .main_left > div:nth-child(1):hover {
    background-color: transparent;
  }

  .head-container {
    height: 40px !important;
  }

  .main_left .myPhoto {
    height: 40px !important;
    width: 40px !important;
    position: relative;
    left: -5px;
  }

  .main_left div {
    display: block;
    width: calc((100% - 40px) / 4);
    margin: 0 5px;
    border-radius: 25px;
    height: 40px !important;
  }

  .main_left div button {
    font-size: 10px;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
  }

  .main_left div button i {
    font-size: 15px !important;
    margin: 0 !important;
    margin-bottom: 5px !important;
  }

  .main_right {
    padding-left: 0;
    width: 90vw;
    z-index: 90;
  }

  .content-box {
    width: 90%;
    top: 50px;
    height: 70vh;
  }

  .item {
    height: calc((70vh - 30px * 3 - 20px) / 4);
  }

  .main_right .content-box:nth-child(1) .item {
    height: calc((70vh - 30px * 4 - 20px) / 5);
  }

  .label {
    position: absolute;
    width: calc(90vw * 0.9);
    height: 50%;
    line-height: calc(((70vh - 30px * 4 - 20px) / 5 - 40px) * 0.5);
    top: 25%;
    text-align: center;
    font-size: 18px;
    font-weight: 600;
    color: #555;
  }

  .jelly-btn {
    border-radius: 25px;
    padding: 4px 6px;
    font-size: 14px;
  }

  .table-content {
    overflow: hidden;
    max-width: 80px;
  }
}
</style>
