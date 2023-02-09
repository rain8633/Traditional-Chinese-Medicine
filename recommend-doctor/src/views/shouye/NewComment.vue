<template>
 <div class="home">
   <the-header/>
  <div class="trip-list">
    <ul class="trip-list-header">
      <li class="active">
        未查看回复
      </li>
    </ul>
    <div v-if="newCommentList.length>0">
      <div v-for="comment in newCommentList" :key="comment.id">
       <el-card class="box-card">
        <div slot="header" class="clearfix">
        <span style="overflow: hidden;margin-left:50px;">评论信息:&nbsp;&nbsp;{{comment.content}}</span>
        <el-button style="float: right; padding: 3px 0" @click="bingliDetail(comment)" type="text">点击进入病历详情</el-button>
        </div>
        <div class="text">
        <el-descriptions title="病历信息" border style="width:80%;margin-left:50px;margin-top:20px;">
        <el-descriptions-item label="患者姓名">{{comment.realName}}</el-descriptions-item>
        <el-descriptions-item label="手机号">{{comment.phone}}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{comment.clientAge}}</el-descriptions-item>
         <el-descriptions-item label="病历状态">
        <el-tag v-if="comment.status==0" size="small">未查看</el-tag>

        <el-tag v-if="comment.status==1"  size="small">已查看</el-tag>
        <el-tag v-if="comment.status==2" size="small">已诊断</el-tag>
        <el-tag v-if="comment.status==3" size="small">已结束</el-tag>
        <!-- <el-tag  @click="bingliDetail(item)" style="margin-left: 10px" size="small">点击查看详情</el-tag> -->
        <!-- <el-link  style="margin-left: 10px" type="primary">点击查看详情</el-link> -->
        </el-descriptions-item>
        <el-descriptions-item label="病症详情">{{comment.info}}</el-descriptions-item>
       </el-descriptions>
       </div>
      </el-card>
  </div>

      <div class="pagination" style="margin-top:25px;margin-left:70px;">
        <el-pagination
         @current-change="handleCurrentChange"
          background
          layout="total, prev, pager, next"
          :current-page="currentPage"
          :page-size="pageSize"
          :total="total">
        </el-pagination>
      </div>
    </div>
    <div v-if="newCommentList.length==0">
      <el-empty description="暂无未查看回复"></el-empty>
    </div>
  </div>
  <scroll-top/>
  <the-footer/>
</div>
</template>

<script type="text/javascript">
import { mapGetters } from 'vuex'
import TheHeader from '../../components/TheHeader.vue'
import TheFooter from '../../components/TheFooter.vue'
import ScrollTop from '../../components/ScrollTop.vue'

export default {
  name:'new-comment',
  data() {
    return {
      newCommentList:[],
      pageSize: 5, // 页数
      currentPage: 1, // 当前页
      total:0,
      doctorId:''
    }
  },
  components: {
    TheHeader,
    TheFooter,
    ScrollTop
  },
  computed: {
    ...mapGetters([
      'songsList',
      'loginIn'
    ]),
    // 计算当前表格中的数据
    data () {
      return this.tripList.slice((this.currentPage - 1) * this.pageSize, this.currentPage * this.pageSize)
    }
  },
  mounted () {
    let doctor = JSON.parse(window.sessionStorage.getItem("doctor"))
    if(doctor != null){
       this.doctorId = doctor.id
    }
   this.queryNewComment()
  },
  methods: {

    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      console.log(this.currentPage)
      this.queryNewComment()
    },
    // handleChangeView(name) {
    //   this.activeName = name
    //   this.tripList = []
    //   if (name === '全部景点') {
    //     this.getTripList()
    //   } else {
    //     this.getTripListBylevel(name)
    //   }
    // },

    queryNewComment () {

     this.$http.get("/comment/queryNewComment",{params:{
           doctorId:this.doctorId,
           pageSize:this.pageSize,
           pageNum:this.currentPage
          }})
        .then(res => {
          this.currentPage = res.data.data.pageNum
          // this.tripList = res.data.data.rows
          // console.log(this.tripList)
          this.total=res.data.data.total
          this.newCommentList = res.data.data.rows
          console.log(this.newCommentList)
          // console.log(this.total)
          //  this.pageNum=res.data.data.pageNum
        })
        .catch(err => {
          console.log(err)
        })

    },
      bingliDetail(item){

       if(item.status == 0 ){
           this.$http.post('/bingli/updateBingLiStatus/'+item.id+'/'+1).then(res=>{
          if(res.data.code == 200) {
         this.$store.commit('setBingLi', item)
        this.$router.push({path: `/bingli-detail/${item.id}`})
          }
       })
       }else{
           this.$store.commit('setBingLi', item)
        this.$router.push({path: `/bingli-detail/${item.id}`})
       }
      }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/trip-list.scss';
.the-footer{
  margin-top:-60px !important
}

.text {
    font-size: 14px;
    margin-top:-10px;
  }
  .clearfix:before,
  .clearfix:after {
    display: table;
    content: "";
  }
  .clearfix:after {
    clear: both
  }

  .box-card {
    width: 880px;
    margin-left: 180px;
  }
</style>
