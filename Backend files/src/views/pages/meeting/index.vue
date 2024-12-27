<template>
  <div style="background-color: rgba(223, 115, 115, 0.3)">
    <div class="nav-box">
      <navs />
    </div>
    <div class="search-container">
      <div class="shell">
        <input v-model="meetingName" type="text" placeholder="输入会议名加入会议">
        <button @click.prevent="toMeeting">
          <i class="fa fa-hand-o-right" />
          <i class="fa fa-search" />
        </button>
      </div>
      <main class="table-main">
        <section class="header">
          <h1>待加入会议</h1>
          <div class="input-group">
            <input id="search-input" type="search" placeholder="输入社团名筛选">
          </div>
        </section>
        <section class="table-shell">
          <table class="styled-table">
            <thead>
              <tr>
                <th>社团名称</th>
                <th>会议名</th>
                <th>开始时间</th>
                <th>结束时间</th>
                <th>操作</th>
              </tr>
            </thead>
            <tbody>
              <tr v-for="item in tempList" :key="item.miId">
                <td>{{ item.clubName }}</td>
                <td>{{ item.name }}</td>
                <td>{{ item.startTime }}</td>
                <td>{{ item.endTime }}</td>
                <td>
                  <button
                    class="button"
                    @click.prevent="copyMeetingPassword(item.password, item.miId)"
                  >
                    复制密码并前往
                  </button>
                </td>
              </tr>
              <div v-if="tempList == 0" class="empty-words" />
            </tbody>
          </table>
        </section>
      </main>
    </div>

    <foot footer-class="club-meeting" />
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import { parseTime } from '@/utils'
import request from '@/utils/request'
import 'font-awesome/css/font-awesome.min.css'

export default {
  name: 'Index',
  components: { navs, foot },
  data() {
    return {
      meetInfoList: null,
      tempList: null,
      meetingName: ''
    }
  },
  created() {
    this.getMeetInfoList()
  },
  mounted() {
    const searchInput = document.getElementById('search-input')
    searchInput.addEventListener('input', () => {
      const searchContent = searchInput.value
      this.filteredItems(searchContent)
    })
  },
  methods: {
    parseTime,
    getMeetInfoList() {
      request.get('meeting/getMeetInfoListByUserId').then((res) => {
        if (res.code === 20000) {
          this.meetInfoList = res.data
          this.tempList = this.meetInfoList
        }
      })
    },
    toMeeting() {
      request
        .get('meeting/getMeetingInfoByName', {
          params: {
            name: this.meetingName
          }
        })
        .then((res) => {
          if (res.data) {
            this.$router.push({
              path: '/clubmeeting',
              query: {
                name: this.meetingName
              }
            })
          } else {
            this.$message.error(`会议名为 \"${this.meetingName}\" 的会议不存在！`)
          }
        })
    },
    copyMeetingPassword(password, id) {
      const textArea = document.createElement('textarea')
      textArea.value = password
      document.body.appendChild(textArea)
      textArea.select()
      try {
        document.execCommand('copy')
        this.$message({
          message: `成功复制会议密码！两秒后跳转会议界面`,
          type: 'success'
        })
        setTimeout(() => {
          this.$router.push({
            path: '/clubmeeting',
            query: {
              miId: id
            }
          })
        }, 2000)
        console.log('Fallback: 成功复制到剪贴板')
      } catch (err) {
        console.error('Fallback: 无法复制到剪贴板', err)
      }
      document.body.removeChild(textArea)
      // navigator.clipboard.writeText(title).then(() => {

      // })
    },
    filteredItems(searchText) {
      this.tempList = this.meetInfoList
      this.tempList = this.tempList.filter(
        (item) => item.clubName.toLowerCase().includes(searchText.toLowerCase()) // 根据 name 筛选
      )
    }
  }
}
</script>

<style scoped>
@import "../../../assets/css/templatemo-edu-meeting.css";

.nav-box {
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
  border: none;
}

.search-container {
  position: relative;
  width: 100vw;
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: rgba(215, 245, 241, 0.1);
}

.shell {
  position: absolute;
  background-color: rgba(115, 119, 223, 1);
  border-radius: 20px;
  transition: 0.3s;
}

.shell input {
  border: 0;
  background-color: transparent;
}

.shell input::placeholder {
  color: rgb(241, 242, 251);
}

.shell button {
  display: flex;
  position: absolute;
  background-color: transparent;
  border: none;
  color: #fff;
}

.shell button .fa {
  margin: 10px 28px;
  transition: 0.3s;
}

.shell button .fa-search {
  transform: translateX(-80px);
  opacity: 1;
}

.shell button .fa-hand-o-right {
  transform: translateX(-100px);
  opacity: 0;
}

.shell button:hover .fa-search {
  transform: translateX(0);
  opacity: 0;
}

.shell button:hover .fa-hand-o-right {
  transform: translateX(60px);
  opacity: 1;
}

.shell button::before {
  content: "GO !";
  position: absolute;
  display: block;
  font-size: 30px;
  background-color: rgba(115, 119, 223, 1);
  padding: 5px 30px;
  top: 50px;
  right: 40px;
  border-radius: 10px;
  transition: 0.3s;
  opacity: 0;
  animation: box 1s infinite ease;
}

.shell button:hover::before {
  top: -90px;
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

/* Table */

.table-main,
.table-main * {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

.table-main {
  position: relative;
  background-color: #fff5;
  box-shadow: 0px 16px 16px #0005;
  overflow: hidden;
}

.header {
  background-color: #fff4;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.header h1 {
  color: #6c7ae0;
}

.header .input-group {
  background-color: #fff5;
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

.table-shell {
  width: 95%;
  height: 71%;
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

.empty-words {
  position: absolute;
  width: 150px;
  height: 150px;
  left: calc(50% - 75px);
  top: calc(50% - 60px);
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

.empty-words::before {
  content: "好像没有要加入的会议!";
  position: absolute;
  display: block;
  font-size: 20px;
  background-color: transparent;
  color: #6c7ae0;
  padding: 5px;
  width: 300px;
  top: 50%;
  right: calc(50% - 150px);
  border-radius: 10px;
  animation: box-scale 10s infinite ease;
}

.shell {
  top: 18vh;
  width: 65%;
  padding: 15px 35px;
  box-shadow: 0 10px 50px rgba(115, 119, 223, 1), 0 0 0 15px #fff;
}

.shell input {
  height: 3vh;
  line-height: 3vh;
}

.shell button {
  font-size: 38px;
  right: -50px;
  top: 0px;
  width: 10vw;
  height: 150px;
}

.shell input {
  width: 80%;
  color: #fff;
  font: 300 25px "优设标题黑";
}

.shell:hover {
  transform: scale(1.05);
}

.table-main {
  width: 65%;
  height: 50vh;
  top: 60px;
  border-radius: 16px;
}

.header {
  width: 100%;
  height: 18%;
  padding: 0 40px;
}

.header .input-group {
  width: 35%;
  height: 50%;
  padding: 0 20px;
  border-radius: 10px;
}

.header .input-group:hover {
  width: 45%;
  background-color: #fff8;
  box-shadow: 0 5px 40px #0002;
}

.table-shell {
  height: 71%;
}

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

@media (max-width: 1800px) {
  .shell a::before {
    content: "GO !";
    font-size: 30px;
    background-color: rgba(115, 119, 223, 1);
    padding: 5px 30px;
    top: 50px;
    right: 20px;
    border-radius: 10px;
    transition: 0.3s;
    opacity: 0;
    animation: box 1s infinite ease;
  }
}

@media (max-width: 1200px) {
  .shell {
    top: 12vh;
    width: 90%;
    padding: 10px 15px;
    box-shadow: 0 10px 50px rgba(115, 119, 223, 1), 0 0 0 8px #fff;
  }

  .shell input {
    height: 2vh;
    line-height: 2vh;
  }

  .shell button {
    font-size: 20px;
    right: 20px;
    top: 0px;
    width: 10vw;
    height: 44px;
  }

  .shell button::before {
    content: "GO!";
    font-size: 10px;
    background-color: rgba(115, 119, 223, 1);
    padding: 5px 10px;
    top: 20px;
    right: -15px;
    border-radius: 10px;
    transition: 0.3s;
    opacity: 0;
    animation: box 1s infinite ease;
  }

  .shell button:hover::before {
    top: -40px;
    opacity: 1;
  }

  .shell input {
    width: 80%;
    color: #fff;
    font: 300 15px "优设标题黑";
  }

  .table-main {
    width: 98%;
    height: 65vh;
    top: 40px;
    border-radius: 16px;
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

  .table-shell {
    height: 80%;
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
</style>
