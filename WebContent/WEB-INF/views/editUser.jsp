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
	<nav class="deep-orange darken-4" role="navigation">
		<div class="nav-wrapper container">
			<a id="logo-container" href="index" class="brand-logo">Holy
				Note</a>
			<ul class="right">
			</ul>
		</div>
	</nav>
	<div class="section no-pad-bot" id="index-banner">
		<div class="container">
			<br>
			<h3 style="padding-left: 11.25px; padding-right: 11.25px;"> EDIT USER </h3>
			<div class="row">
				<div class="col s12 m12">
					<div class="card">
						<div class="card-content">
							<form action="updateUser" method="post">
								<div class="row">
									<div class="input-field col s12">
										<input id="username" type="text" class="validate"
											name="new_username"> <label for="username">Edit
											Username</label>
									</div>
								</div>
								<div class="row">
									<div class="input-field col s12">
										<input id="pass" type="password" class="validate"
											name="old_password"> <label for="pass"> Old
											Password</label>
									</div>
								</div>
								<div class="row">
									<div class="input-field col s12">
										<input id="pass" type="password" class="validate"
											name="new_password"> <label for="pass"> New
											Password</label>
									</div>
								</div>
								<div class="row" style="margin: 0px;">
									<button class="btn waves-effect waves-light yellow darken-4"
										type="submit">Save</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<h3 style="padding-left: 11.25px; padding-right: 11.25px;"> REMOVE USER </h3>
			<div class="row">
				<div class="col s12 m12">
					<div class="card">
						<div class="card-content">
							<form action="removeUser" method="post">
								<div class="row">
									<div class="input-field col s12">
										<input id="pass" type="password" class="validate"
											name="password"> <label for="pass">Password</label>
									</div>
								</div>
								<div class="row" style="margin: 0px;">
									<button class="btn waves-effect waves-light red darken-4"
										type="submit">Delete</button>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
			<br> <br>
		</div>
	</div>



	<!--  Scripts-->
	<script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
	<script src="js/init.js"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".dropdown-trigger").dropdown();
		});
	</script>

</body>
</html>
