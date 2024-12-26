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
                <i class="fa fa-address-card" />
                <span>个人信息</span>
              </button>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-phone" />
                <span>联系方式</span>
              </button>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-commenting" />
                <span>在线留言</span>
              </button>
            </div>
            <div>
              <button class="content-btn">
                <i class="fa fa-ellipsis-h" />
                <span>更多操作</span>
              </button>
            </div>
          </nav>
          <section class="main_right">
            <div class="content-box">
              <div class="container">
                <div class="item">
                  <div class="tag">用户昵称</div>
                  <div class="content">
                    <span class="label">{{ userInfo.userName }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="
                        handleEdit(userInfo.userName, '用户昵称', 'userName')
                      "
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">真实名称</div>
                  <div class="content">
                    <span class="label">{{ userInfo.realName }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="
                        handleEdit(userInfo.realName, '真实姓名', 'realName')
                      "
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">用户性别</div>
                  <div class="content">
                    <span class="label">{{ userInfo.sex }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit(userInfo.sex, '用户性别', 'sex')"
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">用户类型</div>
                  <div class="content">
                    <span v-if="userInfo.type === 0" class="label">普通用户</span>
                    <span v-else-if="userInfo.type === 1" class="label">社团管理员</span>
                    <span v-else-if="userInfo.type === 2" class="label">系统管理员</span>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">创建时间</div>
                  <div class="content">
                    <span class="label">{{
                      userInfo.createTime | parseTime("{y}-{m}-{d} {h}:{i}")
                    }}</span>
                  </div>
                </div>
              </div>
            </div>

            <div class="content-box hidden">
              <div class="container">
                <div class="item">
                  <div class="tag">QQ号</div>
                  <div class="content">
                    <span class="label">{{ userInfo.qq }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit(userInfo.qq, 'QQ号', 'qq')"
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">微信号</div>
                  <div class="content">
                    <span class="label">{{ userInfo.wx }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit(userInfo.wx, '微信号', 'wx')"
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">电话号码</div>
                  <div class="content">
                    <span class="label">{{ userInfo.tel }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit(userInfo.tel, '电话号码', 'tel')"
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">邮箱</div>
                  <div class="content">
                    <span class="label">{{ userInfo.email }}</span>
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit('', '邮箱', 'email')"
                    >
                      修改
                    </button>
                  </div>
                </div>
              </div>
            </div>

            <div class="content-box hidden">
              <section class="header">
                <h1>在线留言</h1>
                <div class="input-group">
                  <input id="search-input" type="search" placeholder="输入社团名筛选">
                </div>
              </section>
              <section class="table-shell">
                <table class="styled-table">
                  <thead>
                    <tr>
                      <th>社团名称</th>
                      <th>留言内容</th>
                      <th>创建时间</th>
                      <th>操作</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr v-for="item in tempList" :key="item.leaveInfoId">
                      <td>{{ item.clubName }}</td>
                      <td class="table-content">{{ item.content }}</td>
                      <td>{{ item.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}</td>
                      <td>
                        <button
                          class="button"
                          @click.prevent="deleteLeaveInfo(item.leaveInfoId)"
                        >
                          删除
                        </button>
                      </td>
                    </tr>
                    <div v-if="leaveInfoList == 0" class="empty-words" />
                  </tbody>
                </table>
              </section>
            </div>

            <div class="content-box hidden">
              <div class="container">
                <div class="item">
                  <div class="tag">个人头像管理</div>
                  <div class="content">
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit('', '个人头像', 'image')"
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">修改密码</div>
                  <div class="content">
                    <button
                      class="jelly-btn"
                      @click.prevent="handleEdit('', '密码', 'password')"
                    >
                      修改
                    </button>
                  </div>
                </div>
                <div class="item">
                  <div class="tag">登录管理</div>
                  <div class="content">
                    <button class="jelly-btn" @click.prevent="loginOut">退出登录</button>
                  </div>
                </div>
              </div>
            </div>
          </section>
        </main>
      </div>
    </div>

    <el-dialog :visible.sync="dialogFormVisible" class="custom-dialog">
      <el-form ref="editForm" :model="editForm" :rules="editRlues" class="custom-form">
        <el-form-item prop="value">
          <span class="form-label">{{ editForm.label }}</span>

          <div
            v-if="editForm.label == '密码' || editForm.label == '邮箱'"
            class="input-pwd-container"
          >
            <el-input
              v-model="editForm.value"
              autocomplete="off"
              class="custom-input pwd-input"
              :show-password="passwordType"
              :placeholder="editForm.label == '密码' ? '请输入新密码' : '请输入新邮箱'"
            />
            <span class="show-pwd" @click="showPwd">
              <svg-icon :icon-class="passwordType ? 'eye' : 'eye-open'" />
            </span>
          </div>
          <el-upload
            v-else-if="editForm.label == '个人头像'"
            class="avatar-uploader"
            :action="getImgUrlHeader() + '/public/uploadImg?type=0'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="getImgUrlHeader() + imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
          <el-input
            v-else
            v-model="editForm.value"
            autocomplete="off"
            class="custom-input"
          />
        </el-form-item>

        <el-form-item
          v-if="editForm.label == '密码' || editForm.label == '邮箱'"
          prop="captcha"
        >
          <el-input
            v-model="editForm.captcha"
            name="captcha"
            autocomplete="off"
            class="custom-input captcha-input"
            :show-password="passwordType"
            placeholder="请获取验证码"
          />
          <button class="captcha-button" @click.prevent="handleCaptcha">
            获取验证码
          </button>
        </el-form-item>
      </el-form>
      <div class="dialog-footer">
        <button class="cancel-button" @click.prevent="dialogFormVisible = false">
          取 消
        </button>
        <button class="confirm-button" @click.prevent="checkEditData">确 定</button>
      </div>
    </el-dialog>

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
import store from '@/store'
export default {
  name: 'Index',
  components: { navs, foot },
  data() {
    return {
      curIndex: null,
      userInfo: {
        userName: ''
      },
      leaveInfoList: false,
      tempList: null,
      searchContent: '',
      dialogFormVisible: false,
      editForm: {
        label: '',
        value: ''
      },
      passwordType: false,
      fileList: [],
      imageUrl: '',
      isImgUpload: false,
      lastName: '',
      editRlues: {
        value: [{ required: true, message: '值不能为空哦', trigger: 'blur' }],
        captcha: [{ required: true, message: '需要验证码', trigger: 'blur' }]
      }
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

    const searchInput = document.getElementById('search-input')
    searchInput.addEventListener('input', () => {
      this.searchContent = searchInput.value
      this.filteredItems()
    })
  },
  created() {
    this.getUserInfo()
    this.getLeaveInfoList()
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
    getLeaveInfoList() {
      request.get('leaveInfo/queryLeaveInfoListByUserId').then((res) => {
        if (res.code === 20000) {
          this.leaveInfoList = res.data
          this.tempList = this.leaveInfoList
        }
      })
    },
    filteredItems() {
      this.tempList = this.leaveInfoList
      this.tempList = this.tempList.filter(
        (item) => item.clubName.toLowerCase().includes(this.searchContent.toLowerCase()) // 根据 name 筛选
      )
    },
    deleteLeaveInfo(id) {
      request
        .delete('leaveInfo/deleteById', {
          params: {
            leaveInfoId: id
          }
        })
        .then((res) => {
          if (res.code === 20000) {
            this.getLeaveInfoList()
            this.filteredItems()
            this.$message({
              message: `删除成功`,
              type: 'success'
            })
          }
        })
    },
    loginOut() {
      request.post('login/loginOut').then(() => {
        store.commit('user/SET_ROLES', '')
        this.$router.push({ path: '/' })
      })
    },
    checkEditData() {
      this.$confirm(`此操作将修改${this.editForm.label}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.editData()
        })
        .catch(() => {
          this.$message({
            type: 'info',
            message: '已取消修改'
          })
        })
    },
    handleEdit(value, label, name) {
      this.editForm.value = value
      this.editForm.label = label
      this.editForm.name = name

      this.dialogFormVisible = true
      this.$refs.editForm.clearValidate()
    },
    editData() {
      const temp = {}
      if (this.editForm.name === 'image') {
        if (this.isImgUpload) {
          this.isImgUpload = false
          temp['image'] = this.imageUrl
          request.post('user/editUser', temp).then(() => {
            this.getUserInfo()
            this.$message({
              message: `修改${this.editForm.label}成功!`,
              type: 'success'
            })
          })
          this.dialogFormVisible = false
        } else {
          this.$message.error(`修改${this.editForm.label}失败!`)
        }
      } else {
        temp[`${this.editForm.name}`] = this.editForm.value
        if (this.editForm.name === 'password' || this.editForm.name === 'email') {
          temp['captcha'] = this.editForm.captcha
        }
        this.$refs.editForm.validate((valid) => {
          if (valid) {
            request
              .post('user/editUser', temp)
              .then(() => {
                this.getUserInfo()
                this.$message({
                  message: `修改${this.editForm.label}成功!`,
                  type: 'success'
                })
                this.dialogFormVisible = false
              })
              .catch(() => {
                this.$message.error(`修改${this.editForm.label}失败!`)
              })
          }
        })
      }
    },
    showPwd() {
      this.passwordType = !this.passwordType
    },
    handleAvatarSuccess(res) {
      this.imageUrl = res.data
      this.isImgUpload = true
    },
    beforeAvatarUpload(file) {
      const isJPG = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 或 png 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },
    handleCaptcha() {
      request
        .post('login/sendCaptcha?type=2&token=' + this.$store.getters.token)
        .then((res) => {
          this.$message({
            message: '已发送验证码到您的邮箱！',
            type: 'success'
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
  content: "好像没有在线留言过!";
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
  position: absolute;
  width: 100%;
  text-align: center;
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

.custom-dialog .pwd-input >>> .el-input__inner {
  width: 80%;
  left: 10%;
}

.custom-dialog .captcha-input >>> .el-input__inner {
  width: 40%;
  left: 10%;
}

.custom-dialog .show-pwd {
  position: absolute;
  top: 30px;
  margin-top: 10px;
  left: 85%;
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
.confirm-button,
.captcha-button {
  padding: 12px 25px;
  font-size: 14px;
  border: none;
  border-radius: 20px;
  cursor: pointer;
  transition: transform 0.3s, background-color 0.3s;
  font-weight: bold;
}

.captcha-button {
  background-color: #6cb7f5;
  color: white;
  margin-right: 10px;
  box-shadow: 0 4px 10px rgba(0, 0, 0, 0.2);
  position: relative;
  padding: 0;
  height: 36px;
  line-height: 36px;
  width: 30%;
  left: calc(55%);
  top: -35px;
  letter-spacing: 10px;
}

.captcha-button:hover {
  background-color: #47a5f1;
  transform: scale(1.1);
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

.avatar-uploader >>> .el-upload {
  border: 1px dashed #aaa;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  top: 30px;
  left: calc(50% - 89px);
  margin: 20px 0;
  overflow: hidden;
}

.avatar-uploader >>> .el-upload:hover {
  border-color: #6c7ae0;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
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

/* 不同分辨率适配 */

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
    width: calc(90vw * 0.9 + 10px);
    height: 50%;
    line-height: calc(((70vh - 30px * 4 - 20px) / 5 - 40px) * 0.5);
    top: 25%;
    text-align: center;
    font-size: 15px;
    font-weight: 600;
    color: #555;
  }

  .jelly-btn {
    border-radius: 10px;
    padding: 4px 6px;
    font-size: 10px;
  }

  .table-content {
    overflow: hidden;
    max-width: 80px;
  }

  .custom-dialog >>> .el-dialog {
    width: 90vw;
  }

  .custom-input >>> .el-input__inner {
    left: 15%;
    width: 70%;
  }

  .custom-dialog .pwd-input >>> .el-input__inner {
    width: 90%;
    left: 5%;
  }

  .custom-input >>> .el-input__inner:hover {
    left: 5%;
    width: 90%;
  }

  .custom-dialog .captcha-input >>> .el-input__inner {
    width: 45%;
    left: 5%;
  }

  .captcha-button {
    width: 35%;
    left: calc(57.5%);
    letter-spacing: 2px;
  }

  .el-form >>> .el-form-item__error {
    left: 15%;
  }

  .el-form-item:hover >>> .el-form-item__error {
    left: 5%;
  }
}
</style>
