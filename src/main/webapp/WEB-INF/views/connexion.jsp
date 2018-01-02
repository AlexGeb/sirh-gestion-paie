<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec"
	uri="http://www.springframework.org/security/tags"%>
<!DOCTYPE html>
<html>
<%@include file="./includes/header.jsp"%>
<body class="container">
	<h1>Connexion</h1>
	<!-- Spring Security s'attend aux param�tres "username" et "password" -->
	<form method="post">
		<div class="form-group">
			<label for="username">Username</label> <input id="username"
				name="username" />
		</div>
		<div class="form-group">
			<label for="password">Password</label> <input id="password"
				name="password" />
		</div>
		<sec:csrfInput />
		<button type="submit" class="btn btn-primary">Se connecter</button>
	</form>
	<!-- en cas d'erreur un param�tre "error" est cr�� par Spring Security -->
	<c:if test="${param.error !=null}">Erreur d'authentification</c:if>
</body>
</html>