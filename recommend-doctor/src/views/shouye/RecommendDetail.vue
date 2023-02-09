<template>
<div class="home">
  <the-header/>
  <div class="song-list-album">
 <el-row :gutter="20">
  <el-col :span="10">
 <div style="margin-top:20px">
  <el-card class="box-card" style="width:70%;margin-left:260px;max-height:1000px;">
  <div slot="header" class="clearfix">
    <span>病历详情</span>
    <el-button style="float: right; padding: 3px 0" type="text">操作按钮</el-button>
  </div>
  <div class="text item" style="line-height:35px;white-space:normal; word-break:break-all:width;overflow:hidden;">
    <strong>患者姓名:&nbsp;&nbsp;</strong>{{bingli.realName}}<br>
    <strong>患者年龄:&nbsp;&nbsp;</strong>{{bingli.clientAge}}<br>
    <strong>患者联系方式:&nbsp;&nbsp;</strong>{{bingli.phone}}<br>
    <strong>病情自述:&nbsp;&nbsp;</strong>{{bingli.info}}
  </div>
  <div class="demo-image__preview">
  <div><strong style="margin-bottom:20px;">病症图片:(点击查看大图)&nbsp;&nbsp;</strong></div>
  <el-image
    style="width: 100px; height: 100px;margin-top:20px;"
    :src="path+pic1"
    :preview-src-list="[srcList[0]]">
  </el-image>
   <el-image
    style="width: 100px; height: 100px;margin-left:20px;"
    :src="path+pic2"
    :preview-src-list="[srcList[1]]">
  </el-image>
   <el-image v-if="pic3!=null && pic3!=''"
    style="width: 100px; height: 100px;margin-left:20px;"
    :src="path+pic3"
    :preview-src-list="[srcList[2]]">
  </el-image>
</div>
</el-card>
</div>
</el-col>
<el-col :span="10">
 <el-card class="box-card" style="width:80%;margin-left:120px;margin-top:20px;max-height:1000px;">
   <div slot="header" class="clearfix">
    <span>诊断结果</span>
  </div>
  <el-form ref="Dialog" :model="Dialog" label-width="80px">
  <el-form-item label="主诉">
    <el-input type="textarea" v-model="Dialog.zhusu" disabled></el-input>
  </el-form-item>
  <el-form-item label="现病史">
   <el-input type="textarea" v-model="Dialog.xbshi"  disabled></el-input>
  </el-form-item>
  <el-form-item label="既往史">
    <el-input type="textarea" v-model="Dialog.jwshi" disabled></el-input>
  </el-form-item>
    <el-form-item label="诊断结果">
    <el-input type="textarea"  v-model="Dialog.result" disabled></el-input>
  </el-form-item>
  <el-form-item label="药方/治疗方案">
    <el-input type="textarea" v-model="Dialog.solution" disabled></el-input>
  </el-form-item>
  <el-form-item label="医嘱">
    <el-input type="textarea" v-model="Dialog.yizhu" disabled></el-input>
  </el-form-item>
  <el-form-item>
  </el-form-item>
</el-form>
 </el-card>
 </el-col>
</el-row>
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


export default {
  name: 'recommend-detail',
  components: {
    TheHeader,
    TheFooter,
    ScrollTop,
  },
  data () {
    return {
      bingli: {},
      bingliId: '', // 病历ID
      num: 1,
      pic1:'',
      pic2:'',
      pic3:'',
      srcList:[],
       Dialog: {},

    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 登录标识
      'BingLi',
    ])
  },
  created () {
    this.bingliId = this.$route.params.id

    this.queryBingLi()
    this.queryDialog()
  },

  mixins: [mixin],
  methods: {
      queryDialog(){
       this.$http.post('/dialog/queryDialogByBingLiId/'+this.bingliId).then(res=>{
         if(res.data.code == 200){
                this.Dialog = res.data.data
             console.log(this.Dialog)
         }
       })
     },
     queryBingLi(){
       this.$http.post('/bingli/queryBingLiById/'+this.bingliId).then(res => {
            if(res.data.code == 200){
              this.bingli = res.data.data
                 this.pic1 = this.bingli.pic1
                 this.pic2 = this.bingli.pic2
                 this.pic3 = this.bingli.pic3
               this.srcList.push('http://localhost:9090'+this.pic1)
                this.srcList.push('http://localhost:9090'+this.pic2)
               if(this.pic3!=null && this.pic3!=''){
                 this.srcList.push('http://localhost:9090'+this.pic3)
              }
            }
       })
     }

  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/trip-list-album.scss';
</style>
