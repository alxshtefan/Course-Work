function startTimer(seconds) {
	// Set the date we're counting down to
	var countDownDate = new Date().getTime();
	countDownDate += seconds * 1000 * 60;

	// Update the count down every 1 second
	var x = setInterval(function() {

		// Get todays date and time
		var now = new Date().getTime();

		// Find the distance between now an the count down date
		var distance = countDownDate - now;

		// Time calculations for days, hours, minutes and seconds

		var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
		var seconds = Math.floor((distance % (1000 * 60)) / 1000);

		// Display the result in the element with id="demo"
		if (minutes < 10) {
			minutes = "0" + minutes;
		}
		
		if (seconds < 10) {
			seconds = "0" + seconds;
		}
		document.getElementById("demo").innerHTML = minutes + ":" + seconds;

		// If the count down is finished, write some text
		if (distance < 0) {
			clearInterval(x);
			document.getElementById("submitTest").submit();
		}
	}, 995);
}