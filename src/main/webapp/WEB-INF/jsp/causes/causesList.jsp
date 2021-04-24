<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="causes">
    <h2>Causes

		<spring:url value="/causas/new" var="causaNewUrl">
		</spring:url>
		<a href="${fn:escapeXml(causaNewUrl)}">Add Cause</a>
	</h2>
	<table id="causasTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Name</th>
				<th style="width: 200px;">Total Donation</th>
				<th style="width: 200px;">Budget Target</th>



			</tr>
		</thead>
        <tbody>
        <c:forEach items="${causas}" var="causa">
            <tr>
				<td><c:out value="${causa.name}" /></td>
				<td><c:out value="${causa.totalDonation}" /></td>
				<td><c:out value="${causa.budgetTarget}" /></td>            
     
 
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
