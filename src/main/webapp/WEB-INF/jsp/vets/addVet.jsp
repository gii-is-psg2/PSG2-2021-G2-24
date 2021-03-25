<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>


<petclinic:layout pageName="add vets">
	
        <h2>New Vet</h2>


        <form:form modelAttribute="vet" class="form-horizontal" action="/vets/save">
            <div class="form-group has-feedback">
                <petclinic:inputField label="First Name" name="firstName"/>
                <div class="control-group">
                    <petclinic:inputField label="Last Name" name="lastName"/>
                </div>
                <div class="control-group">
                	<petclinic:selectField label="Specialties" name="specialties" names="${specialties}" size="3"/>
                </div>
                 
                
             
       
            </div>

            <div class="form-group">
                <div class="col-sm-offset-2 col-sm-10">
                    <input type="hidden" name="vetId" value="${vet.id}"/>
                    <button class="btn btn-default" type="submit">Add Vet</button>
                </div>
            </div>
        </form:form>


  

</petclinic:layout>