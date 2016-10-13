<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
 <div id="person">
  <div style="border:red 2px dashed;margin:10px;padding:5px;">
   <b>Name</b> :<c:out value="${translator.firstName}"/> <c:out value="${translator.lastName}"/><br/>
   <b>Hourly Rate</b> : $<c:out value="${translator.hourlyRate}"/><br/>
  </div>
