<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ru'}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources"/>

<html>

	<head>
		<title>Тестирование</title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<link rel="stylesheet" type="text/css" href="css/secondaryStyle.css">
		<script type="text/javascript" src="js/sortingScript.js"></script>
		<style type="text/css">
			table {
				text-align: center;
			}
			.chooseSubject {
				position: absolute;
				left: 85%;
			}
		</style>
		<script type="text/javascript">
			function changeFunc() {
				var selectBox = document.getElementById("selectBox");
				var selectedValue = selectBox.options[selectBox.selectedIndex].value;
				if (selectedValue == "") {
					document.location.replace("Controller?command=openTestsPage");
				} else {
					document.location.replace("Controller?command=openTestsPage&type=" + selectedValue);
				}
			}
		</script>
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
				<form action= "Controller" method="GET">
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

		<!-- =============================== ALL TESTS ================================== -->
		<div class="advertLogo">
			<c:choose>
				<c:when test="${param.type != null}">
					<c:choose>
						<c:when test="${param.type == 'english'}">
							Тесты по английскому
						</c:when>
						<c:when test="${param.type == 'germany'}">
							Тесты по немецкому
						</c:when>
						<c:when test="${param.type == 'spanish'}">
							Тесты по испанскому
						</c:when>
						<c:when test="${param.type == 'french'}">
							Тесты по французкому
						</c:when>
						<c:otherwise>
							Все тесты
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					Все тесты
				</c:otherwise>
			</c:choose>
		</div>
		
		<br>
		<center>
			<div class="chooseSubject">
				<select id="selectBox" onchange="changeFunc();">
					<option value="" disabled selected>Выберите тему тестов</option>
					<option value="">Все темы</option>
						<option value="english">Английский</option>
						<option value="germany">Немецкий</option>
						<option value="spanish">Испанский</option>
						<option value="french">Французкий</option>
				</select>
			</div>
			<c:choose>
				<c:when test="${tests.isEmpty() }">
					<m style="font-size: 20">В данный момент тестов нет.<br>Попробуйте зайти позже.</m>
				</c:when>
				<c:otherwise>
					<table id="myTable" style="width: 65%; border-collapse: collapse; border: 2px solid #008B8B;">
						<tr style="border: 2px solid #008B8B;">
							<th class="thtr" onclick="sortTable(0)">Название теста	<img alt="updown" src="images/sort.png" width="12"></th>
		    				<th class="thtr" onclick="sortTable(1)">Сложность	<img alt="updown" src="images/sort.png" width="12"></th>
		    				<th class="thtr" onclick="sortTable(2)">Количество вопросов	<img alt="updown" src="images/sort.png" width="12"></th>
		    				<th class="thtr" onclick="sortTable(3)">Время (мин)	<img alt="updown" src="images/sort.png" width="12"></th>
						</tr>
						
							<c:forEach items="${tests}" var="test" varStatus="loop">
								<form action="Controller" mehod="POST" id="startTest">
									<input type="hidden" name="command" value="startTest" />
									<tr class="thtr">
										<td style="width: 300px">
											<a href="?command=startTest&title=${test.getTitle() }${test.getId() }">${test.getTitle() }</a>
										</td>
									    <td style="width: 100px; text-align: center">
									    	<c:forEach begin="1" end="${test.getDifficult() }" var="star">
									    		<img alt="start" src="images/star3.png" width="10%">
									    	</c:forEach>
									    </td>
									    <td style="width: 100px; text-align: center">${test.getqNumber() }</td>
									    <td style="width: 100px; text-align: center">${test.getTime() }</td>
									</tr>
								</form>
							</c:forEach>
						
					</table>
				</c:otherwise>
			</c:choose>
			<br>
			<br><br>
			Что бы после прохождения теста, посмотреть правильные ответы<br>вы должны набрать более 60 баллов!
		</center>
		<!--============================================================================= -->

		<!-- =================================== JS ===================================== -->
		<!-- ============================================================================ -->
	</body>

</html>