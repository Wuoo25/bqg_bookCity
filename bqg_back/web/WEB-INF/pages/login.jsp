<%--
  Created by IntelliJ IDEA.
  User: W
  Date: 2021/7/15
  Time: 10:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
<div>
  用户名：<input id="userName"><br>
  密码：<input id="password"><br>
  <button onclick="login()">登录</button>
</div>
</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
  function login(){
    var userName=$("#userName").val();
    var password=$("#password").val();
    if(userName==undefined||userName==""||
            password==undefined||password==""){
      alert("对不起，用户名密码不为空");
      return;
    }

    //是ajax请求（url、type、data、dataType、success：function（data））的简写，type是post请求
    $.post("/bqg_back/user/login",{'userName':userName,'password':password},
    function (data){
      if(data.code==1){
        location.href="/bqg_back/user/toHome";
      }
      alert(data.msg)
    },"json");


  }
</script>
</html>