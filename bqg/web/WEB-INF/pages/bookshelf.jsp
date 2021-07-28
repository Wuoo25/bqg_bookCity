<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>我的书架</title>
    <base href="/bqg/">
    <style>
        body{
            width: 80%;
            margin: auto;
        }
        #content{
            width: 100%;
        }
        #content img{
            width: 100px;
        }
    </style>
</head>
<body>
    <div>
        <a href="user/toHome">主页</a>
    </div>
    <hr>
    <input type="checkbox" id="checkAll" > <button onclick="deleteBatch()">移除选中</button>
    <table id="content">

    </table>
</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/wangeditor@latest/dist/wangEditor.min.js"></script>

<script>

    var chapterId;
    $(function(){
        //获取作品信息
        getBookShelf();

        $("#checkAll").click(function(){
            $(".checkOne").prop('checked',$(this).prop('checked'));
        })
    })

    function checkOne() {
        if($(".checkOne").length==$(".checkOne:checked").length){
            $("#checkAll").prop("checked", true);
        }else{
            $("#checkAll").prop("checked", false);
        }
    }
    //移除选中的多本图书
    function deleteBatch(){
        var arr = new Array();
        $(".checkOne:checked").each(function () {
            arr.push($(this).val())
        });

        $.ajax({
            url:'bookshelf/deleteBatch',
            data:{'ids':arr},
            dataType: "json",
            traditional:true,
            success: function (data) {
                if (data.code == 1) {
                    location.reload();
                }else{
                    alert(data.msg);
                }
            }
        })
    }

    //移除一个图书
    function deleteBook(id) {
        $.post('bookshelf/delete', {'id':id}, function (data) {
            if (data.code == 1) {
                getBookShelf();
            }else{
                alert(data.msg);
            }
        }, 'json');
    }
    function getBookShelf() {
        $.post('bookshelf/getList', {}, function (data) {
            if (data.code == 1) {
                var list = data.data;
                var html = "";
                list.forEach(function (item) {
                   html += '<tr>' +
                       '<td><input value="'+item.id+'" onclick="checkOne()" class="checkOne" type="checkbox"></td>'+
                       '<td><a href="opus/toOpus?opusId='+item.opusId+'"><img src="img/getImg?imgName='+item.icon+'"></a></td>' +
                       '<td>'+item.name+'</td>' +
                       '<td><button onclick="deleteBook('+item.id+')">移除</button></td>' +
                       '</tr>'
                });
                $("#content").html(html);
            }else{
                alert(data.msg);
            }
        }, 'json');
    }

</script>
</html>
