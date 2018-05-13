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
<c:if test="${sessionScope.user == null }">
	<script type="text/javascript">
      document.location.replace("welcome.jsp");
	</script>
</c:if>
<c:remove var="correctTest"/>
<c:remove var="score"/>
<!-- ================================= HEADER =================================== -->
<table class="header">
	<tr>
		<td width="5%">
			<img src="images/logo.png" width="100%">
		</td>
		<td class="logo">
			<fmt:message key="header.label" />
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
		<form action= "Controller" method="POST">
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
<div class="advertLogo"><strong>Види тестів</strong></div>

<form id="goToTests" action="Controller" method="GET">
	<input type="hidden" name="command" value="openTestsPage" />
	<div class="advert">
		<div>
			<a href="Controller?command=openTestsPage&type=english" onclick="document.getElementById('goToTests').submit()" class="clarity"><div class="firstText"><fmt:message key="advert.english"/></div>
				<img class="pics" src="images/english.jpg"></a>
			<a href="Controller?command=openTestsPage&type=germany" onclick="document.getElementById('goToTests').submit()" class="clarity"><div class="secondText"><fmt:message key="advert.germany"/></div>
				<img class="pics" src="images/germany.jpg"></a>
		</div>
		<br>
		<div>
			<a href="Controller?command=openTestsPage&type=spanish" onclick="document.getElementById('goToTests').submit()" class="clarity"><div class="thirdText"><fmt:message key="advert.spanish"/></div>
				<img class="pics" src="images/spanish.jpg"></a>
			<a href="Controller?command=openTestsPage&type=french" onclick="document.getElementById('goToTests').submit()" class="clarity"><div class="fourthText"><fmt:message key="advert.french"/></div>
				<img class="pics" src="images/french.jpg"></a>
		</div>
	</div>

	<div class="footerLogo">
		<a class="all" href="#" onclick="document.getElementById('goToTests').submit()">
			<fmt:message key="advert.showall"/>
		</a>
	</div>
</form>
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