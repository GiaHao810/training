package vn.longvan.training.spring.user.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.longvan.training.spring.user.auth.AuthenticationResponse;
import vn.longvan.training.spring.user.service.AuthenticationService;
import vn.longvan.training.spring.user.auth.RegisterRequest;
import vn.longvan.training.spring.user.model.ResponseObject;
import vn.longvan.training.spring.user.model.User;
import vn.longvan.training.spring.user.service.ExelExport;
import vn.longvan.training.spring.user.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "/user")
@RequiredArgsConstructor
public class UserController  {
    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private final UserService userService;

    private final AuthenticationService authService;

//    Find All User
    @GetMapping("")
    List<User> getAllUser(){
        logger.info("Find All User");
        return userService.findAll();
    }

//    User Registration
    @PostMapping("/register")
    public ResponseEntity<ResponseObject> register(
            @RequestBody RegisterRequest request
    ){
        Optional<User> users = userService.findByName(request.getName());

        if(users.isEmpty()){
            AuthenticationResponse jwtToken = authService.register(request);

            logger.info("User Created");

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "User Created", jwtToken.getToken())
            );
        } else {
            logger.info("User Existed");
            return ResponseEntity.status(HttpStatus.FOUND).body(
                    new ResponseObject("FOUND", "User Existed", request.getName())
            );
        }
    }

//    Update User
    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseObject> updateUser(@RequestBody User updateUser,@PathVariable String id){
        Optional<User> foundUser = userService.findById(id);

        if(foundUser.isPresent()){
            User newUser = new User();

            newUser.setName(updateUser.getName());
            newUser.setStatus(updateUser.getStatus());
            newUser.setPhone(updateUser.getPhone());
            newUser.setGender(updateUser.getGender());
            newUser.setBirthDate(updateUser.getBirthDate());
            newUser.setDescription(updateUser.getDescription());
            newUser.setAddress(updateUser.getAddress());
            newUser.setEmail(updateUser.getEmail());
            newUser.setPassword(updateUser.getPassword());
            newUser.setId(id);

            userService.updateUser(newUser);
            logger.info("User Updated");
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "User Updated", newUser.toString())
            );
        }

        logger.info("User Non-Existed");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResponseObject("NOT_FOUND", "User Non-Existed", null)
        );
    }

//    Delete User
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseObject> deleteUser(@RequestBody String id){
        Boolean idExist = userService.existById(id);
        logger.info(id);
        if(idExist){
            logger.info("User with id '" + id + "' deleted");
            userService.deleteUser(id);

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject("OK", "User with id '" + id + "' deleted", null)
            );
        } else {
            logger.info("ID User Non-Exist");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResponseObject("NOT_FOUND", "ID User Non_Exist", id)
            );
        }
    }

    @GetMapping("/export")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("HH:mm-dd-MM-yy");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.findAll();

        ExelExport excelExporter = new ExelExport(listUsers);

        excelExporter.export(response);

        logger.info("Export User to Excel");
    }

    @PostMapping("/filter")
    ResponseEntity<ResponseObject> filterUser(@RequestBody String keyword) {
        List<User> user = userService.findUsers(keyword);
        logger.info("Filter Users");
            if(!user.isEmpty()){
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject("OK", "FOUND USER", user.toArray())
                );
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResponseObject("NOT FOUND", "USER NON EXIST", keyword)
                );
            }
    }
}
