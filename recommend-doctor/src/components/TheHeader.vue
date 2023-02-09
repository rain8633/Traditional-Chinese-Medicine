<template>
  <div class="the-header">
    <!--图标-->
    <div class="header-logo" @click="goHome()">
      <span>{{musicName}}</span>
    </div>
    <ul class="navbar" ref="change">
      <li :class="{active: item.name === activeName}" v-for="item in navMsg" :key="item.path" @click="goPage(item.path, item.name)">
        {{item.name}}
      </li>
      <li v-if="!loginIn" :class="{active: item.name === activeName}" v-for="item in loginMsg" :key="item.type" @click="goPage(item.path, item.name)">{{item.name}}</li>
    </ul>
    <!--设置-->
    <div class="header-right" v-show="loginIn">
         <div style="margin-left:20px;">
       <el-badge :value="newbingliNum"  type="primary">
      <el-tooltip v-if="newbingliNum>0" class="item" effect="dark" content="您有新的病历未查看" placement="top-start">
       <el-button @click="newBingLi()" size="small">病历</el-button>
     </el-tooltip>
     <el-tooltip v-if="newbingliNum==0" class="item" effect="dark" content="暂未有新的病历" placement="top-start">
       <el-button @click="newBingLi()" size="small">病历</el-button>
     </el-tooltip>
        </el-badge>
       <el-badge :value="newhuifuNum"  type="warning" style="margin-left:20px;">
       <el-tooltip v-if="newhuifuNum>0" class="item" effect="dark" content="您有新的回复未查看" placement="top-start">
       <el-button @click="newComment()" size="small">回复</el-button>
     </el-tooltip>
<el-tooltip v-if="newhuifuNum==0" class="item" effect="dark" content="暂未有新的回复" placement="top-start">
       <el-button @click="newComment()" size="small">回复</el-button>
     </el-tooltip>

     </el-badge>
      </div>
      <div id="user">
        <img :src="path+doctor.pic"/>
      </div>
      <ul class="menu">
        <li v-for="(item, index) in menuList" :key="index" @click="goMenuList(item.path)">{{item.name}}</li>
      </ul>
    </div>
  </div>
</template>

<script>
import { mixin } from '../mixins'
import { mapGetters } from 'vuex'
import { navMsg, loginMsg, menuList } from '../assets/data/header'

export default {
  name: 'the-header',
  mixins: [mixin],
  data () {
    return {
      isLogin:false,
      musicName: '中医在线诊断平台',
      navMsg: [], // 左侧导航栏
      loginMsg: [], // 右侧导航栏
      menuList: [], // 用户下拉菜单项
      keywords: '',
      doctor: {
       id:'',
       dname:'',
       password:'',
       phone:'',
       email:'',
       ksName:'',
       cityName:'',
       hospName:'',
       sex:'',
       pic:''
     },
     newbingliNum:0,
     newhuifuNum:0,
    }
  },
  computed: {
    ...mapGetters([
      'loginIn',
      'activeName',
    ])
  },
  created () {

    this.navMsg = navMsg
    this.loginMsg = loginMsg
    this.menuList = menuList
    let doctor=JSON.parse(window.sessionStorage.getItem("doctor"));
   if(doctor != null){
     this.doctor=doctor;
   }
    this.queryNewBingLiNum(doctor.id);
    this.queryNewCommentNum(doctor.id);
  },
  mounted () {
    document.querySelector('#user').addEventListener('click', function (e) {
      document.querySelector('.menu').classList.add('show')
      e.stopPropagation()// 关键在于阻止冒泡
    }, false)
    // 点击“菜单”内部时，阻止事件冒泡。(这样点击内部时，菜单不会关闭)
    document.querySelector('.menu').addEventListener('click', function (e) {
      e.stopPropagation()
    }, false)
    document.addEventListener('click', function () {
      document.querySelector('.menu').classList.remove('show')
    }, false)
  },
  methods: {
    goHome () {
      this.$router.push({path: '/'})
    },
    goPage (path, value) {
      document.querySelector('.menu').classList.remove('show')
      this.changeIndex(value)
      if (!this.loginIn && path === '/zd-list') {
        this.notify('请先登录', 'warning')
      }else {
        this.$router.push({path: path})
      }
    },

    queryNewBingLiNum(id){
      this.$http.post('/bingli/queryNewBingliNum/'+id).then(res=>{
            if(res.data.code == 200){
              this.newbingliNum = res.data.data
            }
      })
    },
     queryNewCommentNum(id){
      this.$http.post('/comment/queryNewcommentNum/'+id).then(res=>{
            if(res.data.code == 200){
              this.newhuifuNum = res.data.data
            }
      })
    },
    changeIndex (value) {
      this.$store.commit('setActiveName', value)
    },
    goMenuList (path) {
      if (path === 0) {
        this.$store.commit('setIsActive', false)
      }
      document.querySelector('.menu').classList.remove('show')
      if (path) {
         if (!this.loginIn && path === '/myorder') {
        this.notify('请先登录', 'warning')
      }
       else this.$router.push({path: path})
      } else {
        this.$store.commit('setLoginIn', false)
        //  window.sessionStorage.clear()
         window.sessionStorage.removeItem("user");
        this.$router.go(0)
      }
    },
    newBingLi(){
      this.$router.push({path: '/newBingli'})
    },
    newComment(){
      this.$router.push({path: '/newComment'})
    }
    // goSearch () {
    //   this.$store.commit('setSearchword', this.keywords)
    //   this.$router.push({path: '/search', query: {keywords: this.keywords}})
    // }
  }
}
</script>

<style lang="scss">
@import '../assets/css/the-header.scss';
.el-badge__content.is-fixed{
  top:20px !important;
}
</style>
