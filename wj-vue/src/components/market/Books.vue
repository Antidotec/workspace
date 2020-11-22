<template>
  <div>
    <el-row style="height: 1000px;">
      <search-bar @onSearch="searchResult" ref="searchBar" @setSale2="allBooks" @setSale1="saleBooks"
                  @setSale0="noSaleBooks"></search-bar>
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
        <el-card style="width: 140px;margin-bottom: 20px;height: 280px;float: left;margin-right: 15px" class="book"
                 bodyStyle="padding:10px" shadow="hover">
          <div class="cover" @click="Purchase(item)">
            <img :src="item.cover" alt="封面">
          </div>
          <div class="info">
            <div class="title">
              <a href="">{{item.title}}</a>
            </div>
          </div>
          <div class="author">{{item.author}}</div>
          <div class="price">
            <el-icon class="el-icon-price-tag"></el-icon>
            <i>￥{{item.price}}</i>
            <el-icon class="el-icon-shopping-cart-2" style="color: #333333"></el-icon>
          </div>
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
  import SearchBar from './SearchBar'

  export default {
    name: 'Books',
    components: {PayForm, SearchBar},
    data() {
      return {
        books: [],
        currentPage: 1,
        pageSize: 18,
        index: 2, //控制显示什么书籍的全局变量
        cid: 0
      }
    },
    mounted: function () {
      this.loadBooks() //初始加载全部书籍
    },
    methods: {
      loadBooks() {
        var _this = this
        var url = 'categories/' + this.cid + '/books?index=' + this.index;
        this.$axios.get(url).then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
            //currentPage归为1页
            _this.currentPage = 1
          }
        })
      },
      handleCurrentChange: function (currentPage) {
        this.currentPage = currentPage
      },
      searchResult() {
        var _this = this
        this.$axios
          .get('/search?keywords=' + this.$refs.searchBar.keywords + '&index=' + this.index + '&cid=' + this.cid).then(resp => {
          if (resp && resp.status === 200) {
            _this.books = resp.data
          }
        })
      },
      Purchase(item) {
        this.$refs.pay.dialogFormVisible = true
        this.$refs.pay.onSale = item.sale;
        this.$refs.pay.form = {
          id: item.id,
          cover: item.cover,
          title: item.title,
          author: item.author,
          date: item.date,
          press: item.press,
          abs: item.abs,
          price: this.$store.state.user.vip ? (item.price * (10-this.$store.state.user.vip) / 10).toFixed(2) : item.price, //这里直接处理单价信息
          category: {
            id: item.category.id.toString(),
            name: item.category.name
          }
        }
        this.$refs.pay.numofbooks = 1
        this.$refs.pay.form.deposit = (this.$refs.pay.form.price * 0.2).toFixed(2)
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
      },
      allBooks() {
        this.index = 2;
        this.loadBooks();
      },
      saleBooks() {
        this.index = 1;
        this.loadBooks();
      },
      noSaleBooks() {
        this.index = 0;
        this.loadBooks()
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

  .price {
    color: #ff4d26;
    width: 150px;
    font-size: 18px;
    /*margin-bottom:6px;*/
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

