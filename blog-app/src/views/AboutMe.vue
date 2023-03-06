<template>
  <div data-title="个人" class="container">
    <div class="box">
      <img src="../assets/img/klee.jpg" class="avatar"/>
    </div>

    <div class="box" style="margin-bottom: 10px;">
      <span>若再许我少年时， 一两黄金一两风</span>
      <el-divider></el-divider>
    </div>


    <div class="box">
      <div class="line-items">
        <span> 陈杭军 </span>
      </div>
      <div class="line-items">
        <span> Java工程师 / 研究生在读 </span>
      </div>
      <div class="line-items">
        <span><i class="el-icon-location-outline"></i> &nbsp;&nbsp;浙江&nbsp;&&nbsp;杭州</span>
      </div>
    </div>

    <div class="box" style="margin-bottom: 20px;">
      <div style="width: 60%">
        我该要肆意妄为，要志得意满，遨游山川和湖海，要世界所有烂漫。
      </div>
    </div>

    <div class="box">
      <div class="info-table">
        <div>
          <div>
            <span> 姓名 | 陈杭军 </span>
          </div>
          <div class="line-items">
            <span> 性别 | 男 </span>
          </div>
          <div class="line-items">
            <span> 出生 | 1997.07 </span>
          </div>
          <div class="line-items">
            <span> 居住 | 浙江杭州 </span>
          </div>
          <div class="line-items">
            <span> 学历 | 研究生 </span>
          </div>
        </div>
        <div style="margin-left: 200px">
          <div class="line-items">
            <span> 手机 | 177XXXX9874 </span>
          </div>
          <div class="line-items">
            <span> 邮箱 | 976913999@qq.com </span>
          </div>
          <div class="line-items">
            <span> Github | <a href="https://github.com/chenhangjun">https://github.com/chenhangjun</a> </span>
          </div>
          <div class="line-items">
            <span> CSDN | <a href="https://blog.csdn.net/chen1030416518">https://blog.csdn.net/chen1030416518</a> </span>
          </div>
        </div>
      </div>
    </div>

    <div class="experience">
      <div class="title">
        <span>教育经历</span>
        <el-divider></el-divider>
      </div>
      <div style="margin-left: 100px;">
        <div class="line-items">
          <span> 2016/09 - 2020/06 &nbsp;&nbsp;&nbsp;&nbsp; 江南大学 (211) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;计算机科学与技术</span>
        </div>
        <div class="line-items" >
          <span> 2020/09 - 2022/09 &nbsp;&nbsp;&nbsp;&nbsp; 服役于中国人民解放军某部队</span>
        </div>
        <div class="line-items">
          <span> 2022/09 - 2023/06 &nbsp;&nbsp;&nbsp;&nbsp; 杭州电子科技大学 &nbsp;&nbsp;&nbsp;&nbsp;计算机技术</span>
        </div>
      </div>
    </div>

    <div class="experience">
      <div class="title">
        <span>项目经历</span>
        <el-divider></el-divider>
      </div>
      <div style="margin-left: 100px;">
      </div>
    </div>

    <div class="experience">
      <div class="title">
        <span>获奖经历</span>
        <el-divider></el-divider>
      </div>
      <div style="margin-left: 100px;">
      </div>
    </div>




  </div>

</template>

<script>

export default {
  name: "AboutMe",
  created() {
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

  .container {
    width: 100%;
    margin-top: 120px;
    color: white;
  }

  .box {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-bottom: 40px;
  }

  .avatar {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    /*object-fit: cover;*/
  }

  .el-divider {
    width: 50%;
    background-color: white;
    height: 2px;
  }

  .line-items {
    font-size: 18px;
    margin: 10px 0;
  }

  .info-table {
    display: flex;
    flex-direction: row;
    align-items: baseline;
  }

  span>a {
    color: white;
    text-decoration: underline;
  }

  span>a:hover {
    color: #5FB878
  }

  .experience {
    margin-top: 40px;
    width: 50%;
    margin-left: 25%;
  }

  .title {
    font-size: 30px;
    font-weight: 600;
    margin-bottom: 50px;
  }

  .title .el-divider {
    margin-top: 5px;
    width: 120px;
    height: 4px;
  }


</style>
