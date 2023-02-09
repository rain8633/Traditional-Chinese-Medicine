<template>
  <div class="info">
    <p class="title">编辑个人资料</p>
    <hr/>
    <div class="personal">
      <el-form :model="registerForm" ref="registerForm" class="demo-ruleForm" label-width="80px">
        <el-form-item prop="password" label="密码">
          <el-input type="password" placeholder="密码" v-model="registerForm.password"></el-input>
        </el-form-item>
        <el-form-item prop="phone" label="手机">
          <el-input  placeholder="手机" v-model="registerForm.phone" ></el-input>
        </el-form-item>

         <el-form-item prop="info" label="基本信息">
          <el-input type="textarea"  placeholder="请输入医生基本信息,供患者参考" v-model="registerForm.info" ></el-input>
        </el-form-item>

        <el-form-item prop="email" label="邮箱">
          <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>


      </el-form>
    <div class="btn">
      <div @click="saveMsg()">保存</div>
      <div @click="goback">取消</div>
    </div>
    </div>
</div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mixin } from "../mixins";

export default {
  name: 'info',
  mixins: [mixin],
  data: function () {
    return {
      doctorId:'',
      registerForm: {
        password: '',
        phone: '',
        info:'',
        email:''
      },

    }
  },
  created () {
     this.doctorId=JSON.parse(window.sessionStorage.getItem('doctor')).id;
    //  console.log(this.userId)
     this.getMsg(this.doctorId)
  },
  mounted () {

  },
  methods: {
    getMsg (id) {
     this.$http.post("/doctor/queryDoctorById/"+id)
        .then(res => {

          this.registerForm.password = res.data.data.password
          this.registerForm.phone = res.data.data.phone
          this.registerForm.email = res.data.data.email
          this.registerForm.info = res.data.data.info
        })
        .catch(err => {
          console.log(err)
        })
    },
    goback () {
      this.$router.go(-1)
    },
    saveMsg () {
      let params = new URLSearchParams()
      params.append('id', this.doctorId)
      params.append('password', this.registerForm.password)
      params.append('phone', this.registerForm.phone)
      params.append('email', this.registerForm.email)
      params.append('info', this.registerForm.info)

     this.$http.post("/doctor/updatedoctorMsg",params)
        .then(res => {
          if (res.data.code == 200) {
            this.showError = false
            this.showSuccess = true
             this.$notify.success({
              title: '编辑成功',
              showClose: true
            })
            let doctor=JSON.parse(window.sessionStorage.getItem("doctor"))
          // console.log(res.data)
          doctor.password=res.data.data.password
          doctor.phone=res.data.data.phone
          doctor.email=res.data.data.email
          doctor.info = res.data.data.info
           window.sessionStorage.setItem("doctor",JSON.stringify(doctor))
            setTimeout(function () {
              this.$router.go(-1)
            }, 2000)
          } else {
            this.showSuccess = false
            this.showError = true
            this.$notify.error({
              title: '编辑失败',
              showClose: true
            })
          }
        })
        .catch(err => {
          console.log(err)
        })
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/info.scss';
</style>
