<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
        <title><tiles:getAsString name="title"/></title>
        <link href="/springhelloworld/css/hello.css" rel="stylesheet" type="text/css">
        <script type="text/javascript" src="<c:url value="/resources/dojo/dojo.js" />"> </script>
        <script type="text/javascript" src="<c:url value="/resources/spring/Spring.js" />"> </script>
        <script type="text/javascript" src="<c:url value="/resources/spring/Spring-Dojo.js" />"> </script>
</head>
<body>

<div id="wrap">
         <div id="header">
         <h1><tiles:insertAttribute name="header"/></h1>
         </div>

         <div id="content">
         <tiles:insertAttribute name="content"/>
         </div>
 
         <div id="footer">
         <h3><tiles:insertAttribute name="footer"/></h3>
         </div>
</div>
</body>
</html>
