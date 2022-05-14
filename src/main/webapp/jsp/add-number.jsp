<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
	<meta charset="UTF-8">
	<title>Add number</title>
</head>
<body>

<form method="POST" action="/lab13/notebook/add-number">
	Name: <label>
	<input name="name"/>
</label>
	<br>
	Number: <label>
	<input type="number" name="number"/>
</label>
	<br>
	<input type="submit" value="Submit"/>
</form>
<br>
<nav><a href="/lab13/notebook">Go back</a></nav>

</body>
</html>