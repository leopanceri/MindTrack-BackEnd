package com.mindtrack.repository;

import com.mindtrack.entity.PasswordResetToken;
import com.mindtrack.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    PasswordResetToken findByUser(Usuario user);
}
