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
      <m-table :meet-info-list="tempList" />
    </div>

    <foot footer-class="club-meeting" />
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import MTable from '../../qdutils/mTable/index'
import { getMeetInfoListByUserId, getMeetingInfoByName } from '@/api/meeting'

export default {
  name: 'Index',
  components: { navs, foot, MTable },
  data() {
    return {
      tempList: [],
      meetInfoList: [],
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
    getMeetInfoList() {
      getMeetInfoListByUserId().then((res) => {
        if (res.code === 20000) {
          this.meetInfoList = res.data
          this.tempList = this.meetInfoList
        }
      })
    },
    toMeeting() {
      getMeetingInfoByName(this.meetingName).then((res) => {
        if (res.data) {
          this.$router.push({
            path: '/clubmeeting',
            query: {
              name: this.meetingName
            }
          })
        } else {
          this.$notify.warning({
            title: '加入会议失败',
            message: `未找到可加入的会议-${this.meetingName}`
          })
        }
      })
    },
    joinMeeting(id) {
      this.$router.push({
        path: '/clubmeeting',
        query: {
          miId: id
        }
      })
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
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
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
}
</style>
