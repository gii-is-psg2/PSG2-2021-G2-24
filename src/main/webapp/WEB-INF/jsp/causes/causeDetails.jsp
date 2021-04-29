
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causas">

	<h2>Cause Details</h2>
	
	<br />
		<key="name" />
	<h1>
		<c:out value="${causa.name}" />
	</h1>
	<br />
		<key="description" />
	<h1>
		<c:out value="${causa.descriptionCausa}" />
	</h1>
	<br />
		<key="budgetTarget" />
	<h4>
		<c:out value="${causa.budgetTarget}" />
	</h1>
	<br />
		<key="activeOrganization" />
	<h1>
		<c:out value="${causa.ActivenpOrganization}" />
	</h1>
	<br />
	

	<table id="donationsTable" class="table table-striped">
		<thead>
			<tr>
				<th style="width: 150px;">Owner</th>
				<th style="width: 150px;">Importe</th>
				
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${donations}" var="donation">
				<tr>
					<td><c:out value="${donation.owner}" /></td>
					<td><c:out value="${donation.importeDonacion}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>



</petclinic:layout>