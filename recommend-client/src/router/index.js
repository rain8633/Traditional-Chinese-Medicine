import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login/login.vue'
import AddRecords from '../views/bingli/AddRecords.vue'
import Welcome from '../views/Welcome.vue'
import AllRecords from '../views/bingli/AllRecords.vue'
import RecordDetail from '../views/bingli/RecordDetail.vue'
import Home from '../views/Home.vue'
import DocList from '../views/bingli/DoctorList.vue'
import DoctorDetail from '../views/bingli/DoctorDetail.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'login',
    component: Login
  },
   {
    path: '/home',
    name: 'Home',
    component: Home,
    redirect:'/welcome',
    children:[
      {
      path: '/welcome',
      name: 'Welcome',
      component: Welcome
      },
      {
        path:"/addRecords",
        name:"addRecords",
        component:AddRecords
      },{
        path:'/record-detail/:id',
        name:'recordDetail',
        component:RecordDetail
      },{
        path:"/allRecords",
        name:"allRecords",
        component:AllRecords
      },{
        path:'/docList',
        name:'DocLists',
        component:DocList
      },
        {
       path:'/doctor-detail/:id',
       name:'doctor-detail',
       component: DoctorDetail
    },
    ]
  },
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
