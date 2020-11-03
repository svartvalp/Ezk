<template>
  <v-card>
    <v-dialog v-model="dialog" max-width="300px">
      <v-card>
        <v-text-field v-model="mark" label="Оценка"></v-text-field>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="blue darken-1" text @click="cancel">Cancel</v-btn>
          <v-btn color="blue darken-1" text @click="saveMark">OK</v-btn>
          <v-spacer></v-spacer>
        </v-card-actions>
      </v-card>
    </v-dialog>
    <v-card-title>{{ user.firstName + ' ' + user.lastName }}
    <v-spacer></v-spacer>
    <v-text-field v-model="search"
                  append-icon="mdi-magnify"
                  label="Поиск"
                  single-line
                  hide-details></v-text-field>
    </v-card-title>
    <v-data-table :headers="headers"
                  :items="items"
                  :search="search"
                  >
      <template v-slot:item.actions="{ item }">
        <v-icon
            small
            class="mr-2"
            @click="edit(item)"
        >
          mdi-pencil
        </v-icon>
      </template>
    </v-data-table>
  </v-card>
</template>
<script>
export default {
  props : {
    user : Object
  },
  mounted() {
    this.$http.get(`/studentSubject/teacher/${this.user.id}`).then(response => {
      this.items = response.data
    })
  },
  methods : {
    edit(item) {
      this.editItem = item
      this.dialog = true
      this.mark = this.editItem.mark
    },
    cancel() {
      this.dialog = false
      this.mark = ''
      this.editItem = null
    },
    saveMark() {
      this.$http.put(`/studentSubject/${this.editItem.id}/mark?value=${this.mark}`).then(() => {
        this.items = this.items.map(item => {
          if(item.id === this.editItem.id) {
            item.mark = this.mark
          }
          return item
        })
        this.dialog = false
      })
    }
  },
  data : () => ({
    search : '',
    headers : [
      {
        text : 'Группа',
        value : 'groupName',
        align : 'start'
      },
      {
        text : 'Имя',
        value: 'studentFirstName'
      },
      {
        text : 'Фамилия',
        value: 'studentLastName'
      },
      {
        text: 'Предмет',
        value: 'subjectName'
      },
      {
        text: 'Оценка',
        value: 'mark'
      },
      {
        value: 'actions',
        sortable : 'false'
      }
    ],
    items : [],
    editItem : null,
    dialog : false,
    mark : ''
  })
}
</script>