<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 <div id="helloMessages">
 <c:forEach var="hello" items="${helloworlds}">
   <div style="border:red 2px dashed;margin:10px;padding:5px">
       <b><c:out value="${hello.language}"/></b>
       <c:out value="${hello.message}"/> - Transated on:  <fmt:formatDate value="${hello.transdate}" dateStyle="long" /> <br/>
       <a id="deleteMessage_${hello.id}" href="deleteMessage?id=${hello.id}">Delete Message </a>
       <script type="text/javascript">
                Spring.addDecoration(new Spring.AjaxEventDecoration({
                elementId:"deleteMessage_${hello.id}",
                event:"onclick",
                params: {fragments:"helloMessages,helloTranslators"}
        }));
       </script>
       <br/>

   </div>
 </c:forEach>
</div>
