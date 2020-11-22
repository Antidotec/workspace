<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>图书管理</el-breadcrumb-item>
        <el-breadcrumb-item>图书列表</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <div align="left" style="margin-top: 20px">
      <el-input
        @keyup.enter.native="searchClick"
        placeholder="通过作者或者图书名搜索..."
        prefix-icon="el-icon-search"
        size="small"
        style="width: 400px;margin-right: 10px"
        v-model="keywords">
      </el-input>
      <el-button size="small" type="primary" icon="el-icon-search" @click="searchClick">搜索</el-button>
    </div>
    <el-card style="margin-top: 20px">
      <el-table
        :data="books"
        stripe
        :default-sort="{prop: 'id', order: 'ascending'}"
        style="width: 100%">
        <el-table-column
          type="selection"
          width="55">
        </el-table-column>
        <el-table-column
          prop="id"
          label="id"
          sortable
          width="80">
        </el-table-column>
        <el-table-column
          label="封面"
          width="100">
          <template slot-scope="scope">
            <img :src="scope.row.cover" style="width: 50px;height: 50px"/>
          </template>
        </el-table-column>
        <el-table-column
          prop="title"
          label="标题"
          width="100">
        </el-table-column>
        <el-table-column
          prop="author"
          label="作者"
          width="120">
        </el-table-column>
        <el-table-column
          prop="date"
          label="出版日期"
          width="100">
        </el-table-column>
        <el-table-column
          prop="press"
          label="出版社"
          width="120">
        </el-table-column>
        <el-table-column
          prop="category.name"
          label="分类"
          width="100">
        </el-table-column>
        <el-table-column
          prop="price"
          label="价格"
          width="75">
        </el-table-column>
        <el-table-column
          label="上架"
          width="75">
          <template slot-scope="scope">
            <el-switch
              v-model="!scope.row.unshelve"
              active-color="#13ce66"
              @change="(value)=>putAwayOrUnshelve(value,scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          label="在售"
          width="75">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.sale"
              active-color="#13ce66"
              :disabled=scope.row.unshelve
              @change="(value)=>onSaleOrUnSale(value,scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column
          align="right"
          width="150"
          :resizable="false">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="editBook(scope.row)">编辑
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="delBook(scope.row)">删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <edit-form @onSubmit="loadBooks()" ref="edit"></edit-form>
  </div>
</template>

<script>
  import EditForm from "./EditForm";

  export default {
    name: "BooksList",
    components: {EditForm},
    data() {
      return {
        books: [],
        keywords: '',
      }
    },
    mounted() {
      this.loadBooks();
    },
    methods: {
      loadBooks() {
        var that = this
        this.$axios.get("/getAllBooks").then(resp => {
          if (resp && resp.status === 200) {
            this.books = resp.data;
          }
        })
      },
      editBook(item) {
        this.$refs.edit.dialogFormVisible = true
        this.$refs.edit.form = {
          id: item.id,
          cover: item.cover,
          title: item.title,
          author: item.author,
          date: item.date,
          press: item.press,
          abs: item.abs,
          price: item.price,
          category: {
            id: item.category.id.toString(),
            name: item.category.name
          },
          sale: item.sale
        }
      },
      delBook(item) {
        this.$confirm('此操作将永久删除该书籍, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios
              .post('/delBook', {id: item.id}).then(resp => {
              if (resp && resp.status === 200) {
                this.loadBooks() //删除之后重新加载书籍
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
        // alert(id)
      },
      putAwayOrUnshelve(value, item) {
        if (value) {
          this.$axios.post("/admin/book/shelve?id=" + item.id).then(resp => {
            if (resp && resp.status === 200) {
              this.$confirm('请问是在售还是预售该书籍', '提示', {
                confirmButtonText: '在售',
                cancelButtonText: '预售',
                type: 'warning'
              }).then(() => {
                this.$axios.post('/admin/book/onSale?id=' + item.id).then(resp => {
                  if (resp && resp.status === 200) {
                    this.$message({
                      type: 'success',
                      message: '在售成功!'
                    });
                    this.loadBooks();
                  }
                })
              }).catch(() => {
                this.$axios.post('/admin/book/preSell?id=' + item.id).then(resp => {
                  if (resp && resp.status === 200) {
                    this.$message({
                      type: 'info',
                      message: '预售成功!'
                    });
                    this.loadBooks();
                  }
                })
              })
            }
          })
        } else {
          this.$axios.post('/admin/book/unshelve?id=' + item.id).then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'success',
                message: '下架成功!'
              });
              this.loadBooks();
            }
          })
        }
      },
      onSaleOrUnSale(value,item){
        //在售
        if(value){
          this.$axios.post('/admin/book/onSale?id=' + item.id).then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'success',
                message: '在售成功!'
              });
              this.loadBooks()
            }
          })
        }else{
          this.$axios.post('/admin/book/preSell?id=' + item.id).then(resp => {
            if (resp && resp.status === 200) {
              this.$message({
                type: 'info',
                message: '预售成功!'
              });
              this.loadBooks()
            }
          })
        }
      },
      searchClick() {
        this.$axios
          .get('/admin/book/search?keywords=' + this.keywords).then(resp => {
          if (resp && resp.status === 200) {
            this.books = resp.data
          }
        })
      }
    }
  }


</script>

<style scoped>

</style>
