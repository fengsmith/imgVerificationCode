<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2015/12/16
  Time: 9:54
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <script type="text/javascript" src="http://apps.bdimg.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript">
        function refreshCode() {
            var id = document.getElementById("imgVerifyCode");
            var randomData = new Date();
            // 参数添加一个随机数，如果不添加的话，浏览器会误以为图片没有发生变化，并没有向服务器发送请求，所以就不会产生新的图片验证码了
            id.src = "${pageContext.request.contextPath}/generateImgVerificationCode?date" + randomData.getMilliseconds();
        }

        function verifyImageVerificationCode() {
            var verifyCode = $("#imgVerifyCode_input").val();
            if (!verifyCode) {
                return;
            }
            if (verifyCode.length != 4) {
                alert("验证码不正确");
                return false;
            }
            $.ajax({
                type: "POST",
                url: "${pageContext.request.contextPath}/ajaxVerifyImageVerificationCode",
                data: {
                    "imageVerifyCode": verifyCode
                },
                success: function (data) {
                    if (!data.result) {
                        $("验证码不正确");
                    } else {
                        alert("验证码正确");

                    }
                }
            });
        }


    </script>
</head>
<body>
<form method="post" action="login">
    登录名<input type="text" name="loginName" value="user">
    密码<input type="text" name="password" value="111111">
    验证码<input type="text" name="imgVerifyCode" id="imgVerifyCode_input" onblur="verifyImageVerificationCode();">

    <img id="imgVerifyCode" src="${pageContext.request.contextPath}/generateImgVerificationCode"/>
    <a onclick="refreshCode();">换一张</a>
    <input type="submit" value="登录">
</form>
</body>
</html>
