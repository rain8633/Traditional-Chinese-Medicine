<template>
<div class="home">
  <the-header/>
  <div class="riji-list">
    <ul class="riji-list-header">
      <li v-for="(item,index) in riji" :key="index"
      :class="{active:item.name === activeName}"
       @click="handleChangeView(item.path,item.name)">
        {{item.name}}
      </li>
    </ul>
    <div class="song-content">
      <router-view></router-view>
    </div>
  </div>
  <scroll-top/>
  <the-footer/>

  </div>
</template>

<script>

import $ from 'jquery';
import TheHeader from '../../components/TheHeader.vue'
import {riji} from '../../assets/data/riji'
import TheFooter from '../../components/TheFooter.vue'
import ScrollTop from '../../components/ScrollTop.vue'

export default {
  data() {
    return {

       doctorId:'',
       riji:[],
       activeName: '全部病历',

    }
  },
  components: {
     TheHeader,
     TheFooter,
     ScrollTop
  },
  created() {
    this.$router.push('/AllBingli')
  },
  mounted () {
    this.doctorId = JSON.parse(window.sessionStorage.getItem("doctor")).id
    this.riji = riji
    this.summernote()
  },
  methods: {
    handleChangeView(path,name){
      this.activeName = name
       this.$router.push(path);
    },
    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      console.log(this.currentPage)
      // this.getAllRiJiList()
    },
    summernote(){
       $('#summernote').summernote({
         height: 300,
         lang:'zh-CN',
          callbacks: {
            onChange: function(contents, $editable) {
            console.log(contents);
      },
          onImageUpload: function(files) {
	        	sendFile(files[0]);
	        }

  }
       });
    },
    sendFile(files){
        var data = new FormData();
    	    data.append("files", files);
          data.append("doctorId",this.doctorId);

    },
    tijiao(){

    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/riji.scss';
</style>
