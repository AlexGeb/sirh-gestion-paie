<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="../includes/header.jsp"%>
<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="container">
		<div class="jumbotron">
			<h1 class="display-3">Créer un nouvel employé</h1>
			<hr class="my-4">
			<p>Préfixe Matricule : ${entreprises}</p>
			<form:form method="post" modelAttribute="remunerationEmploye">
				<div class="form-group">
					<label for="matricule">Matricule</label>
					<form:input id="matricule" path="matricule" />
				</div>
				<div class="form-group">
					<label for="entreprise">Entreprise</label>
					<form:select id="entreprise" path="entreprise">
						<form:options items="${entreprises}" itemLabel="denomination"
							itemValue="id" />
					</form:select>
				</div>
				<div class="form-group">
					<label for="profil">Profil</label>
					<form:select id="profil" path="profilRemuneration">
						<form:options items="${profils}" itemLabel="code" itemValue="id" />
					</form:select>
				</div>
			</form:form>
		</div>
	</div>


</body>