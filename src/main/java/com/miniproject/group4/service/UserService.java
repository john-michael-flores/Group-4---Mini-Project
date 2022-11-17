package com.miniproject.group4.service;

import com.miniproject.group4.enums.UserRoles;
import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface UserService {

    User saveUser(User user);
    Page<User> getAllUser(Pageable pageable);
    User getUserById(Long id) throws RecordNotFoundException;
    Page<User> getUserByRole(UserRoles type, Pageable pageable);
    User updateUser(User updateUser, Long id) throws RecordNotFoundException;
    void deleteUser(Long id);
}
