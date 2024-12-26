<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.realName" placeholder="真实姓名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.clubId" placeholder="选择社团" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        添加社团成员
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
      <el-table-column label="编号" prop="clubMemberId" sortable="custom" align="center" width="120px">
        <template slot-scope="{row}">
          <span>{{ row.clubMemberId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.user.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="50px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.user.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.user.tel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="250px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.user.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="社团Id" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.clubId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="社团名称" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.clubName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="入团时间" min-width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.joinTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
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
      <el-form ref="dataForm" :rules="rules" :inline="true" :model="temp" :hide-required-asterisk="dialogFormReadonly" label-position="right" label-width="80px" class="membership-form">
        <el-form-item label="真实姓名">
          <el-input v-model="temp.user.realName" readonly :disabled="formDisabled" />
        </el-form-item>
        <el-form-item label="成员昵称">
          <el-input v-model="temp.user.userName" readonly :disabled="formDisabled" />
        </el-form-item>
        <el-form-item label="性别">
          <el-input v-model="temp.user.sex" readonly :disabled="formDisabled" />
        </el-form-item>
        <el-form-item label="所属社团" prop="clubId">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.clubName" readonly />
          <el-select v-if="dialogStatus==='update'" v-model="temp.clubId" placeholder="选择社团" class="form-select">
            <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
          </el-select>
        </el-form-item>
        <el-form-item label="入团时间" prop="joinTime">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.joinTime" readonly />
          <el-date-picker v-else v-model="temp.joinTime" type="datetime" placeholder="请选择入团时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item label="电话">
          <el-input v-model="temp.user.tel" readonly :placeholder="placeholder" :disabled="formDisabled" />
        </el-form-item>
        <el-form-item label="邮箱">
          <el-input v-model="temp.user.email" readonly :placeholder="placeholder" :disabled="formDisabled" />
        </el-form-item>
        <el-form-item label="QQ">
          <el-input v-model="temp.user.qq" readonly :placeholder="placeholder" :disabled="formDisabled" />
        </el-form-item>
        <el-form-item label="微信">
          <el-input v-model="temp.user.wx" readonly :placeholder="placeholder" :disabled="formDisabled" />
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
      <el-form ref="createForm" :rules="createRules" :inline="true" :model="temp" label-position="right" label-width="80px" class="membership-form">
        <el-form-item label="成员Id" prop="userId">
          <el-input v-model.number="temp.userId" />
        </el-form-item>
        <el-form-item label="社团" prop="clubId">
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
  data() {
    return {
      baseUrl: 'clubMember/',
      tableKey: 0,
      list: null,
      clubList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        realName: undefined,
        clubId: undefined,
        order: 'ASC'
      },
      dialogFormReadonly: true,
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        user: {}
      },
      dialogDetailFormVisible: false,
      dialogCreateFormVisible: false,
      formDisabled: false,
      dialogStatus: '',
      textMap: {
        detail: '社团成员详情',
        update: '修改社团成员详情',
        create: '添加社团成员'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        clubId: [{ required: true, message: '社团不能为空', trigger: 'blur' }],
        joinTime: [{ required: true, message: '入团时间不能为空', trigger: 'blur' }]
      },
      createRules: {
        userId: [
          { required: true, message: '成员Id不能为空', trigger: 'blur' },
          { type: 'number', message: '成员Id必须为数字', trigger: 'blur' }
        ],
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
      request.get(this.baseUrl + 'queryClubMemberList', { params: this.listQuery }).then(res => {
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
      if (prop === 'clubMemberId') {
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
          this.temp.joinTime = new Date().toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'addClubMember', JSON.parse(JSON.stringify(this.temp, ['userId', 'joinTime', 'clubId']))).then(
            res => {
              this.dialogCreatelFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '添加社团成员成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '添加社团成员失败！',
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
      this.textMap['detail'] = '社团成员详情——' + this.temp.user.realName
      this.dialogFormReadonly = true
      this.dialogDetailFormVisible = true
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
          this.temp.joinTime = new Date(this.temp.joinTime).toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'editClubMember', JSON.parse(JSON.stringify(this.temp, ['clubMemberId', 'joinTime', 'clubId']))).then(
            res => {
              this.dialogDetailFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '编辑社团类型成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '编辑社团类型失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            })
        }
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?clubMemberId=' + row.clubMemberId).then(
        res => {
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '删除社团成员成功！',
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

.membership-form {
  max-width: 800px;
  margin: auto;
}

.el-form>>>.el-input {
    width: 305px;
}

@media (max-width: 870px) {
  .el-form>>>.el-input {
    width: auto;
  }
}

@media (min-width: 1660px) {
  .membership-form {
    display: flex;
    flex-wrap: wrap;
  }
}

@media (max-width: 1660px) {
  .membership-form {
    text-align: center;
  }
}

</style>
