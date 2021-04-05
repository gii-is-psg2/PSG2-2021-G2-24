<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<!-- %@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %-->  

<petclinic:layout pageName="home">
<div class="welcomePage"  style="text-align: center;">
 <!--     <div style="padding: 10px; margin-top:-30px">
    	<a class="btn btn-default" href="?lang=es">Español</a>
        <a class="btn btn-default" href="?lang=en">English</a>
    </div> -->
    <h2><fmt:message key="welcome"/></h2>
        <div class="div">
            <spring:url value="/resources/images/loro.jpg" htmlEscape="true" var="petsImage"/>
            <img class="d-flex justify-content-center img-fluid" style="max-width:100%" src="${petsImage}"/>
        </div>      
    </div>
   
</petclinic:layout>
