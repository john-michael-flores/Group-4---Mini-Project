package com.miniproject.group4.controller;

import com.miniproject.group4.enums.UserRoles;
import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.User;
import com.miniproject.group4.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping
    public ResponseEntity<Page<User>> getAllUsers(Pageable pageable){
        return new ResponseEntity<>(userService.getAllUser(pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(userService.getUserById(id), HttpStatus.OK);
    }

    @GetMapping("/role/{role}")
    public ResponseEntity<Page<User>> getUserByRole(@PathVariable UserRoles type, Pageable pageable){
        return new ResponseEntity<>(userService.getUserByRole(type, pageable), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserById(@RequestBody User user, @PathVariable Long id) throws RecordNotFoundException {
        return new ResponseEntity<>(userService.updateUser(user, id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        return new ResponseEntity<>("USER " + id + " is deleted.", HttpStatus.OK);
    }
}
