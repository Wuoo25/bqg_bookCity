<%--
  Created by IntelliJ IDEA.
  User: W
  Date: 2021/7/16
  Time: 9:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作品分类</title>
<%--有此路径后浏览器会将其进行拼接--%>
    <base href="/bqg_back/">
    <style>
        #content{
            width: 80%;
            margin: auto;
        }
        #content div{
            margin:10px auto;
        }
        #list{
            width: 100%;
            text-align: center;
            border: 1px solid;
            border-collapse: collapse;
        }
        #list td,th {
            bpagesorder: 1px solid;
        }
        #pages {
            text-align: center;
        }
        #pages button{
            margin: auto 5px;
        }
        #insert {
            width: 80%;
            margin: 10px auto;
        }
        #update {
            width: 80%;
            margin: 10px auto;
        }
    </style>
</head>
<body>
    <div>
        <a href="user/toHome">主页</a>
    </div>
    <div id="content">
        <div>
            <button onclick="showInsert()">添加分类</button>
        </div>
        <table id="list">

        </table>
        <div id="pages">

        </div>
    </div>
    <div id="insert" style="display: none">
        分类名称 ：<input id="name"><br>
        分类介绍 ：<textarea id="intro"></textarea><br>
        <button onclick="insert()">提交</button><button onclick="showInsert()">取消</button>
    </div>
    <div id="update" style="display: none">
        <input id="idUpdate" type="hidden">
        分类名称 : <input id="nameUpdate"> <br>
        分类介绍 : <textarea id="introUpdate"></textarea> <br>
        <button onclick="update()">提交</button><button onclick="showUpdate()">取消</button>
    </div>

</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    $(function (){
        getTypeList();
    })

    //打开输入框
    function showInsert(){
        //点击后，原来隐藏的显示，原来显示的隐藏
        $("#content").toggle();
        $("#insert").toggle();
    }

    //添加类型信息
    function insert(){
        var name=$("#name").val();
        var intro=$("#intro").val();
        if(name==undefined||name==''||intro==undefined||intro==''){
            alert("对不起，内容不能为空");
            return;
        }

        $.post('type/insert', {'name':name,'intro':intro}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                location.reload();
            }else{
                alert(data.msg);
            }
        }, 'json');
    }

    function getTypeList(pageNo){
        if (pageNo == undefined) {
            pageNo = 1;
        }
        $.post('type/getList',{'pageNo':pageNo},function (data){
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var pageData=data.data;
                var list=pageData.list;//获取的是pagedate
                var html=`<tr><th>类型名称</th><th>介绍</th><th>创建时间</th><th>修改时间</th><th>操作</th></tr>` ;
                //拼接table表格
                //item是遍历出来的元素
                list.forEach(function (item){
                    html+=`<tr>
                        <td>\${item.name}</td>
                        <td>\${item.intro}</td>
                        <td>\${item.createDate}</td>
                        <td>\${item.updateDate}</td>
                        <td>
                            <button onclick="showUpdate(\${item.id})">修改</button>
                            <button onclick="deleteType(\${item.id})">删除</button>
                        </td>
                    </tr>`

                })
                $("#list").html(html);

                //组装分页
                //计算有多少页,这是js的算法，向上取整
                var pages = Math.ceil(pageData.count/pageData.pageSize);
                var html = "";
                for (var i = 1; i <= pages; i++) {
                    if (pageData.pageNo == i) {
                        html+='<button style="background-color: #abd9ab">'+i+'</button>';
                    }else{
                        html+='<button onclick="getTypeList('+i+')">'+i+'</button>';
                    }
                }
                $("#pages").html(html);

            }else{
                alert(data.msg);
            }
        },'json')
    }

    //删除信息
    //根据id删除类型信息
    function deleteType(id) {

        var flg = confirm("您确定要删除吗?")

        if (!flg) {
            return;
        }

        $.post('type/delete', {'id': id}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                location.reload();
            }else{
                alert(data.msg);
            }
        },'json');
    }

    //根据id修改类型信息
    function update() {
        var id = $("#idUpdate").val();
        var name = $("#nameUpdate").val();
        var intro = $("#introUpdate").val();
        if (name == undefined || name == '' || intro == undefined || intro == '') {
            alert("对不起,分类内容不能为空");
            return;
        }
        $.post('type/update',{'id':id,'name':name,'intro':intro},function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                location.reload();
            }else{
                alert(data.msg);
            }
        },"json")
    }

    // 打开修改框

    function showUpdate(id){
        $("#content").toggle();
        $("#update").toggle();
        if(id==undefined){
            return;
        }

        //根据id查询类型的信息,回显到修改框中
        $.post('type/get',{'id':id},function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var typeBean = data.data;
                $("#nameUpdate").val(typeBean.name);
                $("#introUpdate").val(typeBean.intro);
                $("#idUpdate").val(typeBean.id);
            }else{
                alert(data.msg);
            }
        },'json')
    }

</script>

</html>
