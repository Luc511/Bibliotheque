
<%@ page import="classes.Book" %>
<%@ page import="classes.Queries" %>
<%@ page import="classes.User" %>
<%--
  Created by IntelliJ IDEA.
  User: lucasbalon
  Date: 04/10/2023
  Time: 11:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
    <title>Bibliothèque</title>
</head>
<% User user = (User) request.getSession().getAttribute("loggedUser");%>
<body class="bg-light">
<div class="container py-5">
    <div class="jumbotron text-center">
        <h1 class="display-4">Bibliothèque - Ajouter un livre</h1>
    </div>

    <form method="post" action="#">
        <div class="form-group">
            <label for="titre">Titre</label>
            <input type="text" class="form-control" id="titre" name="titre" required>
        </div>

        <div class="form-group">
            <label for="auteur">Auteur</label>
            <input type="text" class="form-control" id="auteur" name="auteur" required>
        </div>

        <div class="form-group">
            <label for="annee">Année de publication</label>
            <input type="number" class="form-control" id="annee" name="annee" required>
        </div>

        <div class="form-group">
            <label for="isbn">Numéro ISBN</label>
            <input type="text" class="form-control" id="isbn" name="isbn" required>
        </div>

        <button type="submit" class="btn btn-primary">Ajouter le livre</button>
    </form>

    <%
        String isbn = request.getParameter("isbn");
        String title = request.getParameter("titre");
        String author = request.getParameter("auteur");
        String year = request.getParameter("annee");

        if (isbn != null && title != null && author != null && year != null) {
            boolean result = Queries.addBook(Book.builder()
                    .bookId(-1) //id n'est jamais utilisé après pour un livre encodé
                    .authorId(Queries.getAuthorIdByName(author))
                    .title(title)
                    .yearOfPublication(Integer.parseInt(year))
                    .ISBN(isbn)
                    .available(true)
                    .build(), user);
            if (result) {
    %>
    <div class="alert alert-success mt-4">Le livre a été ajouté avec succès</div>
    <%
    } else {
    %>
    <div class="alert alert-danger mt-4">Il y a eu un problème lors de l'ajout du livre </div>
    <%
            }
        }
    %>
    <a class="btn btn-primary mt-4" href="dashboard.jsp" role="button">Retour à l'accueil</a>
</div>
</body>
</html>