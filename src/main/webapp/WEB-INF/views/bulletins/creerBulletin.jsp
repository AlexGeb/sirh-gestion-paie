<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>
<%@include file="../includes/header.jsp"%>
<body>
	<%@include file="../includes/navbar.jsp"%>
	<div class="container">
		<div class="jumbotron">
			<h1 class="display-3">Ajouter un bulletin de salaire</h1>
			<hr class="my-4">
			<form:form method="post" modelAttribute="bulletinSalaire">
				<div class="form-group">
					<label for="periode">Periode</label> <select id="periode"
						name="periode_id">  
						<c:forEach var="periode" items="${periodes}">
							<option value="${periode.id}">${periode.periodeString}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="employe">Matricule</label> <select id="employe"
						name="employe_id">
						<c:forEach var="employe" items="${employes}">
							<option value="${employe.id}">${employe.matricule}</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label for="prime">Prime exceptionnelle</label>
					<form:input id="prime" path="primeExceptionnelle" value="0" />
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