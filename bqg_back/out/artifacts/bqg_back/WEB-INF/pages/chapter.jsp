<%--
  Created by IntelliJ IDEA.
  User: W
  Date: 2021/7/19
  Time: 19:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>章节</title>
    <base href="/bqg_back/">
    <style>
        #content{
            width: 80%;
            margin: auto;
        }
        #opusContent div{
            margin: 0px 0px 15px 15px;
        }
        #list{
            width: 100%;
            text-align: center;
            border: 1px solid;
            border-collapse: collapse;
            text-align: left;
        }
        #list span{
            margin: 10px;
            cursor: pointer;
        }

        #pages button{
            margin: auto 5px;
        }
        #insert {
            width: 80%;
            margin: 10px auto;
        }
        #insert img{
            width: 100px;
        }
        #update {
            width: 80%;
            margin: 10px auto;
        }
        #update img{
            width: 100px;
        }

        .opus{
            display: inline-block;
            margin-left: 15px;
        }
        #opusContent img{
            width: 150px;
            vertical-align: top;
        }
    </style>
</head>
<body>
<div>
    <a href="user/toHome">主页</a>
</div>
<hr>
<div id="content">
    <div id="opusContent">

    </div>
    <hr>
    <div style="margin-bottom: 10px">
        <button onclick="showInsert()">添加章节</button>
        <%----%>
        <a href="javascript:void(0)" onclick="getChapterList('asc')">正序</a><a href="javascript:void(0)" onclick="getChapterList('desc')">倒序</a>
    </div>
    <div id="list">

    </div>

</div>
<div id="insert" style="display: none">
    章节名称 : <input id="name"> <br>
    章节内容 : <div id="chapterContent"></div> <br>
    <button onclick="insert()">提交</button><button onclick="showInsert()">取消</button>
</div>

<div id="update" style="display: none">
    <input type="hidden" id="id">
    章节名称 : <input id="nameUpdate"> <br>
    章节内容 : <div id="chapterContentUpdate"></div> <br>
    <button onclick="update()">提交</button><button onclick="showUpdate()">取消</button>
</div>

</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/wangeditor@latest/dist/wangEditor.min.js"></script>

<script>
    var editor;
    var opusId;
    var editor2 ;

    $(function (){
        //从地址栏中截取作品id信息,最后一个等号之后的数字
        opusId = location.href.substring(location.href.lastIndexOf("=") + 1);

        //初始化使用富文本
        const E = window.wangEditor;
        editor = new E('#chapterContent');
        editor.create()
        editor2 = new E('#chapterContentUpdate');
        editor2.create()

        //获取作品信息
        getOpus();
        getChapterList();
    })

    //插入操作
    function insert(){
        var name = $("#name").val();
        //此处插入的是html格式的字符串
        var content = editor.txt.html();
        if (name == '' || content == '') {
            alert("对不起,内容不能为空")
            return;
        }
        $.post('chapter/insert',{'opusId':opusId,'name':name,'content':content},
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
    function showInsert() {
        $("#content").toggle();
        $("#insert").toggle();
    }

    //显示作品
    function getOpus(){
        $.post('opus/get', {'id': opusId}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var opus = data.data;
                var html = '<img src="img/getImg?imgName='+opus.icon+'"></img>';
                html += '<div class="opus">' +
                    '<div> 作品名称 : '+opus.name+'</div>'+
                    '<div> 作品类型 : '+opus.typeName+'</div>'+
                    '<div> 创建时间 : '+opus.createDate+'</div>'+
                    '<div> 作品简介 : '+opus.intro+'</div>'+
                    '</div>'
                $("#opusContent").html(html);

            }else{
                alert(data.msg);
            }
        }, 'json');
    }
    function getChapterList(order){
        $.post('chapter/getList', {'opusId':opusId,'order':order}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var list = data.data;

                var html = "";

                list.forEach(function (item) {
                    html +='<span onclick="showUpdate('+item.id+')">'+item.name+' <button onclick="deleteChapter('+item.id+')">删除</button></span>'
                });
                $("#list").html(html);

            }else{
                alert(data.msg);
            }
        }, 'json');
    }

    //删除作品
    function deleteChapter(id){
        var flg = confirm("您确定要删除吗?");
        if(!flg){
            return;
        }

        $.post('chapter/delete', {'id': id}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                getChapterList();
            }else{
                alert(data.msg);
            }

        },'json');

    }

    //修改作品
    function update(){
        var name = $("#nameUpdate").val();
        var content = editor2.txt.html();
        var id = $("#id").val();
        if (name == '' || content == '') {
            alert("对不起,内容不能空")
        }

        $.post('chapter/update', {'id':id,'name': name, 'content': content},
            function (data) {
                if (data.code == -2) {
                    alert(data.msg);
                    location.href = '/bqg_back/user/toLogin';
                    return;
                }
                if (data.code == 1) {
                    showUpdate();
                    getChapterList();
                }else{
                    alert(data.msg);
                }
            }, 'json');
    }
    function showUpdate(id){
        $("#update").toggle();
        $("#content").toggle();
        if (id == undefined) {
            return;
        }

        $.post('chapter/get', {'id': id}, function (data) {
            if (data.code == -2) {
                alert(data.msg);
                location.href = '/bqg_back/user/toLogin';
                return;
            }
            if (data.code == 1) {
                var chapter = data.data;
                $("#nameUpdate").val(chapter.name);
                editor2.txt.html(chapter.content);
                $("#id").val(chapter.id);
            }else{
                alert(data.msg);
            }
        }, 'json');
    }

</script>

</html>
