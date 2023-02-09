import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/axios'
import element from 'element-ui'
import VueParticles from 'vue-particles'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/js/iconfont.js'
import '@/assets/js/iconfont1.js'
import '@/assets/js/iconfont2.js'
import '@/assets/js/iconfont3.js'

Vue.config.productionTip = false
Vue.use(element)
Vue.use(VueParticles)
import './assets/css/et.css'


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
