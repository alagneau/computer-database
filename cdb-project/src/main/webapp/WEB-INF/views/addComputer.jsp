<%@ page pageEncoding="iso-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
<link href="css/font-awesome.css" rel="stylesheet" media="screen">
<link href="css/main.css" rel="stylesheet" media="screen">
<script src="js/createComputer.js"></script>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard"> Application - Computer Database </a>
	        <div class="pull-right">
	        	<a href="?lang=en"><fmt:message key="label.lang.en" /></a>
	        	<a href="?lang=fr"><fmt:message key="label.lang.fr" /></a>
	        </div>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <div class="row">
                <div class="col-xs-8 col-xs-offset-2 box">
                    <h1><fmt:message key="label.editComputer.title" /></h1>
                    <form action="addComputer" method="POST" onsubmit="return validateDates(computerName.value, introduced.value, discontinued.value, companyId.value)">
                        <fieldset>
                            <div class="form-group">
                                <label for="computerName"><fmt:message key="label.addComputer.field.name" /></label>
                                <input type="text" class="form-control" name="computerName" id="computerName" placeholder="<fmt:message key="label.addComputer.field.name" />">
                                <span id="computerName-validation-message" style="color:red"></span>
                            </div>
                            <div class="form-group">
                                <label for="introduced"><fmt:message key="label.addComputer.field.introduced" /></label>
                                <input type="date" class="form-control" name="introduced" id="introduced" placeholder="<fmt:message key="label.addComputer.field.introduced" />">
                                <span id="introduced-validation-message" style="color:red"></span>
                            </div>
                            <div class="form-group">
                                <label for="discontinued"><fmt:message key="label.addComputer.field.discontinued" /></label>
                                <input type="date" class="form-control" name="discontinued" id="discontinued" placeholder="<fmt:message key="label.addComputer.field.discontinued" />">
                                <span id="discontinued-validation-message" style="color:red"></span>
                            </div>
                            <div class="form-group">
                                <label for="companyId"><fmt:message key="label.addComputer.field.company" /></label>
                                <select class="form-control" id="companyId" name="companyId" >
                                	<option value="-1">...</option>
                                	<c:forEach items="${ companyList }" var="company">
                                    <option value="${company.getID() }"><c:out value="${company.getName() }"/></option>
                                    </c:forEach>
                                </select>
                                <span id="companyId-validation-message" style="color:red"></span>
                            </div>                  
                        </fieldset>
						<span id="dateComparison-validation-message" style="color:red"></span>
                        <div class="actions pull-right">
                            <input type="submit" value="<fmt:message key="label.addComputer.button.edit" />" class="btn btn-primary">
                            or
                            <a href="dashboard" class="btn btn-default"><fmt:message key="label.addComputer.button.cancel" /></a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </section>
</body>
</html>