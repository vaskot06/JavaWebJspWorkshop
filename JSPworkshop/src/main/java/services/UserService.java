package services;

import models.service.UserCreateServiceModel;
import models.service.UserServiceModel;

public interface UserService {

    void registerUser(UserCreateServiceModel userCreateServiceModel) throws Exception;

    UserServiceModel loginUser(String username, String password);
}
