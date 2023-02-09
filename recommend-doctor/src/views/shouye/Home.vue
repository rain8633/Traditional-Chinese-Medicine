<template>
  <div class="home">
    <the-header/>
    <swiper/>
    <div class="section" v-for="(item, index) in tripsList" :key="index">
      <div class="section-title">欢迎使用在线诊断平台</div>
      <home-list></home-list>
    </div>
    <scroll-top/>
    <the-footer/>
  </div>
</template>


<script type="text/javascript">

import Swiper from "../../components/Swiper.vue"
import TheHeader from '../../components/TheHeader.vue'
import TheFooter from '../../components/TheFooter.vue'
import HomeList from '../../components/HomeList.vue'
import ScrollTop from '../../components/ScrollTop.vue'


export default {
  data() {
    return {
      tripsList:[
        {name:'热门景点推荐',list:[]}
      ]
    }
  },
  components: {
    Swiper,
    TheHeader,
    TheFooter,
    HomeList,
    ScrollTop
  },
  created () {

  },
  methods: {

  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/home.scss';
</style>
