<template>
  <div class="the-footer">
   <p v-for="(item,index) in list" :key="index">
    <font size="3">{{item}}</font>
   </p>
    <!-- <div>
<span style="color:#FFFFFF;">
    <p><font size="3">版权所有 © 山东农业大学信息学院农业大数据研究中心 2020</font></p></span>
</div> -->
  </div>
</template>

<script type="text/javascript">
export default {
   name: 'the-footer',
  data() {
    return {
      list:
       [
       `关于 | 帮助 | 条款 | 反馈`,
        `Copyright © 2022`,
        `Designed To BiShe`
        ]

    }
  }
}
</script>

<style lang="scss" scoped>
@import "../assets/css/the-footer.scss";
</style>
