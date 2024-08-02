package com.example.journeyednt.repository;

import com.example.journeyednt.entity.Role;
import com.example.journeyednt.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByNickname(String nickName);
    Optional<User> findByAccountId(String accountId);
    boolean existsByAccountId(String accountId);
    boolean existsByNickname(String nickName);

    @Query("SELECT u.nickname FROM User u WHERE u.id = :accountId")
    Optional<String> findNicknameById(@Param("accountId") String accountId);

    @Modifying
    @Query("UPDATE User u SET u.isVisible = :isVisible WHERE u.accountId = :accountId")
    int updateUserVisibleByAccountId(@Param("accountId") String accountId, @Param("isVisible") Boolean isVisible);

    @Modifying
    @Query("UPDATE User u SET u.role = :role WHERE u.accountId = :accountId")
    int updateUserRoleByAccountId(@Param("accountId") String accountId, @Param("role") Role role);
}
