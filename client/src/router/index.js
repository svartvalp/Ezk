import Vue from 'vue'
import VueRouter from 'vue-router'
import Groups from "@/components/Groups";
import Group from "@/components/Group";
import Teachers from "@/components/Teachers";


Vue.use(VueRouter)

const routes = [
  {
    path : "",
    component : Groups
  },
  {
    path: "/group/:groupId",
    component: Group
  },
  {
    path : '/teachers',
    component: Teachers
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
