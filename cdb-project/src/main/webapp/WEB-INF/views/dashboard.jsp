<%@ page pageEncoding="iso-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
	<title>Computer Database</title>
	<meta name="viewport" content="width=device-width, initial-scale=1.0">
	<meta charset="utf-8">
	<!-- Bootstrap -->
	<link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
	<link href="css/font-awesome.css" rel="stylesheet" media="screen">
	<link href="css/main.css" rel="stylesheet" media="screen">
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
            <h1 id="homeTitle">
                <c:out value="${ maxComputers } "/><fmt:message key="label.dashboard.title" />
                
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="<fmt:message key="label.dashboard.entry.search" />" />
                        <input type="submit" id="searchsubmit" value="<fmt:message key="label.dashboard.button.search" />" class="btn btn-primary" />
                        <input type="hidden" name="orderByValue" value="${ORDER_BY_VALUES[0]}"/>
                        <input type="submit" value="<fmt:message key="label.dashboard.button.orderBy" />" class="btn btn-primary"/>
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer"><fmt:message key="label.dashboard.button.add" /></a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();"><fmt:message key="label.dashboard.button.edit" /></a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="dashboard" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            <fmt:message key="label.dashboard.column.name" />
                            <a href="
								<c:url value="dashboard">
									<c:param name="orderByValue" value="${ORDER_BY_VALUES[1]}"/>
								</c:url>">
								<i class="fa fa-fw fa-sort"></i>
							</a>
                        </th>
                        <th>
                            <fmt:message key="label.dashboard.column.introduced" />
                            <a href="
								<c:url value="dashboard">
									<c:param name="orderByValue" value="${ORDER_BY_VALUES[2]}"/>
								</c:url>">
								<i class="fa fa-fw fa-sort"></i>
							</a>
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            <fmt:message key="label.dashboard.column.discontinued" />
                            <a href="
								<c:url value="dashboard">
									<c:param name="orderByValue" value="${ORDER_BY_VALUES[3]}"/>
								</c:url>">
								<i class="fa fa-fw fa-sort"></i>
							</a>
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            <fmt:message key="label.dashboard.column.company" />
                            <a href="
								<c:url value="dashboard">
									<c:param name="orderByValue" value="${ORDER_BY_VALUES[4]}"/>
								</c:url>">
								<i class="fa fa-fw fa-sort"></i>
							</a>
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${ listComputers }" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="${ computer.getID() }">
                        </td>
                        <td>
                            <a href="<c:url value="editComputer">
	                    			<c:param name="computerId" value="${ computer.getID() }"/>
	                    		</c:url>
	                    		" onclick=""><c:out value="${ computer.getName() }"/></a>
                        </td>
                        <td><c:out value="${ computer.getIntroduced() }"/></td>
                        <td><c:out value="${ computer.getDiscontinued() }"/></td>
                        <td><c:out value="${ computer.getCompanyName() }"/></td>
                    </tr>
					</c:forEach>
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
					<a href="<c:url value="dashboard">
						<c:param name="pageIndex" value="${ pageIndex > 1 ? pageIndex-1 : 1 }"/>
						</c:url>
						" aria-label="Previous">
					    <span aria-hidden="true">&laquo;</span>
					</a>
				</li>
				<!-- Affichage des boutons de choix de page -->
				<c:forEach begin="${pageIndex > 3 ? pageIndex-2 : 1}" end="${pageIndex < maxPage-2 ? pageIndex+2 : maxPage}" var="loopValue">
					<li>
						<a href="
							<c:url value="dashboard">
								<c:param name="pageIndex" value="${loopValue}"/>
							</c:url>
						">
							<c:out value="${loopValue}"/>
						</a>
					</li>
				</c:forEach>
				<li>
	                <a href="<c:url value="dashboard">
	                    		<c:param name="pageIndex" value="${ pageIndex < maxPage ? pageIndex+1 : maxPage }"/>
	                    		</c:url>
	                    		"
	                     aria-label="Previous">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
	        </ul>
	        
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	        	<form method="get" action="dashboard">
					<input type="submit" class="btn btn-default" name="numberOfValues" value="10">
					<input type="submit" class="btn btn-default" name="numberOfValues" value="20">
					<input type="submit" class="btn btn-default" name="numberOfValues" value="30">
				</form>
	        </div>
        </div>

    </footer>
    <script type="text/javascript">
		var strings = new Array();
		strings['view'] = "<fmt:message key="label.dashboard.button.view" />";
		strings['edit'] = "<fmt:message key="label.dashboard.button.edit" />";
	</script>
	<script src="js/jquery.min.js"></script>
	<script src="js/bootstrap.min.js"></script>
	<script src="js/dashboard.js"></script>
	
</body>
</html>