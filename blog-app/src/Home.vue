<template>
  <div id="home">
    <div class="background">
      <img src="./assets/img/bg.jpg" width="100%" height="100%" alt/>
    </div>
    <el-container>
    	<base-header :activeIndex="activeIndex"></base-header>

		  <router-view class="me-container"/>

			<base-footer v-show="footerShow"></base-footer>
<!--      <base-footer></base-footer>-->

		</el-container>

  </div>

</template>

<script>
import BaseFooter from '@/components/BaseFooter'
import BaseHeader from '@/components/BaseHeader'

export default {
  name: 'Home',
  data (){
  	return {
  			activeIndex: '/',
  			footerShow:true
  	}
  },
  components:{
  	'base-header':BaseHeader,
  	'base-footer':BaseFooter
  },
  beforeRouteEnter (to, from, next){
  	 next(vm => {
    	vm.activeIndex = to.path
  	})
  },
  beforeRouteUpdate (to, from, next) {
	  if(to.path == '/'){
	  	this.footerShow = true
	  }else{
	  	this.footerShow = false
	  }
	  this.activeIndex = to.path
	  next()
	}
}
</script>

<style>

  .background {
    width: 100%;
    height: 100%; /**宽高100%是为了图片铺满屏幕 */
    z-index: -1;
    position: fixed;
    top: 0px; /*这里是设置与顶部的距离*/
    left: 0px; /*这里是设置与左边的距离*/
    bottom: 0px;
    right: 0px;
  }

  .me-container{
    margin: 90px auto 140px;
  }

</style>
