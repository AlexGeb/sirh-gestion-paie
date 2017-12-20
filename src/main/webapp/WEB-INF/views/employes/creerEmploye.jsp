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
			<form:form method="post" modelAttribute="remunerationEmploye">
				<div class="form-group">
					<label for="matricule">Matricule</label>
					<form:input id="matricule" path="matricule" />
				</div>
				<div class="form-group">
					<label for="entreprise">Entreprise</label> 
					<select id="entreprise" name="entreprise_id">
						<c:forEach var="entreprise" items="${entreprises}">
							<option value="${entreprise.id}">${entreprise.denomination}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="profil">Profil</label>
					<select id="profil" name="profil_id">
						<c:forEach var="profil" items="${profils}">
							<option value="${profil.id}">${profil.code}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="grade">Grade</label>
					<select id="grade" name="grade_id">
						<c:forEach var="grade" items="${grades}">
							<option value="${grade.id}">${grade.code}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group row">
					<div class="col-sm-10">
						<button type="submit" class="btn btn-primary">Ajouter</button>
					</div>
				</div>
			</form:form>
		</div>
	</div>


</body>