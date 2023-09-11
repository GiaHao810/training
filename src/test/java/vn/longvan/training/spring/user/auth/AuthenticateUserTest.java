package vn.longvan.training.spring.user.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import vn.longvan.training.spring.user.controller.AuthenticationController;
import vn.longvan.training.spring.user.controller.LoginController;
import vn.longvan.training.spring.user.service.AuthenticationService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuthenticateUserTest {
    @Mock
    private AuthenticationService authService;

    private AuthenticationController authController;
    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        authController = new AuthenticationController(authService);
    }

    @Test
    public void testAuthUser(){
        AuthenticationRequest request = new AuthenticationRequest("testUser", "pass");
        AuthenticationResponse response = new AuthenticationResponse("testtoken");

        when(authService.authenticate(request)).thenReturn(response);

        ResponseEntity<AuthenticationResponse> actualResults = authController.authenticate(request);

        verify(authService, times(1)).authenticate(request);

        assertEquals(response, actualResults.getBody());
        assertEquals(HttpStatus.OK, actualResults.getStatusCode());
    }
}
