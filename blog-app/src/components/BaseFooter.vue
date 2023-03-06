<template>
  <el-footer class="me-area">
    <div class="me-footer">
      <p>Any suggestions please contact at 976913999@qq.com</p>
    </div>
  </el-footer>

</template>

<script>

  export default {
    name: 'BaseFooter',
    data() {
      return {}
    },
    methods: {},
    mounted() {
    }
  }
</script>

<style>

  .el-footer {
    background-color: white;
    opacity: 0.6;
    min-width: 100%;
    box-shadow: 0 -2px 3px hsla(0, 0%, 7%, .1), 0 0 0 1px hsla(0, 0%, 7%, .1);
    position: absolute;
    bottom: 0;
    left: 0;
    z-index: 1024;
    height: 30px !important;
    /*border: solid 2px red;*/
  }

  .me-footer {
    /*color: white;*/
    text-align: center;
    line-height: 30px;
    font-family: 'Open Sans', sans-serif;
    font-size: 10px;

  }

  .me-login-design-color {
    color: #5FB878 !important;
  }

</style>
