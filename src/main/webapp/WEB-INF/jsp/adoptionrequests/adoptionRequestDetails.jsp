<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="adoptionDetails">

	<h2>Adoption Details</h2>

	<table class="table table-striped">
		<tr>
			<th>Owner Name</th>
			<td><b><c:out value="${owner.firstName} ${owner.lastName}" /></b></td>
		</tr>
		<tr>
			<th>Address</th>
			<td><c:out value="${owner.address}" /></td>
		</tr>
		<tr>
			<th>City</th>
			<td><c:out value="${owner.city}" /></td>
		</tr>
		<tr>
			<th>Telephone</th>
			<td><c:out value="${owner.telephone}" /></td>
		</tr>
	</table>
	<c:if test="${loggedUsername == userName || isAdmin}">
		<spring:url value="{ownerId}/edit" var="editUrl">
			<spring:param name="ownerId" value="${owner.id}" />
		</spring:url>
		<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit
			Owner</a>

		<spring:url value="{ownerId}/pets/new" var="addUrl">
			<spring:param name="ownerId" value="${owner.id}" />
		</spring:url>
		<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New
			Pet</a>
		<div style="float:right">
		<form:form method="post">
			<button type="submit"  class="btn btn-default" name="postDeleteAccount">Delete
				Account</button>
		</form:form></div>
	</c:if>
	<br />
	<br />
	<br />
	<h2>Pets and Visits</h2>

	<table class="table table-striped">
		<c:forEach var="pet" items="${owner.pets}">

			<tr>
				<td valign="top">
					<dl class="dl-horizontal">
						<dt>Name</dt>
						<dd>
							<c:out value="${pet.name}" />
						</dd>
						<dt>Birth Date</dt>
						<dd>
							<petclinic:localDate date="${pet.birthDate}" pattern="yyyy-MM-dd" />
						</dd>
						<dt>Type</dt>
						<dd>
							<c:out value="${pet.type.name}" />
						</dd>
					</dl>
				</td>
				<td valign="top">
					<table class="table-condensed">
						<thead>
							<tr>
								<th>Visit Date</th>
								<th>Description</th>
							</tr>
						</thead>
						<c:forEach var="visit" items="${pet.visits}">
							<tr>
								<td><petclinic:localDate date="${visit.date}"
										pattern="yyyy-MM-dd" /></td>
								<td><c:out value="${visit.description}" /></td>
							</tr>
						</c:forEach>
						<tr>
							<c:if test="${loggedUsername == userName || isAdmin}">
								<td><spring:url value="/owners/{ownerId}/pets/{petId}/edit"
										var="petUrl">
										<spring:param name="ownerId" value="${owner.id}" />
										<spring:param name="petId" value="${pet.id}" />

									</spring:url> <a href="${fn:escapeXml(petUrl)}">Edit Pet</a></td>

								<form:form method="post">
									<input type="hidden" name="petId" value="${pet.id}">
									<button type="submit"  class="btn btn-default" name="postDeletePet">Delete Pet</button>
								</form:form>

								<td><spring:url
										value="/owners/{ownerId}/pets/{petId}/visits/new"
										var="visitUrl">
										<spring:param name="ownerId" value="${owner.id}" />
										<spring:param name="petId" value="${pet.id}" />
									</spring:url> <a href="${fn:escapeXml(visitUrl)}">Add Visit</a></td>
							</c:if>
						</tr>
					</table>
				</td>
			</tr>

		</c:forEach>
	</table>

</petclinic:layout>
