<%--
  Created by IntelliJ IDEA.
  User: spring
  Date: 2021/7/15
  Time: 9:34 上午
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>登录</title>
</head>
<body>
    <div>
        用户名 : <input id="userName"> <br>
        密码 : <input id="password" type="password"> <br>
        验证码: <input id="imgCode" type="password"><img src="/bqg/img/getImgCode"> <br>
        <button onclick="login()">登录</button>
    </div>
</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    function login() {
        var userName = $("#userName").val();
        var password = $("#password").val();
        if (userName == undefined || userName == "" ||
            password == undefined || password == "") {
            alert("对不起,用户名密码不能空");
            return;
        }

        $.post("/bqg/user/login", {'userName': userName, 'password': password},
            function (data) {
                if (data.code == 1) {
                    location.href = "/bqg/user/toHome";
                }else{
                    alert(data.msg)
                }
            }, "json");
    }

</script>
</html>
