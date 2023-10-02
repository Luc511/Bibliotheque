<%@ page import="com.example.demo1.Javanais" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: lucasbalon
  Date: 02/10/2023
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<% Javanais javanais = (Javanais) request.getAttribute("javanais"); %>
<% ArrayList<Javanais> javanaisList = (ArrayList<Javanais>) request.getAttribute("list"); %>
        <html>
<head>
    <title>Random</title>
    <link rel="stylesheet" type="text/css" href="style.css" />
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
</head>
<body>
<div>
    <input type="hidden" id="prenom" value="<%= javanais.getPrenom() %>"/>
    <h1>
        <%= javanais.getPrenom()%>, tu es l'élu !
    </h1>
    <h2>Liste des inscrits: </h2>
    <ul>
        <% for(Javanais j: javanaisList) {%>
        <li> <%= j.getPrenom()%></li>
        <%}%>
    </ul>
</div>


<button id="spin">Spin</button>
<span class="arrow"></span>
<div class="container">
    <div class="one">1</div>
    <div class="two">2</div>
    <div class="three">3</div>
    <div class="four">4</div>
    <div class="five">5</div>
    <div class="six">6</div>
    <div class="seven">7</div>
    <div class="eight">8</div>
</div>

<script src="script.js"></script>
</body>
</html>