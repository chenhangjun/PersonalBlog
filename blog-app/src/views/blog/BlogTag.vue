<template>
  <div v-title :data-title="title">
    <div class="me-area">
      <ul class="me-tag-list">
        <li v-for="a in tags" :key="a.id" class="me-tag-item">
          <el-badge :value="a.count">
            <el-button @click="changeTag(a.id)" size="medium" type="success" round plain>{{a.tagName}}
            </el-button>
          </el-badge>
        </li>
      </ul>
    </div>
    <el-container>
      <el-main class="me-articles">
        <article-scroll-page v-bind="article"></article-scroll-page>
      </el-main>
    </el-container>
  </div>

</template>

<script>
import ArticleScrollPage from '@/views/common/ArticleScrollPage'
import {getAllTags} from '@/api/tag'

export default {
  name: "BlogTag",
  components: {
    ArticleScrollPage
  },
  created() {
    this.getTags()
  },
  watch: {
    '$route'() {
      if (this.$route.params.id) {
        this.article.query.tagId = this.$route.params.id
        let index = this.tags.findIndex((tags) => tags.id == this.article.query.tagId)
        this.currentTagName = this.tags[index].tagName
      }
    }
  },
  data() {
    return {
      article: {
        query: {
          tagId: this.$route.params.id
        }
      },
      tags: [],
      currentTagName: ''
    }
  },
  computed: {
    title (){
      return this.currentTag + ' - 标签'
    },
    currentTag (){
      if(this.article.query.tagId){
        return this.currentTagName
      }
      return '全部'
    }
  },
  methods: {

    changeTag(id) {
      this.$router.push({path: `/tag/${id}`})
    },

    getTags() {
      getAllTags().then(data => {
        console.log(data)
        this.tags = data.data
      }).catch(error => {
        if (error !== 'error') {
          this.$message({type: 'error', message: '标签加载失败', showClose: true})
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
}

.me-tag-list {
  background-color: white;
  margin-top: 10px;
  margin-bottom: 10px;
  padding: 40px 20px;
  text-align: center;
  list-style-type: none;
  border-radius: 5px;
}

.me-tag-item {
  margin-top: 18px;
  padding: 4px;
  font-size: 18px;
  color: #5FB878;
}

</style>
