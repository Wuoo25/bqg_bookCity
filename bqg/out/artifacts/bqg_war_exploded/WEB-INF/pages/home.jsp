<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>首页</title>
    <style>
        body{
            width: 80%;
            margin: auto;
        }
        #search{
            margin: 10px auto;
            text-align: right;
        }
        #search input{
            width: 90%;
        }
        #types{
            margin: 10px 0px;
            background-color: aliceblue;
            padding: 0px 5px;
        }

        #types a{
            margin: auto 5px;
        }
        .recommend{
            margin-bottom: 10px;
        }
        .type{
            background-color: aquamarine;
            padding: 0px 5px;
        }
        .opuses{
            border: 1px solid black;

        }
        .opus{
            display: inline-block;
            width: 150px;
        }
        .opus img{
            width: 150px;
        }
        .opus div{
            text-align: center;
        }

        .book{
            border: 1px solid;
            width: 100%;
            margin: 10px auto;
        }
        .book img{
            width: 20%;
            vertical-align: top;
        }
        .bookIntro{
            display: inline-block;
            width: 75%;
            margin-left: 15px;
        }
        .bookIntro div{
            margin-bottom: 10px;
        }
        .pages{
            text-align: center;
        }
        .pages button{
            margin: auto 10px;
        }
    </style>
</head>
<body>
<div>
    <c:if test="${login==null}">
        游客, 您好
        <a href="/bqg/user/toLogin">登录</a> &nbsp;
        <a href="/bqg/user/toRegister">注册</a>
    </c:if>
    <c:if test="${login!=null}">
        ${login.nickName}
        <a href="/bqg/user/logout">退出</a>
    </c:if>
    <a href="/bqg/bookshelf/toBookShelf">我的书架</a>
</div>
<hr>
<div>
    <div id="search">
        <input id="name"> <button onclick="getList()">搜索</button>
    </div>
    <input type="hidden" id="typeId" value="0">
    <div id="types">
        <a href="#">武侠</a>
        <a href="#">武侠</a>
        <a href="#">武侠</a>
        <a href="#">武侠</a>
    </div>
    <div id="content">
        <div class="recommend">
            <div class="type">武侠</div>
            <div class="opuses">
                <div class="opus">
                    <img src="https://www.biqooge.com/files/article/image/46/46319/46319s.jpg">
                    <div>
                        一念成仙
                    </div>
                </div>
                <div class="opus">
                    <img src="https://tse1-mm.cn.bing.net/th/id/OIP-C.NXINrsyTaRA7WFI0UzCOXwAAAA?w=126&h=189&c=7&o=5&dpr=1.67&pid=1.7">
                    <div>
                        百年孤独
                    </div>
                </div>
            </div>
        </div>

    </div>

</div>
</body>
<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
<script>
    $(function () {
        getTypes();
        getRecommend();
    })

    function checkType(typeId,th) {
        $("#typeId").val(typeId);
        $("types a").css('color','blue')
        if(th!=undefined){
            th.style.color='red';
        }

        getList();
    }
    function getList(pageNo) {
        var name = $("#name").val();
        var typeId = $("#typeId").val();


        $.post('/bqg/opus/getList', {'name': name,'typeId':typeId,'pageNo':pageNo}, function (data) {
            if (data.code == 1) {
                var pageData = data.data;
                var list = pageData.list;

                var html = '';
                list.forEach(function (item) {
                    html += '<div class="book">' +
                        '<a href="/bqg/opus/toOpus?opusId='+item.id+'"><img src="/bqg/img/getImg?imgName='+item.icon+'"></a>' +
                        '<div class="bookIntro">' +
                        '<div> 作品名称 : '+item.name+'</div>'+
                        '<div> 作品类型 : '+item.typeName+'</div>'+
                        '<div> 创建时间 : '+item.createDate+'</div>'+
                        '<div> 作品简介 : '+item.intro+'</div>'+
                        '</div></div>'
                })

                html += '<div class="pages">';
                var pages = Math.ceil(pageData.count/pageData.pageSize);
                for (let i = 1; i <=pages ; i++) {
                    if(i==pageData.pageNo){
                        html += '<button style="background-color: blue">'+i+'</button>';
                    }else{
                        html += '<button onclick="getList('+i+')">'+i+'</button>';
                    }
                }
                html+='</div>'

                $("#content").html(html);

            }else{
                alert(data.msg);
            }

        },'json');
    }


    //获取推荐的作品
    function getRecommend() {
        $.post('/bqg/opus/recommend',{},function(data){
            if (data.code == 1) {
                var list = data.data;//[[{},{}],[{},{}],[]]
                var html = '';
                list.forEach(function(item){
                    if (item.length > 0) {
                        html += '<div class="recommend">'
                            +'<div class="type">'+item[0].typeName+'</div>'
                            +'<div class="opuses">';
                        item.forEach(function (opus){
                            html+='<div class="opus">';
                            html+='<a href="/bqg/opus/toOpus?opusId='+opus.id+'"><img src="/bqg/img/getImg?imgName='+opus.icon+'"></a>';
                            html+='    <div>';
                            html+= opus.name;
                            html+= ' </div>';
                            html+='</div>';;
                        });
                        html+=' </div>'
                        +'  </div>';
                    }
                })
                $("#content").html(html);
            }else{
                alert(data.msg)
            }

        },'json')
    }

    //获取所有的类型,并展示在页面中
    function getTypes() {

        $.post('/bqg/type/getListAll', {}, function (data) {
            if (data.code == 1) {
                //如果获取到了所有的类型,将类型拼接放到页面中
                var html = '<a onclick="checkType(0)" href="javascript:void(0)">全部</a>';
                var list = data.data;
                list.forEach(function (item) {
                    html+='<a onclick="checkType('+item.id+',this)" href="javascript:void(0)">'+item.name+'</a>'
                });
                $("#types").html(html);
            }else{
                alert(data.msg);
            }
        }, 'json');
    }

</script>
</html>