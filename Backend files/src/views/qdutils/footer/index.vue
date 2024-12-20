<template>
  <div :class="footerClass">
    <section style=" margin-top: 20px;background-color: transparent; text-align: center; font-family: 'Arial', sans-serif;">
  <div style="width: 100%; margin: 0 auto; background-color: transparent; padding: 20px; border-radius: 8px;">
    <p style="font-size: 18px; color: #333; line-height: 1.6;">
      版权所有©2024 保留所有权利。
      <br>
      <span style="font-size: 20px; font-weight: bold; color: #007BFF;">新的开始 新的启点</span>
    </p>
  </div>
</section>

  </div>
</template>

<script>
export default {
  name: 'Index',
  props: {
    footerClass: {
      type: String,
      required: true
    }
  }
}
</script>

<style scoped>
  .normal{
    position: relative;
    width: 100vw;
  }

  .club-list{
    position: relative;
    top: 3vh;
    width: 100vw;
  }

  .club-meeting{
    position: fixed;
    top: 85vh;
    width: 100vw;
  }
</style>
