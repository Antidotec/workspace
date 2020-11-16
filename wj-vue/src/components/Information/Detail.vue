<template>
  <el-card>
    <el-form v-model="form" :label-width="formLabelWidth">
      <el-form-item v-model=form.username label="账号">
        <span >{{form.username}}</span>
      </el-form-item>
      <el-form-item label="昵称">
        <el-input v-model="form.name" :readonly="!editable"></el-input>
      </el-form-item>
      <el-form-item label="邮箱">
        <el-input v-model="form.email" :readonly="!editable"></el-input>
      </el-form-item>
      <el-form-item label="电话">
        <el-input v-model="form.phone" :readonly="!editable"></el-input>
      </el-form-item>
      <el-form-item align="right">
        <el-button type="primary" @click="editable=true" v-if="!editable">编辑个人信息</el-button>
        <el-button @click="editInfo" v-if="editable" type="danger">保存</el-button>
        <el-button @click="cancel" v-if="editable" type="info">取消</el-button>
      </el-form-item>
    </el-form>
  </el-card>
</template>

<script>
  export default {
    name: "Detail",
    data() {
      return {
        form: {
          username: '',
          password: '',
          name: '',
          email: '',
          phone: ''
        },
        formLabelWidth: '40px',
        editable: false
      }
    },
    mounted() {
      this.loadInfo()
    },
    methods: {
      loadInfo() {
        let that = this;
        this.$axios.get('/getUser?id=' + this.$store.state.user.id).then(resp => {
          if (resp && resp.status === 200) {
            const user = resp.data;
            that.form = {
              username: user.username,
              password: user.password,
              name: user.name,
              email: user.email,
              phone: user.phone
            }
          }
        })
      },
      editInfo() {
        let that = this;
        this.$axios.post('/modifyUserInfo', {
          id: this.$store.state.user.id,
          username: this.form.username,
          name: this.form.name,
          email: this.form.email,
          password: this.form.password,
          phone: this.form.phone
        }).then(resp => {
          if (resp && resp.status === 200) {
            that.$message({
              type: "success",
              message: "修改信息成功!"
            })
            that.editable = false
          }
        })
      },
      cancel() {
        this.editable = false;
        this.loadInfo();
      }
    }
  }
</script>

<style scoped>

</style>
