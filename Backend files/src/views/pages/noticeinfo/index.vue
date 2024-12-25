<template>
  <div>
    <div class="body" />
    <div class="sec1_circle1" />
    <div class="sec1_circle2" />
    <div class="sec1_circle3" />

    <h1 class="page-title">{{ noticeInfo.clubName }}社团公告</h1>

    <div class="activity-card">
      <div class="activity-details">
        <h2>{{ noticeInfo.name }}</h2>
        <h2>{{ noticeInfo.title }}</h2>
        <p>
          <strong>发布时间：</strong>{{ noticeInfo.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}
        </p>
        <pre><strong>公告内容：</strong>{{ noticeInfo.content }}</pre>
        <p style="text-align: right">
          <strong>&#8212;</strong>
          {{ noticeInfo.clubName }}
        </p>
        <button class="join-button" @click="backFunc">返回社团公告页面</button>
      </div>
    </div>
  </div>
</template>

<script>
import request from '@/utils/request'
import { getImgUrlHeader } from '@/utils/imagespath'
import { parseTime } from '@/utils'

export default {
  name: 'Index',
  data() {
    return {
      noticeInfo: {}
    }
  },
  mounted() {
    request
      .get('notice/queryNoticeById', {
        params: {
          noticeId: this.$route.query.noticeId
        }
      })
      .then((res) => {
        if (res.code === 20000) {
          this.noticeInfo = res.data
        }
      })
  },
  methods: {
    getImgUrlHeader,
    parseTime,
    backFunc() {
      this.$router.push({
        path: '/clubnotice'
      })
    }
  }
}
</script>
<style scoped>
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

/* 页面标题样式 */
.page-title {
  position: relative;
  text-align: center;
  font-size: 2rem;
  color: #333;
  margin: 0;
  margin-bottom: 20px;
  height: 50px;
  line-height: 50px;
  text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
}

/* 活动卡片 */
.activity-card {
  display: flex;
  flex-direction: column;
  position: relative;
  z-index: 1;
  width: 50vw;
  height: auto;
  margin: 0 auto;
  padding: 30px 50px;
  background: rgba(255, 255, 255, 0.5);
  border-radius: 10px;
  box-shadow: 0 8px 16px rgba(0, 0, 0, 0.1);
  overflow: hidden;
  transition: transform 0.3s ease, box-shadow 0.3s ease;
}

.activity-card:hover {
  transform: translateY(-5px);
  box-shadow: 0 12px 24px rgba(0, 0, 0, 0.15);
}

/* 公告详情 */
.activity-details {
  position: relative;
  background-color: rgba(255, 255, 255, 0.5);
  height: calc(100% - 50vw + 100px) !important;
  padding: 20px;
  font-size: 1rem;
  line-height: 1.6;
  color: #555;
}

.activity-details {
  width: 100%;
  padding: 5px;
}

h2 {
  color: #333;
  font-size: 1.8em;
  text-align: center;
  margin: 20px 0;
}

pre {
  font-family: Helvetica Neue, Helvetica, PingFang SC, Hiragino Sans GB, Microsoft YaHei,
    Arial, sans-serif;
  white-space: pre-wrap;
  word-wrap: break-word;
}

pre,
p {
  font-size: 1em;
  color: #555;
  line-height: 1.6;
  margin: 10px 0;
  padding: 0 20px;
}

pre strong,
p strong {
  color: #222;
  font-weight: bold;
}

span {
  font-weight: bold;
  color: #4caf50;
  /* 活动状态的颜色，可根据需求调整 */
}

/* 按钮样式 */
.join-button {
  display: block;
  margin: 0 auto;
  width: 50%;
  margin-top: 20px;
  margin-bottom: 30px;
  padding: 12px 24px;
  font-size: 1rem;
  color: #ffffff;
  background: #4caf50;
  border: none;
  border-radius: 8px;
  cursor: pointer;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease;
}

.join-button:hover {
  background: #45a049;
  transform: scale(1.1);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.join-button:active {
  transform: translateY(2px);
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

/* 响应式调整 */
@media (max-width: 768px) {
  .page-title {
    font-size: 1.5rem;
    margin-bottom: 5px;
  }

  .activity-card {
    width: 90vw;
    padding: 10px;
  }

  .img-container {
    height: calc(90vw * 0.8 - 20px) !important;
  }

  .activity-details {
    height: calc(100% - 90vw * 0.8 + 20px) !important;
  }

  h2 {
    font-size: 1.2em;
    margin: 5px 0;
  }

  pre,
  p {
    padding: 0 10px;
    margin: 5px 0;
    font-size: 0.7em;
  }

  .join-button {
    width: 100%;
    padding: 10px;
    font-size: 0.7em;
  }
}
</style>
