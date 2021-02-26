<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!-- Bootstrap -->
<style><%@include file="../css/bootstrap.min.css"%></style>
<style><%@include file="../css/font-awesome.css"%></style>
<style><%@include file="../css/main.css"%></style>
</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="dashboard.html"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
                <c:out value="${ maxComputers } Computers found ${maxPage}"/>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="#" method="GET" class="form-inline">

                        <input type="search" id="searchbox" name="search" class="form-control" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="addComputer">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="#" method="POST">
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
                            Computer name
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                	<c:forEach items="${ listComputers }" var="computer">
                    <tr>
                        <td class="editMode">
                            <input type="checkbox" name="cb" class="cb" value="0">
                        </td>
                        <td>
                            <a href="editComputer.html" onclick=""><c:out value="${ computer.getName() }"/></a>
                        </td>
                        <td><c:out value="${ computer.getIntroduced() }"/></td>
                        <td><c:out value="${ computer.getDiscontinued() }"/></td>
                        <td><c:out value="${ computer.getCompany().getName() }"/></td>
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
<script src="../js/jquery.min.js"></script>
<script src="../js/bootstrap.min.js"></script>
<script src="../js/dashboard.js"></script>

</body>
</html>