<template>
  <div class="login-in">

    <login-logo />
    <div class="login">
      <div class="login-head">
        <span>帐号登录</span>
      </div>
      <el-form
        :model="loginForm"
        status-icon
        :rules="rules"
        ref="loginForm"
        class="demo-ruleForm"
      >
        <el-form-item prop="username">
          <el-input
            placeholder="手机号/邮箱"
            v-model="loginForm.username"
          ></el-input>
        </el-form-item>
        <el-form-item prop="password">
          <el-input
            type="password"
            placeholder="密码"
            v-model="loginForm.password"
            @keyup.enter.native="loginIn"
          ></el-input>
        </el-form-item>
        <el-form-item prop="code">
          <el-input
            size="normal"
            type="text"
            v-model="loginForm.code"
            auto-complete="off"
            placeholder="点击图片更换验证码"
            @keydown.enter.native="handleleLoginIn('loginForm')"
            style="width: 170px"
          ></el-input>
          <img
            :src="vcUrl"
            @click="updateVerifyCode"
            alt=""
            style="cursor: pointer"
          />
        </el-form-item>
        <div class="login-btn">
          <el-button @click="goSignUp()">注册</el-button>
          <el-button type="primary" @click="handleleLoginIn('loginForm')">登录</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { mixin } from "../../mixins";
import LoginLogo from "../../components/LoginLogo.vue";

export default {
  name: "login-in",
  components: {
    LoginLogo,
  },
  mixins: [mixin],
  data: function () {
    let validateName = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("手机号或邮箱不能为空"));
      } else {
        callback();
      }
    };
    let validatePassword = (rule, value, callback) => {
      if (value === " ") {
        callback(new Error("请输入密码"));
      } else {
        callback();
      }
    };
    return {
      vcUrl: "http://localhost:9090/user/verifyCode?time=" + new Date(),
      loginForm: {
        // 登录用户名密码
        username: "",
        password: "",
        code: "",
      },
      rules: {
        username: [
          { validator: validateName, message: "请输入手机号或邮箱", trigger: "blur" },
        ],
        password: [
          {
            validator: validatePassword,
            message: "请输入密码",
            trigger: "blur",
          },
        ],
        code: [{ required: true, message: "请输入验证码", trigger: "blur" }],
      },
    };
  },
  mounted() {
    this.changeIndex("登录");
  },
  methods: {
    updateVerifyCode() {
      this.vcUrl = "http://localhost:9090/doctor/verifyCode?time=" + new Date();
    },
    changeIndex(value) {
      this.$store.commit("setActiveName", value);
    },
    handleleLoginIn(formName) {
       const _this=this;
          let params = new URLSearchParams();
      params.append("name", this.loginForm.username);
      params.append("password", this.loginForm.password);
      params.append("code", this.loginForm.code);
       this.$refs[formName].validate((valid) => {
          if (valid) {
            this.$http.post('/doctor/queryDoctor',params).then(res=>{
            if (res.data.code === 3) {
            _this.notify("验证码错误", "error");
            this.vcUrl = this.path+"/doctor/verifyCode?time=" + new Date();
          }else if(res.data.code == 200){
                window.sessionStorage.setItem("doctor",res.data.data);
                this.$router.push('/home');
                _this.$store.commit("setLoginIn", true);
                this.$message.success("欢迎回来")
            }
            else  if(res.data.code === 4){
              this.$message.error(res.data.msg);
              console.log(res.data);
              this.vcUrl = this.path+"/doctor/verifyCode?time=" + new Date();
            }

            })
          } else {
            this.$message.error("校验失败")
            return false;
          }
        });
    },
    goSignUp() {
      this.$router.push({ path: "/sign-up" });
    },
  },
};
</script>

<style lang="scss" scoped>
@import "../../assets/css/login-in.scss";
</style>
