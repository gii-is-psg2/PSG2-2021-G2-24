<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<br />
<br />
<div class="container">
	<div class="row">
		<div class="col-12 text-center">
			<img height="150px"
				src="<spring:url value="/resources/images/logopsgus.png" htmlEscape="true" />"
				alt="Sponsored by Pivotal" />
		</div>
	</div>
	<spring:url value="/contact" var="contact">
	</spring:url>
	<a href="${fn:escapeXml(contact)}"><strong>Contact us</strong></a>
	
	<p></p>
	
	<spring:url value="/terms_and_conditions" var="terms_and_conditions">
	</spring:url>
	<a href="${fn:escapeXml(terms_and_conditions)}"><strong>Terms and Conditions</strong></a>
	
</div>


	