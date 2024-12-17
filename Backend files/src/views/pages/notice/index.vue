<template>
        <div class="sub-header">
        <div class="container">
            <div class="row">
                <div class="col-lg-8 col-sm-8">
                    <div class="left-content">
                        <p>欢迎来到大学社团公告页面，查看最新活动和通知。</p>
                    </div>
                </div>
                <div class="col-lg-4 col-sm-4">
                    <div class="right-icons">
                        <ul>
                            <li><a href="/Backend files/src/views/login/index.vue">登录</a></li>
                            <li><a href="#">注册</a></li>
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <!-- Header -->
    <header class="header-area header-sticky">
        <div class="container">
            <div class="row">
                <div class="col-12">
                    <nav class="main-nav">
                        <a href="index.html" class="logo">大学社团</a>
                        <ul class="nav">
                            <li class="scroll-to-section"><a href="/Backend files/src/views/main/index.vue" class="active">主页</a></li>
                            <li><a href="#announcements">公告</a></li>
                            <li><a href="#Activity">活动</a></li>
                            <li><a href="/Backend files/src/views/pages/leave/index.vue">联系我们</a></li>
                        </ul>
                        <a class="menu-trigger">
                            <span>Menu</span>
                        </a>
                    </nav>
                </div>
            </div>
        </div>
    </header>

    <!-- 公告区域 Start -->
    <section id="announcements" class="section announcement-section">
        <div class="container">
            <div class="section-heading">
                <h2>最新公告</h2>
            </div>

            <div class="row" id="announcement-list">
                <!-- 公告项将通过 JavaScript 动态加载 -->
            </div>
        </div>
    </section>

    <foot />
</template>

<script>
import foot from '../../qdutils/footer/index'

export default {
    name: 'Index',
    components:{
        foot
    },
    data() {
        return {
            
        }
    },
}
</script>