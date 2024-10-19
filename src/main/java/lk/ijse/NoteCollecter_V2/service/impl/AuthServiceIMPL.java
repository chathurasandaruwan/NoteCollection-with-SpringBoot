package lk.ijse.NoteCollecter_V2.service.impl;

import lk.ijse.NoteCollecter_V2.dao.UserDAO;
import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;
import lk.ijse.NoteCollecter_V2.entity.impl.User;
import lk.ijse.NoteCollecter_V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollecter_V2.secure.SignIn;
import lk.ijse.NoteCollecter_V2.service.AuthService;
import lk.ijse.NoteCollecter_V2.service.JWTService;
import lk.ijse.NoteCollecter_V2.util.Mapping;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceIMPL implements AuthService {
    private final UserDAO userDAO;
    private final Mapping mapping;
    private final JWTService jwtService;
    @Override
    public JWTAuthResponse signIn(SignIn signIn) {
        return null;
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
        return null;
    }
}
