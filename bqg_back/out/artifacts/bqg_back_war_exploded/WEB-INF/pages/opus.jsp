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
    <title>作品管理</title>
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
            border: 1px solid;
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
    </div>
    <div id="insert" style="display: none">
        封面图片 : <img id="img" onclick="openFile()" src="https://tse4-mm.cn.bing.net/th/id/OIP-C.NL79l3WKRgPuCsPE-s9D2QHaKe?w=120&h=180&c=7&o=5&dpr=1.5&pid=1.7">
        <input type="file" id="file" onchange="uploadImg()" style="display: none">
        <input type="hidden" id="icon">   <br>
        作品名称 : <input id="name"> <br>
        作品类型 : <select id="typeId" ></select>        <br>
        作品简介 : <textarea id="intro"></textarea> <br>
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
        getTypeListAll();
    })

    function getTypeListAll() {
        $.post('type/getListAll', {}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var list = data.data;
                var html = '';
                list.forEach(function (item) {
                    html += '<option value="'+item.id+'">'+item.name+'</option>'
                });
                $("#typeId").html(html);
            }else{
                alert(data.msg);
            }
        }, 'json');
    }

    //打开输入框
    function showInsert(){
        //点击后，原来隐藏的显示，原来显示的隐藏
        $("#content").toggle();
        $("#insert").toggle();
    }

    //添加书籍信息
    function insert() {
        var icon = $("#icon").val();
        var name = $("#name").val();
        var typeId = $("#typeId").val();
        var intro = $("#intro").val();
        if (icon == '' || name == '' || intro == '') {
            alert("对不起,作品内容不能为空")
            return;
        }
        $.post('opus/insert', {'icon': icon, 'name': name, 'typeId': typeId, 'intro': intro},
            function (data) {
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

    //上传图片
    function openFile() {
        $("#file").click();
    }

    function uploadImg() {
        var file = $("#file")[0].files[0];
        var formData = new FormData();
        formData.append('file', file);

        $.ajax({
            url: 'img/upload',
            type: 'post',
            data: formData,
            contentType:false,
            processData:false,
            dataType: 'json',
            success: function (data) {
                if (data.code == -2) {
                    alert(data.msg);
                    location.href = '/bqg_back/user/toLogin';
                    return;
                }
                if (data.code == 1) {
                    $("#img").prop("src", "img/getImg?imgName=" + data.data);
                    $("#icon").val(data.data);
                }else{
                    alert(data.msg);
                }
            }
        });
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
