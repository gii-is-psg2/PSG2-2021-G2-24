
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>

<petclinic:layout pageName="causas">

	<h2>Cause Details</h2>
	
	<br />
	<h1>
		<c:out value="Name: ${causa.name}" />
	</h1>
	<br />
	<h2>
		<c:out value="Description: ${causa.descriptionCausa}" />
	</h2>
	<br />
	<h1>
		<c:out value="budget target: ${causa.budgetTarget}" />
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
			<c:forEach items="${causa.donations}" var="donation">
				<tr>
					<td><c:out value="${donation.owner.firstName}" /></td>
					<td><c:out value="${donation.importeDonacion}" /></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>



</petclinic:layout>