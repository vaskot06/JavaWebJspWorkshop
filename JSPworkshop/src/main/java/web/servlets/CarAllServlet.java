package web.servlets;

import models.view.CarViewModel;
import services.CarService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/cars/all")
public class CarAllServlet extends HttpServlet {

    private final CarService carService;

    @Inject
    public CarAllServlet(CarService carService) {
        this.carService = carService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CarViewModel> cars = carService.viewAll();
        req.setAttribute("viewModel", cars);

        req.getRequestDispatcher("/car-all.jsp").forward(req, resp);

    }

}
