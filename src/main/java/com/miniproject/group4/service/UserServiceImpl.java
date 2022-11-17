package com.miniproject.group4.service;

import com.miniproject.group4.enums.Message;
import com.miniproject.group4.enums.Roles;
import com.miniproject.group4.enums.UserTypes;
import com.miniproject.group4.model.User;
import com.miniproject.group4.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.miniproject.group4.model.User;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if(user == null) throw new UsernameNotFoundException(Message.INVALID_USER.toString());
        if(user.getRole().equals(UserTypes.ADMIN)){
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), adminAuthority());
        }else{
            return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(), userAuthority());
        }
    }

    private List<SimpleGrantedAuthority> adminAuthority(){
        return Arrays.asList(new SimpleGrantedAuthority(Roles.ROLE_ADMIN.toString()));
    }
    private List<SimpleGrantedAuthority> userAuthority(){
        return Arrays.asList(new SimpleGrantedAuthority(Roles.ROLE_USER.toString()));
    }
}
