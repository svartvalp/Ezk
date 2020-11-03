<template>
  <v-card>
    <v-dialog v-model="dialog" max-width="300px">
      <v-card>
        <v-text-field v-model="login" label="Логин"></v-text-field>
        <v-text-field v-model="password" label="Пароль"></v-text-field>
        <v-text-field v-model="firstName" label="Имя"></v-text-field>
        <v-text-field v-model="lastName" label="Фамилия"></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancel">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="saveTeacher">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-card-title>
      Преподаватели
      <v-spacer></v-spacer>
      <v-btn @click="openAdd" >Добавить</v-btn>
    </v-card-title>
    <v-data-table
        :headers="teacherHeaders"
        :items="teachers"
    >
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="deleteTeacher(item)"
        >
          mdi-delete
        </v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>
<script>
export default {
  mounted() {
    this.$http.get('/user/teacher').then(res => {
      this.teachers = res.data
      console.log(this.teachers)
    })
  },
  methods : {
    deleteTeacher(item) {
      this.$http.delete(`/user/${item.id}`).then(() => {
        this.teachers = this.teachers.filter(t => t.id !== item.id)
      })
    },
    openAdd() {
      this.dialog = true
    },
    cancel() {
      this.dialog = false
    },
    saveTeacher() {
      let user = {
        login : this.login,
        password : this.password,
        firstName : this.firstName,
        lastName : this.lastName
      }
      this.$http.post('/user/teacher', user).then((res) => {
         this.teachers.push(res.data)
        this.login = ''
        this.password = ''
        this.firstName = ''
        this.lastName = ''
        this.dialog = false
      })
    }

  },
  data : () => ({
    teacherHeaders : [
      {
        text : 'FirstName',
        value : 'firstName'
      },
      {
        text : 'LastName',
        value : 'lastName'
      },
      {
        value: 'actions'
      }
    ],
    teachers : [],
    dialog : false,
    login : '',
    password : '',
    firstName : '',
    lastName : ''
  })
}
</script>