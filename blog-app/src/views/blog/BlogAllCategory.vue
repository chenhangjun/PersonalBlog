<template>
  <div class="me-allct-body" v-title data-title="博客分类" >
    <el-container class="me-allct-container">
      <el-main>
        <ul class="me-allct-items">
          <li v-for="c in categories" @click="view(c.id)" :key="c.id" class="me-allct-item">
            <div class="me-allct-content">
              <a class="me-allct-info">
                <img class="me-allct-img" :src="c.avatar?c.avatar:defaultAvatar"/>
                <h4 class="me-allct-name">{{c.categoryName}}</h4>
                <p class="me-allct-description">{{c.description}}</p>
              </a>

              <div class="me-allct-meta">
                <span>{{c.articles}} 文章</span>
              </div>
            </div>
          </li>
        </ul>
      </el-main>
    </el-container>
  </div>
</template>

<script>
  import defaultAvatar from '@/assets/img/logo.png'
  import {getAllCategoriesDetail} from '@/api/category'

  export default {
    name: 'BlogAllCategory',
    created() {
      this.getCategories()
    },
    data() {
      return {
        defaultAvatar:defaultAvatar,
        categories: [],
      }
    },
    methods: {
      view(id) {
        this.$router.push({path: `/category/${id}`})
      },
      getCategories() {
        let that = this
        getAllCategoriesDetail().then(data => {
          that.categories = data.data
        }).catch(error => {
          if (error !== 'error') {
            that.$message({type: 'error', message: '博客分类加载失败', showClose: true})
          }
        })
      }
    }
  }
</script>

<style scoped>
  .me-allct-body {
    margin: 60px auto 140px;
  }

  .me-allct-container {
    width: 1000px;
  }

  .me-allct-items {
    padding-top: 2rem;
  }

  .me-allct-item {
    width: 25%;
    display: inline-block;
    margin-bottom: 2.4rem;
    padding: 0 .7rem;
    box-sizing: border-box;
  }

  .me-allct-content {
    display: inline-block;
    width: 100%;
    background-color: #fff;
    border: 1px solid #f1f1f1;
    transition: border-color .3s;
    text-align: center;
    padding: 1.5rem 0;
  }

  .me-allct-info {
    cursor: pointer;
  }

  .me-allct-img {
    margin: -40px 0 10px;
    width: 60px;
    height: 60px;
    vertical-align: middle;

  }

  .me-allct-name {
    font-size: 21px;
    font-weight: 150;
    overflow: hidden;
    text-overflow: ellipsis;
    white-space: nowrap;
    margin-top: 4px;
  }

  .me-allct-description {
    min-height: 50px;
    font-size: 13px;
    line-height: 25px;
  }

  .me-allct-meta {
    font-size: 12px;
    color: #969696;
  }
</style>
