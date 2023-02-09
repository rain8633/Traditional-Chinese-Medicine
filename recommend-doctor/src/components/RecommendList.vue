<template>
  <div style="width:500px;margin-top:20px;max-height:1000px;">
     <el-card>
        <ul class="riji-list-header">
      <li class="active">
        诊断&&药方推荐列表
      </li>
    </ul>
        <div v-if="recommendList.length==0">
        <el-empty :image-size="200"></el-empty>
        </div>
        <div v-if="recommendList.length>0">
           <div style="margin-left:15px;" v-for="recommend in recommendList" :key="recommend.id">
                <el-divider></el-divider>
              治疗医生:{{recommend.dname}}&nbsp;<br/>
              诊断结果:{{recommend.result}}&nbsp;<br/>
              患者评分:<el-rate v-model="recommend.score" disabled show-score text-color="#ff9900" score-template="{value}"></el-rate>
              治疗方案/药方:{{recommend.solution}}<br/>
              <el-link @click="recommendDetail(recommend.bingliId)" type="primary">点击查看详情</el-link>
           </div>
        </div>
     </el-card>
  </div>
</template>

<script type="text/javascript">
export default {
  name:'recommend-list',
  props: [
    'recommendList'
  ],
  data() {
    return {

    }
  },
  created(){
     this.recommendList = this.recommendList.slice(0,5)
  },
  components: {

  },
  methods:{
      recommendDetail(id) {

       this.$router.push({path: `/recommend-detail/${id}`})
      }
  }
}
</script>

<style lang="scss"  scoped>
@import '../assets/css/recommend.scss';
</style>
