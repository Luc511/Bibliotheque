<%@ page import="java.util.ArrayList" %>
<%@ page import="classes.Book" %>
<%@ page import="classes.Queries" %>
<%@ page import="java.sql.SQLException" %><%--
  Created by IntelliJ IDEA.
  User: lucasbalon
  Date: 04/10/2023
  Time: 11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% ArrayList<Book> books = null;
    try {
        books = Queries.displayAvailableBooks();
    } catch (SQLException e) {
        throw new RuntimeException(e);
    } %>
<html>
<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css">
    <title>Bibliothèque - Livres disponibles</title>
</head>
<body>
<div class="container py-5">
    <h1 class="mb-4">Livres disponibles:</h1>
    <ul class="list-group">
    <% for(Book b: books) {%>
        <li class="list-group-item"><%= b.toString()%></li>
    <%}%>
    </ul>
    <a class="btn btn-primary mt-4" href="dashboard.jsp" role="button">Retour à l'accueil</a>
</div>
</body>
</html>
