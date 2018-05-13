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
	</head>

	<body>
		<!-- ================================= HEADER =================================== -->
		<table class="header">
			<tr>
				<td width="5%">
					<img src="images/logo.png" width="100%">
				</td>
				<td class="logo">
					<fmt:message key="header.label" />
				</td>
				<td class="entrance">					
					<a href="#" rel="popup1" class="show_popup"><fmt:message key="header.account"/></a>
				</td>
				<td width="6.5%" class="entrancePic">
					<a href="#" rel="popup1" class="show_popup"><img src="images/account.png" width="100%" height="auto"></a>
				</td>
				<td class="entrance">
					<a href="#" rel="popup1" class="show_popup"><fmt:message key="header.entrance"/></a>
				</td>
				<td width="6.4%" class="entrancePic">
					<a href="#" rel="popup1" class="show_popup"><img src="images/exit.png" width="100%" height="auto"></a>
				</td>
			</tr>
		</table>
		<!-- ============================================================================ -->

		<!-- ================================= POP UP =================================== -->
		<div class="overlay_popup"></div>

		<div class="popup" id="popup1">
			<div class="object">
				<form action= "Controller" method="POST">
					<input type="hidden" name="command" value="login" /> 
					<strong>
						<fmt:message key="popup.entrance"/>
					</strong>
					<hr>
					<p><fmt:message key="popup.login"/>: </p>
					<input type="text" name="login" placeholder="login" pattern="[A-Za-z0-9]+"
					minlength="3" maxlength="20" title="just latin letters or numbers" required> 
					
					<p><fmt:message key="popup.password"/>: </p>
					<input type="password" name="password" placeholder="password" pattern="[A-Za-z0-9]+" 
					minlength="4" maxlength="20" title="just latin letters or numbers" required> 
					
					<p><input type="submit" value=<fmt:message key="popup.signin"/>></p>
				</form>			
				<a href="registration.jsp" class="registration"><fmt:message key="popup.registration"/></a>
			</div>
		</div>
		<!-- ============================================================================ -->

		<!-- ================================= BANNER =================================== -->
		<div>
			<img src="images/banner.jpg" width="auto" height="auto" alt="Фотография" class="banner">
			<h5 class="bannerText">
				<fmt:message key="header.label"/>
				<hr>
				Lorem ipsum dolor sit amet, consectetuer adipiscing elit. Aenean commodo ligula eget dolor. 
				Aenean massa. Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. 
				Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim. 
				Donec pede justo, fringilla vel, aliquet nec, vulputate eget, arcu. In enim justo, rhoncus ut, 
				imperdiet a, venenatis vitae, justo. Nullam dictum felis eu pede mollis pretium. Integer tincidunt.
				Cras dapibus. Vivamus elementum semper nisi. Aenean vulputate eleifend tellus. Aenean leo ligula, 
				porttitor eu, consequat vitae, eleifend ac, enim. Aliquam lorem ante, dapibus in, viverra quis, 
				feugiat a, tellus. 
			</h5>
		</div>
		<!-- ============================================================================ -->

		<!-- ================================= ADVERTS ================================== -->
	
		<div class="advertLogo"><strong>Виды тестов</strong></div>

		<div class="advert">
			<div>
				<a href="#" rel="popup1" class="show_popup clarity"><div class="firstText"><fmt:message key="advert.math"/></div>
				<img class="pics" src="images/math.jpg"></a>
				<a href="#" rel="popup1" class="show_popup clarity"><div class="secondText"><fmt:message key="advert.english"/></div>
				<img class="pics" src="images/english.jpg"></a>
			</div>
			<br>
			<div>
				<a href="#" rel="popup1" class="show_popup clarity"><div class="thirdText"><fmt:message key="advert.physics"/></div>
				<img class="pics" src="images/phisics.jpg"></a>
				<a href="#" rel="popup1" class="show_popup clarity"><div class="fourthText"><fmt:message key="advert.biology"/></div>
				<img class="pics" src="images/biology.jpg"></a>
			</div>
		</div>

		<div class="footerLogo"><a href="#" rel="popup1" class="show_popup all"><fmt:message key="advert.showall"/></a></div>
		
		<!-- ============================================================================ -->

		<!-- ================================= FOOTER =================================== -->
		
		<table class="footer">
			<tr>
				<td class="cr">
					author: Aleksey Shtefan
				</td>
				<td width="5%" class="entrancePic">
					<form method="POST">
						<input name="language" type="image" value="ru"
						${language == 'ru' ? 'selected' : ''} src="images/rus.jpg" width="300%">
					</form>
				</td>
				<td width="5%" class="entrancePic">
					<form method="POST">
						<input name="language" type="image" value="en"
						${language == 'en' ? 'selected' : ''} src="images/eng.jpg" width="300%">
					</form>
				</td>
			</tr>
		</table>
		
		<!-- ============================================================================ -->

		<!-- =================================== JS ===================================== -->
		<script type="text/javascript" src="js/jquery/jquery-3.2.1.min.js"></script>	
		<script type="text/javascript" src="js/script.js"></script>
		<!-- ============================================================================ -->
	</body>

</html>