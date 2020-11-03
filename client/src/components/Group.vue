<template>
  <v-container>
    <v-dialog v-model="studentDialog" max-width="300px">
      <v-card>
        <v-text-field v-model="studentLogin" label="Логин"></v-text-field>
        <v-text-field v-model="studentPassword" label="Пароль"></v-text-field>
        <v-text-field v-model="studentFirstName" label="Имя"></v-text-field>
        <v-text-field v-model="studentLastName" label="Фамилия"></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancelStudent">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="saveStudent">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-dialog v-model="subjectDialog" max-width="300px">
      <v-card>
        <v-select :items="teachers" v-model="subjectTeacher" item-text="lastName"
                  :hint="`${subjectTeacher.firstName} ${subjectTeacher.lastName}`"
                  persistent-hint
                  return-object
                  single-line
        />
        <v-text-field v-model="subjectName" label="Название"></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancelSubject">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="saveSubject">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-subheader>{{ this.group ? this.group.groupName : '' }}</v-subheader>
    <v-card>
      <v-card-title>
        Студенты
        <v-spacer></v-spacer>
        <v-btn @click="openAddStudent" >Добавить</v-btn>
      </v-card-title>
      <v-data-table
          :headers="studentHeaders"
          :items="studentItems"
      >
        <template v-slot:item.actions="{ item }">
          <v-icon
              small
              class="mr-2"
              @click="deleteStudent(item)"
          >
            mdi-delete
          </v-icon>
        </template>
      </v-data-table>
    </v-card>
    <v-divider></v-divider>
    <v-card>
      <v-card-text>
        <v-card-title>
          Предметы
          <v-spacer></v-spacer>
          <v-btn @click="openAddSubject" >Добавить</v-btn>
        </v-card-title>
        <v-data-table
            :headers="subjectHeaders"
            :items="subjectItems"
        >
          <template v-slot:item.actions="{ item }">
            <v-icon
                small
                class="mr-2"
                @click="deleteSubject(item)"
            >
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
      </v-card-text>
    </v-card>
  </v-container>
</template>
<script>
export default {
  props: {
    user: Object
  },
  mounted() {
    this.groupId = this.$route.params.groupId
    this.loadStudents()
    this.loadSubjects()
    this.loadGroup()
    this.loadTeachers()
  },
  methods: {
    loadStudents() {
      this.$http.get(`/user/student/group/${this.groupId}`).then(response => {
        this.studentItems = response.data
      })
    },
    loadSubjects() {
      this.$http.get(`/subject/group/${this.groupId}`).then(response => {
        this.subjectItems = response.data
      })
    },
    loadGroup() {
      this.$http.get(`/group/${this.groupId}`).then(response => {
        this.group = response.data
      })
    },
    loadTeachers() {
      this.$http.get('/user/teacher').then(response => {
        this.teachers = response.data
        console.log(this.teachers)
      })
    },
    deleteSubject(item) {
      console.log(item.id)
      this.$http.delete(`/subject/${item.id}`).then(() => {
        this.subjectItems = this.subjectItems.filter(it => it.id !== item.id)
        this.$nextTick(() => {

        })
      })
      console.log(item)
    },
    deleteStudent(item) {
      this.$http.delete(`/user/${item.userId}`).then(() => {
        this.studentItems = this.studentItems.filter(it => it.userId !== item.userId)
        console.log(this.studentItems)
      })
      console.log(item)
    },
    cancelStudent() {
      this.studentDialog = false
      this.studentLogin = ''
      this.studentPassword = ''
      this.studentFirstName = ''
      this.studentLastName = ''
    },
    saveStudent() {
      let student = {
        login : this.studentLogin,
        password : this.studentPassword,
        firstName : this.studentFirstName,
        lastName : this.studentLastName
      }
      this.$http.post(`/user/student?groupId=${this.group.id}`, student).then(res => {
        this.studentItems.push(res.data)
        this.studentDialog = false
      })
    },
    cancelSubject() {
      this.subjectDialog = false
      this.subjectName = ''
      this.subjectTeacher = null
    },

    openAddStudent() {
      this.studentDialog = true
    },
    openAddSubject() {
      this.subjectDialog = true
    },
    saveSubject() {
      let subject = {
        groupId : this.group.id,
        groupName : this.group.groupName,
        name : this.subjectName,
        teacherId : this.subjectTeacher.id,
        teacherFirstName : this.subjectTeacher.firstName,
        teacherLastName : this.subjectTeacher.lastName
      }
      this.$http.post('/subject', subject).then(res => {
        this.subjectItems.push(res.data)
        this.subjectDialog = false
      })
    }
  },
  data: () => ({
    groupId: null,
    studentHeaders: [{
      text: 'Имя',
      value: 'firstName'
    }, {
      text: 'Фамилия',
      value: 'lastName'
    },
      {
        value: 'actions',
        sortable : false
      }
    ],
    studentItems: [],
    subjectHeaders: [{
      text: 'Название',
      value: 'name'
    }, {
      text: 'Имя преподавателя',
      value: 'teacherFirstName'
    }, {
      text: 'Фамилия преподавателя',
      value: 'teacherLastName'
    },
      {
        value: 'actions',
        sortable : false
      }],
    subjectItems: [],
    teachers : [],
    group: null,
    studentDialog : false,
    studentLogin : '',
    studentPassword : '',
    studentFirstName : '',
    studentLastName : '',
    subjectDialog : false,
    subjectName : '',
    subjectTeacher : {}
  })
}
</script>