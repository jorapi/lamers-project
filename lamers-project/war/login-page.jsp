<%@ page contentType="text/html; charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Login Page" content="text/html; charset=UTF-8">
	<title>Login Page</title>
	
	<style type="text/css">
		h2{
			font-family: Helvetica, Arial, sans-serif;
		}
		
		input[type=text], input[type=email]{
			
		}
	</style>
</head>

<body>
		<h2>Login Page</h2>
		<form action="/login-page" method="POST">
		<table cellpadding="5">
			<tr>
				<td>User Name: </td>
				<td><input type="text" name="user_name"></td>
			</tr>
			
			<tr>
				<td>Password: </td>
				<td><input type="text" name="password"></td>
			<tr>		
			<select>
 			 	<option value="student">Student</option>
  			 	<option value="instructor">Instructor</option>
 			    <option value="administrator">Administrator</option>	
			</select>
			</tr>		
			</table>
			
		</table>
		</br><input type="submit" value="login">
		<input type="submit" value="Clear">
		</form>
</body>
</html>
