<template>
  <div id="login">
    <!--<img src="./assets/logo.png">-->
    <div class="wrap-banner">
      <div class="main-title" v-show="showTitle">
        <h2>中医诊断平台</h2>
        <p class="subtitle">Welcome to System</p>
        <br />
         <el-button type="text" @click="dialogFormVisible = true,showTitle = false"><h2 style="color: black">登录</h2></el-button>

      </div>
      <vue-particles
        style="position: absolute; width: 100%; height: 100%"
        color="#00FFFF"
        :particleOpacity="0.7"
        linesColor="#00FFFF"
        :particlesNumber="80"
        shapeType="circle"
        :particleSize="5"
        :linesWidth="2"
        :lineLinked="true"
        :lineOpacity="0.4"
        :linesDistance="150"
        :moveSpeed="3"
        :hoverEffect="true"
        hoverMode="grab"
        :clickEffect="true"
        clickMode="push"
      >
      </vue-particles>
    </div>
    <el-dialog   :visible.sync="dialogFormVisible"
    width=30%
    @close="loginClose()"
    v-show="loginShow"
    >
<h2 style="color: white;">登录</h2>
  <el-form :model="loginForm" ref="loginForm" :rules="rules">
    <el-form-item  prop="name">
      <el-input v-model="loginForm.name" placeholder="请输入用户名" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="loginForm.password" type="password" placeholder="请输入密码" autocomplete="off" ></el-input>
      <!-- @keyup.enter.native="login('loginForm')" -->
    </el-form-item>
    <el-form-item prop="code">
          <el-input
            size="normal"
            type="text"
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="点击图片更换验证码"
            @keydown.enter.native="login('loginForm')"
            style="width: 180px;float: left;"
          ></el-input>
          <img
            :src="vcUrl"
            @click="updateVerifyCode"
            alt=""
            style="cursor: pointer;margin-left:5px;float:left;height:38px;"
          />
        </el-form-item>
  </el-form>
  <div slot="footer" class="dialog-footer">
    <el-button class="buttoncss" style="float: left;" @click="login('loginForm')">登 录</el-button>
    <el-button class="buttoncss" style="float: left;" @click="chongzhi('loginForm')">重 置</el-button>
    <el-button class="buttoncss" type="primary" @click="regVisible=true,loginShow=false">注 册</el-button>
  </div>
</el-dialog>


 <el-dialog   :visible.sync="regVisible"
    width=30%
   @close="regClose()"
    >
<h2 style="color: white;">注册</h2>
  <el-form :model="regForm" ref="regForm" :rules="Rrules">
    <el-form-item  prop="userName">
      <el-input v-model="regForm.userName" placeholder="请输入用户名" @blur="cheakUsername()" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="password">
      <el-input v-model="regForm.password" type="password" placeholder="请输入密码" autocomplete="off"></el-input>
    </el-form-item>
      <el-form-item prop="repassword">
      <el-input v-model="regForm.repassword" type="password" @blur="cheakPassword()" placeholder="请确认密码" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="phone">
      <el-input v-model="regForm.phone" type="text"  placeholder="请输入手机号" autocomplete="off"></el-input>
    </el-form-item>
     <el-form-item prop="email">
      <el-input v-model="regForm.email" type="email" @blur="cheakEmail()"  placeholder="请输入邮箱" autocomplete="off"></el-input>
    </el-form-item>
    <el-form-item prop="img" style="display:table">
     <el-upload
       name="file"
      :action="path+'/user/upload'"
      :on-success="success"
      :limit="1"
      :on-exceed="exceed"
      :file-list="fileList"
      list-type="picture">
  <el-button  size="small" type="primary" style="margin-right:25px;">上传头像</el-button>
  <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
</el-upload>
    </el-form-item>
  </el-form>

  <div slot="footer" class="dialog-footer">
    <el-button class="buttoncss" style="float: left;" @click="quxiao('regForm')">取 消</el-button>
    <el-button class="buttoncss" type="primary" @click="reg('regForm')">注 册</el-button>
  </div>
</el-dialog>

  </div>
</template>

<script>
import { mixin } from "../../mixins";


export default {
  name: "login",
  mixins: [mixin],
  data() {
    return {
      showTitle:true,
      loginForm:{
          name:'',
          password:'',
          code: "",
      },
      regForm:{
          userName:'',
          password:'',
          repassword:'',
          phone:'',
          email:'',
          img:''
      },
      vcUrl: this.path+"/user/verifyCode?time=" + new Date(),
      dialogTableVisible: false,
      dialogFormVisible: false,
      regVisible:false,
      loginShow:true,
      fileList:[],
      isOk:false,//用户名是否合法
      rules:{
         name: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          password:[
            {required:true,message: '请输入密码', trigger: 'blur'},
            {min:6,max:12,message: '密码长度为6~12位',trigger:'blur'}
          ],
          code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
       Rrules:{
         userName: [
            { required: true, message: '请输入用户名', trigger: 'blur' },
            { min: 3, max: 5, message: '长度在 3 到 5 个字符', trigger: 'blur' }
          ],
          password:[
            {required:true,message: '请输入密码', trigger: 'blur'},
            {min:6,max:12,message: '密码长度为6~12位',trigger:'blur'}
          ],
           repassword:[
            {required:true,message: '请输入确认密码', trigger: 'blur'},
            {min:6,max:12,message: '密码长度为6~12位',trigger:'blur'}
          ],
           img:[
            {required:true,message: '请选择头像', trigger: 'change'},
          ],
          phone:[
            {required:true,message: '请输入手机号',trigger: 'blur'},
          ],
           email:[
             { required: true, message: '请输入邮箱地址', trigger: 'blur' },
             { type: 'email', message: '请输入正确的邮箱地址', trigger: ['blur', 'change'] }
          ]
      },
    }
  },
  methods: {
    loginClose(){
      this.showTitle=true;
      this.$refs['loginForm'].resetFields();
    },
    regClose(){
      this.loginShow=true;
      this.$refs['regForm'].resetFields();
      this.fileList=[];
    },
    success(file,response){
      this.regForm.img=file.data;
      this.$refs['regForm'].validateField('img');
    },
    updateVerifyCode() {
      this.vcUrl = this.path+"/user/verifyCode?time=" + new Date();
    },
    login(formName){
         const _this=this;
          let params = new URLSearchParams();
      params.append("userName", this.loginForm.name);
      params.append("password", this.loginForm.password);
      params.append("code", this.loginForm.code);
       this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post('user/queryUser',params).then(res=>{
            if (res.data.code === 3) {
            _this.notify("验证码错误", "error");
            this.vcUrl = this.path+"/user/verifyCode?time=" + new Date();
          }else if(res.data.code == 200){
                window.sessionStorage.setItem("user",res.data.data);
                // console.log(res.data.data);
                this.$router.push('/home');
               this.$message.success("欢迎回来")
            }
            else  if(res.data.code === 4){
              this.$message.error(res.data.msg);
              console.log(res.data);
              this.vcUrl = this.path+"/user/verifyCode?time=" + new Date();
            }

            })
          } else {
            this.$message.error("校验失败")
            return false;
          }
        });
    },
    reg(formName){
          this.$refs[formName].validate((valid) => {
          if (valid) {
             let _this = this
              if(this.isOk){
              if(this.regForm.password == this.regForm.repassword){

               this.$http.post('/user/addUser',this.regForm).then(res=>{
             if(res.data.code==200) {
           console.log(res.data)
                 this.fileList=[];
                 this.$refs['regForm'].resetFields();
                // this.$message.success("注册成功")
                let email=res.data.data;
            _this.notify('请前往邮箱'+email+'激活', 'success')
            setTimeout(function () {
              this.regVisible=false;
            }, 5000)
             }
            })
            }
            else{
              this.$message.error("两次密码不一致，请重新输入")
            }
            }
            else{
              this.$message.error("请更换用户名或邮箱")
            }
          } else {
            this.$message.error("校验失败")
            return false;
          }
        });
    },
    chongzhi(formName){
      this.$refs[formName].resetFields();
    },
    quxiao(formName){
      this.$refs[formName].resetFields();
      this.fileList=[];
      this.regVisible=false;
    },
    cheakUsername(){
      let userName=this.regForm.userName || null
      this.$http.get('/user/cheakUsername/'+userName).then(res=>{
        if(res.data.code==200){
           this.isOk=true;
        }else{
          this.$message.error("该用户名已被占用");
        }
      })
    },
     cheakEmail(){
      let email=this.regForm.email || null
      this.$http.get('/user/cheakEmail/'+email).then(res=>{
        if(res.data.code==200){
           this.isOk=true;
        }else{
          this.isOk=false;
          this.$message.error("该邮箱已被注册");
        }
      })
    },
    cheakPassword(){
      let password=this.regForm.password || null
      let repassword=this.regForm.repassword || null
      if( password != repassword){
         this.$message.error("两次密码不一致，请重新输入")
      }
    },
    exceed(){
      const _this=this
      // this.$message.error("只能上传一张图片")
      _this.notify("只能上传一张图片","error")
    }
  },
};
</script>
// scoped只对本页面起作用
<style scoped>
*,
:after,
:before {
  box-sizing: border-box;
}

html,
body {
  height: 100%;
}

body {
  margin: 0;
  background: #34393f;
}

#login {
  height: 100%;
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}


p {
 color:black;
}

h1,
h2 {
  font-size: 40px;
  font-weight: normal;
  color: black;
}

ul {
  list-style-type: none;
  padding: 0;
}

li {
  display: inline-block;
  margin: 0 10px;
}

a {
  color: #42b983;
}

h3 {
  font-weight: 100;
  font-size: 2rem;
}

.white {
  color: #ffffff;
}


.wrap-banner {
  background: url("../../assets/img/3.png") no-repeat;
  background-size:contain;
  background-position: right;
  position: relative;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
  display: -webkit-flex;
  display: flex;
  height: 100%;
  justify-content: center;
}

.main-section {
  max-width: 650px;
  margin: auto;
  padding: 0 1rem;
}

.main-title {
  color: #fff;
  z-index: 999;
}

.section-title {
  margin: 2rem 0 0 0;
}

.footer {
  margin: 1rem 0;
}



/deep/.el-dialog,/deep/.el-input__inner,.buttoncss,.radioclass{
  background-color: transparent;
  color:white;
}

/* .el-dialog__title{
  font-size :28px  !important;
  color:white !important;
} */

</style>
