package vn.longvan.training.spring.user.manager;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import vn.longvan.training.spring.user.model.User;


import java.util.List;
import java.util.Optional;


public interface UserManager extends MongoRepository<User, String> {
    Optional<User> findByName(String name);

    @Query("{$or:[{'name': {$regex: ?0, $options: 'i'}}" +
            ", {'birthDate': {$regex: ?0, $options: 'i'}}" +
            ", {'description': {$regex: ?0, $options: 'i'}}" +
            ", {'gender': {$regex: ?0, $options: 'i'}}" +
            ", {'address': {$regex: ?0, $options: 'i'}}" +
            ", {'phone': {$regex: ?0, $options: 'i'}}" +
            ", {'status': {$regex: ?0, $options: 'i'}}" +
            ", {'email': {$regex: ?0, $options: 'i'}}]}"
    )
    List<User> searchUsers(String keyword);
}
