<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causes">
	<h2>
		Causes

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
				<th style="width: 200px;">Status</th>
				<th style="width: 200px;">Donate to this Cause</th>
				<th style="width: 200px;">Details</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${causas}" var="causa">
				<tr>
					<td><c:out value="${causa.name}" /></td>
					<td><c:out value="${causa.totalDonation}" /></td>
					<td><c:out value="${causa.budgetTarget}" /></td>
					<c:if test="${causa.closed}">
						<td><c:out value="Close" /></td>
						<td><c:out value="Currently Closed" /></td>
					</c:if>
					<c:if test="${causa.closed == false}">
						<td><c:out value="Open" /></td>
						<td><spring:url value="donations/{causaId}/new"
								var="donationURL">
								<spring:param name="causaId" value="${causa.id}" />
							</spring:url> <a href="${fn:escapeXml(donationURL)}">Donate</a></td>

					</c:if>

					<c:if test="${isAdmin == true}">
					<td><spring:url value="details/{causaId}" var="detailsURL">
							<spring:param name="causaId" value="${causa.id}" />
						</spring:url> <a href="${fn:escapeXml(detailsURL)}">Cause Details</a></td>
					</c:if>
					<c:if test="${(isAdmin == false) && (username == causa.owner.user.username)}">
					<td><spring:url value="details/{causaId}" var="detailsURL">
							<spring:param name="causaId" value="${causa.id}" />
						</spring:url> <a href="${fn:escapeXml(detailsURL)}">Cause Details</a></td>
					</c:if>
					<c:if test="${(isAdmin == false) && (username != causa.owner.user.username)}">
						<td><c:out value="Not available" /></td>
					</c:if>
				</tr>
			</c:forEach>
		</tbody>
	</table>



</petclinic:layout>
