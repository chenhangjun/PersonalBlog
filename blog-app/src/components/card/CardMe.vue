<template>
  <el-card>
    <h1 class="me-author-name">宸</h1>

    <div class="sec-box">
      <div class="col">
        <span>{{ statistics.article }}</span>
        <span style="font-size: 15px; font-weight: 400">博客数</span>
      </div>
      <div class="col">
        <span>{{ statistics.category }}</span>
        <span style="font-size: 15px; font-weight: 400">分类数</span>
      </div>
      <div class="col">
        <span>{{ statistics.tag }}</span>
        <span style="font-size: 15px; font-weight: 400">标签数</span>
      </div>
    </div>





<!--    <div class="me-author-description">-->
<!--      <span><i class="el-icon-location-outline"></i> &nbsp;浙江&杭州</span>-->
<!--    </div>-->
<!--    <div class="me-author-items">-->
<!--      <span><i class="el-icon-school"></i> &nbsp;杭州电子科技大学</span>-->
<!--    </div>-->
<!--    <div class="me-author-items">-->
<!--      <span><i class="el-icon-message"></i> &nbsp;976913999@qq.com</span>-->
<!--    </div>-->
  </el-card>

</template>

<script>
  export default {
    name: 'CardMe',
    props: {
      statistics: Object
    },
    data() {
      return {
      }
    },
    methods: {
    }
  }
</script>

<style scoped>
  .me-author-name {
    text-align: center;
    font-size: 40px;
    border-bottom: 1px solid #5FB878;
  }

  .sec-box {
    display: flex;
    margin-top: 10px;
    justify-content: space-around;
  }

  .col {
    display: flex;
    flex-flow: column;
    text-align: center;
    font-size: 35px;
    font-weight: 600;
  }

  .me-author-description {
    padding-top: 16px;
  }

  .me-author-items {
    padding-top: 6px;
  }

  .me-author-tool {
    text-align: center;
    padding-top: 10px;
  }

  .me-author-tool i {
    cursor: pointer;
    padding: 4px 10px;
    font-size: 30px;
  }
</style>
