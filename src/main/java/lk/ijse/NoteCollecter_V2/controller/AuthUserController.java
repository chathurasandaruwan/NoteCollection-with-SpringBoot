package lk.ijse.NoteCollecter_V2.controller;

import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;
import lk.ijse.NoteCollecter_V2.entity.Role;
import lk.ijse.NoteCollecter_V2.exeption.DataPersistException;
import lk.ijse.NoteCollecter_V2.secure.JWTAuthResponse;
import lk.ijse.NoteCollecter_V2.secure.SignIn;
import lk.ijse.NoteCollecter_V2.service.AuthService;
import lk.ijse.NoteCollecter_V2.service.UserService;
import lk.ijse.NoteCollecter_V2.util.AppUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequestMapping("api/v1/auth/")
@RestController
@RequiredArgsConstructor
public class AuthUserController {
    private final UserService userService;
     private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    @PostMapping(value = "signup", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> saveUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("role") String role,
            @RequestPart("profilePic") MultipartFile profilePic
    ) {
        // profilePic ----> Base64
        String base64ProPic = "";
        try {
            byte[] bytesProPic = profilePic.getBytes();
            base64ProPic = AppUtil.profilePickToBase64(bytesProPic);
            //UserId generate
            String userId = AppUtil.generateUserId();
            //Build the Object
            UserDTO buildUserDTO = new UserDTO();
            buildUserDTO.setUserId(userId);
            buildUserDTO.setFirstName(firstName);
            buildUserDTO.setLastName(lastName);
            buildUserDTO.setEmail(email);
            buildUserDTO.setPassword(passwordEncoder.encode(password));
            buildUserDTO.setProfilePic(base64ProPic);
            buildUserDTO.setRole(Role.valueOf(role));
            return ResponseEntity.ok(authService.signUp(buildUserDTO));
//            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping(value = "signin",consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JWTAuthResponse> signIn(@RequestBody SignIn signIn){
        return ResponseEntity.ok(authService.signIn(signIn));
    }
    @PostMapping("refresh")
    public ResponseEntity<JWTAuthResponse> Token(@RequestParam("refreshToken") String refreshToken) {
        return ResponseEntity.ok(authService.refreshToken(refreshToken));
    }


}
