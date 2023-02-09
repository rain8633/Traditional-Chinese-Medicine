<template>
  <div class="TripList">

<!-- 展示医生列表 -->
    <el-card style="margin-bottom:20px;margin-top:-20px;width:1380px;margin-left:20px;">
     <el-form :model="queryForm" ref="queryForm">
       <el-row :gutter="24">

         <el-col :span="4">
           <el-form-item prop="cityName">
          <el-select  v-model="queryForm.cityName" clearable placeholder="请选择地区" @change="queryHosp()" style="width:150px;">
          <el-option
         v-for="item in cities"
         :key="item.value"
        :label="item.label"
        :value="item.value"
       >
    </el-option>
  </el-select>

           </el-form-item>
           </el-col>

           <el-col :span="4">
           <el-form-item prop="hospName">
        <el-select v-model="queryForm.hospName"  clearable placeholder="请选择医院" @change="queryKs()" style="width:150px;">
    <el-option
      v-for="item in hospList"
      :key="item.hospName"
      :label="item.hospName"
      :value="item.hospName">
    </el-option>
  </el-select>
           </el-form-item>
         </el-col>


         <el-col :span="4">
         <el-form-item prop="ksName">
       <el-select  v-model="queryForm.ksName" clearable placeholder="请选择科室" @change="queryDoc()" style="width:150px;">
    <el-option
      v-for="item in ksList"
      :key="item.ksName"
      :label="item.ksName"
      :value="item.ksName">
    </el-option>
  </el-select>
           </el-form-item>
         </el-col>


         <el-col :span="4">
           <el-form-item prop="id">
         <el-select v-model="queryForm.id" clearable placeholder="请选择医生" style="width:150px;">
    <el-option
      v-for="item in docsList"
      :key="item.id"
      :label="item.dname"
      :value="item.id">
    </el-option>
  </el-select>
           </el-form-item>
         </el-col>

         <el-col :span="4">
      <el-button type="primary" @click="queryDocInfo()">查询</el-button>
      <el-button type="warning" @click="resetForm()">重置</el-button>
    </el-col>
       </el-row>

     </el-form>
    </el-card>
<div v-if="DocList.length>0">
  <el-row >
  <el-col :span="6" v-for="(Doc,index) in DocList" :key="index" >
    <el-card shadow="hover" :body-style="{ padding: '0px' }" style="width:280px;height:360px">
      <img :src="path+Doc.pic" class="image" @click="Docdetail(Doc)" height="280px" width="280px">
      <div style="padding: 5px;">
        <span>{{Doc.dname}}</span>
        <span style="margin-left:8px;color: orange;">{{Doc.ksName}}</span>
      </div>
       <div style="margin-left:5px;color: red;">
         <span>联系方式:</span>
        <span>{{Doc.phone}}</span>
      </div>
    </el-card>
  </el-col>
</el-row>

 <el-pagination style="margin-top:30px;margin-left:5px;"
      @size-change="handleSizeChange"
      @current-change="handleCurrentChange"
      :current-page="pageNum"
      :page-sizes="[4,8,16,32]"
      :page-size="pageSize"
      layout="total, sizes, prev, pager, next, jumper"
      :total="total">
    </el-pagination>
</div>

<div v-if="DocList.length==0">
<el-empty description="请选择查询条件"></el-empty>
</div>

  </div>
</template>

<script type="text/javascript">
import { mixin } from "../../mixins";
import {cities} from '../../assets/data/form'
export default {
  name:"DoctorList",
  mixins: [mixin],
  data() {
    return {
      DocList:[],
      cities:[],
      total:0,
      pageSize:4,
      pageNum:1,
      queryForm:{
       cityName:'',
       hospName:'',
       ksName:'',
       id:''
      },
     hospList:[],
     ksList:[],
     docsList:[]
    }
  },
  components: {

  },
  methods: {
    resetForm(){
     this.queryForm={
        cityName: "",
        hospName:'',
        ksName:'',
        id:''
      }
      this.DocList = null
    },
      queryHosp(){
      this.$http.post('/queryHosp/'+this.queryForm.cityName).then(res=>{
         if(res.data.code == 200){
             this.hospList = res.data.data
            //  console.log(res.data.data)
         }
      })
    },
    queryKs(){
         this.$http.post('/queryKeshi/'+this.queryForm.hospName).then(res=>{
         if(res.data.code == 200){
             this.ksList = res.data.data
            //  console.log(res.data.data)
         }
      })
    },
     queryDoc(){
         this.$http.post('/queryDoc/'+this.queryForm.ksName+'/'+this.queryForm.hospName+'/'+this.queryForm.cityName).then(res=>{
         if(res.data.code == 200){
             this.docsList = res.data.data
            //  console.log(res.data.data)
         }
      })
    },
    Docdetail(doctor){
        this.$store.commit('setDoctor', doctor)
        this.$router.push({path: `/doctor-detail/${doctor.id}`})
    },
    handleSizeChange(pageSize){
       this.pageSize=pageSize
       this.queryDocInfo()
    },
    handleCurrentChange(pageNum){
      this.pageNum=pageNum
      this.queryDocInfo()
    },
    queryDocInfo(){
       let params = new URLSearchParams()
       params.append("id",this.queryForm.id)
       params.append("hospName",this.queryForm.hospName)
       params.append("ksName",this.queryForm.ksName)
       params.append("cityName",this.queryForm.cityName)
       params.append("pageNum",this.pageNum)
       params.append("pageSize",this.pageSize)
       this.$http.post('/doctor/queryDocList', params).then(res=>{
          if(res.data.code==200){
            this.DocList = res.data.data.rows
            this.pageNum = res.data.data.pageNum

            this.total = res.data.data.total
          }
       })
    }

  },
  created(){
     let user=JSON.parse(window.sessionStorage.getItem("user"));
   if(user != null){
     this.user=user;
   }
     this.cities = cities
  }
}


</script>

<style scoped>
  .time {
    font-size: 13px;
    color: #999;
  }

  /deep/input::-webkit-outer-spin-button,
  /deep/input::-webkit-inner-spin-button {
    -webkit-appearance: none;
    appearance: none;
    margin: 0;
  }

</style>
