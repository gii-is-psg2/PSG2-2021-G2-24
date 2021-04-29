<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<petclinic:layout pageName="adoptionRequests">
	<h2>
	 	List Notifications
	</h2>

	<table id="adoptionTable" class="table table-striped">
		<thead id="psg2Head">
			<tr>
				<th style="width: 150px;">Sender</th>
				<th style="width: 200px;">Notification Message</th>
				<th style="width: 200px;">Receiver</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${notifications}" var="notification">
				<tr>
					<td><c:out
							value="${notification.response.adoptionrequest.owner.firstName} ${notification.response.adoptionrequest.owner.lastName}" />
					</td>
					<td><c:out value="${notification.message}" /></td>
					<td><c:out value="${notification.response.owner.firstName} ${notification.response.owner.lastName}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
</petclinic:layout>