package ua.com.nix;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "my-app", urlPatterns = "/")
public class ServletApp extends HttpServlet {

    private final Set<String> responseSet = ConcurrentHashMap.newKeySet();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter respWriter = resp.getWriter();

        String currentUser = req.getRemoteHost() + "::" + req.getHeader("User-Agent");
        responseSet.add(currentUser);

        respWriter.println("<h1>List of accessed users: </h1>");
        respWriter.println("<ul>");
        for (String s: responseSet) {
            respWriter.println("<li><b>" + s + "</b></li>");
        }
        respWriter.println("</ul>");
    }
}