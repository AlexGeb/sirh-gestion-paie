<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<nav class="navbar navbar-expand-lg navbar-light bg-light">
	<a class="nav-item nav-link"
		href="<c:url value='/mvc/employes/lister'/>">Employés</a> <a
		class="nav-item nav-link"
		href="<c:url value='/mvc/bulletins/lister'/>"> Bulletins</a>
	
	<c:url var="logoutUrl" value="/logout"/>
	<form:form method="post" action="${logoutUrl}">	
		<button type="submit" class="btn btn-danger">Déconnexion</button>
	</form:form>
</nav>