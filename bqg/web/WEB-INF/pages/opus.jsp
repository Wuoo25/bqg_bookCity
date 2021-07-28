<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>作品详情</title>
    <base href="/bqg/">
    <style>
        body{
            width: 80%;
            margin: auto;
        }
        #opusContent img{
            width: 150px;
            vertical-align: top;
        }
        .opus{
            display: inline-block;
            margin-left: 10px;
            width: 80%;
        }
        .opus div{
            margin-bottom: 10px;
        }
        #list span{
            margin: 5px;
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
        &nbsp;<a href="javascript:void(0)" onclick="getChapterList('asc')">正序</a> &nbsp;
        <a href="javascript:void(0)" onclick="getChapterList('desc')">倒序</a>
    </div>
    <div id="list">

    </div>
</div>

</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/wangeditor@latest/dist/wangEditor.min.js"></script>

<script>

    var opusId;
    $(function(){
        //从地址栏中截取作品id信息
        opusId = location.href.substring(location.href.lastIndexOf("=") + 1);

        //获取作品信息
        getOpus();
        getChapterList();
    })

    function insertBook(opusId) {
        $.post('bookshelf/insert', {'opusId':opusId}, function (data) {
            if (data.code == -2) {
                location.href = 'user/toLogin';
                return;
            }

            alert(data.msg);

        }, 'json');
    }



    //查询当前作品的所有章节
    function getChapterList(order) {

        $.post('chapter/getList', {'opusId':opusId,'order':order}, function (data) {

            if (data.code == 1) {
                var list = data.data;

                var html = "";

                list.forEach(function (item) {
                    html +='<span><a href="chapter/toChapter?chapterId='+item.id+'">'+item.name+'</a></span>'
                });
                $("#list").html(html);

            }else{
                alert(data.msg);
            }
        }, 'json');

    }

    function getOpus() {
        $.post('opus/get', {'id': opusId}, function (data) {

            if (data.code == 1) {
                var opus = data.data;
                var html = '<img src="img/getImg?imgName='+opus.icon+'"></img>';
                html += '<div class="opus">' +
                    '<div> 作品名称 : '+opus.name+'</div>'+
                    '<div> 作品类型 : '+opus.typeName+'</div>'+
                    '<div> 创建时间 : '+opus.createDate+'</div>'+
                    '<div> 作品简介 : '+opus.intro+'</div>'+
                    '<div> <button onclick="insertBook('+opus.id+')">收藏</button></div>'+
                    '</div>'
                $("#opusContent").html(html);

            }else{
                alert(data.msg);
            }
        }, 'json');
    }

</script>
</html>
