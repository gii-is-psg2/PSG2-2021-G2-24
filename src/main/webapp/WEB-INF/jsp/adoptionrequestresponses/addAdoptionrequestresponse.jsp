<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags"%>


<petclinic:layout pageName="adoptionrequests">

    <jsp:body>
        <h2>Formulario para dar aplicar a la adopción</h2>


        <form:form modelAttribute="adoptionrequestresponse" class="form-horizontal" action="/adoptionrequests">
            <div class="form-group has-feedback">
            	<div class="control-group">
                     <petclinic:selectField name="ownerrequest.user.username" label="Username" names="${usernames}"  size="5"/>
              	</div>
                <div class="control-group">
                    <petclinic:selectField name="petrequest.name" label="Mascota" names="${pets}" size="5"/>
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