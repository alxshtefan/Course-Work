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
	<c:choose>
		<c:when test="${sessionScope.invalidLogin}">
			<style>
				input[type="text"]::-webkit-input-placeholder {
					color: red;
				}
			</style>
		</c:when>
		<c:when test="${sessionScope.invalidPassword}">
			<style>
				input[type="password"]::-webkit-input-placeholder {
					color: red;
				}
			</style>
		</c:when>
	</c:choose>
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
			<a href="#" rel="popup1" class="show_popup"><fmt:message key="header.entrance"/></a>
		</td>
		<td width="6.4%" class="entrancePic">
			<a href="#" rel="popup1" class="show_popup"><img src="images/exit.png" width="100%" height="auto"></a>
		</td>
	</tr>
</table>
<!-- ============================================================================ -->

<!-- ================================= POP UP =================================== -->
<c:choose>
<c:when test="${sessionScope.invalidLogin || sessionScope.invalidPassword || sessionScope.blocked}">
<div class="overlay_popup" style="display: flex"></div>
<div class="popup" id="popup1" style="display: block ">
	</c:when>
	<c:otherwise>
	<div class="overlay_popup"></div>
	<div class="popup" id="popup1">
		</c:otherwise>
		</c:choose>

		<div class="object">
			<form action= "Controller" method="POST">
				<input type="hidden" name="command" value="login" />
				<strong>
					<c:choose>
						<c:when test="${sessionScope.blocked}">
							<fmt:message key="popup.blocked"/>
						</c:when>
						<c:otherwise>
							<fmt:message key="popup.entrance"/>
						</c:otherwise>
					</c:choose>
				</strong>
				<hr>
				<p><fmt:message key="popup.login"/>: </p>
				<c:choose>
					<c:when test="${sessionScope.invalidLogin}">
						<input type="text" name="login" placeholder=" Неверный логин" pattern="[A-Za-z0-9]+"
									 minlength="3" maxlength="20" title="just latin letters or numbers"
									 style="border: 4px solid red;" required>
						<c:set var="invalidLogin" scope="session" value = "${false}"/>
					</c:when>
					<c:otherwise>
						<input type="text" name="login" placeholder="login" pattern="[A-Za-z0-9]+"
									 minlength="3" maxlength="20" title="just latin letters or numbers"
									 value="${sessionScope.login}" required>
						<c:set var="login" scope="session" value = ""/>
					</c:otherwise>
				</c:choose>

				<p><fmt:message key="popup.password"/>: </p>
				<c:choose>
					<c:when test="${sessionScope.invalidPassword}">
						<input type="password" name="password" placeholder=" Неверный пароль" pattern="[A-Za-z0-9]+"
									 minlength="4" maxlength="20" title="just latin letters or numbers"
									 style="border: 4px solid red;" required>
						<c:set var="invalidPassword" scope="session" value = "${false}"/>
					</c:when>
					<c:otherwise>
						<input type="password" name="password" placeholder="password" pattern="[A-Za-z0-9]+"
									 minlength="4" maxlength="20" title="just latin letters or numbers" required>

					</c:otherwise>
				</c:choose>

				<c:choose>
					<c:when test="${sessionScope.blocked}">
						<p><input type="submit" disabled value=<fmt:message key="popup.signin"/>></p>
					</c:when>
					<c:otherwise>
						<p><input type="submit" value=<fmt:message key="popup.signin"/>></p>
					</c:otherwise>
				</c:choose>

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
			Сайт для проходження тестів ЗНО онлайн та підготовки майбутніх абітурієнтів для
			проходження зовнішнього незалежного оцінювання. На сайті «ЗНО онлайн» розміщені
			тести, що складали абітурієнти під час зовнішнього незалежного оцінювання
			2009-2017 років, а також варіанти тестів, що пропонувались вступникам до вищих
			навчальних закладів України під час пробного зовнішнього незалежного оцінювання 2009-2015 років.
			Спосіб виконання всіх тестових завдань у запропонованих на цьому сайті тестах
			максимально наближений до реальних тестів, а форма надання відповіді відповідає виду,
			що пропонується абітурієнтам у бланку відповідей під час проходження реальних тестів ЗНО.
		</h5>
	</div>
	<!-- ============================================================================ -->

	<!-- ================================= ADVERTS ================================== -->

	<div class="advertLogo"><strong>Види тестов</strong></div>

	<div class="advert">
		<div>
			<a href="#" rel="popup1" class="show_popup clarity"><div class="firstText"><fmt:message key="advert.english"/></div>
				<img class="pics" src="images/english.jpg"></a>
			<a href="#" rel="popup1" class="show_popup clarity"><div class="secondText"><fmt:message key="advert.germany"/></div>
				<img class="pics" src="images/germany.jpg"></a>
		</div>
		<br>
		<div>
			<a href="#" rel="popup1" class="show_popup clarity"><div class="thirdText"><fmt:message key="advert.spanish"/></div>
				<img class="pics" src="images/spanish.jpg"></a>
			<a href="#" rel="popup1" class="show_popup clarity"><div class="fourthText"><fmt:message key="advert.french"/></div>
				<img class="pics" src="images/french.jpg"></a>
		</div>
	</div>

	<div class="footerLogo"><a href="#" rel="popup1" class="show_popup all"><fmt:message key="advert.showall"/></a></div>

	<!-- ============================================================================ -->

	<!-- ================================= FOOTER =================================== -->

	<table class="footer">
		<tr>
			<td class="cr">
				author: Aleksey Shtefan & Ekaterina Yankovskaya
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