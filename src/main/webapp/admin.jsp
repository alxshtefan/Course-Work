<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 

<html>

	<head>
		<title>Администрирование</title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/adminStyle.css">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<script type="text/javascript">
			function openContent(evt, content) {
			    var i, tabcontent, tablinks;
	
			    tabcontent = document.getElementsByClassName("tabcontent");
			    for (i = 0; i < tabcontent.length; i++) {
			        tabcontent[i].style.display = "none";
			    }
	
			    tablinks = document.getElementsByClassName("tablinks");
			    for (i = 0; i < tablinks.length; i++) {
			        tablinks[i].className = tablinks[i].className.replace(" active", "");
			    }
	
			    document.getElementById(content).style.display = "block";
			    evt.currentTarget.className += " active";
			}
			
			setTimeout(function(){
			    document.getElementById('aap').className = 'waa';
			}, 2000);
			
			function openWin(type) {
				myWin= open('Controller?command=open'+type, 'displayWindow', 
				'width=1248,height=1080,status=no,toolbar=no,menubar=no,resizable=no');
			}
		</script>
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
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="5%">
					<img src="images/logo.png" width="100%">
				</td>
				<td class="logo">
					Тестирование
				</td>
				<form action="Controller" method="POST">
					<input type="hidden" name="command" value="logout" /> 
					<td width="5%" >
						<input type="submit" style="background-color:transparent; border: 0;
						color: #fff; font-size: 1.5vw; font-family: Times; outline: 0;" value="Выход" />
					</td>
					<td width="6.4%" class="entrancePic">
						<input type="image" style="outline: 0;" src="images/exit.png" width="100%">
					</td>
				</form>
			</tr>
		</table>
		<!-- ============================================================================ -->
		
		<!-- ================================== BODY ==================================== -->
		
		<!-- ================================== LINK ==================================== -->
		<c:if test="${done }"><my id="aap">Операция успешна</my></c:if>
		<div class="tab">
		  <button class="tablinks" onclick="openContent(event, 'Создать тест')">Cоздать тест</button>
		  <c:if test="${errorCreate != null }">
		  	<img class="attention_symbol_tab" alt="attention" src="images/attention.png" style=" left: 7.2% ">
		  </c:if> 
		   
		  <button class="tablinks" onclick="openContent(event, 'Редактировать тест')">Редактировать тест</button>
		  <c:if test="${errorUpdate != null }">
		  	<img class="attention_symbol_tab" alt="attention" src="images/attention.png" style=" left: 19.2% ">
		  </c:if>
		  
		  <button class="tablinks" onclick="openContent(event, 'Удалить тест')">Удалить тест</button>
		  <c:if test="${errorDelete != null }">
		  	<img class="attention_symbol_tab" alt="attention" src="images/attention.png" style=" left: 28% ">
		  </c:if>
		  
		  <button class="tablinks" onclick="openContent(event, 'Работа с пользователями')">Работа с пользователями</button>
		  <c:if test="${errorBlock != null }">
		  	<img class="attention_symbol_tab" alt="attention" src="images/attention.png" style=" left: 42.9% ">
		  </c:if>
		</div>
		<!-- ============================================================================ -->
		
		<!-- ================================= PAGE 1 =================================== -->
		<center>
			<div id="Создать тест" class="tabcontent">
			  <form action="Controller" method="POST">
			  	<input type="hidden"name="command" value="addTest" /> 
			  	<p>Название теста: <input type="text" name="title"  maxlength="30" placeholder="название" pattern="[A-Za-zА-Яа-я0-9 ]+" autocomplete="off" required></p>
			  	<p>Тема теста:
						<select name="subject" required>
							<option>Английский</option>
							<option>Немецкий</option>
							<option>Испанский</option>
							<option>Французкий</option>
						</select>
			  	<p>Сложность теста: <input type="text" name="difficult" placeholder="2" pattern="[1-5]" title="from 1 up to 5" autocomplete="off" required></p>
			  	<p>Время на прохождение теста: <input type="text" name="time" placeholder="180" pattern="[0-9]+" title="in minutes" autocomplete="off" required></p>
			  	<p>Количество вопросов  в тесте: <input type="text" name="numOfQuest" placeholder="10" pattern="[0-9]+" title="just numbers" autocomplete="off" required></p>
			  	<input type="submit" value="Перейти к заполнению вопросов">
			  </form>
			  <div class="errorText">
					<c:if test="${errorCreate != null}">
						${errorCreate }
					</c:if>
				</div>
			</div>
		</center>
		<!-- ============================================================================ -->
		
		<!-- ================================= PAGE 2 =================================== -->
		<center>
			<div id="Редактировать тест" class="tabcontent">
		  		<form action="Controller" method="POST">
		  			<input type="hidden"name="command" value="updateTest" /> 
					<p>Введите название теста: <input type="text" name="title"  maxlength="30" placeholder="название" pattern="[A-Za-zА-Яа-я0-9 ]+" autocomplete="off" required></p>
					<input type="submit" value="Найти тест">
		  		</form>
		  		<div class="errorText">
					<c:if test="${errorUpdate != null}">
						${errorUpdate }
					</c:if>
				</div>
			</div>
		</center>
		<!-- ============================================================================ -->
		
		<!-- ================================= PAGE 3 =================================== -->
		<center>
			<div id="Удалить тест" class="tabcontent">
		  		<form action="Controller" method="POST">
		  			<input type="hidden"name="command" value="deleteTest" /> 
					<p>Введите название теста: <input type="text" name="title"  maxlength="30" placeholder="название" pattern="[A-Za-zА-Яа-я0-9 ]+" autocomplete="off" required></p>
					<input type="submit" value="Удалить тест">
		  		</form>
		  		<div class="errorText">
					<c:if test="${sessionScope.errorDelete != null}">
						${sessionScope.errorDelete }
					</c:if>
				</div>
			</div>
		</center>
		<!-- ============================================================================ -->
		
		<!-- ================================= PAGE 4 =================================== -->
		<center>
			<div id="Работа с пользователями" class="tabcontent">
				<form action="Controller" method="POST">
					<input type="hidden"name="command" value="blockUser" /> 
					<p>Введите логин пользователя: <input type="text" name="login"  maxlength="20" placeholder="логин" pattern="[A-Za-z0-9 ]+" autocomplete="off" required></p>
					<input type="submit" value="Заблокировать пользователя">
				</form>
				<form action="Controller" method="POST">
					<input type="hidden"name="command" value="unLockUser" /> 
					<p>Введите логин пользователя: <input type="text" name="login"  maxlength="20" placeholder="логин" pattern="[A-Za-z0-9 ]+" autocomplete="off" required></p>
					<input type="submit" value="Разблокировать пользователя">
				</form>
				<div class="errorText">
					<c:if test="${errorBlock != null}">
						${errorBlock }
					</c:if>
				</div>
			</div>
		</center>
		<!-- ============================================================================ -->
		
	</body>
	
</html>