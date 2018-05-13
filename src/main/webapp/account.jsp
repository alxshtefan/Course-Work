<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ru'}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources"/>

<html>

	<head>
		<title><fmt:message key="header.label"/></title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<link rel="stylesheet" type="text/css" href="css/secondaryStyle.css">
		<style type="text/css">
			.errorMessageAccount {
				font-size: 150%;
				color: #505050;
			}
			
			.statistisImage {
				padding-top: 5%;
				left: 40%;
				position:relative;
				width:5%;
				cursor:pointer;
				outline:none;
			}
			
			.statistisText {
				left: 40%;
				position:relative;
				width:5%;
			}
		</style>
	</head>

	<body>
		<c:if test="${sessionScope.user == null }">
			<script type="text/javascript">
				document.location.replace("welcome.jsp");
			</script>
		</c:if>
		
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="4.9%">
					<a href="index.jsp">
						<img src="images/logo.png" width="100%">
					</a>
				</td>
				<td class="logo">
					<a href="index.jsp" style="text-decoration: none; color: white;">
						<fmt:message key="header.label" />
					</a>
				</td>
				<form id="formid" action="Controller" method="GET">
					<input type="hidden" name="command" value="openAccountPage" /> 
					<td class="entrance">					
						<a href="#" onclick="document.getElementById('formid').submit()">
							${sessionScope.user.getLogin() }
						</a>
					</td>
					<td width="6.5%" class="entrancePic">
						<a href="#" onclick="document.getElementById('formid').submit()">
							<img src="images/account.png" width="100%" height="auto">
						</a>
					</td>
				</form>
				<form action="Controller" method="POST">
					<input type="hidden" name="command" value="logout" /> 
					<td width="5%" >
						<input type="submit" style="background-color:transparent; border: 0;
						color: #fff; font-size: 1.5vw; font-family: Times; outline: 0;" value=<fmt:message key="header.exit" /> />
					</td>
					<td width="6.4%" class="entrancePic">
						<input type="image" style="outline: 0;" src="images/exit.png" width="100%">
					</td>
				</form>
			</tr>
		</table>
		<!-- ============================================================================ -->

		<!-- ================================= ACCOUNT ================================== -->
		<div class="advertLogo">
			Привет, <bold>"${sessionScope.user }"</bold>!<br>
			Это твой аккаунт и твоя статистика.<hr>
		</div>
		<center>
			<c:choose>
				<c:when test="${testsResults.isEmpty()}">
					<div class="errorMessageAccount">
						<form id="goToTests" action="Controller" method="GET">
							<input type="hidden" name="command" value="openTestsPage" />
							<br><br>Вы еще не успели пройти ни одного теста.<br>
							Перейдите по <u><b><a href="#" onclick="document.getElementById('goToTests').submit()">ссылке</a></b></u> и начните проходить тесты!
						</form>
					</div>
				</c:when>
				<c:otherwise>
					<table id="myTable" class="resultTable" style="width: 65%; border-collapse: collapse; border: 2px solid #008B8B;">
						<tr style="border: 2px solid #008B8B;">
							<th class="thtr" onclick="sortTable(0)">Название теста	<img alt="updown" src="images/sort.png" width="12"></th></th>
		    				<th class="thtr" onclick="sortTable(1)">Результат	<img alt="updown" src="images/sort.png" width="12"></th></th>
		    				<th class="thtr">Дата прохождения теста</th>
						</tr>
						<c:forEach items="${testsResults }" var="result" >
							<tr class="thtr">
							    <td style="width: 400px">${result.getTestTitle() }</td>
							    <td style="width: 100px; text-align: center">${result.getScore() }</td>
							    <td style="width: 100px; text-align: center">${result.getDate() }</td>
							</tr>
						</c:forEach>
					</table>
					<form action="Controller" method="POST">
						<input type="hidden" name="command" value="getUsersStatistics" />
						<input class="statistisImage" type="image" name="statis" value="txt" alt="txt" src="images/txt.png" >
						<input class="statistisImage" type="image" name="statis" value="xls" alt="xls" src="images/xlsx.png">
						<input class="statistisImage" type="image" name="statis" value="pdf" alt="pdf" src="images/pdf.png"><br>
						<label class="statistisText">Получить на почту</label><br>
						<label class="statistisText">${sessionScope.user.getEmail() }</label>
					</form>
				</c:otherwise>
			</c:choose>
		</center>
		<br><br>
		<!-- ============================================================================ -->
		
		<!-- =================================== JS ===================================== -->
		<script type="text/javascript" src="js/jquery/jquery-3.2.1.min.js"></script>	
		<script type="text/javascript" src="js/script.js"></script>
		<script type="text/javascript" src="js/sortingScript.js"></script>
		<!-- ============================================================================ -->
	</body>

</html>