<template>
  <div>
    <div class="comment">
      <h2>医患交流区</h2>
      <div class="comment-msg">
        <div class="comment-img">
          <img :src="path+pic" alt="">
        </div>
        <el-input
          class="comment-input"
          type="textarea"
          :rows="2"
          placeholder="请输入内容"
          v-model="textarea">
        </el-input>
      </div>
      <el-button type="primary" class="sub-btn" @click="postComment()">评论</el-button>
    </div>
    <p style="margin-left:20px;">共 {{commentList.length}} 条评论</p>
    <ul class="popular" v-for="(item, index) in commentList" :key="index">
      <li>
        <div class="popular-img" v-if="item.role==0">
          <img :src="path+item.img" alt="">
        </div>
        <div class="popular-img" v-if="item.role==1">
          <img :src="path+item.pic" alt="">
        </div>
        <div class="popular-msg">
          <ul>
            <li class="name">{{item.role == 1?item.dname+"医生":item.userName}}</li>
            <li class="time">{{item.createTime}}</li>
            <li class="content">{{item.content}}</li>
          </ul>
        </div>
      </li>
    </ul>
  </div>
</template>

<script>

import {mixin} from '../mixins'
import { mapGetters } from 'vuex'
// import { getUserOfId, setComment, setLike, getAllComment } from '../api/index'

export default {
  name: 'comment',
  mixins: [mixin],
  props: [
    'bingliId',
  ],
  data () {
    return {
      id:'',
      pic:'',
      commentList: [], // 存放评论内容
      userPic: [], // 保存评论用户头像
      userName: [], // 保存评论用户名字
      textarea: '', // 存放输入内容
      // img1:require("../../static/zan1.png"),
      // url2:require('../assets/img/zan2.png'),
    }
  },
  computed: {
    ...mapGetters([
      'loginIn', // 用户是否登录
      'BingLi'
    ])
  },
  watch: {
    id () {
      this.getAllComment()
    }
  },
  mounted () {

    this.pic=JSON.parse(window.sessionStorage.getItem("doctor")).pic;
    // console.log(this.pic)
    this.id=JSON.parse(window.sessionStorage.getItem("doctor")).id;
     this.getAllComment()
  },
  methods: {
    // 获取所有评论
    getAllComment () {
      this.$http.post("/comment/queryAllComment/"+this.bingliId).then(res=>{
           this.commentList = res.data.data;
          //  for(let item of this.commentList){
          //      this.getUsers(item.userId)
          //  }
      }).catch(err=>{
        console.error(err);
      })
    },
    // // 获取评论用户的昵称和头像
    // getUsers (id) {
    //   this.$http.post("/user/queryUserById/"+id).then(res => {
    //     this.userPic.push(res.data.data.pic);
    //     // console.log(this.userPic)
    //     this.userName.push(res.data.data.userName);
    //   }).catch(err => {
    //     console.log(err)
    //   })
    // },
    // 提交评论
    postComment () {

        let params = new URLSearchParams()
          params.append('bingliId',this.bingliId)
        params.append('content', this.textarea)
        params.append('role',1)
        this.$http.post("/comment/addComment", params)
          .then(res => {
            if (res.data.code == 200) {
              this.textarea = ''
              this.getAllComment()
              this.notify('回复成功', 'success')
            } else {
              this.notify('回复失败', 'error')
            }
          })
          .catch(err => {
            console.log(err)
          })
    },
    // 点赞
    postUp (id, up, index) {
      if (this.loginIn) {
        let params = new URLSearchParams()
        params.append('id', id)
        params.append('up', up + 1)
        this.$http.post('/comment/setTripLike',params).then(res => {
            if (res.data.code === 200) {
              this.$refs.up[index].children[0].style.color = '#2796dd'
              this.getAllComment()
            }
          })
          .catch(err => {
            console.log(err)
          })
      } else {
        this.notify('请先登录', 'warning')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/comment.scss';
</style>
