<%@ page import="photoshare.CommentDao"  %>
<%@ page import="photoshare.NewUserDao"  %>
<%@ page import="photoshare.Piclike" %>
<%@ page import="photoshare.PiclikeDao" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add like</title>
</head>
<body>
<%
    String err= "";
    int picId = Integer.parseInt(request.getParameter("pic_id"));
    int userId;
    PiclikeDao piclikeDaoo = new PiclikeDao();
    NewUserDao currentUser = new NewUserDao();
    if(request.getUserPrincipal() != null){
        String username = request.getUserPrincipal().getName();
        userId = currentUser.getUserIdByEmail(username);
        boolean created = piclikeDaoo.addLike(userId, picId);
        if(!created){
            err = "Failed to create new album :( ";
        }
    }else{

    }


%>
<jsp:forward page="picture.jsp" >
    <jsp:param name="picture_id" value="<%=picId%>" />
</jsp:forward>
</body>
</html>