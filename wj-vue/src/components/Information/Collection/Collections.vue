<template>
  <div>
    <el-row style="height: 840px;">
      <el-tooltip effect="dark" placement="right"
                  v-for="item in books.slice((currentPage-1)*pageSize,currentPage*pageSize)"
                  :key="item.id">
        <p slot="content" style="font-size: 14px;margin-bottom: 6px;">{{item.title}}</p>
        <p slot="content" style="font-size: 13px;margin-bottom: 6px">
          <span>{{item.author}}</span> /
          <span>{{item.date}}</span> /
          <span>{{item.press}}</span>
        </p>
        <p slot="content" style="width: 300px" class="abstract">{{item.abs}}</p>
        <el-card style="width: 135px;margin-bottom: 20px;height: 233px;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover" @click="Purchase(item)">
            <img :src="item.cover" alt="封面">
          </div>
          <div class="info">
            <div class="title">
              <a href="">{{item.title}}</a>
            </div>
            <i class="el-icon-delete" @click="deleteBook(item.id)"></i>
          </div>
          <div class="author">{{item.author}}</div>
        </el-card>
      </el-tooltip>
      <pay-form @onSubmit="loadBooks()" ref="pay"></pay-form>
    </el-row>
    <el-row>
      <el-pagination
        @current-change="handleCurrentChange"
        :current-page="currentPage"
        :page-size="pageSize"
        :total="books.length">
      </el-pagination>
    </el-row>
  </div>
</template>

<script>
  import PayForm from './PayForm'

  export default {
    name: 'Books',
    components: {PayForm},
    data() {
      return {
        books: [],
        currentPage: 1,
        pageSize: 17
      }
    },
    mounted: function () {
      this.loadBooks()
    },
    methods: {
      loadBooks() {
        var _this = this
        this.$axios.get('/collections?uid=' + this.$store.state.user.id).then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
          }
        })
      },
      handleCurrentChange: function (currentPage) {
        this.currentPage = currentPage
      },
      deleteBook(id) { //从收藏夹中删除书籍id
        this.$confirm('此操作将取消收藏该书籍, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios.post('/disCollect',
              {
                uid: this.$store.state.user.id,
                bid: id
              }).then(resp => {
              if (resp.status === 200) {
                this.loadBooks()
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消操作'
          })
        })
        // alert(id)
      },
      Purchase(item) {
        this.$refs.pay.dialogFormVisible = true
        this.$refs.pay.form = {
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
          }
        }
        this.$refs.pay.numofbooks = 1

        //获取用户的地址信息 后续可能会放到个人信息界面中
        var that = this;
        this.$axios.get('/address?uid=' + this.$store.state.user.id).then(resp => {
          if (resp && resp.status === 200) {
            //对地址进行格式化
            var addData = [];
            for (var i = 0; i < resp.data.length; i++) {
              addData.push(
                {
                  aid: resp.data[i].id,
                  key: i,
                  label: resp.data[i].hostname + "  " + resp.data[i].addr + "  " + resp.data[i].phone
                }
              )
            }
            that.$refs.pay.address = addData;//获取用户的地址信息
          }
        })
        this.$axios.post('/isCollected',
          {
            uid: this.$store.state.user.id,
            bid: item.id
          }).then(resp => {
          if (resp.status === 200)
            that.$refs.pay.collected = resp.data;
        })
        //获取该用户的收藏信息
      }
    }
  }
</script>
<style scoped>

  .cover {
    width: 115px;
    height: 172px;
    margin-bottom: 7px;
    overflow: hidden;
    cursor: pointer;
  }

  img {
    width: 115px;
    height: 172px;
    /*margin: 0 auto;*/
  }

  .title {
    font-size: 14px;
    text-align: left;
  }

  .author {
    color: #333;
    width: 102px;
    font-size: 13px;
    margin-bottom: 6px;
    text-align: left;
  }

  .abstract {
    display: block;
    line-height: 17px;
  }

  .el-icon-delete {
    cursor: pointer;
    float: right;
  }

  .switch {
    display: flex;
    position: absolute;
    left: 780px;
    top: 25px;
  }

  a {
    text-decoration: none;
  }

  a:link, a:visited, a:focus {
    color: #3377aa;
  }

</style>

