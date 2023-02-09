<template>
  <div class="addTrip">
  <el-card class="box-card" style="margin-top:-20px;">
  <div slot="header" class="clearfix" >
    <h2>提交病历</h2>
  </div>
  <el-form :model="addBingliForm" :rules="addBingliRules" ref="addBingliForm" label-width="150px" class="demo-addBingliForm">

     <el-form-item label="患者姓名" prop="name">
    <el-input type="text" v-model="addBingliForm.name" style="width:150px;">
    </el-input>
  </el-form-item>

    <el-form-item label="年龄" prop="age">
    <el-input type="number" v-model="addBingliForm.age" style="width:150px;">
    </el-input>
  </el-form-item>


  <el-form-item prop="docId" label="选择医生">
  <el-select  v-model="city" clearable placeholder="请选择地区" @change="queryHosp()" style="width:150px;">
    <el-option
      v-for="item in cities"
      :key="item.value"
      :label="item.label"
      :value="item.value"
     >
    </el-option>
  </el-select>
  <el-select v-model="hosp"  clearable placeholder="请选择医院" @change="queryKs()" style="width:150px;">
    <el-option
      v-for="item in hospList"
      :key="item.hospName"
      :label="item.hospName"
      :value="item.hospName">
    </el-option>
  </el-select>
  <el-select  v-model="keshi" clearable placeholder="请选择科室" @change="queryDoc()" style="width:150px;">
    <el-option
      v-for="item in ksList"
      :key="item.ksName"
      :label="item.ksName"
      :value="item.ksName">
    </el-option>
  </el-select>
  <el-select v-model="addBingliForm.docId" clearable placeholder="请选择医生" style="width:150px;">
    <el-option
      v-for="item in docList"
      :key="item.id"
      :label="item.dname"
      :value="item.id">
    </el-option>
  </el-select>
  </el-form-item>

   <el-form-item label="病情描述" prop="desc">
    <el-input type="textarea" v-model="addBingliForm.desc" placeholder="包括现在的症状以及既往史"></el-input>
  </el-form-item>

<el-form-item label="病症图片(包括面部，舌苔图片最多三张)" prop="picImg" style="display:table">
     <el-upload
       name="file"
      :action="path+'/bingli/upload'"
      :on-success="success"
      :limit="3"
      :on-exceed="exceed"
      :file-list="fileList"
      list-type="picture">
  <el-button  size="small" type="primary" style="margin-right:25px;">上传图片</el-button>
  <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
</el-upload>
    </el-form-item>

  <el-form-item>
    <el-button type="primary" @click="addBingLi('addBingliForm')">提交病历</el-button>
    <el-button @click="resetForm('addBingliForm')">取消</el-button>
  </el-form-item>
</el-form>
</el-card>

  </div>
</template>

<script type="text/javascript">

import { cities } from '../../assets/data/form'

export default {
  name:"AddRecords",
  data() {
    return {
      userId:'',
      city:'',
      hosp:'',
      keshi:'',
      addBingliForm:{
        name:'',
          age:'',
          docId:'',
          desc:'',
          picImg: '',
      },
      pic1:'',
      pic2:'',
      pic3:'',
      cities:[],
      hospList:[],
      ksList:[],
      docList:[],
      addBingliRules:{
          name: [
            { required: true, message: '患者姓名不能为空', trigger: 'change' },
          ],
          age: [
            { required: true, message: '年龄不能为空', trigger: 'change' },
          ],
          docId: [
            { required: true, message: '请选择一个医生', trigger: 'change' },
          ],
           desc: [
            { required: true, message: '病症描述不能为空', trigger: 'blur' },
          ]
      },
      fileList:[],
    }
  },
  created (){
     this.cities = cities
     this.userId = JSON.parse(window.sessionStorage.getItem("user")).id;
  },
  methods:{
    success(file,response,fileList){

      // console.log(fileList)
      this.pic1 = fileList[0].response.data
      this.pic2 = fileList[1].response.data
      this.pic3 = fileList[2].response.data
      // this.addBingliForm.picImg=fileList.data;
      // console.log(this.addBingliForm.picImg)
      this.$refs['addBingliForm'].validateField('picImg');
    },
     exceed(){
      const _this=this
      this.$message.error("最多上传三张图片")
      // _this.notify("只能上传一张房源图片","error")
    },

    queryHosp(){
      this.$http.post('/queryHosp/'+this.city).then(res=>{
         if(res.data.code == 200){
             this.hospList = res.data.data
            //  console.log(res.data.data)
         }
      })
    },
    queryKs(){
         this.$http.post('/queryKeshi/'+this.hosp).then(res=>{
         if(res.data.code == 200){
             this.ksList = res.data.data
            //  console.log(res.data.data)
         }
      })
    },
     queryDoc(){
         this.$http.post('/queryDoc/'+this.keshi+'/'+this.hosp+'/'+this.city).then(res=>{
         if(res.data.code == 200){
             this.docList = res.data.data
            //  console.log(res.data.data)
         }
      })
    },
     addBingLi(formName) {

        this.$refs[formName].validate((valid) => {
          if (valid) {
            let params = new URLSearchParams();
           params.append("clientId",this.userId)
           params.append("realName",this.addBingliForm.name)
           params.append("doctorId",this.addBingliForm.docId)
           params.append("clientAge",this.addBingliForm.age)
           params.append("info",this.addBingliForm.desc)
           params.append("pic1",this.pic1)
           params.append("pic2",this.pic2)
           params.append("pic3",this.pic3)
            this.$http.post("/bingli/addBingLi",params).then(res=>{
               if(res.data.code==200){
                 this.$message.success("添加成功")
                //  this.notify("添加成功","success"),
                 this.resetForm(formName);
                 this.fileList=[];
                 this.city='';
                 this.hosp='';
                 this.keshi='';
               }
            })
          } else {
            console.log('表单填写有误');
            return false;
          }
        });
      },
      resetForm(formName) {
        this.$refs[formName].resetFields();
        this.fileList=[];
         this.city='';
         this.hosp='';
         this.keshi='';
      },
  }
}
</script>

<style scoped>
</style>
