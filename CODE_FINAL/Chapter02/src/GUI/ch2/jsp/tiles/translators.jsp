<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div id="helloTranslators">
 <ul>
  <c:forEach var="hello" items="${helloworlds}">
       <li><c:out value="${hello.language}"/>
       <a id="showTranslator_${hello.id}" href="translator?id=${hello.id}">Translator Details </a>
       <script type="text/javascript">
                Spring.addDecoration(new Spring.AjaxEventDecoration({
                elementId:"showTranslator_${hello.id}",
                event:"onclick",
                params: {fragments:"translator"}
        }));
       </script></li>
  </c:forEach>
 </ul>
</div>
