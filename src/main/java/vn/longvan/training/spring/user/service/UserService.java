package vn.longvan.training.spring.user.service;
import org.springframework.stereotype.Service;
import vn.longvan.training.spring.user.model.User;
import java.util.List;
import java.util.Optional;


public interface UserService {
    void updateUser(User user);
    void deleteUser(String userID);
    List<User> findUsers(String keyword);
    List<User> findAll();
    Optional<User> findByName(String name);
    Boolean existById(String id);
    Optional<User> findById(String id);
}
