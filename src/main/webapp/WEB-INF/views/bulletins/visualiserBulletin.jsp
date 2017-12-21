<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="../includes/header.jsp"%>
<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="container">
		<h1 class="display-3">Bulletin de Salaire</h1>
		<hr class="my-4">
		<div class=row>
			<div class=col>
				<h5>Entreprise</h5>
				${bulletin.remunerationEmploye.entreprise.denomination } <br>
				siret : ${bulletin.remunerationEmploye.entreprise.siret }
			</div>
			<div class=col>
				<h5>Période</h5>
				Du ${bulletin.periode.dateDebut } au ${bulletin.periode.dateFin }
				<h5>Matricule ${ bulletin.remunerationEmploye.matricule}</h5>
			</div>
		</div>
		<div class="row">
			<h5>Salaire</h5>
			<table class="table table-striped">
				<%@include file="../includes/thead.jsp"%>
				<tbody>
					<c:forEach items="${salaire}" var="item">
						<tr>
							<td>${ item.rubrique }</td>
							<td>${ item.base }</td>
							<td>${ item.tauxSalarial}</td>
							<td>${ item.montantSalarial }</td>
							<td>${ item.tauxPatronal }</td>
							<td>${ item.cotisationsPatronales }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h5>Cotisations</h5>
			<table class="table table-striped">
				<%@include file="../includes/thead.jsp"%>
				<tbody>
					<c:forEach items="${nonImposables}" var="item">
						<tr>
							<td>${ item.rubrique }</td>
							<td>${ item.base }</td>
							<td>${ item.tauxSalarial}</td>
							<td>${ item.montantSalarial }</td>
							<td>${ item.tauxPatronal }</td>
							<td>${ item.cotisationsPatronales }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h5>NET Imposable : ${resultat.netImposable}</h5>
			<table class="table table-striped">
				<%@include file="../includes/thead.jsp"%>
				<tbody>
					<c:forEach items="${imposables}" var="item">
						<tr>
							<td>${ item.rubrique }</td>
							<td>${ item.base }</td>
							<td>${ item.tauxSalarial}</td>
							<td>${ item.montantSalarial }</td>
							<td>${ item.tauxPatronal }</td>
							<td>${ item.cotisationsPatronales }</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
		<div class="row">
			<h5>NET A Payer ${resultat.netAPayer }</h5>
		</div>

	</div>
</body>