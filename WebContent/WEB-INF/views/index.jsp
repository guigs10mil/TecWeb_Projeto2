<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1.0" />
<title>Holy Note</title>

<!-- CSS  -->
<link href="https://fonts.googleapis.com/icon?family=Material+Icons"
	rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">

</head>
<body>
	<!--  Scripts-->
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/init.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".dropdown-trigger").dropdown();
		});
	</script>
	<nav class="deep-orange darken-4" role="navigation">
		<div class="nav-wrapper container">
			<a id="logo-container" href="#" class="brand-logo">Holy Note</a>
			<ul class="right">
			</ul>
		</div>
	</nav>
	<div class="section no-pad-bot" id="index-banner">
		<div class="container">
			<br> <br>
			<h1 class="header center red-text">Better Start Taking Notes
				Boy!</h1>
			<div class="row center">
				<h5 class="header col s12 light">A cool tool to make a fool
					remember stuff without having to think too hard</h5>
			</div>
			<div class="row center">
				<a href="signUp" id="download-button"
					class="btn-large waves-effect waves-light yellow darken-4">Sign
					Up</a>
			</div>
			<div class="row center">
				<p>
					Already have an account? Try <a href="signIn">Signing In</a>.
				</p>
			</div>
			<br> <br>

		</div>
	</div>



	
</body>
</html>
