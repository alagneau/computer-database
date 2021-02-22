<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<ul>
	<li><a href="<%=request.getContextPath() %>/home">Accueil</a></li>
	<li><a href="<%=request.getContextPath() %>/listComputers">Tous les ordinateurs</a></li>
	<li><a href="<%=request.getContextPath() %>/createComputer">Créer un ordinateur</a></li>
</ul>