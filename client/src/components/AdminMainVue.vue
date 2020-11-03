<template>
  <router-view :user="user">

  </router-view>
</template>
<script>
export default {
  props : {
    user : Object
  },
  data : () => ({

  })
}
</script>