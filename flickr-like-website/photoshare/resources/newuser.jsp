<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Sign up</title></head>


<body>

<h2>New user info</h2>

<form action="adduser.jsp" method="post">
  Email: <input type="text" name="email"/><br>
  Password: <input type="password" name="password1"/><br>
  Re-enter password: <input type="password" name="password2"/><br>
  <hr>
  First Name: <input type="text" name="f_name"/><br>
  Last Name: <input type="text" name="l_name"/><br>
  Date of birth: <input type="text" name="dob" placeholder="yyyy-mm-dd"/><br>
  Hometown: <input type="text" name="hometown"/><br>
  Gender:
  <select name="gender">
    <option value="male">male</option>
    <option value="female">female</option>
    <option value="other">other</option>
  </select>

  <input type="submit" value="Create"/><br/>
</form>

</body>
</html>
