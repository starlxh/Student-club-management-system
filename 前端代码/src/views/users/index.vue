<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.realName"
        placeholder="真实姓名"
        style="width: 200px"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select
        v-model="listQuery.type"
        placeholder="权限类型"
        clearable
        class="filter-item"
        style="width: 150px"
      >
        <el-option
          v-for="item in typeOptions"
          :key="item.id"
          :label="item.value"
          :value="item.id"
        />
      </el-select>
      <el-button
        v-waves
        class="filter-item"
        type="primary"
        icon="el-icon-search"
        @click="handleFilter"
      >
        查询
      </el-button>
    </div>

    <el-table
      :key="tableKey"
      v-loading="listLoading"
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
      @sort-change="sortChange"
    >
      <el-table-column
        label="编号"
        prop="adminId"
        sortable="custom"
        align="center"
        width="120px"
      >
        <template slot-scope="{ row }">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" width="150px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="50px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="电话" min-width="150px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.tel }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" min-width="250px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="权限类型" width="120px" align="center">
        <template slot-scope="{ row }">
          <el-tag :type="row.type | statusFilter">
            <span v-if="row.type == 1">社团管理员</span>
            <span v-else-if="row.type == 2">系统管理员</span>
            <span v-else-if="row.type == 0">用户</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" min-width="200px" align="center">
        <template slot-scope="{ row }">
          <span>{{ row.createTime | parseTime("{y}-{m}-{d} {h}:{i}") }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="Actions"
        align="center"
        width="230"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="{ row, $index }">
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            详情
          </el-button>
          <el-button
            v-if="row.status != 'deleted'"
            size="mini"
            type="danger"
            @click="handleDelete(row, $index)"
          >
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="listQuery.page"
      :limit.sync="listQuery.limit"
      @pagination="getList"
    />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogDetailFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :inline="true"
        :model="temp"
        :hide-required-asterisk="dialogFormReadonly"
        label-position="right"
        label-width="80px"
        class="admin-form"
      >
        <el-form-item style="width: 100%; text-align: center">
          <el-image
            v-model="temp.image"
            fit="cover"
            :src="temp.image | imgSrc"
            style="height: 150px"
          />
        </el-form-item>
        <el-form-item label="成员昵称" prop="userName">
          <el-input v-model="temp.userName" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="真实姓名" prop="realName">
          <el-input v-model="temp.realName" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-input v-model="temp.sex" :readonly="dialogFormReadonly" />
        </el-form-item>
        <el-form-item label="创建时间" prop="createTime">
          <el-input v-if="dialogStatus === 'detail'" v-model="temp.createTime" readonly />
          <el-date-picker
            v-else-if="dialogStatus === 'update'"
            v-model="temp.createTime"
            type="datetime"
            placeholder="请选择创建时间"
            class="form-timestamp"
          />
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
        <el-form-item label="密码" prop="password">
          <el-input
            v-model.number="temp.password"
            :readonly="dialogFormReadonly"
            class="admin-text"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDetailFormVisible = false"> 关闭 </el-button>
        <el-button
          :type="dialogStatus === 'detail' ? 'primary' : 'success'"
          @click="dialogStatus === 'detail' ? handleUpdate() : updateData()"
        >
          {{ dialogStatus === "detail" ? "修改" : "提交" }}
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import waves from '@/directive/waves' // waves directive
import Pagination from '@/components/Pagination' // secondary package based on el-pagination
import request from '@/utils/request'
import { getImgUrlHeader } from '@/utils/imagespath'

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'info',
        1: 'primary',
        2: 'success'
      }
      return statusMap[status]
    },
    imgSrc(url) {
      return getImgUrlHeader() + url
    }
  },
  data() {
    return {
      baseUrl: 'user/',
      tableKey: 0,
      list: null,
      clubList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        type: 0,
        realName: undefined,
        order: 'ASC'
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [
        { label: 'ID Ascending', key: '+id' },
        { label: 'ID Descending', key: '-id' }
      ],
      statusOptions: [
        { id: 0, value: '禁用' },
        { id: 1, value: '正常' }
      ],
      typeOptions: [
        { id: 0, value: '普通用户' },
        { id: 1, value: '社团管理员' },
        { id: 2, value: '系统管理员' }
      ],
      showReviewer: false,
      temp: {
        user: {}
      },
      dialogDetailFormVisible: false,
      dialogFormReadonly: true,
      formDisabled: false,
      dialogStatus: '',
      textMap: {
        detail: '详情',
        update: '修改详情'
      },
      rules: {
        status: [{ required: true, message: '状态不能为空', trigger: 'blur' }]
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
      request
        .get(this.baseUrl + 'queryUserList', { params: this.listQuery })
        .then((res) => {
          this.list = res.data
          this.total = res.total
          this.listLoading = false
        })
    },
    getClubList() {
      request.get('club/queryAllClubList').then((res) => {
        this.clubList = res.data
      })
    },
    handleFilter() {
      this.listQuery.page = 1
      this.getList()
    },
    sortChange(data) {
      const { prop, order } = data
      if (prop === 'adminId') {
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
    handleDetail(row) {
      this.temp = Object.assign({}, JSON.parse(JSON.stringify(row))) // 深拷贝
      this.dialogStatus = 'detail'
      this.textMap['detail'] =
        (this.temp.type === 0
          ? '用户'
          : (this.temp.type === 2 ? '系统' : '社团') + '管理员') +
        '详情——' +
        this.temp.realName
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
          this.temp.createTime = new Date(this.temp.createTime)
            .toLocaleString()
            .replaceAll('/', '-')
          request
            .post(
              this.baseUrl + 'editUser',
              JSON.parse(
                JSON.stringify(this.temp, [
                  'userId',
                  'userName',
                  'password',
                  'realName',
                  'sex',
                  'tel',
                  'email',
                  'qq',
                  'wx',
                  'createTime'
                ])
              )
            )
            .then((res) => {
              this.dialogDetailFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '编辑信息成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '编辑信息失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            })
        }
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?userId=' + row.userId).then((res) => {
        if (res.code === 20000) {
          this.$notify({
            title: '成功',
            message: '删除成员成功！',
            type: 'success',
            duration: 2000
          })
          this.list.splice(index, 1)
        }
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

.el-form >>> .el-input {
  width: 305px;
}

@media (max-width: 870px) {
  .el-form >>> .el-input {
    width: auto;
  }
}

@media (min-width: 1660px) {
  .admin-form {
    display: flex;
    flex-wrap: wrap;
  }

  .admin-text {
    width: 700px;
  }
}

@media (max-width: 1660px) {
  .admin-form {
    text-align: center;
  }

  .admin-text {
    width: 305px;
  }
}
</style>
