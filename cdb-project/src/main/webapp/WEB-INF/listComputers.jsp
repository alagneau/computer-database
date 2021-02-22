<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.excilys.formation.model.Computer" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Liste des ordinateurs</title>
</head>
<body>
	<%@ include file="menu.jsp" %>
	
	<p>Voici la liste de tous les ordinateurs</p>

	<table>
	<tr><td>Nom</td><td>Entreprise</td><td>Introduced</td><td>Discontinued</td></tr>
	
	<% 
	for (Computer computer : (List<Computer>)request.getAttribute("listComputers"))
	{ %>
	
	<tr>
		<td><%= computer.getName() %></td>
		<td><%= computer.getCompany().getName() %></td>
		<td><%= computer.getIntroduced() %></td>
		<td><%= computer.getDiscontinued() %></td>
	</tr>
	<%} %>
	
	</table>
	
	<form method="get">
	<p> ${ indiceComputer + 1 } - ${ indiceComputer + numberOfRows}
	
	 Page : <input type="number" value=${indexPage } min=0 max=${maxPage } name="changePageIndex"> / ${ maxPage }</p>
		<label for="changeNumberOfRows">Nombre de lignes</label>
		<select name="changeNumberOfRows">
			<option value=10>10</option>
			<option value=15>15</option>
			<option value=20>20</option>
			<option value=25>25</option>
		</select>
		<input type="submit">
	</form>
	
	<form method="get">
		<input type="submit" name="switchPage" value="Precedent" >
		<input type="submit" name="switchPage" value="Suivant" >
	</form>
	
</body>
</html>