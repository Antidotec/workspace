<template>
  <div>
    <el-row style="margin: 18px 0px 0px 18px ">
      <el-breadcrumb separator-class="el-icon-arrow-right">
        <el-breadcrumb-item :to="{ path: '/admin/dashboard' }">管理中心</el-breadcrumb-item>
        <el-breadcrumb-item>图书管理</el-breadcrumb-item>
        <el-breadcrumb-item>添加图书</el-breadcrumb-item>
      </el-breadcrumb>
    </el-row>
    <el-card style="margin-top: 20px">
      <el-form :model="form" style="text-align: left" ref="dataForm" :rules="rules">
        <el-form-item label="书名" :label-width="formLabelWidth" prop="title">
          <el-input v-model="form.title" autocomplete="off" placeholder="不加《》"></el-input>
        </el-form-item>
        <el-form-item label="作者" :label-width="formLabelWidth" prop="author">
          <el-input v-model="form.author" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出版日期" :label-width="formLabelWidth" prop="date">
          <el-input v-model="form.date" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="出版社" :label-width="formLabelWidth" prop="press">
          <el-input v-model="form.press" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="价格" :label-width="formLabelWidth" prop="price">
          <el-input v-model="form.price" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="封面" :label-width="formLabelWidth" prop="cover">
          <el-input v-model="form.cover" autocomplete="off" placeholder="图片 URL"></el-input>
          <ImgUpload @onUpload="uploadImg" ref="imgUpload"></ImgUpload>
        </el-form-item>
        <el-form-item label="简介" :label-width="formLabelWidth" prop="abs">
          <el-input type="textarea" v-model="form.abs" autocomplete="off" autosize></el-input>
        </el-form-item>
        <el-form-item label="分类" :label-width="formLabelWidth" prop="cid">
          <el-select v-model="form.category.id" placeholder="请选择分类">
            <el-option label="文学" value="1"></el-option>
            <el-option label="流行" value="2"></el-option>
            <el-option label="文化" value="3"></el-option>
            <el-option label="生活" value="4"></el-option>
            <el-option label="经管" value="5"></el-option>
            <el-option label="科技" value="6"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item prop="id" style="height: 0">
          <el-input type="hidden" v-model="form.id" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item :label-width="formLabelWidth">
          <el-button type="primary" @click="onSubmit">在售图书</el-button>
          <el-button type="danger" @click="onSubmit2">预售图书</el-button>
          <el-button type="info" @click="onSubmit3">添加图书</el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
  import ImgUpload from "./ImgUpload";

  export default {
    name: "BookAppend",
    components: {ImgUpload},
    data() {
      return {
        form: {
          id: '',
          title: '',
          author: '',
          date: '',
          press: '',
          cover: '',
          abs: '',
          price: '',
          category: {
            id: '',
            name: ''
          }
        },
        formLabelWidth: '120px',
        rules: {
          title: [
            {required: true, message: '请输入书名', trigger: 'blur'},
          ],
          author: [
            {required: true, message: '请输入作者', trigger: 'blur'}
          ],
          date: [
            {required: true, message: '请输入出版日期', trigger: 'blur'}
          ],
          press: [
            {required: true, message: '请输入出版社', trigger: 'blur'}
          ],
          price: [
            {required: true, message: '请输入图书价格', trigger: 'blur'}
          ],
          cover: [
            {required: true, message: '请选择图书封面', trigger: 'blur'}
          ],
          abs: [
            {required: true, message: '请输入图书简介', trigger: 'blur'}
          ],
        }
      }
    },
    methods: {
      clear() {
        this.form = {
          id: '',
          title: '',
          author: '',
          date: '',
          press: '',
          cover: '',
          abs: '',
          price: '',
          category: {
            id: '',
            name: ''
          }
        }
        this.$refs.imgUpload.clear();
      },
      uploadImg() {
        this.form.cover = this.$refs.imgUpload.url
      },
      onSubmit() {
        var that = this;
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            that.$axios
              .post('/books', {
                cover: this.form.cover,
                title: this.form.title,
                author: this.form.author,
                date: this.form.date,
                press: this.form.press,
                abs: this.form.abs,
                price: this.form.price,
                category: this.form.category,
                sale: true,
                unshelve:false
              }).then(resp => {
              if (resp && resp.status === 200) {
                that.clear();
                that.$message({
                  type: "success",
                  message: "图书上架成功!"
                })
              }
            })
          }
        })
      },
      onSubmit2() {
        var that = this;
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            that.$axios
              .post('/books', {
                cover: this.form.cover,
                title: this.form.title,
                author: this.form.author,
                date: this.form.date,
                press: this.form.press,
                abs: this.form.abs,
                price: this.form.price,
                category: this.form.category,
                sale: false,
                unshelve:false
              }).then(resp => {
              if (resp && resp.status === 200) {
                that.clear();
                that.$message({
                  type: "success",
                  message: "图书预售成功!"
                })
              }
            })
          }
        })
      },
      onSubmit3() {
        var that = this;
        this.$refs['dataForm'].validate((valid) => {
          if (valid) {
            that.$axios
              .post('/books', {
                cover: this.form.cover,
                title: this.form.title,
                author: this.form.author,
                date: this.form.date,
                press: this.form.press,
                abs: this.form.abs,
                price: this.form.price,
                category: this.form.category,
                sale: false,
                unshelve:true
              }).then(resp => {
              if (resp && resp.status === 200) {
                that.clear();
                that.$message({
                  type: "success",
                  message: "图书添加成功!"
                })
              }
            })
          }
        })
      },
    }
  }
</script>

<style scoped>

</style>
