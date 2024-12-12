<template>

  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.user.realName"
        placeholder="真实姓名"
        style="width: 200px; margin-left: 0px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />

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
      <el-table-column label="编号" prop="categoryId" sortable="custom" align="center" width="120px">
        <template slot-scope="{row}">
          <span>{{ row.applyInfoId }}</span>
        </template>
      </el-table-column>

      <el-table-column label="真实姓名" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.user.realName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="性别" align="center" width="50px">
        <template slot-scope="{row}">
          <span>{{ row.user.sex }}</span>
        </template>
      </el-table-column>

      <el-table-column label="电话" align="center" width="150px">
        <template slot-scope="{row}">
          <span>{{ row.user.tel }}</span>
        </template>
      </el-table-column>

      <el-table-column label="邮箱" align="center" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.user.email }}</span>
        </template>
      </el-table-column>

      <el-table-column label="申请社团" align="center" min-width="150px">
        <template slot-scope="{row}">
          <span>{{ row.club.clubName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="申请时间" align="center" min-width="200px">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" class-name="status-col" width="150px">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            <span v-if="row.status == 0">未审核</span>
            <span v-else-if="row.status == 1">审核通过</span>
            <span v-else-if="row.status == 2">已拒绝</span>
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="Actions" align="center" min-width="230" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleDetail(row)">
            详情
          </el-button>
          <el-button v-if="row.status == 0" type="success" size="mini" @click="handleCheck(row)">
            审核
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row, $index)">
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormDetailVisible">
      <el-form
        ref="dataForm"
        :inline="true"
        :rules="rules"
        :model="temp"
        label-position="right"
        label-width="80px"
        class="apply-form"
      >
        <el-form-item label="真实姓名" prop="categoryName">
          <el-input v-model="temp.user.realName" readonly />
        </el-form-item>

        <el-form-item label="申请社团" prop="categoryName">
          <el-input v-model="temp.club.clubName" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="创建时间" prop="categoryName">
          <el-input v-model="temp.createTime" readonly />
        </el-form-item>

        <el-form-item label="性别" prop="categoryName">
          <el-input v-model="temp.user.sex" readonly />
        </el-form-item>

        <el-form-item label="电话" prop="categoryName">
          <el-input v-model="temp.user.tel" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="邮箱" prop="categoryName">
          <el-input v-model="temp.user.email" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="QQ" prop="categoryName">
          <el-input v-model="temp.user.qq" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="微信" prop="categoryName">
          <el-input v-model="temp.user.wx" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="状态" prop="categoryName">
          <el-input v-model="tempStatus" readonly />
        </el-form-item>

        <el-form-item label="申请内容" class="apply-content">
          <el-input v-model="temp.content" type="textarea" readonly placeholder="无" />
        </el-form-item>
      </el-form>

      <div style="margin: 0px 50px;">
        <span
          style="
        display: inline-block;
        color: #606266;
        font-weight: 700;
        height: 36px;
        line-height: 36px;
        padding-left: 12px;"
        >审核记录</span>

        <hr
          style="
        margin: 0px;
        border: none;
        border-top: 1px solid #ddd;
        margin-bottom: 20px;
        "
        >

        <div v-if="tempApplyList.length > 0" class="block">
          <el-timeline>
            <el-timeline-item
              v-for="(applyList, index) in tempApplyList"
              :key="index"
              :timestamp="applyList.createTime "
              type="primary"
            >
              {{ applyList.content }}
            </el-timeline-item>
          </el-timeline>
        </div>
        <span
          v-else
          style="
        display: inline-block;
        color: #606266;
        height: 36px;
        line-height: 36px;
        padding-left: 12px;"
        >暂无审核记录</span>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button v-if="temp.status === 0" type="success" @click="handleCheck(temp)">
          进行审核
        </el-button>
        <el-button @click="dialogFormDetailVisible = false">
          关闭
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormCheckVisible">
      <el-form
        ref="dataForm"
        :inline="true"
        :rules="rules"
        :model="temp"
        label-position="right"
        label-width="80px"
        class="apply-form"
      >
        <el-form-item label="真实姓名">
          <el-input v-model="temp.user.realName" readonly />
        </el-form-item>

        <el-form-item label="申请社团">
          <el-input v-model="temp.club.clubName" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="创建时间">
          <el-input v-model="temp.createTime" readonly />
        </el-form-item>

        <el-form-item label="性别">
          <el-input v-model="temp.user.sex" readonly />
        </el-form-item>

        <el-form-item label="电话">
          <el-input v-model="temp.user.tel" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="邮箱">
          <el-input v-model="temp.user.email" readonly placeholder="无" />
        </el-form-item>

        <el-form-item label="申请内容">
          <el-input v-model="temp.content" readonly placeholder="无" class="apply-text" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="success">
          通过
        </el-button>
        <el-button type="danger" @click="dialogFormCheckVisible = false">
          拒绝
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

import waves from '@/directive/waves' // waves directive
import request from '@/utils/request'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

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
      baseUrl: 'apply/',
      tableKey: 0,
      list: null,
      clubList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        user: {
          realName: undefined
        },
        club: undefined
      },
      tempApplyList: [],
      showReviewer: false,
      tempStatus: '',
      temp: {
        user: {},
        club: {}
      },
      dialogFormDetailVisible: false,
      dialogFormCheckVisible: false,
      dialogStatus: '',
      textMap: {
        detail: '',
        check: '入团申请审核'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {

      },
      downloadLoading: false
    }
  },
  created() {
    this.getClubList()
    this.getList()
  },
  methods: {
    getClubList() {
      request.get('club/queryClubList').then(res => {
        this.clubList = res.data
      })
    },
    getApplyListByApplyInfoId(applyInfoId) {
      request.get(this.baseUrl + 'queryApplyListByApplyInfoId?applyInfoId=' + applyInfoId).then(
        res => {
          this.tempApplyList = res.data
        }
      )
    },
    getList() {
      this.listLoading = true
      request.get(this.baseUrl + 'queryApplyInfoList',
        {
          params: this.listQuery
        }).then(res => {
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
      if (prop === 'categoryId') {
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
    handleDetail(row) {
      this.temp = Object.assign({}, row)
      this.getApplyListByApplyInfoId(row.applyInfoId)
      if (row.status === 0) {
        this.tempStatus = '未审核'
      } else if (row.status === 1) {
        this.tempStatus = '审核通过'
      } else if (row.status === 2) {
        this.tempStatus = '已拒绝'
      }
      this.dialogStatus = 'detail'
      this.textMap['detail'] = '入团申请详情——' + row.user.realName
      this.dialogFormDetailVisible = true
    },
    handleCheck(row) {
      this.temp = Object.assign({}, row)
      this.dialogStatus = 'check'
      this.dialogFormDetailVisible = false
      this.dialogFormCheckVisible = true
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?categoryId=' + row.categoryId).then(
        res => {
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '删除社团类型成功！',
              type: 'success',
              duration: 2000
            })
            this.list.splice(index, 1)
          }
        }
      )
    }
  }
}
</script>

<style scoped>
.filter-container .filter-item {
  margin-left: 10px;
}

@media (min-width: 1660px) {
  .apply-form {
    display: flex;
    flex-wrap: wrap;
    margin: 0px 50px
  }

  .apply-text {
    width: 700px;
  }
}

@media (max-width: 1660px) {
  .apply-form {
    text-align: center;
  }

  .apply-text {
    width: 305px;
  }
}
</style>
