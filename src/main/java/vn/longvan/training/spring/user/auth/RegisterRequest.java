package vn.longvan.training.spring.user.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    private String name;
    private String birthDate;
    private String description;
    private String gender;
    private String address;
    private String phone;
    private String status;
    private String email;
    private String password;
}
