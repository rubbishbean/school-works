<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page import="photoshare.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head>

  <title>album</title>
</head>
<body>
  <a href="/photoshare/">Back to homepage</a><br><br>

  <%
      int albumId = Integer.parseInt(request.getParameter("albumid"));
      AlbumDao albumDao = new AlbumDao();
      Album currentAlbum = albumDao.getAlbumById(albumId);
      String albumName = currentAlbum.getName();
      NewUserDao currentUser = new NewUserDao();
      String username = request.getUserPrincipal().getName();
      int userid = currentUser.getUserIdByEmail(username);
  %>
  <h2>Upload a new picture</h2>


  <form action="album.jsp?albumid=<%= albumId%>" enctype="multipart/form-data" method="post">
    Filename: <input type="file" name="filename"/></br>
    Caption: <input type="text" name="caption"/></br>
    <input type="submit" value="Upload"/><br/>
  </form>

  <%
    PictureDao pictureDao = new PictureDao();

    try {
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture,currentAlbum.getId());
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }
  %>
  <h2>Existing pictures</h2>   
  <table>
    <tr>
      <%
        List<Integer> pictureIds = pictureDao.allPicturesIds(currentAlbum.getId());
        for (Integer pictureId : pictureIds) {
       %>                                     
      <td><a href="/photoshare/picture.jsp?picture_id=<%= pictureId%>" />
          <img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
        </a>
      </td>
       <%
        }
       %> 
     </tr>
  </table>
 </body> 
</html>
