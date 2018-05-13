<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>

	<head>
		<title>Table</title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<link rel="stylesheet" type="text/css" href="css/secondaryStyle.css">
		<script type="text/javascript" src="js/sortingScript.js"></script>
	</head>
	
	<body>
		<c:if test="${sessionScope.user == null }">
			<script type="text/javascript">
				document.location.replace("welcome.jsp");
			</script>
		</c:if>
		<c:if test="${sessionScope.user.getLogin() != 'admin' }">
			<script type="text/javascript">
				document.location.replace("welcome.jsp");
			</script>
		</c:if>
		<c:choose>
			<c:when test="${users != null }">
				<center><br>
				<table id="myTable" class="resultTable" style="width: 65%; border-collapse: collapse; border: 2px solid #008B8B;">
					<tr style="border: 2px solid #008B8B;">
						<th class="thtr" onclick="sortTable(0)">Имя<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(1)">Фамилия<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(2)">Почта<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(3)">Логин<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr">Статус</th>
					</tr>
					<c:forEach items="${users }" var="result" >
						<tr class="thtr">
						    <td style="width: 100px">${result.getName() }</td>
						    <td style="width: 100px">${result.getSname() }</td>
						    <td style="width: 300px">${result.getEmail() }</td>
						     <td style="width: 100px">${result.getLogin() }</td>
						    <td style="width: 100px">
						    	<c:choose>
						    		<c:when test="${result.isCanWork() == true}">
						    			Разблокирован
						    		</c:when>
						    		<c:otherwise>
						    			Заблокирован
						    		</c:otherwise>
						    	</c:choose>
							</td>
						</tr>
					</c:forEach>
				</table>
				</center>
			</c:when>
			
			<c:when test="${tests != null }">
			<center><br>
				<table id="myTable" class="resultTable" style="width: 65%; border-collapse: collapse; border: 2px solid #008B8B;">
					<tr style="border: 2px solid #008B8B;">
						<th class="thtr" onclick="sortTable(0)">Название теста	<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(1)">Тема теста	<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(2)">Сложность	<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(3)">Время	<img alt="updown" src="images/sort.png" width="12"></th>
	    				<th class="thtr" onclick="sortTable(4)">Кол. прох.	<img alt="updown" src="images/sort.png" width="12"></th>
					</tr>
					<c:forEach items="${tests }" var="result" varStatus="loop" >
						<tr class="thtr">
						    <td style="width: 400px">${result.getTitle() }</td>
						    <td style="width: 200px; text-align: center">${result.getSubject() }</td>
						    <td style="width: 160px; text-align: center">${result.getDifficult() }</td>
						    <td style="width: 120px; text-align: center">${result.getTime() }</td>
						    <td style="width: 200px; text-align: center">${passingNumber.get(loop.index) }</td>
						</tr>
					</c:forEach>
				</table>
				</center>
			</c:when>
		</c:choose>
	</body>
	
</html>