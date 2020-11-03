<template>
  <v-row justify="center">
    <v-dialog v-model="dialog" max-width="300px">
      <v-card>
        <v-text-field v-model="groupName" label="Название группы"></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancel">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="saveGroup">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-list>
      <v-subheader>
        <v-btn @click="addGroup" >Добавить группу</v-btn>
        <v-spacer></v-spacer>
        <v-btn @click="openTeachers" >Преподаватели</v-btn>
      </v-subheader>
      <v-list-item v-for="group in groups" :key="group.id">
        <v-list-item-content>
          <v-btn @click="openGroup(group.id)">
          {{group.groupName}}
          </v-btn>
        </v-list-item-content>
      </v-list-item>
    </v-list>
  </v-row>
</template>
<script>
export default {
  mounted() {
    this.$http.get('/group').then(response => {
      this.groups = response.data
    })
  },
  props : {
    user : Object
  },
  data : () => ({
    groups : [],
    dialog : false,
    groupName : ''
  }),
  methods : {
    addGroup() {
      this.dialog = true
    },
    cancel() {
      this.dialog = false
      this.groupName = ''
    },
    saveGroup() {
      this.$http.post(`/group?groupName=${this.groupName}`).then((res) => {
        this.groups.push(res.data)
        this.dialog = false
        this.groupName = ''
      })
    },
    openGroup(groupId) {
      this.$router.push(`/group/${groupId}`)
    },
    openTeachers() {
      this.$router.push('/teachers')
    }
  }
}
</script>