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
        #title{
            font-size: 20px;
            text-align: center;
        }
    </style>
</head>
<body>
    <div>
        <a href="user/toHome">主页</a>
    </div>
    <hr>
    <div id="content">
        <div id='title'></div>
        <div id="chapterContent"></div>
    </div>

</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/wangeditor@latest/dist/wangEditor.min.js"></script>

<script>

    var chapterId;
    $(function(){
        //从地址栏中截取作品id信息
        chapterId = location.href.substring(location.href.lastIndexOf("=") + 1);

        //获取作品信息
        getChapter();
    })

    function getChapter() {
        $.post('chapter/get', {'id': chapterId}, function (data) {

            if (data.code == 1) {
                var chapter = data.data;
                $("#title").text(chapter.name);
                $("#chapterContent").html(chapter.content);
            }else{
                alert(data.msg);
            }
        }, 'json');
    }

</script>
</html>
