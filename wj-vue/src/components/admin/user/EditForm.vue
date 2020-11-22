<template>
  <div>
    <el-dialog
      title="修改用户信息"
      :visible.sync="dialogFormVisible"
      @close="clear">
      <el-form v-model="form" style="text-align: left" ref="dataForm">
        <el-form-item label="用户名" :label-width="formLabelWidth" prop="title">
          <el-input v-model="form.username" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
          <el-input v-model="form.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="昵称" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="电话" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" :label-width="formLabelWidth" prop="name">
          <el-input v-model="form.email" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="会员等级" :label-width="formLabelWidth" prop="vip">
          <el-input-number v-model="form.vip" :min="0" :max="5" label="选择会员等级"></el-input-number>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="onSubmit">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
  export default {
    name: "EditForm",
    data() {
      return {
        form: {
          id: '',
          username: '',
          password: '',
          name: '',
          phone: '',
          email: '',
          enabled: '',
          vip: '',
        },
        dialogFormVisible: false,
        formLabelWidth: '120px'
      }
    },
    methods: {
      clear() {
        this.form = {
          id: '',
          username: '',
          password: '',
          name: '',
          phone: '',
          email: '',
          enabled: '',
          vip: ''
        }
      },
      onSubmit() {
        const that = this;
        this.$axios.post('/modifyUserInfo',
          {
            id: that.form.id,
            username: that.form.username,
            password: that.form.password,
            name: that.form.name,
            phone: that.form.phone,
            email: that.form.email,
            enabled: that.form.enabled,
            vip: that.form.vip
          }).then(resp => {
          if (resp && resp.status === 200) {
            that.$message({
              type: "success",
              message: "用户信息修改成功!"
            })
            that.$emit('submit')
          }
        })
        this.dialogFormVisible = false;
      }
    }
  }
</script>

<style scoped>

</style>
