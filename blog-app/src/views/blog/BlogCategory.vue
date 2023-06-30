<template>
  <div v-title :data-title="title">

    <div class="me-area">
      <ul class="me-category-list">
        <li v-for="a in categories" :key="a.id" class="me-category-item">
          <el-badge :value="a.count">
            <el-button @click="changeCategory(a.id)" size="medium" type="primary" round plain>{{a.categoryName}}
            </el-button>
          </el-badge>
        </li>
      </ul>
    </div>

    <el-container>
      <el-main class="me-articles">
        <article-scroll-page></article-scroll-page>
      </el-main>
    </el-container>
  </div>

</template>

<script>
import ArticleScrollPage from '@/views/common/ArticleScrollPage'
import {getAllCategories} from '@/api/category'

export default {
  name: "BlogCategory",
  components: {
    ArticleScrollPage
  },
  created() {
    // this.listArchives()
    this.getCategories()
  },
  watch: {
    '$route'() {
      if (this.$route.params.id) {
        this.article.query.categoryId = this.$route.params.id
        let index = this.categories.findIndex((category) => category.id == this.article.query.categoryId)
        this.currentCategoryName = this.categories[index].categoryName
      }
    }
  },
  data() {
    return {
      article: {
        query: {
          categoryId: this.$route.params.id
        }
      },
      categories: [],
      currentCategoryName: ''
    }
  },
  computed: {
    title (){
      return this.currentCategory + ' - 分类'
    },
    currentCategory (){
      if(this.article.query.categoryId){
        return this.currentCategoryName
      }
      return '全部'
    }
  },
  methods: {

    changeCategory(id) {
      this.$router.push({path: `/category/${id}`})
    },

    getCategories() {
      getAllCategories().then(data => {
        console.log(data)
        this.categories = data.data
        console.log(data)
      }).catch(error => {
        if (error !== 'error') {
          this.$message({type: 'error', message: '分类加载失败', showClose: true})
        }
      })
    }
  }
}
</script>

<style scoped>


.me-area {
  position: fixed;
  left: 200px;
  margin-right: 50px;
  width: 200px !important;
  opacity: 0.7;
}

.el-container {
  width: 820px;
  /*position: absolute;*/
  /*left: 540px;*/
}

.el-main {
  padding: 0px;
  line-height: 16px;
  /*margin-bottom: -100px;*/
}

.me-category-list {
  background-color: white;
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 40px 20px;
  text-align: center;
  list-style-type: none;
  border-radius: 5px;
}

.me-category-item {
  margin-top: 18px;
  padding: 4px;
  font-size: 18px;
  color: #5FB878;
}

</style>
