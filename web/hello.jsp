<%--
  Created by IntelliJ IDEA.
  User: zyz
  Date: 2019/12/9
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <title>$Title$</title>
    <script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>

  </head>
  <body>
  ${message}
<button id="hello">123</button>
  $END$
  </body>
<script>
  var d={
    value: '123@qq.com',
    type:1
  }

  $("#hello").click(function () {
    axios.post('http://localhost:8080/FileStorage_Web_exploded/user/checkrepeat', d,headers:{'Content-Type':'application/x-www-form-urlencoded'}).then(function (response) {
              console.log(response);
    }).catch(function (error) {
      console.log(error);
    });
  });
</script>
</html>
