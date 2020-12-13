<template>
  <div class="table-classic-wrapper">
    <el-card shadow="always">
      <!-- 操作栏 -->
      <div class="control-btns">
        <el-button type="primary" @click="handleCreate">添加脚本</el-button>
      </div>
      <!-- 查询栏 -->
      <el-form
        ref="searchForm"
        :inline="true"
        :model="listQuery"
        label-width="90px"
        class="search-form"
      >
        <el-form-item label="编号">
          <el-input v-model="listQuery.id" placeholder="编号" />
        </el-form-item>
        <el-form-item label="名称">
          <el-input v-model="listQuery.content" placeholder="名称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="onSubmit">查询</el-button>
        </el-form-item>
      </el-form>
      <!-- 表格栏 -->
      <el-table
        ref="multipleTable"
        v-loading="listLoading"
        :data="tableData"
        tooltip-effect="dark"
        style="width: 100%"
        size="medium"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="60" />
        <el-table-column prop="id" label="编号" align="center" width="120" sortable />
        <el-table-column prop="name" label="名称" align="center">
          <template slot-scope="scope">
            <el-popover trigger="hover" placement="top">
              <p>名称: {{ scope.row.name }}</p>
              <p>内容: {{ scope.row.content }}</p>
              <div slot="reference">
                <el-tag size="medium">{{ scope.row.name }}</el-tag>
              </div>
            </el-popover>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="内容" align="center" />
        <el-table-column label="操作" align="center" width="200">
          <template slot-scope="scope">
            <el-button size="mini" :disabled="scope.row.forbid" @click="handleEdit(scope.$index, scope.row)">详情</el-button>
            <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <!-- 分页栏 -->
      <Pagination :total="total" :page.sync="listQuery.currentPage" :limit.sync="listQuery.pageSize" @pagination="fetchData" />
      <!-- 新增/编辑 弹出栏 -->
      <el-dialog
        title="编辑"
        :visible.sync="formVisible"
        width="30%"
        class="dialog-form"
        :before-close="handleClose"
      >
        <el-form
          ref="dialogForm"
          :model="dialogForm"
          :rules="formRules"
          label-width="100px"
        >
          <el-form-item label="名称" prop="name">
            <el-input v-model="dialogForm.name" />
          </el-form-item>
          <el-form-item label="命令" prop="content">
            <el-input v-model="dialogForm.content" />
          </el-form-item>
           <el-form-item label="扩展属性">
          <el-checkbox-group  v-model="expandGroup">
             <el-checkbox-button v-for="vo in dialogForm.expandVOList" :label="vo.id" :key="vo.content">{{vo.content}}</el-checkbox-button>
          </el-checkbox-group>
           </el-form-item>
          <el-form-item
            v-for="(domain, index) in dynamicValidateForm.domains"
            :key="domain.key"
            :label="'扩展属性' + (index+1)"
            :prop="domain.expandName"
            :rules="{
              required: true, message: '名称不能为空', trigger: 'blur'
            }"
          > 
            <el-input v-model="domain.value"></el-input>
            <el-button @click.prevent="removeDomain(domain)">删除</el-button>
          </el-form-item>
          <div class="add-expand">
            <el-button @click="addExpand">添加扩展属性</el-button>
          </div>
          <div class="footer-item">
            <!-- <el-button @click="cancleForm">取 消</el-button>
            <el-button type="primary" :disabled="isSubmit" @click="submitForm('dialogForm')">确 定</el-button> -->
            <el-button type="primary" :disabled="isSubmit" @click="excute()">执 行</el-button>
          </div>
        </el-form>
      </el-dialog>
    </el-card>
  </div>
</template>

<script>
import { getTableList,excuteShell } from '../../api'
import Pagination from '../../components/Pagination'

export default {
  name: 'Table',
  components: { Pagination },
  data() {
    return {
      // 数据列表加载动画
      listLoading: true,
      // 查询列表参数对象
      listQuery: {
        id: undefined,
        content: undefined,
        married: undefined,
        currentPage: 1,
        pageSize: 10
      },
      // 新增/编辑提交表单对象
      dialogForm: {
        name: undefined,
        content: undefined,
        married: undefined,
        expandVOList:[]
      },
      dynamicValidateForm: {
        domains: [{
          value: '',
          expandName:'',
        }],
        email: ''
      },
      // 数据总条数
      total: 0,
      // 表格数据数组
      tableData: [],
      // 多选数据暂存数组
      multipleSelection: [],
      // 新增/编辑 弹出框显示/隐藏
      formVisible: false,
      // 表单校验 trigger: ['blur', 'change'] 为多个事件触发
      formRules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        content: [
          { required: true, message: '请输入内容', trigger: 'blur' }
        ]
      },
      // expand复选框
      expandGroup:[],
      // 防止多次连续提交表单
      isSubmit: false,
      // 导入数据 弹出框显示/隐藏
      importVisible: false,
      // 导出文件格式
      filesFormat: '.txt, .csv, .xls, .xlsx',
      // 导出数据 弹出框显示/隐藏
      exportVisible: false
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    // 多选操作
    handleSelectionChange(val) {
      this.multipleSelection = val
    },
    // 新建数据
    handleCreate() {
      this.formVisible = true
      this.dialogForm.id = undefined
      this.dialogForm.name = undefined
      this.dialogForm.content = undefined
      this.dialogForm.married = undefined
      this.dialogForm.expandVOList = []
    },
    // 编辑数据
    handleEdit(index, row) {
      console.log(index, row)
      this.formVisible = true
      this.dialogForm.id = row.id
      this.dialogForm.name = row.name
      this.dialogForm.content = row.content
      this.dialogForm.married = row.married
      this.dialogForm.expandVOList = row.resScriptExpandVOList
    },
    // 删除数据
    handleDelete(index, row) {
      console.log(index, row)
      this.$confirm('此操作将删除选中数据, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        // 此处可添加--删除接口
        // 删除成功调用fetchData方法更新列表
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    // 批量删除
    batchDelete() {
      if (this.multipleSelection.length < 1) {
        this.$message({
          message: '请先选择要删除的数据！',
          type: 'warning'
        })
      } else {
        this.handleDelete()
      }
    },
    // 新增/编辑弹出框 关闭时操作
    handleClose() {
      this.formVisible = false
      this.$refs.dialogForm.resetFields()
    },
    // 获取数据列表
    fetchData() {
      this.listLoading = true
      // 获取数据列表接口
      getTableList(this.listQuery).then(res => {
        const data = res.data
        if (data.status === 200) {
          this.total = data.result.total
          this.tableData = data.result.records
          this.listLoading = false
        }
      }).catch(() => {
        this.listLoading = false
      })
    },
    // 查询数据
    onSubmit() {
      this.listQuery.currentPage = 1
      this.fetchData()
    },
    // 导入数据
    handleImport() {
      this.importVisible = true
    },
    // 新增/编辑表单确认提交
    submitForm(formName) {
      this.$refs[formName].validate(valid => {
        if (valid) {
          // 此处添加 新增/编辑数据的接口 新增成功后调用fetchData方法更新列表
          // 先 this.isSubmit = true 接口返回成功后 再 this.isSubmit = false
          this.formVisible = false
        } else {
          this.isSubmit = false
          return false
        }
      })
    },
    // 新增/编辑表单取消提交
    cancleForm() {
      this.$refs.dialogForm.resetFields()
      this.formVisible = false
    },
    addExpand() {
      this.dynamicValidateForm.domains.push({
        value: '',
        key: Date.now()
      });
      console.log(this.expandGroup[0])
    },
    excute(){
     let reqExecuteVO = {
          id : this.dialogForm.id,
          expandIdList : this.expandGroup
     }
     excuteShell(reqExecuteVO).then(res=>{
           const data = res.data
        if (data.status === 200) {
          this.listLoading = false
        }
      }).catch(() => {
        this.listLoading = false
      })
    },
    // submitForm(formName) {
    //   this.$refs[formName].validate((valid) => {
    //     if (valid) {
    //       alert('submit!');
    //     } else {
    //       console.log('error submit!!');
    //       return false;
    //     }
    //   });
    // },
    // resetForm(formName) {
    //   this.$refs[formName].resetFields();
    // },
    removeDomain(item) {
      var index = this.dynamicValidateForm.domains.indexOf(item)
      if (index !== -1) {
        this.dynamicValidateForm.domains.splice(index, 1)
      }
    },
    // 导入数据弹出栏 确认操作
    confirmImport() {
      // 此处添加 后台接收的接口，将导入的数据传给后台处理
      this.importVisible = false
    },
    // 导入数据弹出栏 取消操作
    cancleImport() {
      // 将导入的数据清空
      this.importVisible = false
    },
    // 列表中婚姻状况栏，状态值改变时，调用
    selectChange(row) {
      // 此处添加后台接口，成功后调用fetchData方法更新列表
    },
    // 列表中禁止编辑栏，状态值改变时，调用
    stateChange(row) {
      // 此处添加后台接口，成功后调用fetchData方法更新列表
    }
  }
}
</script>

<style lang="less">
.table-classic-wrapper {
  .el-card {
    min-height: 656px;
  }
  .control-btns {
    margin-bottom: 20px;
  }
  .search-form {
    padding-top: 18px;
    margin-bottom: 15px;
    background-color: #f7f8fb;
  }
  .el-table thead {
    font-weight: 600;
    th {
      background-color: #f2f3f7;
    }
  }
  .dialog-form {
    .el-input {
      width: 380px;
    }
    .footer-item {
      margin-top: 50px;
      text-align: right;
    }
    .add-expand {
      margin-left: 100px;
      text-align: left;
    }
  }
  .upload-box,
  .export-data {
    width: 300px;
    margin: 0 auto 30px;
  }
  .upload-box {
    width: 156px;
    .files-upload {
      color: #20a0ff;
    }
  }
  .hints {
    font-size: 12px;
    color: #aaa;
    text-align: center;
  }
}
</style>
