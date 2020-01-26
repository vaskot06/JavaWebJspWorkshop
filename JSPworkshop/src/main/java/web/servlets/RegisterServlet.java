package web.servlets;

import models.service.UserCreateServiceModel;
import services.HashingService;
import services.UserService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/users/register")
public class RegisterServlet extends HttpServlet {

    private final UserService userService;
    private final HashingService hashingService;
    private final EntityManager entityManager;

    @Inject
    public RegisterServlet(UserService userService, HashingService hashingService, EntityManager entityManager) {
        this.userService = userService;
        this.hashingService = hashingService;
        this.entityManager = entityManager;
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirmPassword = req.getParameter("confirmPassword");

        UserCreateServiceModel userCreateServiceModel = new UserCreateServiceModel();
        userCreateServiceModel.setUsername(username);
        userCreateServiceModel.setEmail(email);
        userCreateServiceModel.setPassword(hashingService.hash(password));
        userCreateServiceModel.setConfirmPassword(hashingService.hash(confirmPassword));

        try {
            userService.registerUser(userCreateServiceModel);
            resp.sendRedirect("/home");
        } catch (Exception e) {
            resp.sendRedirect("/users/register");
        }


    }
}
