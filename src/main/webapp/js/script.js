$('.show_popup').click(function() {
	var popup_id = $('#' + $(this).attr("rel"));
	$(popup_id).show();
	$('.overlay_popup').show();
})

$('.overlay_popup').click(function() {
	$('.overlay_popup, .popup').hide();
})

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