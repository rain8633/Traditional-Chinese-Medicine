<template>
  <div class="home" style="height:100%">
   <el-container style="height:100%">
  <el-header style="position:fixed;z-index:1;top:0px;width:100%">
    <div class="divcss">
     <el-button type="text" style="margin-top:1px" @click="isOpen()"><i style="font-size:35px;color:white" class="el-icon-s-operation"></i></el-button>
     <h2 style="color:white;margin-left:20px;margin-top:3px">中医在线诊断平台</h2>
     <!-- 天气 -->
    <div style="padding-left: 40px;padding-top: 10px">
        <iframe id="tianqi" scrolling="no" src="" frameborder="0" width="470"
                height="60"  allowtransparency="true"></iframe>
    </div>
    </div>
     <el-dropdown trigger="click">
      <span class="el-dropdown-link">
         <div class="demo-type">
    <el-avatar :size="45" style="margin-top:7px">
      <img :src="path+user.img"/>
    </el-avatar>
  </div>
      </span>
      <el-dropdown-menu slot="dropdown">
        <el-dropdown-item icon="el-icon-user-solid">{{user.userName}}</el-dropdown-item>
        <hr/>
        <el-dropdown-item icon="el-icon-key" @click.native="updatePs()">修改密码</el-dropdown-item>
        <el-dropdown-item icon="el-icon-upload" @click.native="uploadVisible=true">修改头像</el-dropdown-item>
        <el-dropdown-item icon="el-icon-switch-button" @click.native="exit()">退出系统</el-dropdown-item>
      </el-dropdown-menu>
    </el-dropdown>
    </el-header>

  <el-container style="margin-top:65px;">
    <el-aside :width="width">
     <el-menu default-active="1-4-1" class="el-menu-vertical-demo"  style="height:100%" :collapse="isCollapse" router>
  <el-menu-item  index="/addRecords">
    <i style="font-size:20px" class="el-icon-service"></i>
    <span class="spanclass" slot="title">病症诊断</span>
  </el-menu-item>
   <el-menu-item index="/allRecords">
    <i style="font-size:20px" class="el-icon-document"></i>
    <span class="spanclass" slot="title">咨询列表</span>
  </el-menu-item>
   <el-menu-item index="/docList">
    <i style="font-size:20px" class="el-icon-user"></i>
    <span class="spanclass" slot="title">医师列表</span>
  </el-menu-item>
</el-menu>
    </el-aside>
    <el-main>
      <!-- <Welcome/> -->
      <router-view></router-view>
    </el-main>
  </el-container>
</el-container>

<el-dialog title="修改密码"   width=30% :visible.sync="updatePasswordVisible" style="text-align:center;">
  <el-form :model="passwordForm" :rules="passwordRule" ref="passwordForm">
    <el-form-item label="原密码" prop="oldpassword">
      <el-input v-model="passwordForm.oldpassword" placeholder="请输入原密码" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item label="新密码" prop="newpassword">
      <el-input v-model="passwordForm.newpassword" placeholder="请输入新密码" autocomplete="off"></el-input>
    </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button  type="warning" @click="quxiao('passwordForm')">取 消</el-button>
    <el-button  type="success" @click="updatePassword('passwordForm')">确 定</el-button>
  </div>
</el-dialog>
<!-- 上传头像 -->
<el-dialog title="修改头像" @close="uploadClose()" width="30%" :visible.sync="uploadVisible"  style="text-align:center;">

<el-upload
class="avatar-uploader"
  name="file"
  :action="path+'/user/upload'"
  :show-file-list="false"
  :file-list="fileList"
  :on-success="success"
  >
  <img v-if="img" :src="path+img" class="avatar" width="300px" height="300px">
  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
</el-upload>
  <div slot="footer" class="dialog-footer">
    <el-button @click="uploadVisible = false">取 消</el-button>
    <el-button type="primary" @click="updateIcon()">确 定</el-button>
  </div>
</el-dialog>


  </div>
</template>

<script>
import Welcome from "./Welcome.vue";
import {mixin} from "../mixins"
import $ from 'jquery';
export default {
  name: 'Home',
  components:{
    Welcome,
  },
  mixins: [mixin],
  mounted() {
    var url = "https://tianqiapi.com/api.php?style=tg&skin=orange";
    $("#tianqi").attr("src", url);
  },
 data() {
   return {
     user: {
       id:'',
       userName:'',
       password:'',
       pic:'',
     },

     isCollapse:false,
     uploadVisible:false,
     width:'200px',
     fileList:[],
     img:'',
     updatePasswordVisible:false,
     passwordForm:{
       oldpassword:'',
       newpassword:''
     },
     passwordRule:{
        oldpassword:[
           { required: true, message: '请输入原密码', trigger: 'blur' },
        ],
        newpassword:[
           { required: true, message: '请输入新密码', trigger: 'blur' },
        ]
     },
   }
 },
 methods:{
   isOpen(){
     if(this.isCollapse){
       this.isCollapse = !this.isCollapse,
       this.width = '200px'
     }else{
       this.isCollapse = !this.isCollapse,
       this.width = '65px'
     }
   },
   updatePs(){
      this.updatePasswordVisible=true
   },
   exit(){
     window.sessionStorage.clear()
     this.$router.push("/")
   },
   quxiao(formName){
     this.$refs[formName].resetFields();
     this.updatePasswordVisible=false;
   },
   success(file,response){
     console.log(file.data)
      this.img=file.data
   },
   uploadClose(){
    //  this.user.pic=JSON.parse(window.sessionStorage.getItem("user")).pic
    this.img=''
   },
   updateIcon(){
     const _this=this
     let param=new URLSearchParams();
     param.append("pic",this.img)
     param.append("userId",this.user.id)
     this.$http.post('/user/updatePic',param).then(res=>{
        if(res.data.code==200){
          let user=JSON.parse(window.sessionStorage.getItem("user"))
          // user.pic= res.data.data
          user.pic=this.img
           window.sessionStorage.setItem("user",JSON.stringify(user))
          let user1=JSON.parse(window.sessionStorage.getItem("user"))
          this.user=user1
          this.uploadVisible=false
          _this.notify("头像上传成功","success")
        }
     })
   },
    updatePassword(formName){
    this.$refs[formName].validate((valid) => {
          if (valid) {
            let params = new URLSearchParams();
            params.append("newPassword",this.passwordForm.newpassword);
            params.append("userId",this.user.id);
            if(this.user.password == this.passwordForm.oldpassword){
              this.$http.post('/user/updatePassword',params).then(res=>{
               if(res.data.code==200) {

                  let  user = JSON.parse(window.sessionStorage.getItem("user"))
                  user.password = res.data.data
                  console.log(res.data.data)
                  window.sessionStorage.setItem("user",JSON.stringify(user))
                 let  user1 = JSON.parse(window.sessionStorage.getItem("user"))
                this.user = user1
                this.updatePasswordVisible = false
                  this.$message.success("密码修改成功!")
               }
           })
            }
            else{
              this.$message.error("原密码错误")
            }
          } else {
            return false;
          }
        });
 }
 },
 created(){
   let user=JSON.parse(window.sessionStorage.getItem("user"));
   if(user != null){
     this.user=user;
   }
 },

}
</script>
<style scoped>
  .el-header {
    background-color: #1565C0;
    display: flex;
    justify-content:space-between;
    color: #333;
    text-align: center;
    line-height: 60px;
  }

  .el-aside {
    background-color: #ffffff;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #ffffff;
    color: #333;
    line-height: 30px;
    margin-top:50px;
  }
  .divcss{
    display:flex;
    justify-content: space-between;
  }
  .spanclass{
    font-size: 18px;
    margin-left: 10px;
  }

   .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 300px;
    height: 250px;
    line-height: 250px;
    text-align: center;
  }
  .avatar {
    width: 300px;
    height: 250px;
    display: block;

  }
</style>

