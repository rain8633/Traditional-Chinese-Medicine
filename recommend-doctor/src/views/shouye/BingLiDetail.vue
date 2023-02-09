<template>
<div class="home">
  <the-header/>
  <div class="song-list-album">
 <el-row :gutter="20">
  <el-col :span="15">
 <div style="margin-top:20px">
  <el-card class="box-card" style="width:60%;margin-left:200px;max-height:1000px;">
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

 <el-card class="box-card" style="width:60%;margin-left:200px;margin-top:20px;max-height:1000px;">
   <div slot="header" class="clearfix">
    <span>诊断结果</span>
  </div>
  <el-form ref="Dialogform" :model="Dialogform" label-width="80px">
  <el-form-item label="主诉">
    <el-input type="textarea" v-model="Dialogform.zhusu" :disabled="!show"></el-input>
  </el-form-item>
  <el-form-item label="现病史">
   <el-input type="textarea" v-model="Dialogform.xbshi" placeholder="输入病人的现病史" :disabled="!show"></el-input>
  </el-form-item>
  <el-form-item label="既往史">
    <el-input type="textarea" v-model="Dialogform.jwshi" placeholder="输入病人的既往史" :disabled="!show"></el-input>
  </el-form-item>
    <el-form-item label="诊断结果">
    <el-input type="textarea" @blur="queryRecommendList()" placeholder="输入您的诊断结果"  v-model="Dialogform.result" :disabled="!show"></el-input>
  </el-form-item>
  <el-form-item label="药方/治疗方案">
    <el-input type="textarea" v-model="Dialogform.solution" :disabled="!show"></el-input>
  </el-form-item>
  <el-form-item label="医嘱">
    <el-input type="textarea" v-model="Dialogform.yizhu" :disabled="!show"></el-input>
  </el-form-item>
  <el-form-item v-if="bingli.status!=3">
    <el-button v-if="show" type="primary" @click="addDialog()">提交诊断记录</el-button>
    <el-button v-if="update" type="primary" @click="cancleUpdate()">取消修改</el-button>
    <el-button  v-if="show" @click="cancle('Dialogform')">重置</el-button>
    <el-button  v-if="!show" type="primary" @click="updateDialog()">修改诊断记录</el-button>
  </el-form-item>
   <el-form-item v-if="bingli.status==3">
  </el-form-item>
</el-form>
 </el-card>
 </div>
  </el-col>
   <el-col :span="5">
  <div style="float:right;margin-right:-150px;">
  <recommend-list :recommendList="recommendList"></recommend-list>
  </div>
   </el-col>
 </el-row>
    <div class="album-content">
      <!--评论-->
      <div class="songs-body">
        <comment :bingliId="bingliId"></comment>
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
import Comment from '../../components/Comment.vue'
import RecommendList from '../../components/RecommendList.vue'
// import { getRankOfSongListId, setRank, getSongOfId, getListSongOfSongId } from '../api/index'

export default {
  name: 'bingli-detail',
  components: {
    TheHeader,
    TheFooter,
    ScrollTop,
    Comment,
    RecommendList
  },
  data () {
    return {
      bingli: {},
      bingliId: '', // 病历ID
      num: 1,
      pic1:'',
      pic2:'',
      pic3:'',
      show:true,
      update:false,
      srcList:[],
       Dialogform: {
          id:'',
          zhusu:'',
          xbshi:'',
          jwshi:'',
          result:'',
          solution:'',
          yizhu:''
        },
      recommendList:[]
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 登录标识
      'BingLi',
    ])
  },
  created () {
    this.bingliId = this.BingLi.id
    this.bingli = this.BingLi
    this.pic1 = this.BingLi.pic1
    this.pic2 = this.BingLi.pic2
    this.pic3 = this.BingLi.pic3
    this.srcList.push('http://localhost:9090'+this.pic1)
    this.srcList.push('http://localhost:9090'+this.pic2)
    if(this.pic3!=null && this.pic3!=''){
      this.srcList.push('http://localhost:9090'+this.pic3)
    }
    this.queryDialog()
  },
  // mounted(){
  //   console.log(this.Dialogform)
  //   if(this.Dialogform.id != ''){
  //     this.show = false
  //   }
  // },
  mixins: [mixin],
  methods: {
     addDialog(){

     if(this.Dialogform==null||this.Dialogform.id==''){
       let params = new URLSearchParams();
       params.append("bingliId",this.bingliId)
       params.append("zhusu",this.Dialogform.zhusu)
       params.append("xbshi",this.Dialogform.xbshi)
       params.append("jwshi",this.Dialogform.jwshi)
       params.append("result",this.Dialogform.result)
       params.append("solution",this.Dialogform.solution)
       params.append("yizhu",this.Dialogform.yizhu)
       this.$http.post('/dialog/addDialog',params).then(res=>{
           if(res.data.code == 200){
              this.$message.success("提交成功!")
              this.show = false
           }
       })
     }else{

         let params = new URLSearchParams();
         params.append("id",this.Dialogform.id)
       params.append("zhusu",this.Dialogform.zhusu)
       params.append("xbshi",this.Dialogform.xbshi)
       params.append("jwshi",this.Dialogform.jwshi)
       params.append("result",this.Dialogform.result)
       params.append("solution",this.Dialogform.solution)
       params.append("yizhu",this.Dialogform.yizhu)
       this.$http.post('/dialog/updateDialog',params).then(res=>{
           if(res.data.code == 200){
              this.$message.success("修改成功!")
              this.show = false
              this.update = false
           }
       })

     }
     },
     queryDialog(){
       this.$http.post('/dialog/queryDialogByBingLiId/'+this.bingliId).then(res=>{
         if(res.data.code == 200){

             if(res.data.data != null){
                this.Dialogform = res.data.data
             console.log(this.Dialogform)
             }
            // console.log(res.data.data)

             if(this.Dialogform.id != ''){
                this.show = false
             }
         }
       })
     },

    queryRecommendList(){
        this.$http.post('/dialog/queryDialogCommentList/'+this.Dialogform.result).then(res=>{
            if(res.data.code == 200){
                this.recommendList = res.data.data
                 for(let item in this.recommendList){
              this.recommendList[item].score =  this.recommendList[item].score / 2
              //  item.feelScore = item.feelScore / 2
            }
                console.log(this.recommendList)
            }
        })
    },
     updateDialog(){
       this.show = true
       this.update = true
     },
     cancleUpdate(){
       this.show = false
       this.update = false
     },
     cancle(formName){
      this.Dialogform.zhusu=''
      this.Dialogform.xbshi=''
      this.Dialogform.jwshi=''
      this.Dialogform.result=''
      this.Dialogform.solution=''
      this.Dialogform.yizhu=''
     }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/trip-list-album.scss';
</style>
