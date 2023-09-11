package vn.longvan.training.spring.user.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import vn.longvan.training.spring.user.controller.LoginController;
import vn.longvan.training.spring.user.controller.UserController;
import vn.longvan.training.spring.user.model.ResponseObject;
import vn.longvan.training.spring.user.service.AuthenticationService;
import vn.longvan.training.spring.user.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RegisterUserTest {
    @Mock
    private AuthenticationService authService;
    @Mock
    private UserService userService;

    private UserController userController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userController = new UserController(userService, authService);
    }

    @Test
    public void testRegisterUser(){
        RegisterRequest registerRequest = new RegisterRequest();
        AuthenticationResponse authResponse = new AuthenticationResponse("test token");

        when(authService.register(registerRequest)).thenReturn(authResponse);

        ResponseEntity<ResponseObject> result = userController.register(registerRequest);

        verify(authService, times(1)).register(registerRequest);

        assertEquals(authResponse.getToken(), result.getBody().getData());
    }
}
