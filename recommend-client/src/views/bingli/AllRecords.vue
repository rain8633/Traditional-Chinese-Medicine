<template>
  <div class="Collections">
<el-card class="box-card">
  <div>
    <h2>病历记录</h2>
  </div>
   <div v-for="item in bingliList" :key="item.id">
     <el-descriptions class="margin-top"  :column="3"  border style="width:80%;margin-left:50px;margin-top:20px;">
    <!-- <template slot="extra">
      <el-button type="primary" size="small">操作</el-button>
    </template> -->
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-user"></i>
        姓名
      </template>
      {{item.realName}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-mobile-phone"></i>
        手机号
      </template>
      {{item.phone}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-time"></i>
        年龄
      </template>
      {{item.clientAge}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        病症详情
      </template>
      {{item.info}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        所属科室
      </template>
      {{item.ksName}}
    </el-descriptions-item>
    <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-tickets"></i>
        诊治医生
      </template>
      {{item.dname}}
    </el-descriptions-item>
     <el-descriptions-item>
      <template slot="label">
        <i class="el-icon-loading"></i>
        病历状态
      </template>
      <el-tag v-if="item.status==0" size="small">未查看</el-tag>

     <el-tag v-if="item.status==1"  size="small">已查看</el-tag>
     <el-tag v-if="item.status==2" size="small">已诊断</el-tag>
     <el-tag v-if="item.status==3" size="small">诊断已结束</el-tag>
      <!-- <el-tag  @click="bingliDetail(item)" style="margin-left: 10px" size="small">点击查看详情</el-tag> -->
      <el-link @click="bingliDetail(item)" style="margin-left: 10px" type="primary">点击查看详情</el-link>
    </el-descriptions-item>
  </el-descriptions>
 </div>
</el-card>
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
</template>

<script>

export default {
  name:"AllRiJi",
  data() {
    return {
      bingliList: [],
      pageSize:5, // 页数
      currentPage: 1, // 当前页
      total:0,
      userId:''
    }
  },
  components: {

  },
   created() {
    this.userId = JSON.parse(window.sessionStorage.getItem("user")).id
     this.queryAllClientBingli()
  },
  mounted () {

  },
  methods:{
     handleCurrentChange(pageNum){
      this.currentPage=pageNum
      // console.log(this.currentPage)
       this.queryAllClientBingli()
    },
      queryAllClientBingli(){
       let params = new URLSearchParams();
       params.append("userId",this.userId)
       params.append("pageSize",this.pageSize)
       params.append("pageNum",this.currentPage)
        this.$http.post("/bingli/queryAllClientBingli",params).then(res=>{
          if(res.data.code == 200){
             this.bingliList = res.data.data.rows
             console.log(this.bingliList)
             this.total=res.data.data.total
           this.currentPage=res.data.data.pageNum
          }
        }).catch(err => {
          console.log(err)
        })
      },
     bingliDetail(item){
        this.$store.commit('setBingLi', item)
        this.$router.push({path: `/record-detail/${item.id}`})
  },
  }
}
</script>

<style scoped>
.Collections{
  margin-top: -15px;
}
</style>
