<template>
  <div class="main">
    <bgAnimation bg-animation-path="./animation/BgAnimation.json" />
    <backBtnAnimation
      back-btn-animation-path="./animation/BackBtnAnimation.json"
      back-url="/"
      btn-class="login-bakck-btn"
    />
    <div id="body">
      <div class="shell">
        <div id="a-container" class="container a-container is-hidden">
          <el-form id="a-form" ref="registerForm" :rules="registerRules" :model="registerForm" autocomplete="on">
            <h2 class="form_title title">注册账号</h2>
            <span class="form_span">请使用您的邮箱注册</span>
            <el-form-item prop="userName">
              <input v-model="registerForm.userName" type="text" class="form_input" name="userName" placeholder="用户名">
            </el-form-item>
            <el-form-item prop="email">
              <input v-model="registerForm.email" type="text" class="form_input" name="eamil" placeholder="邮箱">
            </el-form-item>
            <el-form-item prop="password">
              <input
                v-model="registerForm.password"
                :type="passwordType"
                class="form_input"
                name="password"
                placeholder="密码"
              >
              <span class="show-pwd" @click="showPwd">
                <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
              </span>
            </el-form-item>
            <el-form-item prop="captcha">
              <div class="captcha-container">
                <input
                  v-model="registerForm.captcha"
                  type="text"
                  class="form_input form_captcha"
                  name="captcha"
                  placeholder="验证码"
                >
                <button class="button submit button_captcha" @click.prevent="handleCaptcha">发送验证码</button>
              </div>
            </el-form-item>
            <button class="button submit" @click.prevent="handleRegister">注册</button>
          </el-form>
        </div>

        <div id="b-container" class="container b-container">
          <el-form id="b-form" ref="loginForm" :rules="loginRules" :model="loginForm" autocomplete="on">
            <h2 class="form_title title">登录账号</h2>
            <span class="form_span">请使用您的邮箱登录</span>
            <el-form-item prop="email">
              <input v-model="loginForm.email" type="text" name="username" class="form_input" placeholder="邮箱">
            </el-form-item>
            <el-form-item prop="password">
              <input
                v-model="loginForm.password"
                name="password"
                :type="passwordType"
                class="form_input"
                placeholder="密码"
                @keyup.enter.native="handleLogin"
              >
              <span class="show-pwd" @click="showPwd">
                <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
              </span>
            </el-form-item>

            <a class="form_link" @click.prevent="chanageLost"><span>忘记了密码?</span></a>
            <button class="button submit" @click.prevent="handleLogin">登录</button>
          </el-form>
        </div>

        <div id="c-container" class="container b-container is-hidden">
          <el-form id="b-form" ref="lostForm" :rules="lostRules" :model="lostForm" autocomplete="on">
            <h2 class="form_title title">重置密码</h2>
            <span class="form_span">请使用您的邮箱重置密码</span>
            <el-form-item prop="email">
              <input v-model="lostForm.email" type="text" name="username" class="form_input" placeholder="邮箱">
            </el-form-item>
            <el-form-item prop="password">
              <input
                v-model="lostForm.password"
                name="password"
                :type="passwordType"
                class="form_input"
                placeholder="新密码"
                @keyup.enter.native="handleLost"
              >
              <span class="show-pwd" @click="showPwd">
                <svg-icon :icon-class="passwordType === 'password' ? 'eye' : 'eye-open'" />
              </span>
            </el-form-item>
            <el-form-item prop="captcha">
              <div class="captcha-container">
                <input
                  v-model="lostForm.captcha"
                  type="text"
                  class="form_input form_captcha"
                  name="captcha"
                  placeholder="验证码"
                >
                <button class="button submit button_captcha" @click.prevent="handleLostCaptcha">发送验证码</button>
              </div>
            </el-form-item>
            <a class="form_link" @click.prevent="chanageLost"><span>找回了密码?</span></a>
            <button class="button submit" @click.prevent="handleLost">重置密码</button>
          </el-form>
        </div>

        <div id="switch-cnt" class="switch">
          <div class="switch_circle" />
          <div class="switch_circle switch_circle-t" />
          <div id="switch-c1" class="switch_container is-hidden">
            <h2 class="switch_title title" style="letter-spacing: 0;">Welcome here!</h2>
            <p class="switch_description description">已经有账号了嘛，点此按钮跳转登录吧！</p>
            <button class="switch_button button switch-btn">登录</button>
          </div>

          <div id="switch-c2" class="switch_container">
            <h2 class="switch_title title" style="letter-spacing: 0;">Welcome here!</h2>
            <p class="switch_description description">来创建一个账号，加入心仪的社团吧！</p>
            <button class="switch_button button switch-btn">注册</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import bgAnimation from '../qdutils/bg/bg.vue'
import backBtnAnimation from '../qdutils/backbtn/backbtn.vue'
import request from '@/utils/request'

export default {
  name: 'LoginView',
  components: {
    bgAnimation,
    backBtnAnimation
  },
  data() {
    return {
      switchCtn: null,
      switchC1: null,
      switchC2: null,
      switchCircle: null,
      switchBtn: null,
      aContainer: null,
      bContainer: null,
      cContainer: null,
      allButtons: null,
      passwordType: 'password',
      loginForm: {
        email: '',
        password: ''
      },
      registerForm: {
        userName: '',
        email: '',
        password: '',
        captcha: ''
      },
      lostForm: {
        email: '',
        password: '',
        captcha: ''
      },
      loginRules: {
        email: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      },
      registerRules: {
        userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        captcha: [{ required: true, message: '请选择验证码', trigger: 'blur' }]
      },
      lostRules: {
        email: [{ required: true, message: '请输入邮箱', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }],
        captcha: [{ required: true, message: '请选择验证码', trigger: 'blur' }]
      },
      redirect: undefined,
      otherQuery: {}
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        const query = route.query
        if (query) {
          this.redirect = query.redirect
          this.otherQuery = this.getOtherQuery(query)
        }
      },
      immediate: true
    }
  },
  mounted() {
    // 定义 DOM 引用
    this.switchCtn = document.querySelector('#switch-cnt')
    this.switchC1 = document.querySelector('#switch-c1')
    this.switchC2 = document.querySelector('#switch-c2')
    this.switchCircle = document.querySelectorAll('.switch_circle')
    this.aContainer = document.querySelector('#a-container')
    this.bContainer = document.querySelector('#b-container')
    this.cContainer = document.querySelector('#c-container')
    this.switchBtn = document.querySelectorAll('.switch-btn')
    this.allButtons = document.querySelectorAll('.submit')

    const changeForm = () => {
      if (!(this.switchCtn)) return

      this.switchCtn.classList.add('is-gx')
      setTimeout(() => {
        this.switchCtn.classList.remove('is-gx')
      }, 1500)

      this.switchCtn.classList.toggle('is-txr')
      this.switchCircle[0].classList.toggle('is-txr')
      this.switchCircle[1].classList.toggle('is-txr')

      this.switchC1.classList.toggle('is-hidden')
      this.switchC2.classList.toggle('is-hidden')
      this.aContainer.classList.toggle('is-hidden')
      this.bContainer.classList.toggle('is-hidden')

      if (!(this.cContainer.classList.contains('is-hidden'))) {
        this.cContainer.classList.add('is-hidden')
        this.bContainer.classList.add('is-hidden')
      }

      this.aContainer.classList.toggle('is-txl')
      this.bContainer.classList.toggle('is-txl')
      this.bContainer.classList.toggle('is-z')
    }

    this.switchBtn.forEach((btn) =>
      btn.addEventListener('click', changeForm)
    )
  },
  methods: {
    showPwd() {
      if (this.passwordType === 'password') {
        this.passwordType = 'text'
      } else {
        this.passwordType = 'password'
      }
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true
          this.$store.dispatch('user/login', this.loginForm)
            .then(() => {
              this.$router.push({ path: this.redirect || '/', query: this.otherQuery })
              this.loading = false
            })
            .catch((error) => {
              console.log(error)
              this.loading = false
            })
        } else {
          console('fail')
        }
      })
    },
    handleRegister() {
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = false
        } else {
          console('fail')
        }
      })
    },
    handleCaptcha() {
      if (!(this.registerForm.userName)) {
        this.registerForm.userName = ' '
      }
      if (!(this.registerForm.password)) {
        this.registerForm.password = ' '
      }
      if (!(this.registerForm.captcha)) {
        this.registerForm.captcha = ' '
      }
      this.$refs.registerForm.validate(valid => {
        if (valid) {
          this.loading = false
          request.post('login/sendCaptcha?email=' + this.registerForm.email + '&type=0').then(
            res => {
              this.$notify({
                title: '成功',
                message: '发送验证码成功！',
                type: 'success',
                duration: 2000
              })
            }
          )
        } else {
          if (this.registerForm.userName === ' ') {
            this.registerForm.userName = ''
          }
          if (this.registerForm.password === ' ') {
            this.registerForm.password = ''
          }
          if (this.registerForm.captcha === ' ') {
            this.registerForm.captcha = ''
          }
          if (!(this.registerForm.email)) {
            this.registerForm.email = ''
          }
        }
      })
    },
    chanageLost() {
      this.bContainer.classList.toggle('is-hidden')
      this.cContainer.classList.toggle('is-hidden')
    },
    handleLostCaptcha() {
      if (!(this.lostForm.userName)) {
        this.lostForm.userName = ' '
      }
      if (!(this.lostForm.password)) {
        this.lostForm.password = ' '
      }
      if (!(this.lostForm.captcha)) {
        this.lostForm.captcha = ' '
      }
      this.$refs.lostForm.validate(valid => {
        if (valid) {
          this.loading = false
          request.post('login/sendCaptcha?email=' + this.lostForm.email + '&type=0').then(
            res => {
              this.$notify({
                title: '成功',
                message: '发送验证码成功！',
                type: 'success',
                duration: 2000
              })
            }
          )
        } else {
          if (this.lostForm.password === ' ') {
            this.lostForm.password = ''
          }
          if (this.lostForm.captcha === ' ') {
            this.lostForm.captcha = ''
          }
          if (!(this.lostForm.email)) {
            this.lostForm.email = ''
          }
        }
      })
    },
    handleLost() {

    },
    getOtherQuery(query) {
      return Object.keys(query).reduce((acc, cur) => {
        if (cur !== 'redirect') {
          acc[cur] = query[cur]
        }
        return acc
      }, {})
    }
  }
}
</script>

<style scoped>
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  user-select: none;
  z-index: 100;
}

.main {
  position: relative;
}

#body {
  position: absolute;
  z-index: 100;
  width: 100%;
  top: calc(25vh);
  text-align: left;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 12px;
  background-color: transparent;
  color: #a0a5a8;
}

.el-form {
  text-align: center;
}

.shell {
  position: relative;
  width: 1000px;
  min-width: 1000px;
  min-height: 600px;
  height: 600px;
  padding: 25px;
  background-color: transparent;
  box-shadow: 10px 10px 10px #d1d9e6, -10px -10px 10px #f9f9f9;
  border-radius: 12px;
  overflow: hidden;
}

@media (max-width: 1200px) {
  .shell {
    transform: scale(0.7);
  }
}

@media (max-width: 1000px) {
  .shell {
    transform: scale(0.6);
  }
}

@media (max-width: 800px) {
  .shell {
    transform: scale(0.5);
  }
}

@media (max-width: 400px) {
  .shell {
    transform: scale(0.4);
  }
}

.container {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  width: 600px;
  height: 100%;
  padding: 25px;
  background-color: transparent;
  transition: 1.25s;
}

.form {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  width: 100%;
  height: 100%;
}

.form_input {
  width: 350px;
  height: 40px;
  margin-top: 4px;
  margin-bottom: 25px;
  padding-left: 25px;
  font-size: 13px;
  letter-spacing: 0.15px;
  border: none;
  outline: none;
  background-color: transparent;
  transition: 0.25s ease;
  border-radius: 8px;
  box-shadow: inset 2px 2px 4px #d1d9e6, inset -2px -2px 4px #f9f9f9;
}

.show-pwd {
  position: absolute;
  right: 15px;
  top: 8px;
  font-size: 16px;
  color: #889aa4;
  cursor: pointer;
  user-select: none;
}

.el-form>>>.el-form-item__error {
  top: 41px;
  color: #e74c3c;
  font-weight: bold;
  background: rgba(231, 76, 60, 0.1);
  padding: 3px 6px;
  border-radius: 4px;
  margin-top: 5px;
  line-height: 1.5;
  box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
  transition: all 0.3s ease-in-out;
}

.el-form>>>.el-form-item__error:hover {
  background: rgba(231, 76, 60, 0.2);
  /* 鼠标悬浮时加深背景色 */
  transform: scale(1.05);
  /* 轻微放大 */
}

.form_input:focus {
  box-shadow: inset 4px 4px 4px #d1d9e6, inset -4px -4px 4px #f9f9f9;
}

.form_captcha {
  position: relative;
  width: 200px;
}

.form_span {
  margin-top: 12px;
  margin-bottom: 12px;
}

.form_link {
  display: block;
}

.form_link span {
  color: #181818;
  font-size: 15px;
  margin-top: 25px;
  border-bottom: 1px solid #a0a5a8;
  line-height: 2;
}

.title {
  font-size: 34px;
  font-weight: 700;
  line-height: 3;
  color: #181818;
  letter-spacing: 10px;
}

.description {
  font-size: 14px;
  letter-spacing: 0.25px;
  text-align: center;
  line-height: 1.6;
}

.button {
  width: 180px;
  height: 50px;
  border-radius: 25px;
  margin-top: 20px;
  font-weight: 700;
  font-size: 14px;
  letter-spacing: 1.15px;
  background-color: #4870e2;
  color: #f9f9f9;
  box-shadow: 8px 8px 16px #d1d9e6, -8px -8px 16px #f9f9f9;
  border: none;
  outline: none;
}

.captcha-container {
  display: flex;
  width: 350px;
}

.button_captcha {
  position: relative;
  left: 30px;
  margin: 4px 0;
  height: 40px;
  width: 120px;
}

.a-container {
  z-index: 100;
  left: calc(100% - 600px);
}

.b-container {
  left: calc(100% - 600px);
  z-index: 0;
}

.switch {
  display: flex;
  justify-content: center;
  align-items: center;
  position: absolute;
  top: 0;
  left: 0;
  height: 100%;
  width: 400px;
  padding: 50px;
  z-index: 200;
  transition: 1.25s;
  background-color: transparent;
  overflow: hidden;
  box-shadow: 4px 4px 10px #d1d9e6, -4px -4px 10px #d1d9e6;
}

.switch_circle {
  position: absolute;
  width: 500px;
  height: 500px;
  border-radius: 50%;
  background-color: transparent;
  box-shadow: inset 8px 8px 12px #b8bec7, inset -8px -8px 12px #fff;
  bottom: -60%;
  left: -60%;
  transition: 1.25s;
}

.switch_circle-t {
  top: -30%;
  left: -60%;
  width: 300px;
  height: 300px;
}

.switch_container {
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  position: absolute;
  width: 400px;
  padding: 50px 55px;
  transition: 1.25s;
}

.switch_button {
  cursor: pointer;
}

.switch_button:hover,
.submit:hover {
  box-shadow: 6px 6px 10px #d1d9e6, -6px -6px 10px #f9f9f9;
  transform: scale(0.985);
  transition: 0.25s;
}

.switch_button:active,
.switch_button:focus {
  box-shadow: 2px 2px 6px #d1d9e6, -2px -2px 6px #f9f9f9;
  transform: scale(0.97);
  transition: 0.25s;
}

.is-txr {
  left: calc(100% - 400px);
  transition: 1.25s;
  transform-origin: left;
}

.is-txl {
  left: 0;
  transition: 1.25s;
  transform-origin: right;
}

.is-z {
  z-index: 200;
  transition: 1.25s;
}

.is-hidden {
  visibility: hidden !important;
  opacity: 0 !important;
  position: absolute !important;
  transition: 1.25s !important;
}

.is-gx {
  animation: is-gx 1.25s;
}

@keyframes is-gx {

  0%,
  10%,
  100% {
    width: 400px;
  }

  30%,
  50% {
    width: 500px;
  }
}
</style>

