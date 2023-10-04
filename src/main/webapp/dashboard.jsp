<%@ page import="classes.User" %>
<%@ page import="classes.Role" %><%--
  Created by IntelliJ IDEA.
  classes.User: lucasbalon
  Date: 04/10/2023
  Time: 09:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% User user = (User) request.getSession().getAttribute("loggedUser");%>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <title>Bibliothèque - Dashboard</title>
</head>
<body class="bg-light">
<% if (user != null) {%>

<div class="container py-5">
    <div class="jumbotron text-center">
        <h1 class="display-4">Bibliothèque - Dashboard</h1>
        <p class="lead">Bienvenue, John</p>
    </div>

    <h3 class="mb-3">Actions disponibles:</h3>
    <ul class="list-group mb-4">
        <li class="list-group-item"><a href="displayBooks.jsp">Afficher les livres disponibles</a></li>
        <li class="list-group-item"><a href="bookInfo.jsp">Afficher les informations d'un livre</a></li>
        <li class="list-group-item"><a href="borrowBook.jsp">Emprunter un livre</a></li>
        <li class="list-group-item"><a href="returnBook.jsp">Retourner un livre</a></li>
    </ul>

    <%
        if (user.getRole().equals(Role.ADMIN)) {
    %>
    <h3 class="mb-3">Fonctionnalitées admin:</h3>
    <ul class="list-group">
        <li class="list-group-item"><a href="addBook.jsp">Ajouter un livre</a></li>
        <li class="list-group-item"><a href="modifyBook.jsp">Modifier un livre</a></li>
        <li class="list-group-item"><a href="deleteBook.jsp">Supprimer un livre</a></li>
    </ul>
    <%
        }
    }else {
    %>
    <div class="alert alert-warning" role="alert">
        Attention, vous avez été déconnecté
    </div>

    <a class="btn btn-primary" href="index.html" role="button">Retour au Login</a>
    <%}%>
</div>
</body>
</html>
