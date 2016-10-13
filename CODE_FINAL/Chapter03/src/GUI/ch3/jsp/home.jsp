<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title> Spring DM HelloWorld </title> 
</head>
<body>
<p> Results from accesing Spring-DM service bundle: </p>
<p> Message: <c:out value="${helloworld.message}"/> </p>
<p> Current Time: <c:out value="${helloworld.currentTime}"/> </p>
<p> Model Version: <c:out value="${helloworld.modelVersion}"/> </p>
</body>
