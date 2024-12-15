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
      <el-table-column label="编号" prop="leaveInfoId" sortable="custom" align="center" width="120px" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.leaveInfoId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户ID" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.userId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="真实姓名" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.realName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户昵称" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.userName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="社团ID" width="120px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.clubId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="留言社团" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.clubName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="留言时间" width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="留言内容" min-width="250px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.content }}</span>
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
      <el-form ref="dataForm" :inline="true" :model="temp" :hide-required-asterisk="dialogFormReadonly" label-position="right" label-width="80px" class="leave-form">
        <el-form-item label="用户ID">
          <el-input v-model="temp.userId" readonly />
        </el-form-item>
        <el-form-item label="用户昵称">
          <el-input v-model="temp.userName" readonly />
        </el-form-item>
        <el-form-item label="用户真名">
          <el-input v-model="temp.realName" readonly />
        </el-form-item>
        <el-form-item label="留言时间">
          <el-input v-model="temp.createTime" readonly />
        </el-form-item>
        <el-form-item label="社团ID">
          <el-input v-model="temp.clubId" readonly />
        </el-form-item>
        <el-form-item label="留言社团">
          <el-input v-model="temp.clubName" readonly />
        </el-form-item>
        <el-form-item label="留言内容">
          <el-input v-model="temp.content" readonly type="textarea" resize="none" :autosize="{ minRows: 2, maxRows: 10}" placeholder="无" class="leave-text" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogDetailFormVisible = false">
          关闭
        </el-button>
        <el-button type="danger" @click="deleteData()">
          删除
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
      baseUrl: 'leave/',
      tableKey: 0,
      list: null,
      clubList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        realName: undefined,
        clubId: undefined
      },
      importanceOptions: [1, 2, 3],
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: ['published', 'draft', 'deleted'],
      showReviewer: false,
      temp: {},
      dialogDetailFormVisible: false,
      dialogFormReadonly: true,
      dialogStatus: '',
      textMap: {
        detail: '在线留言详情'
      },
      dialogPvVisible: false,
      pvData: [],
      downloadLoading: false
    }
  },
  created() {
    this.getClubList()
    this.getList()
  },
  methods: {
    getList() {
      this.listLoading = true
      request.get(this.baseUrl + 'queryLeaveInfoList', { params: this.listQuery }).then(res => {
        this.list = res.data
        this.total = res.total
        this.listLoading = false
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
    handleDetail(row) {
      this.temp = Object.assign({}, JSON.parse(JSON.stringify(row))) // 深拷贝
      this.dialogStatus = 'detail'
      this.textMap['detail'] = '在线留言详情 [ ' + this.temp.leaveInfoId + ' ]'
      this.dialogFormReadonly = true
      this.dialogDetailFormVisible = true
      this.formDisabled = false
      this.placeholder = '无'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?leaveInfoId=' + row.leaveInfoId).then(
        res => {
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '删除在线留言成功！',
              type: 'success',
              duration: 2000
            })
            this.dialogDetailFormVisible = false
            this.list.splice(index, 1)
          }
        }
      )
    },
    deleteData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          request.delete(this.baseUrl + 'deleteById?leaveInfoId=' + this.temp.leaveInfoId).then(
            res => {
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '删除在线留言成功！',
                  type: 'success',
                  duration: 2000
                })
                this.dialogDetailFormVisible = false
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '删除在线留言失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            }
          )
        }
      })
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

.leave-form {
  max-width: 800px;
  margin: auto;
}

@media (min-width: 1660px) {
  .leave-form {
    display: flex;
    flex-wrap: wrap;
  }

  .leave-text {
    width: 700px;
  }
}

@media (max-width: 1660px) {
  .leave-form {
    text-align: center;
  }

  .leave-text {
    width: 305px;
  }
}

</style>
