import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/shouye/Home.vue'
import LoginIn from '../views/shouye/LoginIn.vue'
import newBingLi from '../views/shouye/NewBingLi.vue'
import Setting from '../views/shouye/Setting.vue'
import ZdList from '../views/shouye/ZdList.vue'
import AllBingli from '../views/shouye/AllBingLi.vue'
import BingLiDetail from '../views/shouye/BingLiDetail.vue'
import SignUp from '../views/shouye/SignUp.vue'
import newComment from '../views/shouye/NewComment.vue'
import RecommendDetail from '../views/shouye/RecommendDetail.vue'
import Zyss from '../views/shouye/Zyss.vue'
import SearchDetail from '@/views/shouye/SearchDetail'

Vue.use(VueRouter)

const routes = [
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
    {
      path: '/',
      name: 'login-in',
      component: LoginIn
    },{
    path:'/zd-list',
    name:'ZdList',
    component: ZdList,
     children:[
      {
      path: '/AllBingli',
      name: 'AllBingli',
      component: AllBingli
      },
      // {
      // path: '/endBingli',
      // name: 'endBingli',
      // component: endBingli
      // },
      //  {
      // path: '/ingBingli',
      // name: 'ingBingli',
      // component: ingBingli
      // },
    ]
  },

    {
      path: '/newBingLi',
      name: 'newBingLi',
      component: newBingLi
    },
{
      path: '/newComment',
      name: 'newComment',
      component: newComment
    },
  {
      path: '/bingli-detail/:id',
      name: 'bingli-detail',
      component: BingLiDetail
    },
  {
      path:'/setting',
      name:'Setting',
      component: Setting
    },
    {
       path:'/recommend-detail/:id',
       name:'recommend-detail',
       component: RecommendDetail
    },
     {
       path:'/search-detail/:id',
       name:'search-detail',
       component: SearchDetail
    },
    {
      path:'/sign-up',
      name:'SignUp',
      component:SignUp
    },
    {
      path:"/zyss",
      name:"zyss",
      component:Zyss
    }
]

const router = new VueRouter({
  routes,
  mode:'history'
})

const originalPush = VueRouter.prototype.push
   VueRouter.prototype.push = function push(location) {
   return originalPush.call(this, location).catch(err => err)
}

export default router
