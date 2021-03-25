<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="reservas">
    <h2>Reservas</h2>

    <table id="reservasTable" class="table table-striped">
        <thead>
        <tr>
        	<th style="width: 150px;">Id</th>
            <th style="width: 150px;">Owner's First Name</th>
            <th style="width: 200px;">Owner's Last Name </th>
            <th style="width: 200px;">Pet </th>
            <th style="width: 200px;">Room</th>
            <th style="width: 200px;">Start Date</th>
            <th style="width: 200px;">Ending Date</th>
        
            
     
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${reservas}" var="reserva">
            <tr>
            	<td>
            		<c:out value="${reserva.id}"/>
            	</td>
                <td>
                    <c:out value="${reserva.owner.firstName}"/>
                </td>
                <td>
                    <c:out value="${reserva.owner.lastName}"/>
                </td>
                <td>
                    <c:out value="${reserva.pet}"/>
                </td>
                <td>
                    <c:out value="${reserva.room.id}"/>
                </td>
                 <td>
                    <c:out value="${reserva.startDate}"/>
                </td>
                 <td>
                    <c:out value="${reserva.endingDate}"/>
                </td>
          
            </tr>
        </c:forEach>
        </tbody>
    </table>
    
	
	
    
</petclinic:layout>