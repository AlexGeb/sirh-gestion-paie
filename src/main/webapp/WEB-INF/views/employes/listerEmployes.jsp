<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="../includes/header.jsp"%>
<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="container">
		<div class="jumbotron">
			<h1 class="display-3">Liste des Employés</h1>
			<hr class="my-4">
			<a class="btn btn-primary float-right" href="<c:url value='creer'/>">Ajouter</a>
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
	</div>
</body>