<template>
  <div>
    <!--导航-->
    <navs />

    <!--核心内容start-->

    <section id="meetings" class="meetings-page">
      <div class="container">
        <div v-if="acitity!=null" class="row">
          <div class="col-lg-12">
            <div class="row">
              <div class="col-lg-12">
                <div class="meeting-single-item">
                  <div class="thumb">
                    <div class="date">
                      <h6>Nov <span>12</span></h6>
                    </div>

                  </div>
                  <div class="down-content">
                    <a href="meeting-details.html"><h4>活动主题</h4></a>
                    <p>{{ acitity.name }}</p>
                    <p class="description">
                      {{ acitity.ctInfo }} </p>
                    <div class="row">
                      <div class="col-lg-4">
                        <div class="hours">
                          <h5>时间</h5>
                          <p>{{ acitity.acTime }}</p>

                        </div>
                      </div>
                      <div class="col-lg-4">
                        <div class="location">
                          <h5>地点</h5>
                          <p>{{ acitity.ctAddress }}</p>
                        </div>
                      </div>
                      <div class="col-lg-4">
                        <div class="book now">
                          <h5>主持人</h5>
                          <p>{{ acitity.host }}</p>
                        </div>
                      </div>

                    </div>
                  </div>
                </div>
              </div>

            </div>
          </div>
        </div>

      </div>
    </section>
    <section class="contact-us">

      <div class="container">
        <div class="row">
          <div class="col-lg-9 align-self-center">
            <div class="row">
              <div class="col-lg-12">

                <el-form id="contact" ref="dataForm" :rules="rules" :model="temp">
                  <div class="row">
                    <div class="col-lg-12">
                      <h2>让我们保持联系</h2>
                    </div>
                    <div class="col-lg-4">
                      <fieldset>
                        <el-form-item prop="realname">
                          <input v-model="temp.realname" type="text" placeholder="姓名..." required="">
                        </el-form-item>
                      </fieldset>
                    </div>
                    <div class="col-lg-4">
                      <fieldset>
                        <input v-model="temp.sex" type="text" placeholder="性别" required="">
                      </fieldset>
                    </div>
                    <div class="col-lg-4">
                      <fieldset>
                        <el-form-item prop="tel">
                          <input v-model="temp.tel" type="text" placeholder="电话" required="">
                        </el-form-item>
                      </fieldset>
                    </div>

                    <div class="col-lg-4">
                      <fieldset>
                        <el-form-item prop="email">
                          <input v-model="temp.email" type="text" pattern="[^ @]*@[^ @]*" placeholder="邮箱..." required="">
                        </el-form-item>
                      </fieldset>
                    </div>
                    <div class="col-lg-4">
                      <fieldset>
                        <input v-model="temp.address" type="text" placeholder="地址..." required="">
                      </fieldset>
                    </div>
                    <div class="col-lg-4">
                      <fieldset>
                        <input v-model="temp.weixin" type="text" placeholder="微信...*" required="">
                      </fieldset>
                    </div>

                    <div class="col-lg-12">
                      <fieldset>
                        <textarea id="message" v-model="temp.content" type="text" class="form-control" placeholder="申请内容..." required="" />
                      </fieldset>
                    </div>

                    <div class="col-lg-12">
                      <fieldset>
                        <button id="form-submit" type="submit" class="button" @click="addSave()">现在发送消息</button>
                      </fieldset>
                    </div>

                  </div>
                </el-form>

              </div>
            </div>
          </div>

          <div class="col-lg-3">
            <div class="right-info">
              <ul>
                <li>
                  <h6>社团类型名称</h6>
                  <span>{{ club.typeInfo.name }}</span>
                </li>
                <br>
                <li>
                  <h6>社团名称</h6>
                  <span>{{ club.clubName }}</span>
                </li>
                <br>
                <li>
                  <h6>社长姓名</h6>
                  <span>{{ club.captainName }}</span>
                </li>
                <br>
                <li>
                  <h6>创建时间</h6>
                  <span>{{ club.createTime }}</span>
                </li>
              </ul>
            </div>
          </div>
        </div>
      </div>

    </section>

    <foot />

    <!--核心内容end-->

  </div>
</template>

<script>
import navs from '../../qdutils/navs/index'
import foot from '../../qdutils/footer/index'
import request from '@/utils/request'
export default {
  name: 'Index',
  components: { navs, foot },

  data() {
    return {
      acitity: {},
      team: {},
      temp: {
        realname: '',
        tel: '',
        email: '',
        address: '',
        qq: '',
        weixin: '',
        content: ''
      },
      teamId: '',

      rules: {
        realname: [{ required: true, message: '请填写正确的姓名格式(3个长度以上)', trigger: 'blur' }],
        tel: [{ required: true, message: '请填写电话', trigger: 'blur' }, {
          pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
          message: '请输入正确的手机号码',
          trigger: 'blur'
        }],
        email: [{ required: true, message: '请填写邮箱', trigger: 'blur' }, {
          pattern: /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((.[a-zA-Z0-9_-]{2,3}){1,2})$/,
          message: '请输入正确的邮箱',
          trigger: 'blur'
        }]

      }

    }
  },
  created() {
    const teamId = this.$route.query.teamId// 社团的id
    this.teamId = teamId
    this.getAcitityInfoByTeamId(teamId)
    this.getTeamById(teamId)
  },
  methods: {
    getAcitityInfoByTeamId(teamId) {
      request('activity/findByTeamId?id=' + teamId).then(res => {
        this.acitity = res.data
      })
    },
    getTeamById(teamId) {
      request('team/findTeamById?id=' + teamId).then(res => {
        this.team = res.data
      })
    },
    // 添加
    addSave(temp) {
      this.temp.teamId = this.teamId
      // 加验证规则
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          // 向后端发送请求，实现添加功能
          request.post('applyInfo/addInfo', this.temp).then(res => {
            if (res.code === 20000) {
              // 关闭弹出框
              this.$alert(res.msg)
            } else {
              this.$alert('申请失败...')
            }
          })
        }
      })
    }

  }
}
</script>

<style scoped>
  @import "../../../vendor/bootstrap/css/bootstrap.min.css";
  @import "../../../assets/css/templatemo-edu-meeting.css";
</style>
