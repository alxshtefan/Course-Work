<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'ru'}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources"/>

<html>

	<head>
		<title><fmt:message key="popup.registration"/></title>
		<link rel="icon" href="images/logo.png" type="image/x-icon">
		<link rel="stylesheet" type="text/css" href="css/myStyle.css">
		<link rel="stylesheet" type="text/css" href="css/secondaryStyle.css">
		<script src='https://www.google.com/recaptcha/api.js'></script>
		<script type="text/javascript">
			function enableBtn(){
		    	document.getElementById("registr").disabled = false;
		   	}
		</script>
	</head>

	<body onload="document.getElementById('registr').disabled = true">
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="5%">
					<a href="index.jsp"><img src="images/logo.png" width="100%"></a>
				</td>
				<td class="logo">
					<fmt:message key="header.label" />
				</td>
			</tr>
		</table>
		<!-- ============================================================================ -->


		<!-- ================================ REG FORM ================================== -->
		<br><center><strong style="color:#505050; font-size: 200%"><fmt:message key="reg.logo"/></strong></center>
		<br>
		<center>
			<form action="Controller" method="POST" style=" padding-left:36%;  text-align: left">
				<input type="hidden" name="command" value="registration" /> 
				<div style="color: #505050; font-size: 150%; padding-bottom: 2%">
					<fmt:message key="reg.name"/>
					<input style="color: #505050; font-size: 80%; width: 310px" type="text" name="name" placeholder=" name" pattern="[A-Za-zА-Яа-я0-9]+"
					minlength="3" maxlength="20" title="just latin letters or numbers" required>
				</div>
				<div style="color: #505050; font-size: 150%; padding-bottom: 2%">
					<fmt:message key="reg.sname"/>
					<input style="color: #505050; font-size: 80%; width: 260px" type="text" name="sname" placeholder=" sname" pattern="[A-Za-zА-Яа-я0-9]+"
					minlength="3" maxlength="20" title="just latin letters or numbers" required>
				</div>
				<div style="color: #505050; font-size: 150%; padding-bottom: 2%">
					<fmt:message key="popup.login"/>
					<input style="color: #505050; font-size: 80%; width: 290px" type="text" name="login" placeholder=" login" pattern="[A-Za-z0-9]+"
					minlength="3" maxlength="20" title="just latin letters or numbers" required>
				</div>
				<div style="color: #505050; font-size: 150%; padding-bottom: 2%">
					<fmt:message key="popup.password"/>
					<input style="color: #505050; font-size: 80%; width: 280px" type="password" name="password" placeholder=" password" pattern="[A-Za-z0-9]+" 
					minlength="4" maxlength="20" title="just latin letters or numbers" required> 	
				</div>
				<div style="color: #505050; font-size: 150%; padding-bottom: 2%">
					<fmt:message key="reg.email"/>
					<input style="color: #505050; font-size: 80%; width: 292px" type="email" name="email" placeholder=" email"
					maxlength="40" required> 	
				</div>
				
				<div class="g-recaptcha" style="padding-bottom:2%; padding-left: 3%"
					data-sitekey="6LdORkMUAAAAANf5aH39MVsHr50FF4XVxp9m9EOx" data-callback="enableBtn"></div>
					
				<div style="color: #505050; font-size: 150%; padding-left: 76px">
					<input disabled id="registr" type="submit" style="color: #505050; font-size: 80%" value=<fmt:message key="reg.registr"/>> 
				</div>
				
			</form>			
		</center>
		<br/> <br/> <br/>
		<!--============================================================================= -->
		
		<!-- ================================= FOOTER =================================== -->
		<table class="footer">
			<tr>
				<td class="cr">
					author: Aleksey Shtefan & Ekaterina Yankovskaya
				</td>
				<td width="5%" class="entrancePic">
					<form method="POST">
						<input name="language" type="image" value="ru"
						${language == 'ru' ? 'selected' : ''} src="images/rus.jpg" width="280%">
					</form>
				</td>
				<td width="5%" class="entrancePic">
					<form method="POST">
						<input name="language" type="image" value="en"
						${language == 'en' ? 'selected' : ''} src="images/eng.jpg" width="280%">
					</form>
				</td>
			</tr>
		</table>
		<!-- ============================================================================ -->
	</body>

</html>