<%@ page import="photoshare.TagDao"  %>
<%@ page import="photoshare.NewUserDao"  %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add new tag</title>
</head>
<body>
<%
    String err= "";
    int picId = Integer.parseInt(request.getParameter("pic_id_hidden"));
    String tags = request.getParameter("tag");
    TagDao tagDao = new TagDao();
    if(request.getUserPrincipal() != null){
        boolean created = tagDao.createTagRelation(tags, picId);
        if(!created){
            err = "Failed to create new album :( ";
        }
    }


%>
<jsp:forward page="picture.jsp" >
    <jsp:param name="picture_id" value="<%=picId%>" />
</jsp:forward>
</body>
</html>