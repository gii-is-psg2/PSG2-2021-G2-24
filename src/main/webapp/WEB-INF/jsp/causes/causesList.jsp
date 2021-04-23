<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2>Causes</h2>

    <table id="causesTable"	 class="table table-striped">
        <thead id="psg2Head">
        <tr>
            <th style="width: 150px;">Name</th>
            <th>Total Donations</th>
            <th>Budget Target</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${causes}" var="causes">
            <tr>
                <td>
                    <spring:url value="/causes/{causesId}" var="causesUrl">
                        <spring:param name="causesId" value="${causes.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(causesUrl)}"><c:out value="${causes.name}"/></a>
                </td>
                <td>
                    <c:out value="${causes.total_donations}"/>
                </td>
                <td>
                    <c:out value="${causes.budgetTarget_id}"/>
                </td>              
      
<!--
                <td> 
                    <c:out value="${owner.user.username}"/> 
                </td>
                <td> 
                   <c:out value="${owner.user.password}"/> 
                </td> 
-->
                
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
