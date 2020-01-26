package web.servlets;

import models.entities.EngineEnum;
import models.service.CarCreateServiceModel;
import services.CarService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class CarCreateServlet extends HttpServlet {

    private final CarService carService;

    @Inject
    public CarCreateServlet(CarService carService) {
        this.carService = carService;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/car-create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        String brand = req.getParameter("brand");
        String model = req.getParameter("model");
        int year = Integer.parseInt(req.getParameter("year"));
        EngineEnum engine = EngineEnum.valueOf(req.getParameter("engine"));

        CarCreateServiceModel carCreateServiceModel = new CarCreateServiceModel();
        carCreateServiceModel.setBrand(brand);
        carCreateServiceModel.setModel(model);
        carCreateServiceModel.setYear(year);
        carCreateServiceModel.setEngine(engine);
        Object username = req.getSession()
                .getAttribute("user");

        carCreateServiceModel.setUsername(username);
        carService.createCar(carCreateServiceModel);

        resp.sendRedirect("/home");
    }
}
