<%--
  Created by IntelliJ IDEA.
  User: mac
  Date: 2018/12/1
  Time: 下午5:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
  <head>
    <meta charset="UTF-8">
    <title>AIOps异常检测</title>
    <link rel="stylesheet" href="style.css"/>
  </head>
  <body>

  <div id="headline">
    <span id="headTag1">AIOps</span>
    <span id="headTag2">异常视图</span>
    <span id="headTag3">样本库</span>
  </div>

  <!--
  <div id="sidebar">
    <div id="sideTag1">异常视图</div>
    <span id="sideTag2">异常查询</span>
  </div>
  -->

  <div class="flex-container">
    <div class="flex-sidebar">
      <div id="sideTag1">异常视图</div>
      <div id="sideTag2">异常查询</div>
    </div>
    <div class="flex-content">
      <div class="flex-container-column">
        <div class="flex-h2line">异常查询</div>
        <div class="flex-h2content">
          <p>样本视图</p>
          <img src="python/pyculiarity/<%=(String)session.getAttribute("imgPath")%>"/>
        </div>
      </div>
    </div>
  </div>



  </body>

</html>
