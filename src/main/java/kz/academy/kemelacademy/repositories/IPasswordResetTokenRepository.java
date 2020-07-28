package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.PasswordResetTokenEntity;
import org.springframework.data.repository.CrudRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
public interface IPasswordResetTokenRepository extends CrudRepository<PasswordResetTokenEntity, Long> {
    
    PasswordResetTokenEntity findByToken(String token);
    
}
