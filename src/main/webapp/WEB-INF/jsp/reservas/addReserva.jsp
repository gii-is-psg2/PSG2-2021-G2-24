<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>



<petclinic:layout pageName="reservas">
	<jsp:attribute name="customScript">
		<script>
			$(function () {
				$("#startDate").datepicker({dateFormat: 'yy-mm-dd'});
				$("#endingDate").datepicker({dateFormat: 'yy-mm-dd'});
			});
		</script>
	</jsp:attribute>
    <jsp:body>
        <h2>Añadir Reserva</h2>


        <form:form modelAttribute="reserva" class="form-horizontal" action="/reservas">
            <div class="form-group has-feedback">
            	<div class="control-group">
                     <petclinic:selectField name="owner.user.username" label="Username" names="${usernames}"  size="5"/>
              
                <div class="control-group">
                    <petclinic:selectField name="pet.name" label="Mascota" names="${pets}" size="5"/>
                </div>
                <div class="control-group">
                    <petclinic:selectField name="room" label="Habitacion" names="${rooms}" size="5"/>
                </div>
                <div class="control-group">
                    <petclinic:inputField label="Start Date" name="startDate"/>
                </div>
                <div class="control-group">
                    <petclinic:inputField label="Ending Date" name="endingDate"/>
                </div>
               

            </div>
			</div>	
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="reservaId" value="${reserva.id}"/>
                    <button class="btn btn-default" type="submit">Add Reserva</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>