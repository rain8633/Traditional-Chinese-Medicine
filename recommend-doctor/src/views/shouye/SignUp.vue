<template>
<div class="signUp-page">
  <loginLogo/>
  <div class="signUp">
    <div class="signUp-head">
      <span>用户注册</span>
    </div>
    <el-form :model="registerForm" status-icon :rules="rules" ref="registerForm" label-width="80px" class="demo-ruleForm">
      <el-form-item prop="username" label="姓名">
        <el-input v-model="registerForm.username" @blur="cheakDname()" placeholder="姓名"></el-input>
      </el-form-item>
      <el-form-item prop="password" label="密码">
        <el-input type="password" placeholder="密码" v-model="registerForm.password"></el-input>
      </el-form-item>
      <el-form-item prop="sex" label="性别">
        <el-radio-group v-model="registerForm.sex">
          <el-radio :label="0">女</el-radio>
          <el-radio :label="1">男</el-radio>
        </el-radio-group>
      </el-form-item>
      <el-form-item prop="phone" label="手机" >
        <el-input  placeholder="手机" v-model="registerForm.phone"></el-input>
      </el-form-item>
      <el-form-item prop="email" label="邮箱">
        <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
      </el-form-item>
      <el-form-item prop="location" label="地区">
        <el-select v-model="registerForm.location" placeholder="地区" style="width:100%">
          <el-option v-for="item in cities" :key="item.value" :label="item.label" :value="item.value"></el-option>
        </el-select>
      </el-form-item>
       <el-form-item prop="hname" label="所在医院">
        <el-input v-model="registerForm.hname" placeholder="填写所在医院"></el-input>
      </el-form-item>
      <el-form-item prop="keshi" label="所属科室">
        <el-input v-model="registerForm.keshi" placeholder="填写所属科室"></el-input>
      </el-form-item>
      <div class="login-btn">
        <el-button @click="goback(-1)">取消</el-button>
        <el-button type="primary" @click="SignUp">确定</el-button>
      </div>
    </el-form>
  </div>
</div>
</template>

<script>
import loginLogo from '../../components/LoginLogo.vue'
import { mixin } from '../../mixins'
import { rules, cities } from '../../assets/data/form'

export default {
  name: 'SignUp-page',
  components: {
    loginLogo
  },
  mixins: [mixin],
  data () {
    return {
      registerForm: { // 注册
        username: '',
        password: '',
        sex: '',
        phone: '',
        email: '',
        location: '',
        hname:'',
        keshi:''
      },
      rules: {},
      cities: [],
      isOK: false
    }
  },
  created () {
    this.rules = rules
    this.cities = cities
  },
  methods: {
    SignUp() {
      let _this = this
      let params = new URLSearchParams()
      params.append('dname', this.registerForm.username)
      params.append('password', this.registerForm.password)
      params.append('sex', this.registerForm.sex)
      params.append('phone', this.registerForm.phone)
      params.append('email', this.registerForm.email)
      params.append('cityName', this.registerForm.location)
      params.append('hospName',this.registerForm.hname)
      params.append('ksName',this.registerForm.keshi)
      params.append('pic', '/img/doctor.png')
      if(this.isOK){
        this.$http.post('/doctor/addDoctor',params).then(res=>{
         if(res.data.code==200){
            _this.notify('注册成功', 'success')
           setTimeout(function () {
              _this.$router.push({path: '/'})
            }, 3000)
         }else{
              _this.notify('注册失败', 'error')
         }
     }).catch(err=>{
       console.log(err)
     })
     }
    },
    cheakDname(){
      this.$http.post('/doctor/cheakDname/'+this.registerForm.username).then(res=>{
       if(res.data.code==200){
           this.isOK=true;
        }else{
          this.$message.error("该用户名已被占用");
        }
      })
    },

    goback (index) {
      this.$router.go(index)
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/sign-up.scss';
</style>
