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
<body class="bg-light">
<div class="container py-5">
    <div class="jumbotron text-center">
        <h1 class="display-4">Bibliothèque - Emprunter un livre</h1>
    </div>

    <form method="post" action="#">
        <div class="form-group">
            <label for="book">Livres disponibles:</label>
            <select class="form-control" id="book" name="isbn">
                <!-- fetch books and fill the options -->
                <!-- please replace this part with the code which can get the list of available books -->
                <%
                    ArrayList<Book> books = Queries.displayAvailableBooks();
                    for (Book book : books) {
                %>
                <option value="<%= book.getISBN() %>"><%= book.getTitle() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Emprunter</button>
    </form>

    <%
        String isbn = request.getParameter("isbn");
        if (isbn != null) {
            boolean result = Queries.borrowBook(Queries.findByISBN(isbn), (User) request.getSession().getAttribute("loggedUser"));
            if (result) {
    %>
    <div class="alert alert-success mt-4">Le livre a été emprunté avec succès</div>
    <%
    } else {
    %>
    <div class="alert alert-danger mt-4">Il y a eu un problème lors de l'emprunt du livre </div>
    <%
            }
        }
    %>
    <a class="btn btn-primary mt-4" href="dashboard.jsp" role="button">Retour à l'accueil</a>
</div>
</body>
</html>
