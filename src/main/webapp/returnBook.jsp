<%@ page import="classes.Book" %>
<%@ page import="classes.Queries" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="classes.User" %><%--
  Created by IntelliJ IDEA.
  User: lucasbalon
  Date: 04/10/2023
  Time: 11:45
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
        <h1 class="display-4">Bibliothèque - Rendre un livre</h1>
    </div>

    <form method="post" action="#">
        <div class="form-group">
            <label for="book">Livres empruntés:</label>
            <select class="form-control" id="book" name="isbn">
                <%
                    ArrayList<Book> books = user.borrowedBooks();
                    for (Book book : books) {
                %>
                <option value="<%= book.getISBN() %>"><%= book.getTitle() %></option>
                <%
                    }
                %>
            </select>
        </div>

        <button type="submit" class="btn btn-primary">Retourner</button>
    </form>

    <%
        String isbn = request.getParameter("isbn");
        if (isbn != null) {
            boolean result = Queries.returnBook(Queries.findByISBN(isbn), user);
            if (result) {
    %>
    <div class="alert alert-success mt-4">Le livre a été retourné avec succès</div>
    <%
    } else {
    %>
    <div class="alert alert-danger mt-4">Il y a eu un problème lors du retour du livre </div>
    <%
            }
        }
    %>
    <a class="btn btn-primary mt-4" href="dashboard.jsp" role="button">Retour à l'accueil</a>
</div>
</body>
</html>
