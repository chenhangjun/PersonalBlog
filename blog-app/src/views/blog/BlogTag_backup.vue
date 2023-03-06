<template>
  <div class="me-ct-body" v-title data-title="标签">
    <el-container class="me-ct-container">
      <el-main>
        <div class="me-ct-title me-area">
          <template>
            <img class="me-ct-picture" :src="ct.avatar?ct.avatar:defaultAvatar"/>
            <h3 class="me-ct-name">{{ct.tagName}}</h3>
          </template>
          <span class="me-ct-meta">{{ct.articles}} 文章</span>
        </div>

        <div class="me-ct-articles">
          <article-scroll-page v-bind="article"></article-scroll-page>
        </div>

      </el-main>
    </el-container>
  </div>
</template>

<script>
import ArticleScrollPage from '@/views/common/ArticleScrollPage'
import {getArticlesByCategory, getArticlesByTag} from '@/api/article'
import {getTagDetail} from '@/api/tag'
import defaultAvatar from '@/assets/img/logo.png'


export default {
  name: 'BlogTag',
  created() {
    this.getTagsDetailById()
  },
  data() {
    return {
      defaultAvatar: defaultAvatar,
      ct: {},
      article: {
        query: {
          tagId: '',
        }
      },
    }
  },

  methods: {
    getTagsDetailById() {
      let id = this.$route.params.id
      this.article.query.tagId = id
      getTagDetail(id).then(data => {
        this.ct = data.data
      }).catch(error => {
        if (error !== 'error') {
          this.$message({type: 'error', message: '标签加载失败', showClose: true})
        }
      })
    }
  },
  components: {
    ArticleScrollPage
  }
}
</script>

<style scoped>
.me-ct-body {
  margin: 60px auto 140px;
  min-width: 100%;
}

.el-main {
  padding: 0;
}

.me-ct-title {
  text-align: center;
  height: 150px;
  padding: 20px;
}

.me-ct-picture {
  width: 60px;
  height: 60px;
}

.me-ct-name {
  font-size: 28px;
}

.me-ct-meta {
  font-size: 12px;
  color: #969696;
}

.me-ct-articles {
  width: 820px;
  margin: 30px auto;
}

</style>
