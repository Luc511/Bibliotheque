<%@ page import="classes.Book" %>
<%@ page import="classes.Queries" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: lucasbalon
  Date: 04/10/2023
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="fr">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <!-- Bootstrap CSS -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">

    <title> Recherche de livre </title>
</head>
<body class="bg-light">
<div class="container py-5">
    <div class="jumbotron text-center">
        <h1 class="display-4">Bibliothèque</h1>
        <p class="lead">Choisissez un livre pour voir plus de détails</p>
    </div>

    <form method="post" action="#">
        <div class="form-group">
            <label for="book">Livres:</label>
            <select class="form-control" id="book" name="isbn">
                <%
                    ArrayList<Book> books = Queries.displayBooks();
                    for (Book book : books) {
                %>
                <option value="<%= book.getISBN() %>"><%= book.getTitle() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Soumettre</button>
    </form>

    <%
        String isbn = request.getParameter("isbn");
        if (isbn != null) {
            Book book = null;
            try {
                book = Queries.findByISBN(isbn);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            if (book != null) {
    %>
    <div class="card mt-4">
        <div class="card-body">
            <h5 class="card-title"><%= book.getTitle() %></h5>
            <h6 class="card-text">Auteur: <%=Queries.getAuthorNameById(book.getAuthorId())  %></h6>
            <p class="card-text">Année: <%= book.getYearOfPublication() %></p>
            <p class="card-text">ISBN: <%= book.getISBN() %></p>
            <p class="card-text">Disponible: <%= book.isAvailable() ? "Oui" : "Non" %></p>
        </div>
    </div>
    <%
    } else {
    %>
    <p class="mt-4">Aucun livre trouvé avec cet ISBN</p>
    <%
            }
        }
    %>
    <a class="btn btn-primary mt-4" href="dashboard.jsp" role="button">Retour à l'accueil</a>
</div>
</body>
</html>
