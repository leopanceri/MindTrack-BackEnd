package com.mindtrack.services;

import com.mindtrack.entity.PasswordResetToken;
import com.mindtrack.entity.Usuario;
import com.mindtrack.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Service
public class PasswordTokenService {

    @Autowired
    PasswordResetTokenRepository passwordResetTokenRepository;

    public String criaLinkResetPassword(Usuario usuario, int minutes) {
        this.removeToken(usuario);
        String token = UUID.randomUUID().toString();
        Date expiryDate = calculaValidadeToken(minutes);
        PasswordResetToken passwordResetToken = new PasswordResetToken(token, usuario, expiryDate);
        return passwordResetTokenRepository.save(passwordResetToken).getToken();
    }

    public void removeToken(Usuario usuario){
        PasswordResetToken existingToken = passwordResetTokenRepository.findByUser(usuario);
        if(existingToken != null){
            passwordResetTokenRepository.delete(existingToken);
        }
    }

    private Date calculaValidadeToken(int expiryTimeInMinutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, expiryTimeInMinutes);
        return new Date(cal.getTime().getTime());
    }

    public Usuario validaToken(String token) {
        PasswordResetToken isToken = passwordResetTokenRepository.findByToken(token);
        if (isToken == null || isToken.getExpiryDate().before(new Date())) {
            return null;
        }else{
            return isToken.getUser();
        }
    }
}
