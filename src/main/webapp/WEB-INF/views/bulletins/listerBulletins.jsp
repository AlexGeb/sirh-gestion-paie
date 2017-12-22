<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="../includes/header.jsp"%>
<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="container">
		<div class="jumbotron">
			<h1 class="display-3">Liste des Bulletins de Salaire</h1>
			<hr class="my-4">
			<a class="btn btn-primary float-right" href="<c:url value='creer'/>">Ajouter</a>
			<table class="table table-bordered">
				<thead>
					<tr>
						<th>Periode</th>
						<th>matricule</th>
						<th>Salaire brut</th>
						<th>Net Imposable</th>
						<th>Net à Payer</th>
						<th>Actions</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="bull" items="${bulletins_resultats}">
						<tr>
							<td>${ bull.key.periode.periodeString }</td>
							<td>${ bull.key.remunerationEmploye.matricule }</td>
							<td>${ bull.value.salaireBrut }</td>
							<td>${ bull.value.netImposable }</td>
							<td>${ bull.value.netAPayer }</td>
							<td><a href="<c:url value='visualiser/${bull.key.id}'/>">visualiser</a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>