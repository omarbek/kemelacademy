package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.UserEntity;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-20
 * @project kemelacademy
 */
@Repository
public interface IUserRepository extends PagingAndSortingRepository<UserEntity, Long> {
    
    UserEntity findByEmail(String email);
    
    UserEntity findByUserId(String userId);
    
}
