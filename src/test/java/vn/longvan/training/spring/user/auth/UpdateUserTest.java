package vn.longvan.training.spring.user.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.longvan.training.spring.user.controller.UserController;
import vn.longvan.training.spring.user.model.ResponseObject;
import vn.longvan.training.spring.user.model.Role;
import vn.longvan.training.spring.user.model.User;
import vn.longvan.training.spring.user.service.AuthenticationService;
import vn.longvan.training.spring.user.service.UserService;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UpdateUserTest {

    private AuthenticationService authService;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService, authService);
    }

    @Test
    public void testUpdateUser(){
        User user = new User();
        String id = "64d607590e68371366566cc5";

        when(userService.findById(id)).thenReturn(Optional.of(user));

        ResponseEntity<ResponseObject> expectedResult = ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "User Updated", user.toString())
        );

        ResponseEntity<ResponseObject> actualResult = userController.updateUser(user, id);

        verify(userService, times(1)).findById(id);
        verify(userService, times(1)).updateUser(any(User.class));

        assertEquals(expectedResult.getBody().getStatus(), actualResult.getBody().getStatus());
        assertEquals(expectedResult.getBody().getMessage(), actualResult.getBody().getMessage());
    }
}
