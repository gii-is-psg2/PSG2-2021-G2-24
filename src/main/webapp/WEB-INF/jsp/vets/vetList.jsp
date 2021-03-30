<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<petclinic:layout pageName="vets">
	<h2>
		Veterinarians
		<c:if test="${isAdmin}">
			<spring:url value="/vets/new" var="vetNewUrl">
			</spring:url>
			<a href="${fn:escapeXml(vetNewUrl)}">Add Vet</a>
		</c:if>
	</h2>
	<table id="vetsTable" class="table table-striped">

		<thead>

			<tr>
				<th>Name</th>
				<th>Specialties</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${vets.vetList}" var="vet">
				<tr>
					<td><c:out value="${vet.firstName} ${vet.lastName}" /></td>
					<td><c:forEach var="specialty" items="${vet.specialties}">
							<c:out value="${specialty.name} " />
						</c:forEach> <c:if test="${vet.nrOfSpecialties == 0}">none</c:if> <c:if
							test="${isAdmin}">
							<form:form method="post">
								<input type="hidden" name="vetId" value="${vet.id}">
								<button type="submit" name="postDeleteVet">Delete Vet</button>
							</form:form>
							<spring:url value="/vets/{vetId}/edit" var="vetEditUrl">
								<spring:param name="vetId" value="${vet.id}" />
							</spring:url>
							<a href="${fn:escapeXml(vetEditUrl)}">Edit Vet</a>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<table class="table-buttons">
		<tr>
			<td><a href="<spring:url value="/vets.xml" htmlEscape="true" />">View
					as XML</a></td>
		</tr>
	</table>
</petclinic:layout>
