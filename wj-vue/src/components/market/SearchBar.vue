<template>
  <div style="margin-bottom: 10px;display: flex;justify-content: left;align-items: center">
    <el-button type="info" size="small" @click="setSale2">全部书籍</el-button>
    <el-button type="danger" size="small" @click="setSale0">预售书籍</el-button>
    <el-button type="success" size="small" @click="setSale1">在售书籍</el-button>
    <el-input
      @keyup.enter.native="searchClick"
      placeholder="通过书名或作者搜索..."
      prefix-icon="el-icon-search"
      size="small"
      style="width: 400px;margin-right: 10px;margin-left: 40px"
      v-model="keywords">
    </el-input>
    <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
  </div>
</template>

<script>
  export default {
    name: 'SearchBar',
    data() {
      return {
        keywords: '',
        books: [],
        cardLoading: []
      }
    },
    methods: {
      searchClick() {
        this.$emit('onSearch')
      },
      setSale0() {
        this.$emit('setSale0')
      },
      setSale1() {
        this.$emit('setSale1')
      },
      setSale2() {
        this.$emit('setSale2')
      }
    }
  }
</script>

<style scoped>

</style>

