<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN"
        "http://www.w3.org/TR/html4/strict.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
	<title>SpringSource Application Platform - Pro Spring-OSGi </title>
	<link href="../css/hello.css" rel="stylesheet" type="text/css">
</head>
<body>

<div id="wrap">
	 <div id="header">
         <h1>SpringSource Application Platform - Pro Spring-OSGi</h1>
         </div>

         <div id="content">
	   <div id="main">
		<h2>Hello World Messages </h2>
<div id="helloMessages">
 <c:forEach var="hello" items="${helloworlds}">
   <div style="border:red 2px dashed;margin:10px;padding:5px"> 
       <b><c:out value="${hello.language}"/></b>
       <c:out value="${hello.message}"/> - Transated on:  <fmt:formatDate value="${hello.transdate}" dateStyle="long" /> <br/>
       <a id="deleteMessage_${hello.id}" href="deleteMessage?id=${hello.id}">Delete Message </a>
       <br/>

   </div>
     </c:forEach>
 </div>

          </div>
	  <div id="sidebar">
            <h2>Translators </h2>
<div id="helloTranslators">
 <ul>
  <c:forEach var="hello" items="${helloworlds}">
       <li><c:out value="${hello.language}"/>
       <a id="showTranslator_${hello.id}" href="translator?id=${hello.id}">Translator Details </a>
  </c:forEach>
 </ul>
</div>
 <div id="person">
  <div style="border:red 2px dashed;margin:10px;padding:5px;"> 
   <b>Name</b> :<c:out value="${translator.firstName}"/> <c:out value="${translator.lastName}"/><br/>
   <b>Hourly Rate</b> : $<c:out value="${translator.hourlyRate}"/><br/>
  </div>
</div>            
  	   </div>
         </div>

	 <div id="footer">
         <h3>Pro SpringOSGi by Daniel Rubio - Published by Apress</h3>
 	 </div>
</div>
</body>
</html>

