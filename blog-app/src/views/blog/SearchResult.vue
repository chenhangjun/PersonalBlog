<template>
  <div v-title :data-title="title">
    <el-container>

      <el-main class="me-articles">
        <!--        <div class="me-month-title">{{currentArchive}}</div>-->

        <scroll-page :loading="loading" :offset="offset" :no-data="noData" v-on:load="load">
          <article-item v-for="a in articles" :key="a.id" v-bind="a"></article-item>
        </scroll-page>

      </el-main>
    </el-container>
  </div>

</template>

<script>
import ArticleItem from '@/components/article/ArticleItem'
import ScrollPage from '@/components/scrollpage'
import {searchArticle} from '@/api/article'

export default {
  name: "SearchResult",
  components: {
    'article-item': ArticleItem,
    'scroll-page': ScrollPage
  },
  created() {
    this.searchQuery()
  },
  watch: {
    '$route'() {
      if (this.$route.query) {
        this.searchQuery()
      }
    }
  },
  data() {
    return {
      articles: []
    }
  },
  computed: {
    title (){
      return this.$route.params.queryString + ' - 搜索结果'
    },
  },
  methods: {
    searchQuery() {
      var that = this
      searchArticle(this.$route.params.queryString).then((data) => {
        // console.log(data.data.length)
        if(data.data.length === 0) {
          that.$message({type: 'warning', message: '搜索无结果', showClose: true})
        }
        that.articles = data.data
      }).catch(error => {
        if (error !== 'error') {
          that.$message({type: 'error', message: '搜索失败!', showClose: true})
        }
      }).finally(() => {

      })
    }
  }
}
</script>

<style scoped>

.el-container {
  width: 820px;
}


.el-main {
  padding: 0px;
  line-height: 16px;
}

.el-card {
  border-radius: 5px;
}

.el-card:not(:first-child) {
  margin-top: 10px;
}

</style>
