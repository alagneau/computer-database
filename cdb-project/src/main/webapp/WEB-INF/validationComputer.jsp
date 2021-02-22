<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Ordinateur créé !</title>
</head>
<body>
	<%@ include file="menu.jsp" %>

	<p>L'ordinateur a été créé avec succés </p>
	<p>
	<form method="get" action="<%=request.getContextPath() %>/home">
		<input type="submit" value="Accueil">
	</form>
	<form method="get" action="<%=request.getContextPath() %>/createComputer">
		<input type="submit" value="Créer un autre">
	</form>

</body>
</html>