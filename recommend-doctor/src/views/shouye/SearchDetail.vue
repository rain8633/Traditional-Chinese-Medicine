<template>
<div class="home">
  <the-header/>
  <div class="song-list-album" v-if="search.type==0">
    <!-- 类型为0是药材 -->
    <div class="album-slide">
      <div class="album-img">
        <img :src="search.src" alt="">
      </div>
    </div>
    <div class="album-content">
      <div class="album-title">
        <span>药材名称:&nbsp;&nbsp;
          <p  style="margin-left: 110px;margin-top:-37px;"  v-html="search.name"></p>
        </span>
      </div>
      <div class="album-address">
        <p>功效:&nbsp;&nbsp;{{search.gongxiao}}</p>
        <p>味:&nbsp;&nbsp;{{search.wei}} &nbsp;&nbsp;</p>
        <p>用量:&nbsp;&nbsp;{{search.yongliang}}</p>
        <p>形状:&nbsp;&nbsp;{{search.xingzhuang}}</p>
      </div>
    </div>
  </div>

  <div class="song-list-album" v-if="search.type==1">
    <!-- 类型为1是药方 -->
    <div class="album-slide">
      <!-- <div class="album-img">
        <img :src="search.src" alt="">
      </div> -->
    </div>
    <div class="album-content">
      <div class="album-title">
        <span>药方名称:&nbsp;&nbsp;
          <p  style="margin-left: 110px;margin-top:-37px;"  v-html="search.name"></p>
        </span>
      </div>
      <div class="album-address">
        <p>出处:&nbsp;&nbsp;{{search.chuchu}}</p>
        <p>处方:&nbsp;&nbsp;{{search.chufang}} &nbsp;&nbsp;</p>
        <p>功效:&nbsp;&nbsp;{{search.gongxiao}}</p>
        <p>主治:&nbsp;&nbsp;{{search.zhuzhi}}</p>
      </div>
    </div>
  </div>


    <scroll-top/>
    <the-footer/>
</div>
</template>

<script>
import { mixin } from '../../mixins'
import { mapGetters } from 'vuex'
import TheHeader from '../../components/TheHeader.vue'
import TheFooter from '../../components/TheFooter.vue'
import ScrollTop from '../../components/ScrollTop.vue'
// import { getRankOfSongListId, setRank, getSongOfId, getListSongOfSongId } from '../api/index'

export default {
  name: 'search-detail',
  components: {
    TheHeader,
    TheFooter,
    ScrollTop,
  },
  data () {
    return {
      search: {},
      searchId: '',
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 登录标识
      'searchResult', // 搜索结果信息
    ])
  },
  created () {
    this.searchId = this.searchResult.id
    this.search = this.searchResult
  },
  mixins: [mixin],
  methods: {
   }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/search-list-album.scss';
</style>
