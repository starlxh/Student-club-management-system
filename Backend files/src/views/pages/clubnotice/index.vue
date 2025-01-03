<template>
  <div>
    <!--导航-->
    <div class="nav-box">
      <navs />
    </div>
    <div class="body" />
    <!-- 背景圆点 -->
    <div class="sec1_circle1" />
    <div class="sec1_circle2" />
    <div class="sec1_circle3" />

    <main class="table-main">
      <section class="header">
        <h1>社团公告</h1>
        <div class="input-group">
          <input id="search-input" type="search" placeholder="输入社团名筛选">
        </div>
      </section>
      <section class="table-shell">
        <table class="styled-table">
          <thead>
            <tr>
              <th>社团名称</th>
              <th>公告标题</th>
              <th>创建时间</th>
              <th>操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in noticeList" :key="item.noticeId">
              <td>{{ item.clubName }}</td>
              <td>{{ item.title }}</td>
              <td>{{ item.createTime | parseTime("{y}-{m}-{d}") }}</td>
              <td>
                <button class="button" @click.prevent="toClubInfo(item.noticeId)">
                  查看详情
                </button>
              </td>
            </tr>
            <div v-if="noticeList == 0" class="empty-words" />
          </tbody>
        </table>
      </section>
      <div class="hidden-pagination">
        <pagination
          v-show="total > 0"
          :total="total"
          :page-sizes="[10, 20]"
          :page.sync="listQuery.page"
          :limit.sync="listQuery.limit"
          @pagination="getNoticeList"
        />
      </div>
      <div class="hidden-pagination-2">
        <el-pagination
          v-show="total > 0"
          layout="prev, pager, next"
          :total="total"
          :page.sync="listQuery.page"
          :limit.sync="listQuery.limit"
          @pagination="getNoticeList"
        />
      </div>
    </main>
    <foot footer-class="user-center" />
  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import request from '@/utils/request'
import { getImgUrlHeader } from '@/utils/imagespath'
import { parseTime } from '@/utils'
import Pagination from '@/components/Pagination'

export default {
  name: 'Index',
  components: { navs, foot, Pagination },
  data() {
    return {
      noticeList: [],
      total: 0,
      listQuery: {
        page: null,
        limit: null
      }
    }
  },
  mounted() {
    this.getNoticeList()
  },
  methods: {
    getImgUrlHeader,
    parseTime,
    getNoticeList() {
      request
        .get('notice/queryNoticeListPublic', {
          params: this.listQuery
        })
        .then((res) => {
          if (res.code === 20000) {
            this.noticeList = res.data
            this.total = res.total
          }
        })
    },
    toClubInfo(id) {
      this.$router.push({
        path: '/noticeinfo',
        query: {
          noticeId: id
        }
      })
    }
  }
}
</script>
<style scoped>
@import "../../../assets/css/templatemo-edu-meeting.css";

button {
  border: none;
}

.nav-box {
  position: relative;
}

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

.body {
  position: fixed;
  width: 100%;
  height: 100%;
  background-image: linear-gradient(
    45deg,
    rgba(163, 255, 231, 0.6),
    rgba(255, 193, 179, 0.6)
  );
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
  margin: 0 auto;
  width: 80vw;
  height: 80vh;
  top: 80px;
  border-radius: 16px;
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

.header {
  width: 100%;
  height: 10%;
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

.table-shell {
  width: 95%;
  height: 77%;
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
  content: "好像还没有社团公告!";
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

.hidden-pagination >>> .pagination-container {
  position: relative;
  padding: 0;
  background-color: transparent !important;
  border-radius: 8px;
  padding: 10px 15px;
  text-align: center;
}

.hidden-pagination-2 {
  display: none;
}

@media (max-width: 767px) {
  .table-main {
    width: 98%;
    height: 65vh;
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

  .hidden-pagination {
    display: none;
  }

  .hidden-pagination-2 {
    display: block;
  }

  .hidden-pagination-2 >>> .el-pagination {
    padding: 0;
    border-radius: 8px;
    padding: 10px 15px;
    text-align: center;
  }

  .hidden-pagination-2 >>> .el-pagination * {
    background-color: transparent;
  }
}
</style>
