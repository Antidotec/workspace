<template>
  <div>
    <Collections class="books-area" ref="booksArea"></Collections>
  </div>
</template>

<script>
  import Collections from "./Collections";

  export default {
    name: 'AppLibrary',
    components: {Collections},
    methods: {
      listByCategory() {

      }
    }
  }
</script>

<style scoped>
  .books-area {
    width: 990px;
    margin-left: auto;
    margin-right: auto;
  }
</style>

