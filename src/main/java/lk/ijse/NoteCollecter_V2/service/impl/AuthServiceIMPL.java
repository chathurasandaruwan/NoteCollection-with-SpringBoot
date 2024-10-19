package lk.ijse.NoteCollecter_V2.service.impl;

import lk.ijse.NoteCollecter_V2.dao.UserDAO;
import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;
import lk.ijse.NoteCollecter_V2.entity.impl.User;
import lk.ijse.NoteCollecter_V2.exeption.UserNotFoundException;
import lk.ijse.NoteCollecter_V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollecter_V2.secure.SignIn;
import lk.ijse.NoteCollecter_V2.service.AuthService;
import lk.ijse.NoteCollecter_V2.service.JWTService;
import lk.ijse.NoteCollecter_V2.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final UserDAO userDAO;
    private final Mapping mapping;
    private final JWTService jwtService;
    private final AuthenticationManager authenticationManager;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signIn.getEmail(), signIn.getPassword()));
        User user = userDAO.findByEmail(signIn.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        var generateToken = jwtService.generateToken(user);
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse signUp(UserDTO userDTO) {
//        saveUser
        User savedUser = userDAO.save(mapping.toUseEntity(userDTO));
//        generate token and return
        var generateToken = jwtService.generateToken(savedUser);
        return JWTAuthResponse.builder().token(generateToken).build();
    }

    @Override
    public JWTAuthResponse refreshToken(String accessToken) {
//        extractUsername
        var username = jwtService.extractUsername(accessToken);
//        is exists the User
        User user = userDAO.findByEmail(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        var generateToken = jwtService.refreshToken(user);
        return JWTAuthResponse.builder().token(generateToken).build();
    }
}
