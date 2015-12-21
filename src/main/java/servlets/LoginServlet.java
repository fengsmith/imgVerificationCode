package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * author:      shfq
 * create date: 2015/12/16.
 */
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String> userMap = new HashMap<String, String>();
        userMap.put("user", "111111");

        String loginName = req.getParameter("loginName");
        String password = req.getParameter("password");

        if (userMap.containsKey(loginName) || userMap.get(loginName).equals(password)) {
            req.getRequestDispatcher("/WEB-INF/pages/success.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/WEB-INF/pages/fail.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
