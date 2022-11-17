package com.miniproject.group4.repository;

import com.miniproject.group4.enums.UserRoles;
import com.miniproject.group4.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {
    User findByUserName(String userName);

    Page<User> findByRole(UserRoles type, Pageable pageable);
}
