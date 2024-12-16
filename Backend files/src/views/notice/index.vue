<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.realName" placeholder="发布者名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.title" placeholder="公告标题" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        发布公告
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
      @sort-change="sortChange"
    >
      <el-table-column label="编号" prop="noticeId" sortable="custom" align="center" width="120px" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.noticeId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="公告标题" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="公告内容" min-width="250px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.content }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发布时间" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="Actions" align="center" width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            详情
          </el-button>
          <el-button v-if="row.status!='deleted'" size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="getList" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogDetailFormVisible">
      <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" :hide-required-asterisk="dialogFormReadonly" label-position="right" label-width="100px" class="notice-form">
        <el-form-item v-if="dialogStatus==='detail'" label="公告ID">
          <el-input v-model="temp.noticeId" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='detail'" label="创建者昵称">
          <el-input v-model="temp.userName" readonly />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='detail'" label="创建者姓名">
          <el-input v-model="temp.realName" readonly />
        </el-form-item>
        <el-form-item label="发布时间" prop="createTime">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.createTime" :readonly="dialogFormReadonly" />
          <el-date-picker v-else v-model="temp.createTime" type="datetime" placeholder="请选择发布时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item v-if="dialogStatus==='update'" label="公告标题" prop="title">
          <el-input v-model="temp.title" :autosize="{ maxRows: 2 }" type="textarea" resize="none" class="notice-text" />
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input v-model="temp.content" :autosize="{ minRows: 4, maxRows: 16 }" type="textarea" resize="none" :readonly="dialogFormReadonly" class="notice-text" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDetailFormVisible = false">
          关闭
        </el-button>
        <el-button :type="dialogStatus==='detail'?'primary':'success'" @click="dialogStatus==='detail'?handleUpdate():updateData()">
          {{ dialogStatus==='detail'?'修改':'提交' }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogCreateFormVisible">
      <el-form ref="createForm" :rules="createRules" :inline="true" :model="temp" label-position="right" label-width="80px" class="notice-form">
        <el-form-item label="公告标题" prop="title">
          <el-input v-model="temp.title" :autosize="{ maxRows: 2 }" type="textarea" resize="none" class="notice-text" />
        </el-form-item>
        <el-form-item label="公告内容" prop="content">
          <el-input v-model="temp.content" :autosize="{ minRows: 4, maxRows: 16 }" type="textarea" resize="none" class="notice-text" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCreateFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="createData()">
          发布
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :visible.sync="dialogPvVisible" title="Reading statistics">
      <el-table :data="pvData" border fit highlight-current-row style="width: 100%">
        <el-table-column prop="key" label="Channel" />
        <el-table-column prop="pv" label="Pv" />
      </el-table>
      <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="dialogPvVisible = false">Confirm</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { fetchPv } from '@/api/article'
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import request from '@/utils/request'

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'primary',
        1: 'success',
        2: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      baseUrl: 'notice/',
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        realName: undefined,
        title: undefined
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {},
      dialogDetailFormVisible: false,
      dialogCreateFormVisible: false,
      dialogFormReadonly: true,
      dialogStatus: '',
      textMap: {
        detail: '',
        update: '修改公告',
        create: '发布公告'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        createTime: [{ required: true, message: '发布时间不能为空', trigger: 'blur' }],
        title: [{ required: true, message: '公告标题不能为空', trigger: 'blur' }],
        content: [{ required: true, message: '公告内容不能为空', trigger: 'blur' }]
      },
      createRules: {
        title: [{ required: true, message: '公告标题不能为空', trigger: 'blur' }],
        content: [{ required: true, message: '公告内容不能为空', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request.get(this.baseUrl + 'queryNoticeList', { params: this.listQuery }).then(res => {
        this.list = res.data
        this.total = res.total
        this.listLoading = false
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'id') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.sort = '+id'
      } else {
        this.listQuery.sort = '-id'
      }
      this.handleFilter()
    },
    resetTemp() {
      this.temp = {
        user: {}
      }
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogCreateFormVisible = true
      this.$nextTick(() => {
        this.$refs['createForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['createForm'].validate((valid) => {
        if (valid) {
          this.temp.createTime = new Date().toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'addNotice', JSON.parse(JSON.stringify(this.temp, ['title', 'content', 'createTime']))).then(
            res => {
              this.dialogCreatelFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '发布公告成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '发布公告失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            })
        }
      })
    },
    handleDetail(row) {
      this.temp = Object.assign({}, JSON.parse(JSON.stringify(row))) // 深拷贝
      this.dialogStatus = 'detail'
      this.textMap['detail'] = this.temp.title
      this.dialogFormReadonly = true
      this.dialogDetailFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate() {
      this.dialogStatus = 'update'
      this.dialogFormReadonly = false
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.createTime = new Date(this.temp.createTime).toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'editNotice', JSON.parse(JSON.stringify(this.temp, ['noticeId', 'title', 'content', 'createTime']))).then(
            res => {
              this.dialogDetailFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '编辑公告成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '编辑公告失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            })
        }
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?noticeId=' + row.noticeId).then(
        res => {
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '删除社团活动成功！',
              type: 'success',
              duration: 2000
            })
            this.list.splice(index, 1)
          }
        }
      )
    },
    handleFetchPv(pv) {
      fetchPv(pv).then(response => {
        this.pvData = response.data.pvData
        this.dialogPvVisible = true
      })
    },
    handleAvatarSuccess(res, file) {
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      const isRtype = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isRtype) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isRtype && isLt2M
    },
    getSortClass: function(key) {
      const sort = this.listQuery.sort
      return sort === `+${key}` ? 'ascending' : 'descending'
    }
  }
}
</script>

<style scoped>
.filter-container .filter-item {
  margin-left: 10px;
}

.form-timestamp {
  width: inherit;
  max-width: 305px;
}

.notice-form {
  max-width: 840px;
  margin: auto;
}

@media (min-width: 1660px) {
  .notice-form {
    display: flex;
    flex-wrap: wrap;
  }

  .notice-text {
    width: 720px;
  }
}

@media (max-width: 1660px) {
  .notice-form {
    text-align: center;
  }

  .notice-text {
    width: 305px;
  }
}
</style>
