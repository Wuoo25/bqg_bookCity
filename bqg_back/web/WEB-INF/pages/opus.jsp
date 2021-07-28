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
            <button onclick="showInsert()">添加
                分类</button>
        </div>
        <table id="list">

        </table>
    </div>
    <div id="insert" style="display: none">
        封面图片 : <img id="img" onclick="openFile()" STYLE="width: 150PX" height="180PX"
                    src="https://tse4-mm.cn.bing.net/th/id/OIP-C.IKQCNryCaV-yWwhUzN97nwAAAA?w=134&h=191&c=7&o=5&dpr=1.67&pid=1.7">
       <%--用隐藏input框储存图片名称---%>
        <input type="file" id="file" onchange="uploadImg()" style="display: none">
        <input type="hidden" id="icon">   <br>
        作品名称 : <input id="name"> <br>
        作品类型 : <select id="typeId" ></select> <br>
        作品简介 : <textarea id="intro"></textarea> <br>
        <button onclick="insert()">提交</button><button onclick="showInsert()">取消</button>
    </div>

    <div id="update" style="display: none">
        <input type="hidden" id="id">
        封面图片 : <img id="imgUpdate" onclick="openFile()" STYLE="width: 150PX" height="180PX" src="https://tse4-mm.cn.bing.net/th/id/OIP-C.IKQCNryCaV-yWwhUzN97nwAAAA?w=134&h=191&c=7&o=5&dpr=1.67&pid=1.7">
        <br>
        作品名称 : <input id="nameUpdate"> <br>
        作品类型 : <select id="typeIdUpdate" ></select><br>
        作品简介 : <textarea id="introUpdate"></textarea> <br>
        <button onclick="update()">提交</button><button onclick="showUpdate()">取消</button>
    </div>

</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    $(function (){
        getTypeListAll();
        getOpusList();
    })

    //选择框的实现
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
                $("#typeIdUpdate").html(html);
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

    //显示当前用户作品信息
    function getOpusList(){
        $.post('opus/getList', {}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var list = data.data;
                var html = '<tr><th>封面</th><th>名称</th><th>类型</th><th>简介</th><th>创建时间</th><th>操作</th></tr>'
                list.forEach(function (item) {
                    //点击封面图跳转到章节页面，跳转时地址栏会有opusId一起传出
                    html += '<tr><td><a href="chapter/toChapter?opusId='+item.id+'">' +
                        '<img STYLE="width: 150PX" height="180PX" src="img/getImg?imgName=' + item.icon + '"></a>' +
                        '</td>' +
                        '<td>' + item.name + '</td>' +
                        '<td>' + item.typeName + '</td>' +
                        '<td>' + item.intro + '</td>' +
                        '<td>' + item.createDate + '</td>' +
                        '<td><button onclick="showUpdate(' + item.id + ')">修改</button><button onclick="deleteOpus(' + item.id + ')">删除</button></td></tr>';
                });
                $("#list").html(html);
            }else{
                alert(data.msg);
            }
        }, 'json');

    }

    //删除信息
    //根据id删除类型信息
    function deleteOpus(id) {

        var flg = confirm("您确定要删除吗?")

        if (!flg) {
            return;
        }

        $.post('opus/deleteOpus', {'id': id}, function (data) {
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
        var id = $("#id").val();
        var icon = $("#icon").val();
        var name = $("#nameUpdate").val();
        var typeId = $("#typeIdUpdate").val();
        var intro = $("#introUpdate").val();
        if (icon == '' || name == '' || intro == '') {
            alert("对不起,分类内容不能为空");
            return;
        }
        $.post('opus/updateOpus',{'id':id,'icon': icon, 'name': name, 'typeId': typeId, 'intro': intro},function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                showUpdate();
                getOpusList();
            }else{
                alert(data.msg);
            }
        },"json")
    }

    //修改时回显作品信息
    function showUpdate(id){
        $("#content").toggle();
        $("#update").toggle();
        if(id==undefined){
            return;
        }

        //根据id查询类型的信息,回显到修改框中
        $.post('opus/get',{'id':id},function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var opus = data.data;
                $("#imgUpdate").prop('src', 'img/getImg?imgName=' + opus.icon);
                $("#icon").val(opus.icon);
                $("#nameUpdate").val(opus.name);
                $("#typeIdUpdate").val(opus.typeId);
                $("#introUpdate").val(opus.intro);
                $("#id").val(opus.id);
            }else{
                alert(data.msg);
            }
        },'json')
    }

</script>

</html>
