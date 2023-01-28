package com.qupp.mail.respository;

import com.qupp.mail.dto.EmailToken;
import com.qupp.user.repository.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailTokenRepository extends JpaRepository<EmailToken, Long> {
    Optional<EmailToken> findByVerificationToken(String token);
}
