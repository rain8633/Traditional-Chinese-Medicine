import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
// import VueAMap from 'vue-amap'
import './plugins/axios'
import VueParticles from 'vue-particles'
import element from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import * as echarts from 'echarts'
import BMap from 'vue-baidu-map'
import '@/assets/js/iconfont.js'
import '@/assets/js/iconfont1.js'
import '@/assets/js/iconfont2.js'
import '@/assets/js/iconfont3.js'
import '@/assets/js/iconfont4.js'



Vue.use(BMap, {
  // ak 是在百度地图开发者平台申请的密钥 详见 http://lbsyun.baidu.com/apiconsole/key */
  ak: '4jDRKykv7NE0wl0hmx0R5HUyKOoqDDQV'
})
//引用
import 'summernote'
import 'summernote/dist/summernote.css'
import 'summernote/lang/summernote-zh-CN.js'
import 'bootstrap'
import 'bootstrap/dist/css/bootstrap.css'
import 'popper.js'



Vue.prototype.$echarts = echarts
Vue.use(VueParticles)
// Vue.use(VueAMap)

Vue.config.productionTip = false
Vue.use(element)

// VueAMap.initAMapApiLoader({
//     key: 'e0848d46c1f486a05a9a90c8f7748905',
//     plugins: ['AMap.Autocomplete', 'AMap.PlaceSearch', 'AMap.Scale', 'AMap.OverView', 'AMap.ToolBar', 'AMap.MapType', 'AMap.PolyEditor', 'AMap.CircleEditor'],
//     v: '1.4.4',
//     uiVersion: '1.0.11'
// })

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
