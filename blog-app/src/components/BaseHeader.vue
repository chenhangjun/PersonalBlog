<template>
  <el-header class="me-area">
    <div class="me-header">

      <div v-if="!simple" class="menu">
        <div style="display: flex;">
          <router-link to="/home" class="menu-item"><i class="el-icon-s-home"></i>首页</router-link>
          <router-link to="/category" class="menu-item"><i class="el-icon-menu"></i>博客分类</router-link>
          <router-link to="/tag" class="menu-item"><i class="el-icon-price-tag"></i>标签</router-link>
          <router-link to="/archives" class="menu-item"><i class="el-icon-paperclip"></i>日期归档</router-link>
          <router-link to="/about" class="menu-item"><i class="el-icon-info"></i>关于我</router-link>
          <router-link to="/write" style="color: grey; margin-left: 80px;"><i class="el-icon-edit"></i>写博客</router-link>

        </div>
      </div>

      <template v-else>
        <slot></slot>
      </template>


      <div v-if="!simple" class="search-input">
        <div>
          <div>
            <!-- <el-input placeholder="请输入搜索内容"  @keyup.enter.native="searchHandler" v-model="search"></el-input> -->

            <template>
              <el-input
                style="width: 23vw;"
                v-model="queryString"
                @keyup.enter.native="searchEnter"
                placeholder="请输入内容">
              </el-input>
            </template>

          </div>
        </div>
      </div>

      <div class="user">
        <el-menu :router=true menu-trigger="click" mode="horizontal" active-text-color="#5FB878">
          <template v-if="!user.login">
            <el-menu-item index="/login">
              <el-button type="text">登录</el-button>
            </el-menu-item>
            <el-menu-item index="/register">
              <el-button type="text">注册</el-button>
            </el-menu-item>
          </template>

          <template v-else>
            <el-submenu index>
              <template slot="title">
                <img class="me-header-picture" :src="user.avatar"/>
              </template>
              <el-menu-item index @click="logout"><i class="el-icon-back"></i>退出</el-menu-item>
            </el-submenu>
          </template>
        </el-menu>
      </div>

    </div>
  </el-header>
</template>

<script>
import {searchArticle} from '@/api/article'
import {ref} from "vue"
export default {
  name: 'BaseHeader',
  props: {
    activeIndex: String,
    simple: {
      type: Boolean,
      default: false
    }
  },

  watch: {
    '$route'(to, from) {
      // console.log(to)
      // 截取路由路径的前一部分 若不为'/search' 则已跳离search页面 将输入框内容置空
      if (to.path.substring(0, 7) != '/search') {
        this.queryString = ''
      }
    }
  },

  data() {
    return {
      queryString: '',
      articles:[],
    }
  },
  computed: {
    user() {
      let login = this.$store.state.account.length != 0
      let avatar = this.$store.state.avatar
      return {
        login, avatar
      }
    }
  },
  methods: {
    logout() {
      let that = this
      this.$store.dispatch('logout').then(() => {
        this.$router.push({path: '/home'})
      }).catch((error) => {
        if (error !== 'error') {
          that.$message({message: error, type: 'error', showClose: true});
        }
      })
    },

    searchEnter() {
      this.$router.push({path: '/search/' + this.queryString})
    },

  }
}
</script>

<style>

.el-header {
  position: fixed;
  z-index: 1024;
  min-width: 100%;
  box-shadow: 0 2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
}

.me-header {
  display: flex;
}

.me-title {
  margin-top: 10px;
  font-size: 24px;
}

.me-header-left {
  margin-top: 10px;
  /*margin-left: 400px;*/
}

.menu {
  width: 650px;
  height: 60px;
  display: flex;
  align-items: center;
  margin-left: 30px;
}

.menu-item {
  color: grey;
  margin-left: 30px;
}

.router-link-active {
  color: #5FB878
}

a>i {
  margin-right: 5px;
}

.search-input {
  display: flex;
  align-items: center;
  margin-left: 280px;
}

.user {
  width: 200px;
  margin: -1px 0 0 170px;
  display: flex;
  align-items: center;
  /*border: red solid 2px;*/
}

.user-item {
  margin-left: 40px;
  color: #5FB878
}


/*.me-title img {*/
/*  max-height: 2.4rem;*/
/*  max-width: 100%;*/
/*}*/

.me-header-picture {
  width: 36px;
  height: 36px;
  border: 1px solid #ddd;
  border-radius: 50%;
  vertical-align: middle;
  background-color: #5fb878;
}
</style>
