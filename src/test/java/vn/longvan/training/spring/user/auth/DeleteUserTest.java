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
import vn.longvan.training.spring.user.model.User;
import vn.longvan.training.spring.user.service.AuthenticationService;
import vn.longvan.training.spring.user.service.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DeleteUserTest {

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
        String id = "64d607590e68371366566cc5";

        when(userService.existById(id)).thenReturn(true);

        ResponseEntity<ResponseObject> expectedResult = ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject("OK", "User with id '" + id + "' deleted", null)
        );

        ResponseEntity<ResponseObject> actualResult = userController.deleteUser(id);

        verify(userService, times(1)).deleteUser(id);
        verify(userService, times(1)).existById(id);

        assertEquals(expectedResult.getBody().getStatus(), actualResult.getBody().getStatus());
        assertEquals(expectedResult.getBody().getMessage(), actualResult.getBody().getMessage());
    }
}
