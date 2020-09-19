package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Repository
public interface IUserRepository extends JpaRepository<UserEntity, Long> {
    
    @Query("select u from UserEntity u left join fetch u.roles where u.email = :email")
    UserEntity findByEmail(String email);
    
    UserEntity findByUserId(String userId);
    
    UserEntity findUserByEmailVerificationToken(String token);
    
    @Query("SELECT u FROM UserEntity u WHERE u.fullName LIKE CONCAT('%',:name,'%')")
    List<UserEntity> findByName(String name);
    
}
