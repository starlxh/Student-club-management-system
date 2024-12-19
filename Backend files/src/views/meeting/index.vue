<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="记录名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.clubId" placeholder="选择社团" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        添加会议
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
      <el-table-column label="编号" prop="miId" sortable="custom" align="center" width="120px">
        <template slot-scope="{row}">
          <span>{{ row.miId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标题" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.title }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="社团名称" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.clubName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.startTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.endTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
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
      <el-form ref="dataForm" :inline="true" :rules="rules" :model="temp" :hide-required-asterisk="dialogFormReadonly" label-position="right" label-width="100px" class="meeting-form">
        <el-form-item v-if="!dialogFormReadonly" label="标题" prop="title">
          <el-input v-model="temp.title" class="meeting-text" />
        </el-form-item>
        <el-form-item v-if="dialogFormReadonly" label="创建者昵称" prop="userName">
          <el-input v-model="temp.userName" readonly />
        </el-form-item>
        <el-form-item v-if="dialogFormReadonly" label="创建者真名">
          <el-input v-model="temp.realName" readonly />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.startTime" readonly />
          <el-date-picker v-else-if="dialogStatus==='update'" v-model="temp.startTime" type="datetime" placeholder="请选择开始时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.endTime" readonly />
          <el-date-picker v-else-if="dialogStatus==='update'" v-model="temp.endTime" type="datetime" placeholder="请选择结束时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item label="社团名称" prop="clubId">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.clubName" :readonly="dialogFormReadonly" />
          <el-select v-else-if="dialogStatus==='update'" v-model="temp.clubId" placeholder="选择社团" class="form-select">
            <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDetailFormVisible = false">
          关闭
        </el-button>
        <el-button :type="dialogStatus==='detail'?'primary':'success'" @click="dialogStatus==='detail'?handleUpdate():handleUpdate()">
          {{ dialogStatus==='detail'?'修改':'提交' }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogCreateFormVisible">
      <el-form ref="createForm" :rules="createRules" :inline="true" :model="temp" label-position="right" label-width="80px" class="meeting-form">
        <el-form-item label="标题" prop="title">
          <el-input v-model="temp.title" class="meeting-text" />
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker v-model="temp.startTime" type="datetime" placeholder="请选择开始时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker v-model="temp.endTime" type="datetime" placeholder="请选择结束时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item label="社团名称" prop="clubId">
          <el-select v-model="temp.clubId" placeholder="选择社团" class="form-select">
            <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCreateFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="createData()">
          添加
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
      baseUrl: 'meeting/',
      tableKey: 0,
      list: null,
      clubList: null,
      hostList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        title: undefined,
        realName: undefined,
        clubName: undefined,
        order: 'ASC'
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {},
      dialogDetailFormVisible: false,
      dialogCreateFormVisible: false,
      formDisabled: false,
      dialogStatus: '',
      textMap: {
        detail: '',
        update: '修改会议',
        create: '添加会议'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        startTime: [{ required: true, message: '开始时间不能为空', trigger: 'blur' }],
        endTime: [{ required: true, message: '结束时间不能为空', trigger: 'blur' }],
        clubId: [{ required: true, message: '社团不能为空', trigger: 'blur' }]
      },
      createRules: {
        title: [{ required: true, message: '标题不能为空', trigger: 'blur' }],
        startTime: [{ required: true, message: '开始时间不能为空', trigger: 'blur' }],
        endTime: [{ required: true, message: '结束时间不能为空', trigger: 'blur' }],
        clubId: [{ required: true, message: '社团不能为空', trigger: 'blur' }]
      },
      downloadLoading: false,
      placeholder: '无'
    }
  },
  created() {
    this.getClubList()
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request.get(this.baseUrl + 'queryMeetingInfoList', { params: this.listQuery }).then(res => {
        this.list = res.data
        this.total = res.total
        this.listLoading = false
      })
    },
    getClubList() {
      request.get('club/queryAllClubList').then(res => {
        this.clubList = res.data
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'miId') {
        this.sortByID(order)
      }
    },
    sortByID(order) {
      if (order === 'ascending') {
        this.listQuery.order = 'ASC'
      } else {
        this.listQuery.order = 'DESC'
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
      this.placeholder = ''
      this.$nextTick(() => {
        this.$refs['createForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['createForm'].validate((valid) => {
        if (valid) {
          this.temp.startTime = new Date(this.temp.startTime).toLocaleString().replaceAll('/', '-')
          this.temp.endTime = new Date(this.temp.endTime).toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'addMeettingInfo', JSON.parse(JSON.stringify(this.temp, ['title', 'startTime', 'endTime', 'clubId']))).then(
            res => {
              this.dialogCreatelFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '添加会议记录成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '添加会议记录失败！',
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
      this.dialogDetailFormVisible = true
      this.dialogFormReadonly = true
      this.formDisabled = false
      this.placeholder = '无'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleUpdate() {
      this.dialogStatus = 'update'
      this.dialogFormReadonly = false
      this.formDisabled = true
      this.placeholder = ''
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.startTime = new Date(this.temp.startTime).toLocaleString().replaceAll('/', '-')
          this.temp.endTime = new Date(this.temp.endTime).toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'editMeettingInfo', JSON.parse(JSON.stringify(this.temp, ['miId', 'title', 'startTime', 'endTime', 'userId']))).then(
            res => {
              this.dialogDetailFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '编辑会议记录成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '编辑会议记录失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            })
        }
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?miId=' + row.miId).then(
        res => {
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '删除会议记录成功！',
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

.form-select {
  max-width: 305px;
}

.form-timestamp {
  width: inherit;
  max-width: 305px;
}

.meeting-form {
  max-width: 840px;
  margin: auto;
}

@media (min-width: 1660px) {
  .meeting-form {
    display: flex;
    flex-wrap: wrap;
  }

  .meeting-text {
    width: 700px;
  }
}

@media (max-width: 1660px) {
  .meeting-form {
    text-align: center;
  }

  .meeting-text {
    width: 305px;
  }
}

</style>
