webpackJsonp([6],{"3/TF":function(t,e,r){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=r("Q6dk"),i=r("viA7"),n={name:"BlogArchive",components:{ArticleScrollPage:a.a},created:function(){this.listArchives()},watch:{$route:function(){this.$route.params.year&&this.$route.params.month&&(this.article.query.year=this.$route.params.year,this.article.query.month=this.$route.params.month)}},data:function(){return{article:{query:{month:this.$route.params.month,year:this.$route.params.year}},archives:[]}},computed:{title:function(){return this.currentArchive+" - 日期归档"},currentArchive:function(){return this.article.query.year&&this.article.query.month?this.article.query.year+"年"+this.article.query.month+"月":"全部"}},methods:{changeArchive:function(t,e){this.$router.push({path:"/archives/"+t+"/"+e})},listArchives:function(){var t=this;Object(i.e)().then(function(e){console.log(e),t.archives=e.data}).catch(function(e){t.$message({type:"error",message:"博客归档加载失败!",showClose:!0})})}}},s={render:function(){var t=this,e=t.$createElement,r=t._self._c||e;return r("div",{directives:[{name:"title",rawName:"v-title"}],attrs:{"data-title":t.title}},[r("div",{staticClass:"me-area"},[r("ul",{staticClass:"me-month-list"},t._l(t.archives,function(e){return r("li",{key:e.year+e.month,staticClass:"me-month-item"},[r("el-badge",{attrs:{value:e.count}},[r("el-button",{attrs:{size:"small"},on:{click:function(r){return t.changeArchive(e.year,e.month)}}},[t._v(t._s(e.year+"年"+e.month+"月")+"\n            ")])],1)],1)}),0)]),t._v(" "),r("el-container",[r("el-main",{staticClass:"me-articles"},[r("article-scroll-page",t._b({},"article-scroll-page",t.article,!1))],1)],1)],1)},staticRenderFns:[]};var c=r("VU/8")(n,s,!1,function(t){r("xxRf")},"data-v-d1696bee",null);e.default=c.exports},xxRf:function(t,e){}});
//# sourceMappingURL=6.a362cf5433a16da219d9.js.map