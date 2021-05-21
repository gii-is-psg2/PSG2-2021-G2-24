<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<petclinic:layout pageName="contact">
	<div id="central">
		<div class="content">
			<h1>Contact Information</h1>
			<p>Please do not hesitate to contact us, we are always open to receive feeback! :D</p>
			<div><br>
					<div><h4><strong>Company name </strong>: Pet Hotels.</h4></div>
					<div><h4><strong>Email </strong>: <a href = "https://gmail.com/">pethotels@gmail.com</a></h4></div>
					<div><h4><strong>Phone number </strong>: +34 959399456</h4></div>
			</div>
		</div>
	</div>

</petclinic:layout>