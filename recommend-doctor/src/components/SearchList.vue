<template>
  <div class="search-list">
       <ul class="section-content">
      <li class="content-item" v-for="(item, index) in searchList" :key="index">
        <div class="kuo" @click="goAblum(item)">
          <img class="item-img" v-if="item.src!=null" :src="item.src" alt="">
          <img class="item-img" v-if="item.src==null" src="@/assets/img/zanwu.jpg" alt="">
        </div>
        <p class="item-name" v-html="item.name"></p>
      </li>
    </ul>
  </div>
</template>

<script type="text/javascript">
export default {
  name:'search-list',
  props: [
     'searchList'
  ],
  data() {
    return {

    }
  },
  components: {

  },
  methods: {
     goAblum(item){
        this.$store.commit('setSearchResult', item)
        this.$router.push({path: `/search-detail/${item.id}`})
     }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/search-list.scss';
</style>
