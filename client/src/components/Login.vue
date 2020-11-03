<template>
  <v-card width="400px">
    <v-text-field label="Логин" v-model="username"></v-text-field>
    <v-text-field label="Пароль" v-model="password"></v-text-field>
    <v-btn @click="login()">Войти</v-btn>
  </v-card>
</template>
<script>
export default {
  data : () => ({
    username : '',
    password : ''
  }),
  methods : {
      login() {
        if(this.username.length === 0 || this.password.length === 0) {
          alert('Не заполнены все поля')
          return
        }
        this.$http.post(`/login?username=${this.username}&password=${this.password}`).then(() => {
          console.log('Logged in')
          this.$emit('login')
        })
    }
  }
}
</script>