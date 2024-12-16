<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input v-model="listQuery.name" placeholder="活动名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-input v-model="listQuery.creatorName" placeholder="活动创建者名称" style="width: 200px;" class="filter-item" @keyup.enter.native="handleFilter" />
      <el-select v-model="listQuery.clubId" placeholder="选择社团" clearable class="filter-item" style="width: 130px">
        <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" type="primary" icon="el-icon-document-add" @click="handleCreate">
        申请社团活动
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
      <el-table-column label="编号" prop="activityId" sortable="custom" align="center" width="120px" :class-name="getSortClass('id')">
        <template slot-scope="{row}">
          <span>{{ row.activityId }}</span>
        </template>
      </el-table-column>
      <el-table-column label="描述图" width="150px" align="center">
        <template slot-scope="{row}">
          <el-image v-if="row.images" fit="cover" :src="row.images" />
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="活动名称" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.name }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动社团" min-width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.clubName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动地址" width="250px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.acAddress }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建者" min-width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.creatorName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="主持人" width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.hostName }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动时间" min-width="200px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.acTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="活动详情" min-width="250px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.acInfo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" class-name="status-col" width="100px" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            <span v-if="row.status == 0">未审核</span>
            <span v-else-if="row.status == 1">审核通过</span>
            <span v-else-if="row.status == 2">已拒绝</span>
          </el-tag>
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
      <el-form ref="dataForm" :inline="true" :model="temp" label-position="right" label-width="80px" :disabled="formDisabled" class="activity-form">
        <el-form-item style="width: 100%; text-align: center">
          <el-image v-model="temp.images" fit="cover" :src="temp.images" />
        </el-form-item>
        <el-form-item label="活动名称">
          <el-input v-model="temp.name" readonly />
        </el-form-item>
        <el-form-item label="活动社团">
          <el-input v-model="temp.clubName" readonly />
        </el-form-item>
        <el-form-item label="活动地址">
          <el-input v-model="temp.acAddress" readonly />
        </el-form-item>
        <el-form-item label="活动时间">
          <el-input v-model="temp.acTime" readonly />
        </el-form-item>
        <el-form-item label="创建者">
          <el-input v-model="temp.creatorName" readonly />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="temp.createTime" readonly />
        </el-form-item>
        <el-form-item label="主持人">
          <el-input v-model="temp.hostName" readonly />
        </el-form-item>
        <el-form-item label="审核状态">
          <el-input v-model="temp.status" readonly />
        </el-form-item>
        <el-form-item label="活动详情">
          <el-input v-model="temp.acInfo" :autosize="{ maxRows: 6 }" type="textarea" resize="none" readonly placeholder="无" class="activity-text" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button :type="dialogStatus==='detail'?'':'danger'" @click="dialogStatus==='detail'?dialogDetailFormVisible = false:handleJudge(2)">
          {{ dialogStatus==='detail'?'关闭':'拒绝' }}
        </el-button>
        <el-button :type="dialogStatus==='detail'?'primary':'success'" @click="dialogStatus==='detail'?handleCheck():handleJudge(1)">
          {{ dialogStatus==='detail'?'审核':'通过' }}
        </el-button>
      </div>
    </el-dialog>

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogCreateFormVisible">
      <el-form ref="createForm" :rules="rules" :inline="true" :model="temp" label-position="right" label-width="80px" class="activity-form">
        <el-form-item label="活动名称" prop="name">
          <el-input v-model.number="temp.name" />
        </el-form-item>
        <el-form-item label="活动社团" prop="clubId">
          <el-select v-model="temp.clubId" placeholder="选择社团" class="form-select" :change="handleClubChange()">
            <el-option v-for="item in clubList" :key="item.clubId" :label="item.clubName" :value="item.clubId" />
          </el-select>
        </el-form-item>
        <el-form-item label="活动地点" prop="acAddress">
          <el-input v-model.number="temp.acAddress" />
        </el-form-item>
        <el-form-item label="活动时间" prop="acTime">
          <el-date-picker v-model="temp.acTime" type="datetime" placeholder="请选择活动时间" class="form-timestamp" />
        </el-form-item>
        <el-form-item label="联系电话" prop="tel">
          <el-input v-model.number="temp.tel" />
        </el-form-item>
        <el-form-item label="活动主持" prop="hostId">
          <el-input v-model.number="temp.hostId" />
        </el-form-item>
        <el-form-item label="活动详情" prop="acInfo">
          <el-input v-model="temp.acInfo" type="textarea" resize="none" :autosize="{ minRows: 2, maxRows: 10}" placeholder="请输入活动详情" class="activity-text" />
        </el-form-item>
        <el-form-item label="描述图片" prop="images">
          <el-upload
            class="avatar-uploader"
            :action="baseUrl + 'uploadImg'"
            :show-file-list="false"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogCreateFormVisible = false">
          关闭
        </el-button>
        <el-button type="primary" @click="createData()">
          申请
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
      baseUrl: 'activity/',
      tableKey: 0,
      list: null,
      clubList: null,
      hostList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        name: undefined,
        creatorName: undefined,
        clubId: undefined
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
        detail: '社团活动详情',
        update: '社团活动审核',
        check: '申请社团活动'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        name: [{ required: true, message: '活动名称不能为空', trigger: 'blur' }],
        clubId: [{ required: true, message: '社团不能为空', trigger: 'blur' }],
        acAddress: [{ required: true, message: '活动地点不能为空', trigger: 'blur' }],
        acTime: [{ required: true, message: '活动时间不能为空', trigger: 'blur' }],
        tel: [{ required: true, message: '联系电话不能为空', trigger: 'blur' }],
        hostId: [{ required: true, message: '活动主持不能为空', trigger: 'blur' }],
        acInfo: [{ required: true, message: '活动详情不能为空', trigger: 'blur' }]
      },
      downloadLoading: false,
      imageUrl: null,
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
      request.get(this.baseUrl + 'queryActivityList', { params: this.listQuery }).then(res => {
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
    getHostList() {
      request.get('user/queryUserList').then(res => {
        this.hostList = res.data
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
    handleClubChange() {
      console.log(this.temp.clubId)
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
          this.temp.status = 0
          this.temp.createTime = new Date().toLocaleString().replaceAll('/', '-')
          this.temp.acTime = new Date(this.temp.acTime).toLocaleString().replaceAll('/', '-')
          request.post(this.baseUrl + 'addActivity', JSON.parse(JSON.stringify(this.temp, ['name', 'clubId', 'tel', 'images', 'acInfo', 'acTime', 'acAddress', 'createTime', 'hostId', 'status']))).then(
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
      this.textMap['detail'] = '社团活动详情——' + this.temp.name
      this.dialogDetailFormVisible = true
      this.formDisabled = false
      this.placeholder = '无'
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleCheck() {
      this.dialogStatus = 'check'
      this.formDisabled = true
      this.placeholder = ''
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    handleJudge(status) {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.temp.status = status
          request.post(this.baseUrl + 'editStatusById', JSON.parse(JSON.stringify(this.temp, ['activityId', 'status']))).then(
            res => {
              this.dialogDetailFormVisible = false
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '社团活动审核成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              } else {
                this.$notify({
                  title: '失败',
                  message: '社团活动审核失败！',
                  type: 'fail',
                  duration: 2000
                })
              }
            })
        }
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?activityId=' + row.activityId).then(
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

.form-select {
  max-width: 305px;
}

.form-timestamp {
  width: inherit;
  max-width: 305px;
}

.activity-form {
  max-width: 800px;
  margin: auto;
}

@media (min-width: 1660px) {
  .activity-form {
    display: flex;
    flex-wrap: wrap;
  }

  .activity-text {
    width: 700px;
  }
}

@media (max-width: 1660px) {
  .activity-form {
    text-align: center;
  }

  .activity-text {
    width: 305px;
  }
}

.avatar-uploader >>> .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

</style>
