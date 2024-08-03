package com.example.journeyednt.repository;

import com.example.journeyednt.entity.Role;
import com.example.journeyednt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByNickName(String nickName);

    Optional<User> findByAccountId(String accountId);

    boolean existsByAccountId(String accountId);

    boolean existsByNickName(String nickName);

    @Query("SELECT u.role FROM User u WHERE u.accountId = :accountId")
    Optional<Role> findRoleByAccountId(@Param("accountId") String accountId);

    @Query("SELECT u.isVisible FROM User u WHERE u.accountId = :accountId")
    Boolean findIsVisibleByAccountId(@Param("accountId") String accountId);

    @Query("SELECT u.nickName FROM User u WHERE u.id = :accountId")
    Optional<String> findNickNameById(@Param("accountId") String accountId);

    @Modifying
    @Query("UPDATE User u SET u.isVisible = :isVisible WHERE u.id = :Id")
    int updateUserVisibleById(@Param("Id") Integer Id, @Param("isVisible") Boolean isVisible);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.accountId = :accountId")
    int updateUserRoleByAccountId(@Param("accountId") String accountId, @Param("role") Role role);
}
