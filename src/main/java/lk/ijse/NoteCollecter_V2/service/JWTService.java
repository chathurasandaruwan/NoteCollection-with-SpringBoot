package lk.ijse.NoteCollecter_V2.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JWTService {
    String generateToken(UserDetails user);

    String extractUsername(String token);
    boolean validateToken(String token, UserDetails userDetails);
    String refreshToken(UserDetails userDetails);
}
