<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<style><%@include file="/css/bootstrap.min.css"%></style>
<style><%@include file="/css/font-awesome.css"%></style>
<style><%@include file="/css/main.css"%></style>
<script src="js/createComputer.js"></script>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1>Add Computer</h1>
                    <form action="addComputer" method="POST" onsubmit="return validateDates(computerName.value, introduced.value, discontinued.value, companyId.value)">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName">Computer name</label>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="Computer name">
                                <span id="computerName-validation-message" style="color:red"></span>
                            </div>
                            <div class="form-group">
                                <label for="introduced">Introduced date</label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="Introduced date">
                                <span id="introduced-validation-message" style="color:red"></span>
                            </div>
                            <div class="form-group">
                                <label for="discontinued">Discontinued date</label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="Discontinued date">
                                <span id="discontinued-validation-message" style="color:red"></span>
                            </div>
                            <div class="form-group">
                                <label for="companyId">Company</label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<option value="-1">...</option>
                                	<c:forEach items="${ companies }" var="company">
                                    <option value="${company.getID() }"><c:out value="${company.getName() }"/></option>
                                    </c:forEach>
                                </select>
                                <span id="companyId-validation-message" style="color:red"></span>
                            </div>                  
                        </fieldset>
						<span id="dateComparison-validation-message" style="color:red"></span>
                        <div class="actions pull-right">
                            <input type="submit" value="Add" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>