package vn.longvan.training.spring.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import vn.longvan.training.spring.user.manager.UserManager;
import vn.longvan.training.spring.user.model.User;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserManager userManager;

    @Override
    public void updateUser(User updateUser) {
        userManager.save(updateUser);
    }

    @Override
    public void deleteUser(String userID) {
        userManager.deleteById(userID);
    }

    @Override
    public List<User> findUsers(String keyword) {   return userManager.searchUsers(keyword);  }

    @Override
    public List<User> findAll() {   return userManager.findAll();   }

    public Optional<User> findByName(String name){
        return userManager.findByName(name);
    }

    public void deleteById(String id){ userManager.deleteById(id); }

    @Override
    public Boolean existById(String id) {   return userManager.existsById(id);  }

    @Override
    public Optional<User> findById(String id){
        return userManager.findById(id);
    }
}
