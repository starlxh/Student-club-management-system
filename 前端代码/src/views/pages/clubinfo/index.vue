<template>
  <div>
    <div class="body" />
    <div class="sec1_circle1" />
    <div class="sec1_circle2" />
    <div class="sec1_circle3" />

    <h1 class="page-title">社团详情 - {{ clubInfo.clubName }}</h1>

    <div class="activity-card">
      <div class="img-container">
        <img
          :src="getImgUrlHeader() + clubInfo.images"
          alt="社团图片"
          class="activity-image"
        >
      </div>
      <div class="activity-details">
        <h2>{{ clubInfo.name }}</h2>
        <p>
          <strong>创建时间：</strong>{{ clubInfo.createTime | parseTime("{y}-{m}-{d}") }}
        </p>
        <p><strong>社团类型：</strong>{{ clubInfo.categoryName }}</p>
        <p><strong>社长名称：</strong>{{ clubInfo.captainName }}</p>
        <p><strong>当前人数：</strong>{{ clubInfo.count }}</p>
        <pre><strong>社团简介：</strong>{{ clubInfo.introduction }}</pre>
        <button v-if="isJoined" class="join-button" @click="backFunc">
          返回社团列表界面
        </button>
        <div v-else class="button-container">
          <button class="join-button" @click="handleApply">申请加入社团</button>
          <button class="join-button" @click="backFunc">返回社团列表界面</button>
        </div>
      </div>
    </div>
    <div class="leave-board">
      <div class="leave-header">
        <h2>留言区</h2>
        <p>欢迎留言！分享您的想法和建议。</p>
      </div>
      <div class="leave-input-area">
        <el-input
          v-model="newLeave.content"
          type="textarea"
          placeholder="请输入您的留言..."
          maxlength="400"
          show-word-limit
          rows="4"
          class="leave-input"
        />
        <el-button
          type="primary"
          class="submit-button"
          :disabled="!newLeave.content.trim()"
          @click.native.prevent="checkSubmit"
        >
          提交留言
        </el-button>
      </div>
      <el-card
        v-for="(leaveinfo, index) in leaveInfoList"
        :key="index"
        class="leave-card"
      >
        <p class="leave-info">
          <span> <strong>用户名：</strong>{{ leaveinfo.userName }} </span>
          <span>
            <strong>发布时间：</strong>{{ leaveinfo.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}
          </span>
        </p>
        <strong>留言内容：</strong>
        <div class="leave-content">
          <pre>{{ leaveinfo.content }}</pre>
        </div>
      </el-card>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" class="custom-dialog">
      <el-form ref="applyForm" :model="applyForm" :rules="applyRlues" class="custom-form">
        <el-form-item prop="content">
          <span class="form-label">申请理由：</span>
          <el-input
            v-model="applyForm.content"
            autocomplete="off"
            class="custom-input"
            type="textarea"
            placeholder="请输入您的申请理由..."
            maxlength="200"
            show-word-limit
            rows="4"
          />
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <button class="cancel-button" @click.prevent="dialogFormVisible = false">
          取 消
        </button>
        <button class="confirm-button" @click.prevent="checkApply">确 定</button>
      </div>
    </el-dialog>
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
      isJoined: false,
      clubInfo: {},
      newLeave: {
        content: '',
        clubId: null,
        createTime: null
      },
      dialogFormVisible: false,
      applyForm: {
        content: '',
        clubId: null,
        createTime: null
      },
      applyRlues: {
        content: [{ required: true, message: '需要填写申请理由', trigger: 'blur' }]
      },
      leaveInfoList: []
    }
  },
  mounted() {
    this.getClubInfo()
    this.getLeaveInfoList()
  },
  methods: {
    getImgUrlHeader,
    parseTime,
    getJoinedClub() {
      if (!this.$store.getters.roles[0]) {
        return
      }
      request
        .get('club/checkIsJoinedById', {
          params: {
            clubId: this.clubInfo.clubId
          }
        })
        .then((res) => {
          if (res.code === 20000) {
            if (res.data === 1) {
              this.isJoined = true
            } else {
              this.isJoined = false
            }
          }
        })
    },
    getClubInfo() {
      request
        .get('club/queryClubInfoById', {
          params: {
            clubId: this.$route.query.clubId,
            token: this.$store.getters.token
          }
        })
        .then((res) => {
          if (res.code === 20000) {
            this.clubInfo = res.data
            this.getJoinedClub()
          }
        })
    },
    getLeaveInfoList() {
      request
        .get('leaveInfo/queryLeaveInfoListByClubId', {
          params: {
            clubId: this.$route.query.clubId,
            token: this.$store.getters.token
          }
        })
        .then((res) => {
          if (res.code === 20000) {
            this.leaveInfoList = res.data
          }
        })
    },
    handleApply() {
      if (!this.$store.getters.roles[0]) {
        this.$message({
          message: '需要注册账号并登录，才能申请加入社团！',
          type: 'warning'
        })
        return
      }
      this.applyForm.content = ''
      this.dialogFormVisible = true
    },
    checkApply() {
      this.$confirm(`您确定要提交入团申请吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.submitApply()
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消入团申请'
          })
        })
    },
    submitApply() {
      this.applyForm.createTime = parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}')
      this.applyForm.clubId = this.clubInfo.clubId
      request.post('apply/addApplyInfo?', this.applyForm).then((res) => {
        if (res.code === 20000) {
          if (res.data === 'warning') {
            this.$message({
              message: '您已提交加入该社团的申请，请等待管理员处理！',
              type: 'warning'
            })
            return
          }
          this.dialogFormVisible = false
          this.$message({
            message: `提交入团申请成功!`,
            type: 'success'
          })
          this.applyForm.content = ''
        }
      })
    },
    backFunc() {
      this.$router.push({
        path: '/clublist'
      })
    },
    checkSubmit() {
      if (!this.$store.getters.roles[0]) {
        this.$message({
          message: '需要注册账号并登录，才能留言！',
          type: 'warning'
        })
        return
      }
      this.$confirm(`您确定要提交留言吗?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.submitLeave()
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消提交'
          })
        })
    },
    submitLeave() {
      this.newLeave.createTime = parseTime(new Date(), '{y}-{m}-{d} {h}:{i}:{s}')
      this.newLeave.clubId = this.clubInfo.clubId
      request.post('leaveInfo/addLeaveInfo?', this.newLeave).then((res) => {
        if (res.code === 20000) {
          this.$message({
            message: `留言成功!`,
            type: 'success'
          })
          this.getLeaveInfoList()
          this.newLeave.content = ''
        }
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

/* 图片样式 */
.img-container {
  position: relative;
  overflow: hidden;
  width: 100%;
  height: calc((50vw - 100px) * 0.8) !important;
  left: 0;
  top: 0;
}

.activity-image {
  position: relative;
  width: 80%;
  left: 10%;
  height: auto;
  overflow: hidden;
  border-bottom: 1px solid #ddd;
  z-index: -2;
}

/* 活动详情 */
.activity-details {
  position: relative;
  background-color: rgba(255, 255, 255, 0.5);
  width: 80% !important;
  left: 10%;
  height: calc(100% - 50vw * 0.8 + 80px) !important;
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
  color: #222 !important;
  font-weight: bold;
}

span {
  font-weight: bold;
  color: #4caf50;
  /* 活动状态的颜色，可根据需求调整 */
}

/* 按钮样式 */

.button-container {
  display: flex;
}

.join-button {
  display: block;
  margin: 0 auto;
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

.leave-board {
  width: 50vw;
  margin: 0 auto;
  margin-bottom: 30px;
  padding-bottom: 30px;
  font-family: Arial, sans-serif;
  background-color: #f9f9f9;
  border-radius: 10px;
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
  z-index: 1;
}

.leave-header {
  text-align: center;
  margin-bottom: 20px;
}

.leave-header h2 {
  font-size: 24px;
  color: #333;
}

.leave-header p {
  font-size: 14px;
  color: #666;
}

.leave-input-area {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.leave-input {
  width: 100%;
}

.submit-button {
  align-self: flex-end;
  z-index: 10;
  background: #4caf50;
  transition: 0.5s;
}

.submit-button:hover {
  background: #1cc421;
  transform: scale(1.1);
  box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
}

.leave-card {
  margin-top: 20px;
  padding: 15px;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.leave-card p {
  margin: 0;
  padding: 0;
}

.leave-content pre {
  margin: 5px 0;
  padding: 0;
  font-size: 16px;
  color: #333;
}

.leave-card span {
  text-align: right;
  color: #333;
  font-size: 16px;
  font-weight: 400;
}

.leave-card .leave-info {
  display: flex;
  justify-content: space-between;
}

/* 编辑表单 */
.custom-dialog {
  border-radius: 15px;
  padding: 20px;
  background: rgba(255, 255, 255, 0.1);
  backdrop-filter: blur(3px) saturate(180%);
  box-shadow: 0 8px 30px rgba(0, 0, 0, 0.3);
  border: 1px solid rgba(255, 182, 193, 0.4);
  z-index: 1;
}

@keyframes pop-in {
  0% {
    opacity: 0;
  }

  100% {
    opacity: 1;
  }
}

.custom-dialog >>> .el-dialog__header {
  padding: 10;
}

.custom-dialog >>> .el-dialog__body {
  padding: 20px 15px;
}

.form-label {
  font-size: 18px;
  font-weight: bold;
  color: #6c7ae0;
  height: 30px;
}

.custom-dialog >>> .el-dialog__close:hover {
  color: #6c7ae0;
  transition: 0.5s;
  transform: rotate(180deg) scale(1.2);
}

.custom-input >>> .el-input__inner {
  position: relative;
  top: 30px;
  left: 25%;
  border-radius: 10px;
  padding: 12px;
  margin-top: 10px;
  margin-bottom: 30px;
  border: 2px solid #6c7ae0;
  width: 50%;
  background: rgba(255, 255, 255, 0.6);
  transition: border-color 0.3s, box-shadow 0.3s, background-color 0.3s, width 0.5s,
    left 0.5s;
}

.custom-input >>> .el-input__inner:hover {
  width: 80%;
  left: 10%;
  background-color: rgba(91, 44, 188, 0.1);
}

.custom-input >>> .el-input__inner:focus {
  border-color: #6c7ae0;
  box-shadow: 0 0 10px rgba(124, 110, 152, 0.6);
  background-color: rgba(255, 255, 255, 0.9);
}

.custom-dialog >>> .el-input__icon {
  display: none;
}

.dialog-footer {
  justify-content: flex-end;
  text-align: center;
  margin-top: 40px;
  margin-bottom: 20px;
}

.cancel-button,
.confirm-button {
  padding: 12px 25px;
  font-size: 14px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: transform 0.3s, background-color 0.3s;
  font-weight: bold;
}

.cancel-button {
  background-color: #f56c6c;
  color: white;
  margin-right: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.cancel-button:hover {
  background-color: #f78989;
  transform: scale(1.1);
}

.confirm-button {
  background-color: #67c23a;
  color: white;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
}

.confirm-button:hover {
  background-color: #85e58c;
  transform: scale(1.1);
}

.el-form >>> .el-form-item__error {
  color: #e74c3c;
  font-weight: bold;
  background: rgba(231, 76, 60, 0.1);
  padding: 3px 6px;
  border-radius: 4px;
  left: 25%;
  top: 80px;
  line-height: 1.5;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: 0.5s;
}

.el-form >>> .el-form-item__error:hover {
  background: rgba(231, 76, 60, 0.2);
  transform: scale(1.05);
}

.el-form-item:hover >>> .el-form-item__error {
  left: 10%;
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
    font-size: 0.7rem;
    padding: 12px 16px;
  }

  .leave-board {
    width: 90vw;
  }

  .custom-dialog >>> .el-dialog {
    width: 90vw;
  }

  .custom-input >>> .el-input__inner {
    left: 15%;
    width: 70%;
  }

  .custom-input >>> .el-input__inner:hover {
    left: 5%;
    width: 90%;
  }

  .el-form >>> .el-form-item__error {
    left: 15%;
  }

  .el-form-item:hover >>> .el-form-item__error {
    left: 5%;
  }
}
</style>
