package vn.longvan.training.spring.user.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import vn.longvan.training.spring.user.controller.LoginController;
import vn.longvan.training.spring.user.service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class LoginUserTest {
    @Mock
    private AuthenticationService authService;
    private LoginController loginController;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        loginController = new LoginController(authService);
    }

    @Test
    public void testLoginUser(){
        AuthenticationRequest request = new AuthenticationRequest("testUser", "pass");
        AuthenticationResponse response = new AuthenticationResponse("testtoken");

        when(authService.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> result = loginController.login(request);

        verify(authService, times(1)).authenticate(request);

        assertEquals(response.getToken(), result.getBody().getToken());
    }
}
