<template>
  <div class="AllRiJi">
     <!-- <el-card style="margin-bottom:10px;margin-top:-10px;width:1000px;margin-left:90px;height:80px">
        <el-form ref="form" :model="form" label-width="80px">
       <el-row :gutter="24">
         <el-col :span="6">
           <el-form-item label="查询内容">
           <el-input v-model="form.content" style="width: 150px;"   placeholder="请输入关键字"></el-input>
          </el-form-item>
         </el-col>
         <el-col :span="4">
             <el-form-item label="治疗时间">
                 <el-col :span="4">
                   <el-date-picker type="date" placeholder="选择日期" v-model="form.createTime" value-format="yyyy-MM-dd" style="width: 150px;"></el-date-picker>
                 </el-col>
             </el-form-item>
         </el-col>
         <el-col :span="8">
           <el-form-item style="margin-left:10px;">
             <el-button type="primary" @click="queryByInfo()">查询</el-button>
             <el-button type="warning" @click="resetForm()">重置</el-button>
            </el-form-item>
         </el-col>
       </el-row>
</el-form>
     </el-card> -->

   <div v-for="item in bingliList" :key="item.id">
   <el-descriptions title="病历信息" border style="width:80%;margin-left:50px;margin-top:20px;">
    <el-descriptions-item label="患者姓名">{{item.realName}}</el-descriptions-item>
    <el-descriptions-item label="手机号">{{item.phone}}</el-descriptions-item>
    <el-descriptions-item label="年龄">{{item.clientAge}}</el-descriptions-item>
    <el-descriptions-item label="病历状态">
     <el-tag v-if="item.status==0" size="small">未查看</el-tag>

     <el-tag v-if="item.status==1"  size="small">已查看</el-tag>
     <el-tag v-if="item.status==2"  size="small">已诊断</el-tag>
     <el-tag v-if="item.status==3" size="small">已解决</el-tag>
      <!-- <el-tag  @click="bingliDetail(item)" style="margin-left: 10px" size="small">点击查看详情</el-tag> -->
      <el-link @click="bingliDetail(item)" style="margin-left: 10px" type="primary">点击查看详情</el-link>
    </el-descriptions-item>
    <el-descriptions-item label="病症详情">{{item.info}}</el-descriptions-item>

</el-descriptions>
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
</template>

<script>


export default {
  data() {
    return {
      pageSize:5, // 页数
      currentPage: 1, // 当前页
      total:0,
      doctorId:'',
      value5:0,
      form: {
        content: '',
        createTime: '',
        },
      tableData: [],
      table: false,
      bingliList:[],
    }
  },
  components: {

  },
  created () {
     this.doctorId = JSON.parse(window.sessionStorage.getItem("doctor")).id
    this.queryAllBingli();
  },
  mounted () {

  },
  methods: {
     handleCurrentChange(pageNum){
      this.currentPage=pageNum
      // console.log(this.currentPage)
      this.queryAllBingli()
    },
     queryAllBingli() {
       let params = new URLSearchParams();
       params.append("doctorId",this.doctorId);
      //  params.append("content",this.form.content);
      //  params.append("createTime",this.form.createTime);
        params.append("pageSize",this.pageSize);
        params.append("pageNum",this.currentPage)

       this.$http.post('/bingli/queryAllBingli',params).then(res =>{
         if(res.data.code == 200) {
           this.bingliList = res.data.data.rows
            this.currentPage = res.data.data.pageNum
          // this.tableData = res.data.data.rows
          // // console.log(this.tableData)
          this.total=res.data.data.total
          //   for(let item in this.tableData){
          //     this.tableData[item].feelScore =  this.tableData[item].feelScore / 2
          //     //  item.feelScore = item.feelScore / 2
          //   }

         }
       }).catch(err => {
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
``

     },
      onSubmit() {
        console.log('submit!');
      },
      formatter(row, column) {
        return row.content;
      },




      rowdblclick(row, column, cell, event){
         this.$router.push({path:'/rijiDetail',query:{id:row.id}})
      },

      cancle()
      {
        this.table = false
      },
      resetForm(){
         this.form.content='',
         this.form.createTime=''
      }

  }
}
</script>

<style lang="scss"scoped>
</style>
