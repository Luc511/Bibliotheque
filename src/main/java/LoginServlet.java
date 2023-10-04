import classes.Queries;
import classes.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.DriverManager;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String login = req.getParameter("inputLogin");
            String password = req.getParameter("inputPassword");
            User user = Queries.connect(login,password);
            req.setAttribute("user", user);
            req.getRequestDispatcher("/dashboard.jsp").forward(req,resp);
        }catch (Exception e) {
            resp.setStatus(404);
            resp.getWriter().println("<title>Erreur 404, pas bien..</title>");
            resp.getWriter().println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css\">");
            resp.getWriter().println("<div class=\"alert alert-danger\" role=\"alert\">" + e.getMessage()  + "</div>");
            resp.getWriter().println("<a href=\"index.html\">Retour au Login<a>");
        }

    }
}
