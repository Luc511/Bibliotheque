<%@ page import="java.util.ArrayList" %>
<%@ page import="classes.Book" %>
<%@ page import="classes.Queries" %>
<%@ page import="classes.User" %><%--
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
        <h1 class="display-4">Bibliothèque - Modifier un livre</h1>
    </div>

    <form method="post" action="#">
        <div class="form-group">
            <label for="book">Choisissez le livre à modifier :</label>
            <select class="form-control" id="book" name="isbn">
                <!-- fetch books and fill the options -->
                <!-- please replace this part with the code which can get the list of available books -->
                <%
                    ArrayList<Book> books = Queries.displayAvailableBooks(); // you should implement this method that returns all books
                    for (Book book : books) {
                %>
                <option value="<%= book.getISBN() %>"><%= book.getTitle() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Choisir ce livre</button>
    </form>

    <%
        String isbn = request.getParameter("isbn");

        if (isbn != null) {
            Book bookToEdit = Queries.findByISBN(isbn);
            if (bookToEdit != null) {
    %>

    <div class="jumbotron text-center">
        <h2 class="display-5">Modifier les détails du livre</h2>
    </div>

    <form method="post" action="#">
        <div class="form-group">
            <label for="titre">Titre</label>
            <input type="text" class="form-control" id="titre" name="titre" required value="<%= bookToEdit.getTitle() %>">
        </div>

        <div class="form-group">
            <label for="auteur">Auteur</label>
            <input type="text" class="form-control" id="auteur" name="auteur" required value="<%= Queries.getAuthorNameById(bookToEdit.getAuthorId()) %>">
        </div>

        <div class="form-group">
            <label for="annee">Année de publication</label>
            <input type="number" class="form-control" id="annee" name="annee" required value="<%= bookToEdit.getYearOfPublication() %>">
        </div>

        <input type="hidden" name="isbn" value="<%= bookToEdit.getISBN() %>">

        <button type="submit" class="btn btn-primary">Modifier le livre</button>
    </form>
    <%
        String ISBN = request.getParameter("isbn");
        String title = request.getParameter("titre");
        String author = request.getParameter("auteur");
        String year = request.getParameter("annee");

        if (isbn != null && title != null && author != null && year != null) {
            Book modifiedBook = Book.builder()
                    .bookId(-1)
                    .ISBN(ISBN)
                    .title(title)
                    .authorId(Queries.getAuthorIdByName(author))
                    .yearOfPublication(Integer.parseInt(year))
                    .available(true)
                    .build();
            Queries.modifyBook(bookToEdit, modifiedBook, user);

        }
    %>
    <%
    } else {
    %>
    <div class="alert alert-danger mt-4">Aucun livre trouvé avec l'ISBN donné</div>
    <%
            }
        }
    %>

    <a class="btn btn-primary mt-4" href="dashboard.jsp" role="button">Retour à l'accueil</a>
</div>
</body>
</html>