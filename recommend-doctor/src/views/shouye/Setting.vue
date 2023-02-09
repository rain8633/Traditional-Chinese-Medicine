<template>
<div>
  <the-header/>
  <div class="setting">
    <div class="leftCol">
      <div class="settingsMainHeader">设置</div>
      <ul class="setting-aside">
        <li v-for="(item, index) in settingList" :key="index" :class="{activeColor: activeName === item.name}" @click="handleClick(item)">
          {{item.name}}
        </li>
      </ul>
    </div>
    <div class="contentCol">
      <component :is="componentSrc"></component>
    </div>
  </div>
  <scroll-top/>
  <the-footer/>
</div>
</template>

<script>
import Info from '../../components/Info.vue'
import Upload from '../../components/Upload.vue'
import TheHeader from '../../components/TheHeader.vue'
import TheFooter from '../../components/TheFooter.vue'
import ScrollTop from '../../components/ScrollTop.vue'

export default {
  name: 'setting',
  components: {
    Info,
    Upload,
    TheHeader,
    TheFooter,
    ScrollTop,
  },
  data () {
    return {
      activeName: '个人信息',
      componentSrc: 'Info',
      settingList: [{
        icon: '',
        name: '个人信息',
        path: 'Info'
      }, {
        icon: '',
        name: '上传照片',
        path: 'Upload'
      }]
    }
  },
  mounted () {},
  methods: {
    handleClick (item) {
      this.activeName = item.name
      this.componentSrc = item.path
    }
  }
}
</script>

<style lang="scss" scoped>
@import '../../assets/css/setting.scss';
</style>
