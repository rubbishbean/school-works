<%--
--%>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>picture</title>
</head>
<body>
<%
    int picId = Integer.parseInt(request.getParameter("picture_id"));
    NewUserDao currentUser = new NewUserDao();
    PiclikeDao piclikeDao = new PiclikeDao();
    String username = "";
    int userid;
    if(request.getUserPrincipal() == null){
        username = "guest";
        userid = 1;
    }else{
        username = request.getUserPrincipal().getName();
        userid = currentUser.getUserIdByEmail(username);
    }

%>
  <h1>current picture</h1>
  <img src="/photoshare/img?picture_id=<%= picId %>"/>  <br><br>
  <a href="likeToDB.jsp?pic_id=<%= picId%>"><button>Like:<%=piclikeDao.countLike(picId)%></button></a>
  <h2>Tags:</h2>

  <div class="tagList">
      <ul style="display:inline;">
  <%
    TagDao tagDao = new TagDao();
    List<Tag> allTags = tagDao.getAllTagsOfPic(picId);
    for (Tag tag : allTags){
  %>
      <li style="display:inline">
          <a href="selfTag.jsp?tag=<%= tag.getTag()%>"><button><%= tag.getTag() %></button></a>
      </li>
  <%
      }
  %>
      </ul>
  </div>
  <hr>

    <form action="tagToDB.jsp"  method="post">
      Tags: <input type="text" name="tag" placeholder="enter tags, separated by semicolon"></br>
      <input type="hidden" name="pic_id_hidden" value="<%= picId %>" />
      <input type="submit" value="add"/><br/>
    </form>

  <hr>
  <p>Comments:</p> <br>
<%
    CommentDao commentDao = new CommentDao();
    List<Comment> allComments = commentDao.getAllComments(picId);
    for (Comment comment : allComments) {
%>
    <div class="comment">
      <div><%= username %></div><br>
      <p><%= comment.getText() %></p>
      <div>posted on <%= comment.getCommentTime()%></div>
    </div>
    <hr>
<%
    }
%>

  <div class = "create_comment">
      <form action="commentToDB.jsp?pic_id=<%= picId%>" method="post">
          <div class="album_set">
              Wrtie comment:<br>
              <textarea cols=80 rows=3  name="comment_text" class="new_comment"/></textarea><br>
              <input type="submit" value="post" class="submit_comment"/><br>
          </div>
      </form>
  </div>

</body>
</html>