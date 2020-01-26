package services;

import models.service.CarCreateServiceModel;
import models.view.CarViewModel;

import java.util.List;

public interface CarService {

    void createCar(CarCreateServiceModel carCreateServiceModel);

    List<CarViewModel> viewAll();
}
