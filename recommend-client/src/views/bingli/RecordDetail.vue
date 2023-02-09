<template>
  <div class="Recordetail">
    <el-card style="margin-bottom:20px;margin-top:-20px;width:1000px;margin-left:120px;">
    <div slot="header" class="clearfix">
    <span>病历详情</span>
  </div>
  <el-row :gutter="20">
     <el-col :span="9">
    <div>
      <div class="text item" style="line-height:35px;white-space:normal; word-break:break-all:width;overflow:hidden;">
        <strong>患者姓名:&nbsp;&nbsp;</strong>{{bingli.realName}}<br>
        <strong>诊治医生:&nbsp;&nbsp;</strong>{{bingli.dname}}<br>
        <strong>所属科室:&nbsp;&nbsp;</strong>{{bingli.ksName}}<br>
        <strong>患者年龄:&nbsp;&nbsp;</strong>{{bingli.clientAge}}<br>
        <strong>患者联系方式:&nbsp;&nbsp;</strong>{{bingli.phone}}<br>
        <strong>病情自述:&nbsp;&nbsp;</strong>{{bingli.info}}
      </div>
     <div class="demo-image__preview">
      <div><strong style="margin-bottom:20px;">病症图片:(点击查看大图)&nbsp;&nbsp;</strong></div>
      <el-image
       style="width: 100px; height: 100px;margin-top:20px;"
       :src="path+bingli.pic1"
       :preview-src-list="[srcList[0]]">
      </el-image>
      <el-image
       style="width: 100px; height: 100px;margin-left:20px;"
       :src="path+bingli.pic2"
       :preview-src-list="[srcList[1]]">
      </el-image>
      <el-image v-if="bingli.pic3!=null && bingli.pic3!=''"
       style="width: 100px; height: 100px;margin-left:20px;"
      :src="path+bingli.pic3"
      :preview-src-list="[srcList[2]]">
    </el-image>
     </div>
    </div>
  </el-col>
  <el-col :span="1">
<div class="line"> </div>
  </el-col>
    <el-col :span="10">
      <div v-if="Dialog!=null" style="">
      <strong>主诉:&nbsp;&nbsp;</strong>{{Dialog.zhusu}}<br>
        <strong>现病史:&nbsp;&nbsp;</strong>{{Dialog.xbshi}}<br>
        <strong>既往史:&nbsp;&nbsp;</strong>{{Dialog.jwshi}}<br>
        <strong>诊断结果:&nbsp;&nbsp;</strong>{{Dialog.result}}<br>
        <strong>药方:&nbsp;&nbsp;</strong>{{Dialog.solution}}<br>
        <strong>医嘱:&nbsp;&nbsp;</strong>{{Dialog.yizhu}}
      </div>
      <div v-if="Dialog==null">
          <el-empty description="医生还没诊断,请耐心等待">
          <el-button type="primary" @click="notice()">点此通知医生</el-button>
          </el-empty>
      </div>
    </el-col>
  </el-row>

  <el-row :gutter="10"  v-if="bingli.status!=3">
    <el-col :span="10" style="margin-top:50px;margin-left:280px;">
  <el-button type="success"  @click="endDialog()">结束诊断</el-button>
  <el-button type="warning" @click="comment()">对此诊断有疑问</el-button>
  </el-col>
  </el-row>

   <el-row :gutter="10"  v-if="bingli.status==3">
    <el-col :span="10" style="margin-top:50px;margin-left:300px;">
  <el-button type="warning" @click="comment()">对此诊断有疑问</el-button>
  </el-col>
  </el-row>

  <el-row v-if="show == true" :gutter="10">
     <el-col :span="5" style="margin-top:30px;margin-left:200px;">
     给此次治疗做出您的评价:
     </el-col>
    <el-col :span="5" style="margin-top:-25px;margin-left:390px;" >
    <el-rate
  v-model="value"
  show-text
  allow-half
  :disabled="value>0"
  @change="pushValue()">
</el-rate>
  </el-col>
  </el-row>
    </el-card>



   <!--评论界面-->
    <el-drawer title="评论详情" :visible.sync="drawer" :with-header="false"  size="30%"  style="overflow:auto">
      <h3 style="text-align: center;">向医生提出你的问题吧</h3>

      <!--评论填写-->
      <el-form :model="talkeForm">
        <el-form-item>
          <el-input type="textarea" style="width:95%;margin-left:20px;" v-model="talkeForm.content" placeholder="发表交流"  autocomplete="off"></el-input>
          <el-button type="primary" style="float: right;margin-top:15px;margin-right:5px;" @click="submitTalke()" >提交问题</el-button>
        </el-form-item>
      </el-form>
      <el-divider></el-divider>
      <br>
    <div v-if="talkes.length>0" >
      <div v-for="talke in talkes" :key="talke.name">
        <el-row :gutter="15" style="margin-left:10px;">
          <el-col :span="3">
            <div class="demo-type" v-if="talke.role == 0">
              <el-avatar icon="el-icon-user-solid" :src="path+talke.img" :size="40"></el-avatar>
            </div>
            <div class="demo-type" v-if="talke.role == 1">
              <el-avatar icon="el-icon-user-solid" :src="path+talke.pic" :size="40"></el-avatar>
            </div>
          </el-col>
          <el-col :span="12">
            <div style="font-family: Monaco">{{talke.role == 1?talke.dname+"医生":talke.userName}}</div>
            <div style="font-size: small;color: #40c3ff">{{talke.createTime}}</div>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="15"><p style="margin-left: 60px;margin-top: 10px;color: #00060c">{{talke.content}}</p></el-col>
          <el-col :span="9" style="margin-top: -90px;margin-left: 210px">
            <el-button type="text" v-if="talke.role==1"  @click="showReply(talke)">回复</el-button>
            <!-- <el-divider  direction="vertical"></el-divider> -->
            <el-button type="text" v-if="talke.role==0" style="color: #ec3418" @click="deleteTalkeById(talke.id)">删除</el-button>
          </el-col>
        </el-row>
        <el-divider></el-divider>
      </div>
      </div>
      <div v-if="talkes.length==0">
        <el-empty description="暂无评论"></el-empty>
      </div>
    </el-drawer>


  </div>
</template>

<script type="text/javascript">
import { mixin } from "../../mixins";
import { mapGetters } from 'vuex'

export default {
  name:"record-detail",
  mixins: [mixin],
  data() {
    return {
     bingli: {},
      bingliId: '', // 病历ID
      num: 1,
      srcList:[],
       Dialog: {},
       drawer:false,
       talkeForm:{
         content:''
       },
       talkes:[],
       show:false,
         value: 0,
         status:0
    }
  },
    computed: {
    ...mapGetters([
       'BingLi'
    ])
  },
    created(){
     this.bingliId = this.BingLi.id;
     this.bingli = this.BingLi
     this.srcList.push('http://localhost:9090'+this.bingli.pic1)
     this.srcList.push('http://localhost:9090'+this.bingli.pic2)
     if(this.bingli.pic3!=null && this.bingli.pic3!=''){
         this.srcList.push('http://localhost:9090'+this.bingli.pic3)
      }
     if(this.bingli.status==3){
         this.show =true;
      }
    this.queryDialogByBingLiId()
  },
  components: {

  },
  methods: {

      queryDialogByBingLiId(){
        this.$http.post('/dialog/queryDialogByBingLiIdNoStatus/'+this.bingliId).then(res=>{
            if(res.data.code==200){
                this.Dialog = res.data.data
                this.value = this.Dialog.score /2;
            }
        })
      },
      queryAllComment(){

        this.$http.post('/comment/queryAllComment/'+this.bingliId).then(res=>{
          if(res.data.code==200){
               this.talkes = res.data.data
               console.log(this.talkes)
          }
        })
      },
      deleteTalkeById(id){
         this.$confirm('是否删除该评论?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
       this.$http.post('/comment/deleteCommentById/'+id).then(res => {
          if(res.data.code == 200){
              this.$message.success("评论已删除!")
          }
          this.queryAllComment()
       }).catch(err => {
         console.error(err)
       })
    })
      },
      endDialog(){

           this.$confirm('是否结束诊断?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(()=>{
       this.$http.post('/bingli/updateBingLiStatus/'+this.bingliId+"/"+3).then(res => {
          if(res.data.code == 200){
              this.$message.success("恭喜您康复!祝您身体健康!")
              this.bingli.status=3
              this.show =  true
          }
       }).catch(err => {
         console.error(err)
       })
    })
      },
      submitTalke(){

        let params = new URLSearchParams();
        params.append("bingliId",this.bingliId)
        params.append("role",0)
        params.append("content",this.talkeForm.content)
        this.$http.post('/comment/addComment',params).then(res=>{
           if(res.data.code ==200){
             this.talkeForm.content = ''
              this.$message.success("提交问题成功!请等待医生回答")
              this.queryAllComment()
           }
        })
      },
      comment(){
        this.drawer = true
        // console.log(this.drawer)
        this.queryAllComment()
      },
      pushValue(){
         let params = new URLSearchParams();
         params.append("dialogId",this.Dialog.id)
         params.append("score", this.value * 2)
         this.$http.post("/dialog/updateDialogScore",params).then(res=>{
             if(res.data.code==200) {
               this.$message.success("评价成功!")
             }
         })
      },
      notice(){

         let user = JSON.parse(window.sessionStorage.getItem("user"))
      this.$http.post('/doctor/queryDocByBingliId/'+this.bingliId).then(res=>{
         if(res.data.code == 200){
            let email = res.data.data.email
             this.$http.post('/doctor/noticeDoctor/'+user.userName+'/'+email).then(res1=>{
           if(res1.data.code==200) {
             this.$message.success("提醒成功!请耐心等候")
           }
        })
      }
         })

  },
  }
}


</script>

<style scoped>
  .time {
    font-size: 13px;
    color: #999;
  }

  /deep/input::-webkit-outer-spin-button,
  /deep/input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    appearance: none;
    margin: 0;
  }
.line{
  width:15px;
  height:370px;
  border-right: solid black 1px;
}
</style>
