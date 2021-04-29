<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<petclinic:layout pageName="adoptionRequests">
	<h2>List Responses</h2>

	<table id="adoptionTable" class="table table-striped">
		<thead id="psg2Head">
			<tr>
				<th style="width: 150px;">Owner's Name</th>
				<th style="width: 200px;">Pet's name</th>
				<th style="width: 200px;">Description</th>
				<th style="width: 120px">Accept</th>
				<th style="width: 120px">Decline</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${adoptionrequestresponses}"
				var="adoptionrequestresponse">
				<tr>
					<td><c:out
							value="${adoptionrequestresponse.owner.firstName} ${adoptionrequestresponse.owner.lastName}" />
					</td>
					<td><c:out
							value="${adoptionrequestresponse.adoptionrequest.pet.name}" /></td>
					<td><c:out value="${adoptionrequestresponse.description}" /></td>
					<td><form:form method="post">
							<input type="hidden" name="adoptionrequestresponseId"
								value="${adoptionrequestresponse.id}">
							<input type="hidden" name="accepted" value="true">
							<button type="submit" class="btn btn-default"
								name="postDeleteVet">Accept</button>
						</form:form></td>
					<td><form:form method="post">
							<input type="hidden" name="adoptionrequestresponseId"
								value="${adoptionrequestresponse.id}">
							<input type="hidden" name="accepted" value="false">
							<button type="submit" class="btn btn-default"
								name="postDeleteVet">Decline</button>
						</form:form></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>