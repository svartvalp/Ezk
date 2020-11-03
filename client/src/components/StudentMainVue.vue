<template>
  <v-row  justify="center">
    <v-list>
    <v-subheader>
      <div class="black--text mb-2" >
      {{user.firstName + " " + user.lastName}}
      </div>
      <v-spacer></v-spacer>
      <div v-if="studentInfo.groupName" class="black--text mb-2" > Группа {{this.studentInfo.groupName}}</div>
    </v-subheader>
    <v-list-item v-for="subject in studentSubjects" :key="subject.id">
      <v-card min-width="500px">
        <v-card-title>
          {{subject.subjectName}}
          <v-spacer>
          </v-spacer>
          {{subject.teacherFirstName + " " + subject.teacherLastName}}
        </v-card-title>
        <v-card-text class="headline font-weight-bold">
          Оценка :
          {{subject.mark ?  subject.mark : 'Не выставлено'}}
        </v-card-text>
      </v-card>
    </v-list-item>
    </v-list>
  </v-row>
</template>
<script>
export default {
  mounted() {
    this.$http.get(`/user/student/info/${this.user.id}`).then(response => {
      this.studentInfo = response.data
      console.log(this.studentInfo)
    }).then(() => {
      this.$http.get(`/studentSubject/student/${this.user.id}`).then(response => {
        this.studentSubjects = response.data
      })
    })
  },
  props : {
    user : Object,
  },
  data : () => ({
    studentInfo : null,
    studentSubjects : []
  })
}
</script>