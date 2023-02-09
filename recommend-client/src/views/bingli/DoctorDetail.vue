<template>
  <div class="">
     <div style="text-align:center;">
      <img :src="path+Doctor.pic" style="width:300px;height;400px;">
     </div>
     <div style="margin-left:100px;">
     <div>
       <span>姓名:</span>
       <span>{{Doctor.dname}}</span>
     </div>
     <div>
       <span>性别:</span>
       <span v-if="Doctor.sex==1">男</span>
       <span v-if="Doctor.sex==0">女</span>
     </div>
     <div>
       <span>手机号:</span>
       <span>{{Doctor.phone}}</span>
     </div>
     <div>
       <span>邮箱:</span>
       <span>{{Doctor.email}}</span>
     </div>
      <div>
       <span>所在医院:</span>
       <span>{{Doctor.hospName}}</span>
     </div>
     <div>
       <span>科室:</span>
       <span>{{Doctor.ksName}}</span>
     </div>
     <div>
       <span>基本信息:</span>
       <span>{{Doctor.info}}</span>
     </div>
     </div>
  </div>
</template>

<script type="text/javascript">
import { mapGetters } from 'vuex'
export default {
  data() {
    return {

    }
  },
   computed: {
    ...mapGetters([
      'Doctor',
    ])
  },
  components: {

  },
  methods: {

  }
}
</script>

<style scoped>
</style>
