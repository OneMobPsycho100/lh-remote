(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-b3831d40"],{"440d":function(e,r,t){e.exports=t.p+"static/img/logo2.ebf466ae.png"},"4fde":function(e,r,t){"use strict";var n=t("7ce0"),o=t.n(n);o.a},"7ce0":function(e,r,t){},"7ded":function(e,r,t){"use strict";t.d(r,"b",(function(){return c})),t.d(r,"a",(function(){return u}));t("d3b7");var n=t("cebe"),o=t.n(n),a=t("5c96"),i=t("f3e4"),s=o.a.create({baseURL:"http://42.194.194.178:3000",timeout:12e4});s.interceptors.request.use((function(e){return Object(i["a"])()&&(e.headers["Authorization"]="Bearer "+Object(i["a"])()),e.headers["Content-Type"]="application/json",e}),(function(e){console.log(e),Promise.reject(e)})),s.interceptors.response.use((function(e){var r=e.data,t=r.code,n=r.msg;return 200===t?r.data:(Object(a["Message"])({type:"error",message:n}),Promise.reject("error"))}),(function(e){var r=e.response.data.status;return r||Object(a["Message"])({type:"error",message:"接口请求失败"}),Promise.reject(e)}));var l=s;function c(e){return l({url:"/user/login",method:"post",data:e})}function u(e){return l({url:"/user/info",method:"get",params:e})}},a55b:function(e,r,t){"use strict";t.r(r);var n=function(){var e=this,r=e.$createElement,t=e._self._c||r;return t("div",{staticClass:"login-wrapper",style:"background-image:url("+e.Background+")"},[t("div",{staticClass:"form-box"},[e._m(0),t("el-form",{ref:"loginForm",staticClass:"login-form",attrs:{model:e.loginForm,rules:e.loginRules,"label-width":"0px"}},[t("el-form-item",{attrs:{prop:"username"}},[t("el-input",{attrs:{type:"text","auto-complete":"off",placeholder:"请输入账号","prefix-icon":"el-icon-user"},model:{value:e.loginForm.username,callback:function(r){e.$set(e.loginForm,"username",r)},expression:"loginForm.username"}})],1),t("el-form-item",{attrs:{prop:"password"}},[t("el-input",{attrs:{type:"password","auto-complete":"off",placeholder:"请输入密码","prefix-icon":"el-icon-lock"},nativeOn:{keyup:function(r){return!r.type.indexOf("key")&&e._k(r.keyCode,"enter",13,r.key,"Enter")?null:e.handleLogin(r)}},model:{value:e.loginForm.password,callback:function(r){e.$set(e.loginForm,"password",r)},expression:"loginForm.password"}})],1),t("el-form-item",[t("el-checkbox",{model:{value:e.loginForm.rememberMe,callback:function(r){e.$set(e.loginForm,"rememberMe",r)},expression:"loginForm.rememberMe"}},[e._v("记住我")])],1),t("el-form-item",[t("el-button",{staticStyle:{width:"100%"},attrs:{loading:e.loading,size:"small",type:"primary"},nativeOn:{click:function(r){return r.preventDefault(),e.handleLogin(r)}}},[e.loading?t("span",[e._v("登 录 中...")]):t("span",[e._v("登 录")])])],1)],1)],1)])},o=[function(){var e=this,r=e.$createElement,n=e._self._c||r;return n("div",{staticClass:"form-title"},[n("img",{attrs:{src:t("440d"),alt:"icon"}}),n("p",[e._v("账 号 登 录")])])}],a=t("7ded"),i=t("f3e4"),s=t("d847"),l=t.n(s),c={name:"Login",data:function(){return{Background:l.a,loginForm:{username:"admin",password:"admin123",rememberMe:!0},loginRules:{username:[{required:!0,trigger:"blur",message:"用户名不能为空"}],password:[{required:!0,trigger:"blur",message:"密码不能为空"}]},loading:!1,redirect:void 0}},watch:{$route:{handler:function(e){this.redirect=e.query&&e.query.redirect},immediate:!0}},methods:{handleLogin:function(){var e=this;this.$refs.loginForm.validate((function(r){var t={username:e.loginForm.username,password:e.loginForm.password};r&&(e.loading=!0,Object(a["b"])(t).then((function(r){e.loading=!1,Object(i["c"])(r.token),e.$router.push({path:e.redirect||"/"})})).catch((function(){e.loading=!1})))}))}}},u=c,d=(t("4fde"),t("2877")),m=Object(d["a"])(u,n,o,!1,null,null,null);r["default"]=m.exports},d847:function(e,r,t){e.exports=t.p+"static/img/login-background.0f25c16a.jpg"}}]);