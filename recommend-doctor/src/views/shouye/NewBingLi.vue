<template>
 <div class="home">
   <the-header/>
  <div class="trip-list">
    <ul class="trip-list-header">
      <li class="active">
        未查看病历
      </li>
    </ul>
    <div v-if="newBingLiList.length>0">
      <content-list :contentList="newBingLiList"></content-list>

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
   <div v-if="newBingLiList.length==0">
      <el-empty description="暂无未查看病历"></el-empty>
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
import ContentList from '../../components/ContentList.vue'
import ScrollTop from '../../components/ScrollTop.vue'

export default {
  name:'new-bingli',
  data() {
    return {
      newBingLiList:[],
      pageSize: 5, // 页数
      currentPage: 1, // 当前页
      total:0,
      doctorId:''
    }
  },
  components: {
    TheHeader,
    TheFooter,
   ContentList,
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
   this.queryNewBingLi()
  },
  methods: {

    handleCurrentChange(pageNum){
      this.currentPage=pageNum
      console.log(this.currentPage)
      this.queryNewBingLi ()
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

    queryNewBingLi () {

     this.$http.get("/bingli/queryNewBingLi",{params:{
           doctorId:this.doctorId,
           pageSize:this.pageSize,
           pageNum:this.currentPage
          }})
        .then(res => {
          this.currentPage = res.data.data.pageNum
          // this.tripList = res.data.data.rows
          // console.log(this.tripList)
          this.total=res.data.data.total
          this.newBingLiList = res.data.data.rows
          console.log(this.newBingLiList)
          // console.log(this.total)
          //  this.pageNum=res.data.data.pageNum
        })
        .catch(err => {
          console.log(err)
        })

    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/trip-list.scss';
.the-footer{
  margin-top:-60px !important
}
</style>
