<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>注册</title>
</head>
<body>
    用户名 : <input id="userName"> <br>
    昵称 : <input id="nickName"> <br>
    密码 : <input id="password" type="password"> <br>
    确认密码: <input id="repassword" type="password"> <br>
    <button onclick="register()">注册</button> <a href="/bqg/user/toLogin">去登录</a>
</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    function register() {
        var userName = $("#userName").val();
        var nickName = $("#nickName").val();
        var password = $("#password").val();
        var repassword = $("#repassword").val();
        if (userName == undefined || userName == "" || password == undefined || password == "") {
            alert("对不起,用户名密码不能为空");
            return;
        }
        if(password!=repassword){
            alert("对不起, 两次密码不一致");
            return;
        }
        $.ajax({
            url:"/bqg/user/register",
            type:"post",
            data:{'userName':userName,'nickName':nickName,'password':password,'repassword':repassword},
            dataType: "json",
            success:function(data){

                if(data.code==1){
                    location.href = "/bqg/user/toLogin";
                }else{
                    alert(data.msg);
                }
                //如果注册成功, 跳转到登录页
                //如果注册失败, 不跳转,弹出信息提示用户
            }
        })
    }
</script>
</html>
