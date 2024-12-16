<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.realName" placeholder="真实姓名" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.type" placeholder="选择权限类型" clearable class="filter-item" style="width: 150px">
        <el-option label="系统管理员" value="2" />
        <el-option label="社团管理员" value="1" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-plus" @click="handleCreate">
        添加社团管理员
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
      <el-table-column label="编号" prop="adminId" sortable="custom" align="center" width="120px" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="头像" min-width="150px" align="center">
        <template slot-scope="{row}">
          <el-image v-if="row.images" fit="cover" :src="row.images" />
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="50px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.tel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="250px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="权限类型" width="120px" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.type | statusFilter">
            <span>
              {{ row.type==2?'系统管理员':'社团管理员' }}
            </span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
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
      <el-form ref="dataForm" :rules="rules" :inline="true" :model="temp" :hide-required-asterisk="dialogFormReadonly" label-position="right" label-width="80px" class="admin-form">
        <el-form-item style="width: 100%; text-align: center">
          <el-image v-model="temp.images" fit="cover" :src="temp.images" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="temp.realName" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="成员昵称" prop="userName">
          <el-input v-model="temp.userName" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-input v-model="temp.sex" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-input v-if="dialogStatus==='detail'" v-model="temp.createTime" />
          <el-date-picker v-else v-model="temp.joinTime" type="datetime" placeholder="请选择创建时间" class="form-timestamp" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="电话" prop="tel">
          <el-input v-model="temp.tel" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="temp.email" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="QQ" prop="qq">
          <el-input v-model="temp.qq" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="微信" prop="wx">
          <el-input v-model="temp.wx" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="权限类型" prop="type">
          <el-input v-if="dialogStatus==='detail'" :value="temp.type==2?'系统管理员':'社团管理员'" readonly />
          <el-select v-else v-model="temp.type" placeholder="选择权限类型" class="form-select">
            <el-option value="系统管理员" />
            <el-option value="社团管理员" />
          </el-select>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="temp.password" :readonly="dialogFormReadonly" />
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
      <el-form ref="createForm" :rules="createRules" :inline="true" :model="temp" label-position="right" label-width="80px" class="admin-form">
        <el-form-item label="成员Id" prop="userId">
          <el-input v-model.number="temp.userId" />
        </el-form-item>
        <el-form-item label="权限类型" prop="type">
          <el-select v-model="temp.type" placeholder="选择权限类型" class="form-select">
            <el-option label="系统管理员" value="2" />
            <el-option label="社团管理员" value="1" />
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
        1: 'primary',
        2: 'success'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      baseUrl: 'user/',
      tableKey: 0,
      list: null,
      clubList: null,
      userList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        realName: undefined,
        type: 1
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {
        user: {}
      },
      dialogDetailFormVisible: false,
      dialogCreateFormVisible: false,
      dialogFormReadonly: true,
      formDisabled: false,
      dialogStatus: '',
      textMap: {
        detail: '管理员详情',
        update: '修改管理员详情',
        create: '添加社团管理员'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        realName: [{ required: true, message: '真实姓名不能为空', trigger: 'blur' }],
        userName: [{ required: true, message: '成员昵称不能为空', trigger: 'blur' }],
        sex: [{ required: true, message: '性别不能为空', trigger: 'blur' }],
        createTime: [{ required: true, message: '创建时间不能为空', trigger: 'blur' }],
        tel: [{ required: true, message: '电话不能为空', trigger: 'blur' }],
        email: [{ required: true, message: '邮箱不能为空', trigger: 'blur' }],
        qq: [{ required: true, message: 'QQ不能为空', trigger: 'blur' }],
        wx: [{ required: true, message: '微信不能为空', trigger: 'blur' }],
        type: [{ required: true, message: '权限类型不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
      },
      createRules: {
        userId: [
          { required: true, message: '成员Id不能为空', trigger: 'blur' },
          { type: 'number', message: '成员Id必须为数字', trigger: 'blur' }
        ],
        type: [{ required: true, message: '社团不能为空', trigger: 'blur' }]
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
      request.get(this.baseUrl + 'queryUserList', { params: this.listQuery }).then(res => {
        this.list = res.data
        this.total = res.total
        this.listLoading = false
      })
    },
    getUserList() {
      request.get(this.baseUrl + 'queryUserList', { params: this.listQuery }).then(res => {
        this.clubList = res.data
      })
    },
    getClubList() {
      request.get('club/queryClubList').then(res => {
        this.clubList = res.data
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
                  message: '添加管理员成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '添加管理员失败！',
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
      this.textMap['detail'] = (this.temp.type === 2 ? '系统' : '社团') + '管理员详情——' + this.temp.realName
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
      this.temp.type = this.temp.type === 2 ? '系统管理员' : '社团管理员'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.type = this.temp.type === '系统管理员' ? 2 : 1
          this.temp.createTime = new Date(this.temp.createTime).toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'editUser', JSON.parse(JSON.stringify(this.temp, ['userId', 'password', 'realName', 'sex', 'tel', 'email', 'qq', 'wx', 'type', 'createTime']))).then(
            res => {
              this.dialogDetailFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '编辑管理员信息成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '编辑管理员信息失败！',
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

.admin-form {
  max-width: 800px;
  margin: auto;
}

@media (min-width: 1660px) {
  .admin-form {
    display: flex;
    flex-wrap: wrap;
  }
}

@media (max-width: 1660px) {
  .admin-form {
    text-align: center;
  }
}

</style>
