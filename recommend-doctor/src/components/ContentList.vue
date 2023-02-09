<template>
  <div class="content-list">
    <div>
      <div v-for="item in contentList" :key="item.id">
     <el-descriptions title="病历信息" border style="width:80%;margin-left:50px;margin-top:20px;">
    <el-descriptions-item label="患者姓名">{{item.realName}}</el-descriptions-item>
    <el-descriptions-item label="手机号">{{item.phone}}</el-descriptions-item>
    <el-descriptions-item label="年龄">{{item.clientAge}}</el-descriptions-item>
    <el-descriptions-item label="病历状态">
     <el-tag v-if="item.status==0" size="small">未查看</el-tag>

     <el-tag v-if="item.status==1"  size="small">已查看</el-tag>
     <el-tag v-if="item.status==2" size="small">已解决</el-tag>
      <!-- <el-tag  @click="bingliDetail(item)" style="margin-left: 10px" size="small">点击查看详情</el-tag> -->
      <el-link @click="bingliDetail(item)" style="margin-left: 10px" type="primary">点击查看详情</el-link>
    </el-descriptions-item>
    <el-descriptions-item label="病症详情">{{item.info}}</el-descriptions-item>
    </el-descriptions>
    </div>
    </div>

  </div>
</template>

<script>
import { mixin } from '../mixins'

export default {
  name: 'content-list',
  mixins: [mixin],
  props: [
    'contentList'
  ],
  methods: {

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


     },
  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
</style>
