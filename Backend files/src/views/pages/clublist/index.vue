<template>
  <div>
    <div class="club-animation">
      <bgAnimation
        bg-animation-path="./animation/BgAnimation1.json"
        class-bind="clubList"
        :speed="0.2"
      />
    </div>
    <div class="club-list-page">
      <navs />
      <section class="head-page-top">
        <div>
          <div>
            <div>
              <h6>或许这里就有你心仪的社团</h6>
              <h2>社团列表</h2>
            </div>
          </div>
        </div>
      </section>

      <el-form :inline="true" :model="listQuery" class="custom-form">
        <el-form-item label="社团名">
          <el-input v-model="listQuery.clubName" placeholder="输入社团名查询" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="getList">查询</el-button>
        </el-form-item>
      </el-form>

      <section class="club-list-container">
        <div class="card-container">
          <div v-for="club in clubList" :key="club.clubId" class="single-card-container">
            <div class="card">
              <img :src="getImgUrlHeader() + club.images" alt="社团图片">
              <div class="card-text">
                <h4>{{ club.clubName }}</h4>
                <p>社长：{{ club.captainName }}</p>
              </div>
            </div>
            <div class="card-back">
              <h2>{{ club.clubName }}</h2>
              <p>{{ "社长：" + club.captainName }}</p>
              <p>{{ "社团类型：" + club.categoryName }}</p>
              <p>{{ "创建时间：" + parseTime_custom(club.createTime) }}</p>
              <div class="footer">欢迎加入我们的社团！</div>
              <button
                href="#"
                class="details-button"
                @click.prevent="toClubInfo(club.clubId)"
              >
                查看详情
              </button>
            </div>
          </div>
        </div>
        <div class="hidden-pagination">
          <pagination
            v-show="total > 0"
            :total="total"
            :page-sizes="[9, 18]"
            :page.sync="listQuery.page"
            :limit.sync="listQuery.limit"
            @pagination="getList"
          />
        </div>
        <div class="hidden-pagination-2">
          <el-pagination
            v-show="total > 0"
            layout="prev, pager, next"
            :total="total"
            :page.sync="listQuery.page"
            :limit.sync="listQuery.limit"
            @pagination="getList"
          />
        </div>
      </section>
      <br>
      <br>
      <br>
      <foot footer-class="club-list" />
    </div>
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import bgAnimation from '../../qdutils/bg/bg.vue'
import request from '@/utils/request'
import { getImgUrlHeader } from '@/utils/imagespath'
import Pagination from '@/components/Pagination'
export default {
  name: 'Index',
  components: { navs, foot, bgAnimation, Pagination },
  // 初始数据
  data() {
    return {
      listLoading: false,
      total: 0,
      clubList: [],
      listQuery: {
        page: null,
        limit: null
      }
    }
  },

  // 获取数据信息
  created() {
    this.getList()
  },
  // 方法列表
  methods: {
    getImgUrlHeader,
    // 获取方法
    getList() {
      this.listLoading = true
      request
        .get('club/queryClubList', {
          params: this.listQuery
        })
        .then((res) => {
          if (res.code === 20000) {
            this.clubList = res.data
            this.total = res.total
            this.listLoading = false
          }
        })
    },
    parseTime_custom(time) {
      if (!time) return null // 如果时间为空，返回 null
      const date = typeof time === 'object' ? time : new Date(time) // 处理时间格式
      if (isNaN(date)) return null // 检查是否为有效日期

      const year = date.getFullYear() // 获取年份
      const month = (date.getMonth() + 1).toString().padStart(2, '0') // 获取月份，补零
      const day = date.getDate().toString().padStart(2, '0') // 获取日期，补零

      return `${year}-${month}-${day}`
    },
    toClubInfo(id) {
      this.$router.push({
        path: '/clubinfo',
        query: {
          clubId: id
        }
      })
    }
  }
}
</script>

<style scoped>
@import "../../../assets/css/templatemo-edu-meeting.css";

.club-animation {
  position: fixed;
}

.club-list-container {
  position: relative;
  top: 70px;
  padding: 0;
}

.card-container {
  display: grid;
  background-color: transparent;
  margin: 0 auto;
}

.card img {
  width: 100%;
  border-radius: 8px;
}

.single-card-container {
  position: relative;
  perspective: 1000px;
}

.card {
  background-color: rgba(164, 196, 231, 0.3);
}

.card,
.card-back {
  position: relative;
  border-radius: 8px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  text-align: center;
  padding: 15px;
  transform-style: preserve-3d;
  transition: transform 1s ease;
}

.card-text {
  width: 100%;
  height: 50px;
}

.pagination-container {
  margin: 30px calc(15vw);
  padding: 0;
  background-color: rgba(164, 196, 231, 0.3);
  border-radius: 8px;
  padding: 10px 15px;
  text-align: left;
}

.club-animation {
  width: 108vw;
  top: 0px;
}

.card-back {
  position: absolute;
  top: 0px;
  left: 0px;
  width: 100%;
  background-color: transparent;
  visibility: hidden;
  z-index: -1;
  transform: rotateY(180deg);
}

.card-back {
  background: linear-gradient(to bottom, rgb(194, 221, 255), rgb(194, 242, 255));
  border-radius: 15px;
  box-shadow: 0 8px 20px rgba(0, 0, 0, 0.2);
  padding: 20px;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.card-back h2 {
  font-size: 1.8rem;
  font-weight: bold;
  margin-bottom: 10px;
  text-shadow: 0 2px 4px rgba(0, 0, 0, 0.3);
}

.card-back p {
  font-size: 1rem;
  line-height: 1.5;
  margin-bottom: 5px;
}

.card-back .footer {
  font-size: 0.9rem;
  opacity: 0.9;
  margin-top: 5px;
  padding: 10px;
  color: #000;
  text-shadow: 0 1px 2px rgba(0, 0, 0, 0.2);
}

.card-back .details-button {
  position: relative;
  padding: 12px 25px;
  background: rgba(255, 255, 255, 0.8);
  color: #ff6f61;
  border: none;
  border-radius: 5px;
  font-size: 1rem;
  cursor: pointer;
  text-decoration: none;
  transition: background-color 0.3s, color 0.3s, transform 0.2s;
}

.card-back .details-button:hover {
  background-color: #849df1;
  color: #fff;
  transform: scale(1.05);
}

.single-card-container:hover .card {
  transform: translateY(-10px) rotateY(-180deg);
  z-index: -1;
}

.single-card-container:hover .card-back {
  transform: translateY(-10px) rotateY(0);
  visibility: visible;
  z-index: 1;
}

.card h4 {
  margin: 10px 0 5px;
  font-size: 1.2em;
  color: #333;
}

.card p {
  margin: 0;
  font-size: 1em;
  color: #777;
}

.hidden-pagination-2 {
  display: none;
}

.card-container {
  gap: 40px;
  width: 60vw;
  grid-template-columns: repeat(3, 1fr);
}

.card img {
  height: calc((60vw - 110px) / 3);
}

.card,
.card-back {
  height: calc((60vw - 110px) / 3 + 100px);
}

.custom-form {
  flex-wrap: wrap;
  position: relative;
  top: 60px;
  left: 20vw;
  margin: 0;
  padding: 0;
  border-radius: 10px;
}

.custom-form .el-form-item {
  margin: 10px 0;
  /* 垂直间距 */
}

.custom-form .el-form-item label {
  font-weight: bold;
  margin-right: 10px;
}

.custom-form .el-input {
  width: 10vw;
  transition: 0.5s;
}

.custom-form .el-input:hover {
  width: 15vw;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.custom-form .el-input:focus {
  border: 1px #6585f0 solid;
  box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
}

.custom-form .el-button {
  margin-left: 10px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  background-color: #6585f0;
}

@media (min-width: 2000px) {
  .club-animation {
    width: 108vw;
    top: 0px;
  }
}

@media (max-width: 2000px) {
  .club-animation {
    width: 200vw;
    top: 0px;
  }
}

@media (max-width: 1200px) {
  .club-animation {
    width: 220vw;
    top: 0px;
  }

  .card-container {
    gap: 30px;
    width: calc(70vw);
    grid-template-columns: repeat(3, 1fr);
  }

  .card img {
    height: calc((70vw - 60px) / 3);
  }

  .card,
  .card-back {
    height: calc((70vw - 60px) / 3 + 100px);
  }

  .card-back h2 {
    font-size: 1.2em;
  }

  .card-back p,
  .card-back .footer {
    font-size: 0.8em;
  }
}

@media (max-width: 767px) {
  .club-animation {
    width: 390vw;
    top: 0px;
  }

  .card-container {
    gap: 20px;
    width: calc(70vw);
    grid-template-columns: repeat(1, 1fr);
  }

  .card img {
    height: calc(70vw - 30px);
  }

  .card,
  .card-back {
    height: calc((70vw - 30px) + 100px);
  }

  .hidden-pagination {
    display: none;
  }

  .hidden-pagination-2 {
    display: block;
  }

  .hidden-pagination-2 >>> .el-pagination {
    margin: 30px calc(15vw);
    padding: 0;
    background-color: rgba(164, 196, 231, 0.3);
    border-radius: 8px;
    padding: 10px 15px;
    text-align: center;
  }

  .hidden-pagination-2 >>> .el-pagination * {
    background-color: transparent;
  }

  .custom-form {
    left: 15vw;
  }

  .custom-form .el-input {
    width: 30vw;
  }

  .custom-form .el-input:hover {
    width: 35vw;
    box-shadow: 0 5px 20px rgba(0, 0, 0, 0.1);
  }
}
</style>
