package services.Impl;

import models.entities.User;
import models.service.UserCreateServiceModel;
import models.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import services.HashingService;
import services.UserService;
import services.UsersValidationService;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final EntityManager entityManager;
    private final ModelMapper modelMapper;
    private final HashingService hashingService;
    private final UsersValidationService usersValidationService;


    @Inject
    public UserServiceImpl(EntityManager entityManager, ModelMapper modelMapper, HashingService hashingService, UsersValidationService usersValidationService) {
        this.entityManager = entityManager;
        this.modelMapper = modelMapper;
        this.hashingService = hashingService;
        this.usersValidationService = usersValidationService;
    }

    @Override
    public void registerUser(UserCreateServiceModel userCreateServiceModel) throws Exception {

        if(!usersValidationService.canCreateUser(userCreateServiceModel.getUsername(), userCreateServiceModel.getEmail(),
                userCreateServiceModel.getPassword(), userCreateServiceModel.getConfirmPassword())) {
            throw new Exception("User cannot be created");
        }


        entityManager.getTransaction().begin();

        User user = this.modelMapper.map(userCreateServiceModel, User.class);
        entityManager.persist(user);

        entityManager.getTransaction().commit();
    }

    @Override
    public UserServiceModel loginUser(String username, String password){

        List<User> users =  entityManager.createQuery("SELECT u from User u where u.username =:username", User.class)
                .setParameter("username", username)
                .getResultList();

       if (users.isEmpty()) {
            return null;
        }

        User user = users.get(0);

        if(!user.getPassword().equals(hashingService.hash(password))) {
            return null;
        }

        return modelMapper.map(user, UserServiceModel.class);
    }
}
