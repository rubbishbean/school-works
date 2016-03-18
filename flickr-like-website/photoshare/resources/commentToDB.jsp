<%@ page import="photoshare.CommentDao"  %>
<%@ page import="photoshare.NewUserDao"  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new comment</title>
</head>
<body>
<%
    String err= "";
    int picId = Integer.parseInt(request.getParameter("pic_id"));
    String text = request.getParameter("comment_text");
    int userId;
    CommentDao commentDao = new CommentDao();
    NewUserDao currentUser = new NewUserDao();
    if(request.getUserPrincipal() != null){
        String username = request.getUserPrincipal().getName();
        userId = currentUser.getUserIdByEmail(username);
        boolean created = commentDao.createComment(userId, picId, text);
        if(!created){
            err = "Failed to create new album :( ";
        }
    }else{
        userId = 1;
        boolean created = commentDao.createComment(userId, picId, text);
    }


%>
<jsp:forward page="picture.jsp" >
  <jsp:param name="picture_id" value="<%=picId%>" />
</jsp:forward>
</body>
</html>