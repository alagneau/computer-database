<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.excilys.formation.model.Company" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Création d'un ordinateur</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	
	<form method="post">
		<p>Nom de l'ordinateur : <input type="text" name="nom"></p>
		
		<p>Companie : 
		<select name="select-company">
			<% 
			if (request.getAttribute("companies") != null) {
			for(Company company : (List<Company>)request.getAttribute("companies")) {%>
			<option value=<%=(int)company.getID() %>><%=company.getName() %></option>
			<%}} %>
		</select>
		</p>
		
		<p>Date d'introduction : <input type="date" name="introduced"></p>
		<p>Date de départ : <input type="date" name="discontinued"></p>
		
	
		<input type="submit" value="Valider">
	</form>

</body>
</html>