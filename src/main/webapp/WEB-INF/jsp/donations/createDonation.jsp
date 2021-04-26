<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>



<petclinic:layout pageName="donations">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#sfechaDonacion").datepicker({dateFormat: 'yy-MM-dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Donacion</h2>


        <form:form modelAttribute="donation" class="form-horizontal" action="/donations">
            <div class="form-group has-feedback">
            	<div class="control-group">
                     <petclinic:selectField name="owner.user.username" label="Username" names="${usernames}"  size="5"/>
              
                <div class="control-group">
                    <petclinic:selectField name="causa.id" label="Causa" names="${causas}" size="5"/>
                </div>
                <div class="control-group">
                    <petclinic:inputField label="Importe" name="importeDonacion"/>
                </div>
                <div class="control-group">
                    <petclinic:inputField label="Fecha" name="fechaDonacion"/>
                </div>
               

            </div>
			</div>	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="donationId" value="${donation.id}"/>
                    <button class="btn btn-default" type="submit">Add Donation</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>