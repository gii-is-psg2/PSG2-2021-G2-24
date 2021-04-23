<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>



<petclinic:layout pageName="adoptionrequests">

    <jsp:body>
        <h2>Formulario para dar en adopción</h2>


        <form:form modelAttribute="adoptionrequest" class="form-horizontal" action="/adoptionrequests">
            <div class="form-group has-feedback">
            	<div class="control-group">
                     <petclinic:selectField name="owner.user.username" label="Username" names="${usernames}"  size="5"/>
              	</div>
                <div class="control-group">
                    <petclinic:selectField name="pet.name" label="Mascota" names="${pets}" size="5"/>
               </div>
			
			</div>
            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="adoptionrequest" value="${adoptionrequest.id}"/>
                    <button class="btn btn-default" type="submit">Send</button>
                </div>
            </div>
        </form:form>

        
    </jsp:body>

</petclinic:layout>