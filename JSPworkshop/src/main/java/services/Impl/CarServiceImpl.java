package services.Impl;

import models.entities.Car;
import models.entities.User;
import models.service.CarCreateServiceModel;
import models.view.CarViewModel;
import org.modelmapper.ModelMapper;
import services.CarService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;

public class CarServiceImpl implements CarService {

    private final EntityManager entityManager;
    private final ModelMapper modelMapper;


    @Inject
    public CarServiceImpl(EntityManager entityManager, ModelMapper modelMapper) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
    }

    @Override
    public void createCar(CarCreateServiceModel carCreateServiceModel) {

        entityManager.getTransaction().begin();

        Car car = this.modelMapper.map(carCreateServiceModel, Car.class);
        String username = carCreateServiceModel.getUsername().toString();
        User user = entityManager.createQuery("select u from User u where u.username =:username", User.class)
                .setParameter("username", username)
                .getSingleResult();
        car.setUser(user);

        entityManager.persist(car);

        entityManager.getTransaction().commit();
    }

    @Override
    public List<CarViewModel> viewAll() {
        return this.entityManager.createQuery("select c from Car c", Car.class)
                .getResultList().stream()
                .map(c-> this.modelMapper.map(c, CarViewModel.class))
                .collect(Collectors.toList());
    }
}
