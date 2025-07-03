<template>
  <div style="width: 100%">
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
            <tr v-for="item in meetInfoList" :key="item.miId">
              <td>{{ item.clubName }}</td>
              <td>{{ item.name }}</td>
              <td>{{ item.startTime }}</td>
              <td>{{ item.endTime }}</td>
              <td>
                <button class="button" @click.prevent="joinMeeting(item.miId)">
                  进入会议
                </button>
              </td>
            </tr>
            <div v-if="meetInfoList == 0" class="empty-words" />
          </tbody>
        </table>
      </section>
    </main>
  </div>
</template>

<script>
export default {
  name: 'MTable',
  props: {
    meetInfoList: {
      type: Array,
      required: true
    }
  }
}
</script>

<style scoped>
.table-main,
.table-main * {
  outline: none;
  padding: 0;
  margin: 0;
  text-decoration: none;
  border: none;
  box-sizing: border-box;
}

.table-main {
  position: relative;
  background-color: #fff5;
  box-shadow: 0px 16px 16px #0005;
  overflow: hidden;
  width: 65%;
  height: 50vh;
  top: 60px;
  margin-left: 17.5%;
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

@media (max-width: 1200px) {
  .table-main {
    width: 98%;
    margin-left: 1%;
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
