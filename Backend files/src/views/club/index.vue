<template>
  <div class="app-container">
    <div class="filter-container">
      <el-input
        v-model="listQuery.clubName"
        placeholder="社团名称"
        style="width: 200px;  margin-left: 0px;"
        class="filter-item"
        @keyup.enter.native="handleFilter"
      />
      <el-select v-model="listQuery.categoryId" placeholder="社团类型" clearable class="filter-item" style="width: 130px">
        <el-option
          v-for="item in categoryList"
          :key="item.categoryId"
          :label="item.categoryName"
          :value="item.categoryId"
        />
      </el-select>
      <el-button v-waves class="filter-item" type="primary" icon="el-icon-search" @click="handleFilter">
        查询
      </el-button>
      <el-button class="filter-item" style="margin-left: 10px;" type="primary" icon="el-icon-edit" @click="handleCreate">
        新建社团
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
      <el-table-column label="编号" prop="clubId" sortable="custom" align="center" width="80">
        <template slot-scope="{row}">
          <span>{{ row.clubId }}</span>
        </template>
      </el-table-column>

      <el-table-column label="社团名称" align="center">
        <template slot-scope="{row}">
          <span class="link-type" @click="handleUpdate(row)">{{ row.title }}</span>
          {{ row.clubName }}
        </template>
      </el-table-column>
      <el-table-column label="社团类型" width="150px" align="center">
        <template slot-scope="{row}">
          <el-tag :type="row.categoryName | statusFilter">
            <span>{{ row.categoryName }}</span>
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column label="社团团长" width="150px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.captainName }}</span>
        </template>
      </el-table-column>

      <el-table-column label="建团时间" align="center" width="250px">
        <template slot-scope="{row}">
          <span>{{ row.createTime | parseTime('{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>

      <el-table-column label="状态" class-name="status-col" width="150px">
        <template slot-scope="{row}">
          <el-tag :type="row.status | statusFilter">
            <span v-if="row.status == 1">正常</span>
            <span v-if="row.status == 0">禁用</span>
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" min-width="200px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            编辑
          </el-button>
          <el-button v-if="row.status != 'deleted'" size="mini" type="danger" @click="handleDelete(row, $index)">
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

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form
        ref="dataForm"
        :rules="rules"
        :model="temp"
        label-position="left"
        label-width="80px"
        style="width: 400px; margin-left:50px;"
      >
        <el-form-item label="社团名称" prop="clubName">
          <el-input v-model="temp.clubName" />
        </el-form-item>
        <el-form-item label="社团类型" prop="categoryId">
          <el-select v-model="temp.categoryId" class="filter-item" placeholder="请选择">
            <el-option
              v-for="item in categoryList"
              :key="item.categoryId"
              :label="item.categoryName"
              :value="item.categoryId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="社团团长" prop="captainId">
          <el-select v-model="temp.captainId" class="filter-item" placeholder="请选择">
            <el-option
              v-for="item in adminList"
              :key="item.userId"
              :label="item.userName"
              :value="item.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="建团时间" prop="createTime">
          <el-date-picker
            v-model="temp.createTime"
            type="datetime"
            placeholder="请填写"
          />
        </el-form-item>

        <el-form-item label="社团状态">
          <el-select v-model="temp.status" class="filter-item" placeholder="请选择">
            <el-option v-for="item in statusOptions" :key="item.id" :label="item.value" :value="item.id" />
          </el-select>
        </el-form-item>
        <el-form-item label="社团图片">
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
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus === 'create' ? createData() : updateData()">
          确定
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
import { parseTime } from '@/utils'
import request from '@/utils/request'
import Pagination from '@/components/Pagination' // secondary package based on el-pagination

export default {
  name: 'ComplexTable',
  components: { Pagination },
  directives: { waves },
  filters: {
    statusFilter(status) {
      const statusMap = {
        0: 'danger',
        1: 'success'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      baseUrl: 'club/',
      imageUrl: null,
      tableKey: 0,
      list: null,
      adminList: null,
      categoryList: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 10,
        clubName: undefined,
        categoryId: undefined
      },
      sortOptions: [{ label: 'ID Ascending', key: '+id' }, { label: 'ID Descending', key: '-id' }],
      statusOptions: [{ id: 0, value: '禁用' },
        { id: 1, value: '正常' }],
      showReviewer: false,
      temp: {
        clubName: '',
        categoryId: undefined,
        captainId: undefined,
        createTime: new Date(),
        status: undefined,
        images: ''
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '编辑社团',
        create: '创建社团'
      },
      dialogPvVisible: false,
      pvData: [],
      rules: {
        clubName: [{ required: true, message: '需要填写社团名称', trigger: 'blur' }],
        categoryId: [{ required: true, message: '需要选择社团类型', trigger: 'change' }],
        captainId: [{ required: true, message: '需要选择团长', trigger: 'blur' }]
      },
      downloadLoading: false
    }
  },
  created() {
    this.getCaptainList()
    this.getCategoryList()
    this.getList()
  },
  methods: {
    getCaptainList() {
      request.get('user/getAdminIdName').then(
        res => {
          if (res.code === 20000) {
            this.adminList = res.data
          }
        }
      )
    },
    getCategoryList() {
      request.get('category/queryCategoryList').then(
        res => {
          if (res.code === 20000) {
            this.categoryList = res.data
          }
        }
      )
    },
    getList() {
      this.listLoading = true
      request.get(this.baseUrl + 'queryClubList', {
        params: this.listQuery
      }).then(
        res => {
          if (res.code === 20000) {
            this.list = res.data
            this.total = res.total
            this.listLoading = false
          }
        }
      )
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
        clubName: '',
        categoryId: undefined,
        captainId: undefined,
        createTime: new Date(),
        status: undefined,
        images: ''
      }
      this.imageUrl = null
    },
    handleCreate() {
      this.resetTemp()
      this.dialogStatus = 'create'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    createData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false
          this.temp.createTime = parseTime(this.temp.createTime, '{y}-{m}-{d} {h}:{i}:{s}')
          if (this.imageUrl) {
            this.temp.images = this.imageUrl
          }
          request.post(this.baseUrl + 'addClub', this.temp).then(
            res => {
              if (res === 20000) {
                this.$notify({
                  title: '成功',
                  message: '添加社团成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              }
            }
          )
        }
      })
    },
    handleUpdate(row) {
      this.temp = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.dialogFormVisible = false
          this.temp.createTime = parseTime(this.temp.createTime, '{y}-{m}-{d} {h}:{i}:{s}')
          if (this.imageUrl) {
            this.temp.images = this.imageUrl
          }
          request.post(this.baseUrl + 'editClub', this.temp).then(
            res => {
              if (res.code === 20000) {
                this.$notify({
                  title: '成功',
                  message: '编辑社团成功！',
                  type: 'success',
                  duration: 2000
                })
                this.getList()
              }
            }
          )
        }
      })
    },
    handleDelete(row, index) {
      request.delete(this.baseUrl + 'deleteById?clubId=' + row.clubId).then(
        res => {
          if (res.code === 20000) {
            this.$notify({
              title: '成功',
              message: '删除社团成功！',
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
    }
  }
}
</script>

<style scoped>
.el-form>>>.el-form-item__label {
  text-align: right;
}

.filter-container .filter-item {
  margin-left: 10px;
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
