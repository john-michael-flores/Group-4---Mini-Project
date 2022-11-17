package com.miniproject.group4.service;

import com.miniproject.group4.enums.Message;
import com.miniproject.group4.enums.UserRoles;
import com.miniproject.group4.exception.RecordNotFoundException;
import com.miniproject.group4.model.User;
import com.miniproject.group4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) throw new UsernameNotFoundException(Message.INVALID_USER.toString());
        if(user.getRole().equals(UserRoles.ROLE_ADMIN)){
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), adminAuthority());
        }else{
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), userAuthority());
        }
    }

    private List<SimpleGrantedAuthority> adminAuthority(){
        return Arrays.asList(new SimpleGrantedAuthority(UserRoles.ROLE_ADMIN.toString()));
    }
    private List<SimpleGrantedAuthority> userAuthority(){
        return Arrays.asList(new SimpleGrantedAuthority(UserRoles.ROLE_USER.toString()));
    }

    @Override
    public User saveUser(User user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUser(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) throws RecordNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException(Message.USER.toString()));
    }

    @Override
    public Page<User> getUserByRole(UserRoles type, Pageable pageable) {
        return userRepository.findByRole(type, pageable);
    }

    @Override
    public User updateUser(User updateUser, Long id) throws RecordNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(()-> new RecordNotFoundException(Message.USER.toString()));
        user.setUserName(updateUser.getUserName());
        user.setPassword(new BCryptPasswordEncoder().encode(updateUser.getPassword()));
        user.setRole(updateUser.getRole());
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                        .orElseThrow(() -> new RecoverableDataAccessException(Message.USER.toString()));
        userRepository.delete(user);
    }
}
