<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<br />
<br />
<div class="container">
	<div class="row">
		<div class="col-12 text-center">
			<img height="80px"
				src="<spring:url value="/resources/images/logopsgus.png" htmlEscape="true" />"
				alt="Sponsored by Pivotal" />
		</div>



	</div>
	<spring:url value="/contact" var="contact">
	</spring:url>
	<a href="${fn:escapeXml(contact)}"><strong>Contact us</strong></a>
</div>
