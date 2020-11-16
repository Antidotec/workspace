<template>
  <div>
    <el-card>
      <el-table :data=tableData
                stripe
                style="width: 100%">
        <el-table-column
          label="收货人姓名"
          prop="hostname"
          width="180"
          :resizable="false">
        </el-table-column>
        <el-table-column
          label="收货人地址"
          prop="addr"
          width="180"
          :resizable="false">
        </el-table-column>
        <el-table-column
          label="联系方式"
          prop="phone"
          width="180"
          :resizable="false">
        </el-table-column>
        <el-table-column
          align="right"
          width="180"
          :resizable="false">
          <template slot="header" slot-scope="scope">
            <el-button icon="el-icon-plus" type="info" @click="addAddress()" size="medium">添加地址</el-button>
          </template>
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="editAddress(scope.$index)">Edit
            </el-button>
            <el-button
              size="mini"
              type="danger"
              @click="delAddress(scope.$index)">Delete
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    <!--    表单内容-->
    <el-dialog title="收货地址" :visible.sync="dialogFormVisible">
      <el-form :model="form" :rules="rules">
        <el-form-item label="收件人姓名" :label-width="formLabelWidth" prop="hostname">
          <el-input v-model="form.hostname" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="地址" :label-width="formLabelWidth" prop="address">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="联系方式" :label-width="formLabelWidth" prop="phone">
          <el-input v-model="form.phone" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="modifyAddress()">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

  export default {
    name: "Address",
    data() {
      return {
        tableData: [],
        dialogFormVisible: false,
        formLabelWidth: '120px',
        form: {
          id: '',
          uid: '',
          hostname: '',
          address: '',
          phone: '',
        },
        search: '',
        rules: {
          hostname: {required: true, message: '请输入收货人姓名', trigger: 'blur'},
          address: {required: true, message: '请输入收货地址', trigger: 'blur'},
          phone: {required: true, message: '请输入联系方式', trigger: 'blur'}
        },
        addOrUpdate: 0
      }
    },
    mounted: function () {
      this.loadAddress()
    },
    methods: {
      loadAddress() {
        var that = this
        this.$axios.get('/address?uid=' + this.$store.state.user.id).then(resp => {
          if (resp && resp.status === 200) {
            that.tableData = resp.data;
          }
        })
      },
      editAddress(index) {
        this.form.address = this.tableData[index].addr;
        this.form.hostname = this.tableData[index].hostname;
        this.form.id = this.tableData[index].id;
        this.form.phone = this.tableData[index].phone;
        this.dialogFormVisible = true;
      },
      delAddress(index) {
        var that = this;
        this.$confirm('此操作将永久删地址, 是否继续?', '提示', {
          confirmButtonText: '确定',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
            this.$axios
              .post('/delAddress', {id: that.tableData[index].id}).then(resp => {
              if (resp && resp.status === 200) {
                that.loadAddress() //删除之后重新加载书籍
              }
            })
          }
        ).catch(() => {
          this.$message({
            type: 'info',
            message: '已取消删除'
          })
        })
      },
      addAddress() {
        this.form.hostname = '';
        this.form.address = '';
        this.form.phone = '';
        this.dialogFormVisible = true;
        this.form.id = -1;//不存在意味着重新插入
      },
      modifyAddress() {
        var that = this
        this.$axios.post('/modifyAddress',
          {
            id: that.form.id,
            uid: this.$store.state.user.id,
            hostname: that.form.hostname,
            addr: that.form.address,
            phone: that.form.phone
          }).then(resp => {
          if (resp) {
            this.loadAddress()  //也不知道为什么这里重新加载数据就有用了
            this.dialogFormVisible = false;
            this.$message({
              message: '地址信息修改成功',
              type: 'success'
            })
          }
        })
      }
    }
  }

</script>

<style scoped>

</style>
