<%--
  Created by IntelliJ IDEA.
  User: W
  Date: 2021/7/15
  Time: 9:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
</head>
<body>
    <div>
        <div>
            ${login.nickName}
                <a href="/bqg_back/type/toType">类型管理</a>
                <a href="/bqg_back/opus/toOpus">作品管理</a>
        </div>
    </div>
    <hr>
    <div>
        <h4>个人信息</h4>
        昵称 : ${login.nickName} <button onclick="updateNickName()">修改昵称</button>
        <hr>
        <h4>详细信息 <button onclick="showUpdateInfo()">修改详情</button></h4>
        <div id="info">
            手机号码 ：<span id="cellphone"></span><br>
            性别 ：<span id="sex"></span><br>
            生日 ：<span id="birth"></span> <br>
            签名 ：<span id="intro"></span> <br>
        </div>
        <div id="infoUpdate" style="display: none">
            手机号码 : <input id="cellphoneUpdate"> <br>
            性别 : <input type="radio" name="sex" value="1"> 男 <input type="radio" name="sex" value="2"> 女 <br>
            生日 : <input id="birthUpdate" type="date"> </span> <br>
            签名 : <textarea id="introUpdate"></textarea> <br>
            <button onclick="updateInfo()">提交</button>
        </div>
    </div>
</body>

<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    //自动执行
    $(function(){
        getUserInfo();
    })

    function updateNickName(){
        var nickName=prompt("请输入新昵称");
        if(nickName=="") {
            alert("对不起，昵称不能为空");
            return;
        }
        $.post('/bqg_back/user/update',{'nickName':nickName},function (data){
            if(data.code==-2){
                alert(data.msg);
                location.href="/bqg_back/user/toLogin";
                return;
            }
            if(data.code==1){
                location.reload();//刷新页面
            }else {
                alert(data.msg);
            }
        },'json')
        /*
            $.ajax({
            url:,
            type: "post",
            data:{'nickName':nickName},
            dataType:"json",
            success:function (data){
            }
        })
        * */
    }

    //打开详情信息输入框 获取用户详情信息
    var infoBean;
    function showUpdateInfo(){
        $("#info").hide();
        $("#infoUpdate").show();
        $("cellphoneUpdate").val(infoBean.cellphone);
        //遍历input框找到两个sex
        $("input[name='sex']").each(function (){
            //value与数据库存储相同，标记为选中状态
            if($(this).val()==infoBean.sex){
                $(this).prop("checked",true);
            }
        })
        $("#birthUpdate").val(infoBean.birth);
        $("#introUpdate").val(infoBean.intro);

    }

    // 更新用户的详情信息
    function updateInfo(){
        var cellphone=$("#cellphoneUpdate").val();
        //找出被选中的input 只能选一个选项
        var sex=$("input[name='sex']:checked").val();
        var birth=$("#birthUpdate").val();
        var intro=$("#introUpdate").val();
        //判断出现空值无意义
        $.post('/bqg_back/userInfo/update',{'cellphone':cellphone,'sex':sex,
            'birth':birth,'intro':intro},function (data){
            if(data.code==-2){
                alert(data.msg);
                location.href="/bqg_back/user/toLogin";
                return;
            }
            if(data.code==1){
                location.reload();
            }else {
                alert(data.msg);
            }

        },'json')

    }

    /**
     * 获取用户信息
     */
    function getUserInfo(){
        $.post('/bqg_back/userInfo/getUserInfo',{},function (data){

            if(data.code==-2){
                alert(data.msg);
                location.href="/bqg_back/user/toLogin";
                return;
            }
            if(data.code==1){
                if(data==null){
                    alert("对不起，还没有录入个人信息")
                    return;
                }
                var info=data.data;
                infoBean=info;
                $("#cellphone").text(info.cellphone);
                $("#sex").text(info.sexName);
                $("#birth").text(info.birth);
                $("#intro").text(info.intro);

            }else {
                alert(data.msg);
            }
        },'json')

    }


</script>

</html>
