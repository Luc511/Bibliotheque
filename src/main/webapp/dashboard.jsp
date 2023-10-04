<%@ page import="classes.User" %>
<%@ page import="classes.Role" %><%--
  Created by IntelliJ IDEA.
  classes.User: lucasbalon
  Date: 04/10/2023
  Time: 09:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User) request.getAttribute("user");%>
<html>
<head>
    <title>Title</title>
</head>
<body>

<h1>Bibliothèque - Dashboard</h1>

<h2>Bienvenue, <%= user.getFirstName() %></h2>

<h3>Actions disponibles:</h3>
<ul>
    <li><a href="displayBooks.jsp">Afficher les livres disponibles</a></li>
    <li><a href="bookInfo.jsp">Afficher les informations d'un livre</a></li>
    <li><a href="borrowBook.jsp">Modifier un livre</a></li>
    <li><a href="returnBook.jsp">Modifier un livre</a></li>


    <%
        if (user.getRole().equals(Role.ADMIN)) {
    %>
    <h3>Fonctionnalitées admin:</h3>
    <li><a href="addBook.jsp">Ajouter un livre</a></li>
    <li><a href="modifyBook.jsp">Modifier un livre</a></li>
    <li><a href="deleteBook.jsp">Supprimer un livre</a></li>


    <%
        }
    %>

</ul>
</body>
</html>
