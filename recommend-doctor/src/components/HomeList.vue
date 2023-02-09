<template>
  <div class="content-list">
    <div>
     <el-row :gutter="12" style="margin-top:100px;margin-left:210px;">
  <el-col :span="8">
    <el-card shadow="hover" style="height:300px;width:200px;border-color:black;text-align:center">
      在线诊断<br/>
      服务人民
    </el-card>
  </el-col>
  <el-col :span="8">
    <el-card shadow="hover" style="margin-left:80px;height:300px;width:200px;border-color:black;text-align:center">
      中医文化<br/>
      助力抗疫
    </el-card>
  </el-col>
  <el-col :span="8">
    <el-card shadow="hover" style="margin-left:180px;height:300px;width:200px;border-color:black;text-align:center">
      中医搜搜<br/>
      传播中医力量
    </el-card>
  </el-col>
</el-row>
    </div>

  </div>
</template>

<script>
import { mixin } from '../mixins'

export default {
  name: 'home-list',
  mixins: [mixin],
  methods: {



  }
}
</script>

<style lang="scss" scoped>
@import '../assets/css/content-list.scss';
</style>
