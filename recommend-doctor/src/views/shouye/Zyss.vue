<template>
  <div>
    <the-header/>
     <div id="search">
         <el-row :gutter="20">
           <el-col :span="3">
             <img src="@/assets/img/12.png" style="margin-top:60px;margin-left:450px;width:100px;height:120px" alt="">
           </el-col>
           <el-col :span="12">
            <div class="header-search">
             <input type="text" placeholder="请输入要搜索的药方或药材" @keyup.enter="goSearch()" v-model="keywords">
            </div>
           </el-col>
         </el-row>
     </div>

   <div v-if="searchList.length > 0">
     <div>
      <search-list :searchList="searchList"></search-list>
     </div>
      <div class="pagination" style="margin-top:25px;margin-left:140px;">
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

     <div v-if="searchList.length==0">
         <el-empty description="暂无数据。请输入关键字搜索"></el-empty>
     </div>

    <scroll-top/>
    <the-footer/>
  </div>
</template>

<script type="text/javascript">
import TheHeader from '../../components/TheHeader.vue'
import TheFooter from '../../components/TheFooter.vue'
import ScrollTop from '../../components/ScrollTop.vue'
import SearchList from '../../components/SearchList.vue'

export default {
  data() {
    return {
       keywords:'',
       searchList:[],
       pageSize: 10, // 页数
       currentPage: 1, // 当前页
       total:0,
    }
  },
  components: {
    TheHeader,
    TheFooter,
    ScrollTop,
    SearchList
  },
  methods: {

     handleCurrentChange(pageNum){
      this.currentPage=pageNum
      console.log(this.currentPage)
      this.goSearch()
    },

     goSearch(){
         this.$http.get('/search/'+this.keywords+'/'+this.currentPage+'/'+this.pageSize).then(res=>{
             if(res.data.code==200){
                 this.searchList = res.data.data.rows
                 this.total=res.data.data.total
                 this.currentPage = res.data.data.pageNum

             }
         })
     }
  }
}
</script>

<style scoped>
  #search{
    margin-top:30px;
    margin-left: 70px;
  }

   #search input[type="text"]{
        width:600px;
        height:48px;
        margin-top: 21px;
        font-size: 20px;
        border-width: 2px;
        border-color: skyblue;
        border-top-left-radius: 10px;
        border-top-right-radius: 10px;
        border-bottom-right-radius:10px;
        border-bottom-left-radius: 10px;
        background: url('../../assets/img/sousuo.png') no-repeat right transparent;
        background-size:35px 35px;
        background-position: right;
    }
 .header-search{
   margin-left:330px;
   margin-top:70px;
 }
 .search-btn{
   margin-left:55px;
   margin-top:90px;
 }
</style>
