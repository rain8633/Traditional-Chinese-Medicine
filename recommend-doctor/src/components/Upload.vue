<template>
  <div class="upload">
    <p class="title">修改头像</p>
    <hr/>
    <div class="section">
      <el-upload
        drag
        :action="path+'/user/upload'"
        :show-file-list="false"
        :on-success="handleAvatarSuccess"
        :before-upload="beforeAvatarUpload">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>修改头像</em></div>
        <div class="el-upload__tip" slot="tip">只能上传jpg/png文件，且不超过10M</div>
      </el-upload>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import { mixin } from '../mixins'

export default {
  name: 'upload',
  mixins: [mixin],
  data () {
    return {
      pic: '',
      doctorId:'',
    }
  },
  computed: {
    // ...mapGetters([
    //   'userId'
    // ])
  },
  created () {
    this.doctorId = JSON.parse(window.sessionStorage.getItem("doctor")).id
  },
  methods: {
    // uploadUrl () {
    //   return path+`/user/avatar/update?id=${this.userId}`
    // },

    handleAvatarSuccess (res, file) {
      // console.log(res.code)
      if (res.code == 200) {
        this.pic = res.data
        this.$http.get("/doctor/updatedoctorImg",{params:{
          pic: this.pic,
          id: this.doctorId
        }}).then(res1 => {
          // console.log(res1);
            if(res1.data.code == 200){
                this.$message({
              message: '修改成功',
              type: 'success'
        })
        // this.user=res1.data.data

        let doctor=JSON.parse(window.sessionStorage.getItem("doctor"))
          // user.pic= res.data.data
          doctor.pic=this.pic
           window.sessionStorage.setItem("doctor",JSON.stringify(doctor))
        //   let user1=JSON.parse(window.sessionStorage.getItem("user"))
        //   this.user=user1

        // window.sessionStorage.removeItem("user")
        // window.sessionStorage.setItem("user",res1.data.data)
            } else {
        this.notify('修改失败', 'error')
      }
        })
        // this.imageUrl = URL.createObjectURL(file.raw)
        // this.$store.commit('setAvator', res.avator)

      }
    },
    beforeUpdate() {

    },
    beforeAvatarUpload (file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 10
      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 10MB!')
      }
      return isJPG && isLt2M
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/upload.scss';
</style>
