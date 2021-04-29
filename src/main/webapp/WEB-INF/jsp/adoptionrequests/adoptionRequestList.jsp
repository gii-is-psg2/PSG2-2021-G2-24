<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<petclinic:layout pageName="adoptionRequests">
	<h2>
		Adoption Request
		<spring:url value="/adoptionrequests/new" var="AdoptionNewUrl">
		</spring:url>
		<a href="${fn:escapeXml(AdoptionNewUrl)}">Add Adoption</a>
	</h2>

	<table id="adoptionTable" class="table table-striped">
		<thead id="psg2Head">
			<tr>
				<th style="width: 150px;">Owner's Name</th>
				<th style="width: 200px;">Pet's name</th>
				<th style="width: 120px">Adopt</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adoptionRequests}" var="adoptionRequest">
				<tr>
					<td><c:out
							value="${adoptionRequest.owner.firstName} ${adoptionRequest.owner.lastName}" />
					</td>
					<td><c:out value="${adoptionRequest.pet.name}" /></td>
					<td><spring:url
							value="/adoptionrequestresponses/{adoptionRequestId}/new"
							var="adoptionRequestUrl">
							<spring:param name="adoptionRequestId"
								value="${adoptionRequest.id}" />
						</spring:url> <a href="${fn:escapeXml(adoptionRequestUrl)}"><c:out
								value="Adopt" /></a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>
