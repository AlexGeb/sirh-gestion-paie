<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="../includes/header.jsp"%>
<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="container">
		<h1>liste des employés</h1>
		<table class="table table-bordered">
			<thead>
				<tr>
					<th>Matricule</th>
					<th>Grade</th>
					<th>Entreprise</th>
					<th>Profil</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="employe" items="${employes}">
					<tr>
						<td>${ employe.matricule }</td>
						<td>${ employe.grade.code }</td>
						<td>${ employe.entreprise.denomination }</td>
						<td>${ employe.profilRemuneration.code }</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
</body>