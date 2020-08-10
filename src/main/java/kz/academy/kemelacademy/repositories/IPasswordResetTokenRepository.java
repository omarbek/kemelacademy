package kz.academy.kemelacademy.repositories;

import kz.academy.kemelacademy.ui.entity.PasswordResetTokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Omarbek.Dinassil
 * on 2020-07-28
 * @project kemelacademy
 */
public interface IPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenEntity, Long> {
    
    PasswordResetTokenEntity findByToken(String token);
    
}
