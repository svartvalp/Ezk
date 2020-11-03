<template>
  <v-app>
    <v-app-bar
      app
      color="secondary"
      dark
    >
      <v-toolbar-title>Электронная зачетная книжка</v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn dark v-if="isAuthenticated" @click="logout()" >Выйти</v-btn>
    </v-app-bar>

    <v-main>
      <v-container fluid v-if="!isAuthenticated" class="fill-height">
        <v-row align="center" justify="center">
          <Login @login="login()">
          </Login>
        </v-row>
      </v-container>
      <v-container v-if="isAuthenticated && user.roles.includes('ADMIN')">
        <AdminMainVue :user="user"></AdminMainVue>
      </v-container>
      <v-container v-if="isAuthenticated && user.roles.includes('TEACHER')">
        <TeacherMainVue :user="user"></TeacherMainVue>
      </v-container>
      <v-container v-if="isAuthenticated && user.roles.includes('STUDENT')">
        <StudentMainVue :user="user" />
      </v-container>
    </v-main>
  </v-app>
</template>

<script>

import Login from "@/components/Login";
import TeacherMainVue from "@/components/TeacherMainVue";
import StudentMainVue from "@/components/StudentMainVue";
import AdminMainVue from "@/components/AdminMainVue";
import axios from "axios";

export default {
  name: 'App',

  components: {
    AdminMainVue,
    StudentMainVue,
    TeacherMainVue,
    Login
  },
  mounted() {
    let me = this
  this.$http.interceptors.response.use(res => res, error => {
      if(error.response.data.status === 403) {
        me.isAuthenticated = false
      } else {
        alert(error.response.data.message)
      }
  })

    this.$http.get('/user/info').then(response => {
      this.isAuthenticated = true
      this.user = response.data
    }).catch(error => {
      if(error.response.data.status === 403) {
        console.log("Not authenticated")
        this.isAuthenticated = false
      }
    })
  },
  methods : {
    login()  {
      this.$http.get('/user/info').then(response => {
        this.user = response.data
        console.log(this.user)
      })
      this.isAuthenticated = true
    },
    logout() {
      this.$http.post('/logout').then(() => {
        this.user = null
        this.isAuthenticated = false
      })
    }
  },
  data: () => ({
    isAuthenticated : false,
    user : null
  }),
};
</script>
