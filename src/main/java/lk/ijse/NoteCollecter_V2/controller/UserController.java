package lk.ijse.NoteCollecter_V2.controller;

import lk.ijse.NoteCollecter_V2.customStatusCode.SelectedUserErrorStatus;
import lk.ijse.NoteCollecter_V2.dto.UserStatus;
import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;
import lk.ijse.NoteCollecter_V2.exeption.DataPersistException;
import lk.ijse.NoteCollecter_V2.exeption.UserNotFoundException;
import lk.ijse.NoteCollecter_V2.service.UserService;
import lk.ijse.NoteCollecter_V2.util.AppUtil;
import lk.ijse.NoteCollecter_V2.util.RegexProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveUser(
        @RequestPart("firstName") String firstName,
        @RequestPart("lastName") String lastName,
        @RequestPart("email") String email,
        @RequestPart("password") String password,
        @RequestPart("profilePic") MultipartFile profilePic
        ) {
//        generateUserId
        String userId = AppUtil.generateUserId();
//        profilePic ---> Base64
        String base64ProPick = "";

        try {
            byte[] bytesPick = profilePic.getBytes();
            base64ProPick = AppUtil.profilePickToBase64(bytesPick);

            //        Build the Object
            UserDTO userDTO = new UserDTO();
            userDTO.setUserId(userId);
            userDTO.setFirstName(firstName);
            userDTO.setLastName(lastName);
            userDTO.setEmail(email);
            userDTO.setPassword(password);
            userDTO.setProfilePic(base64ProPick);
            userService.saveUser(userDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
//        return userDTO;
        }catch (DataPersistException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{userId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public UserStatus getSelectedUser(@PathVariable("userId") String userId) {
/*        String regexForUser = "^USER-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPatten = Pattern.compile(regexForUser);
        var regexMatcher = regexPatten.matcher(userId);*/
        if (!RegexProcess.userIdMatcher(userId)) {
            return new SelectedUserErrorStatus(1,"User Id is Not Valid");
        }
        return userService.getSelectedUser(userId);
    }
    @GetMapping(value = "/getAllUsers", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserDTO> getAllUsers() {
        return userService.getAllUsers();
    }
    @DeleteMapping(value = "/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") String userId) {
/*        String regexForUser = "^USER-[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
        Pattern regexPatten = Pattern.compile(regexForUser);
        var regexMatcher = regexPatten.matcher(userId);*/
        try {
            if (!RegexProcess.userIdMatcher(userId)) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            userService.deleteUser(userId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (UserNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @ResponseStatus(HttpStatus.ACCEPTED)
    @PutMapping(value = "/{userId}",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(
            @RequestPart("firstName") String firstName,
            @RequestPart("lastName") String lastName,
            @RequestPart("email") String email,
            @RequestPart("password") String password,
            @RequestPart("profilePic") MultipartFile profilePic,
            @PathVariable("userId") String userId) {

//        profilePic ---> Base64
        String base64ProPick = "";

        try {
            byte[] bytesPick = profilePic.getBytes();
            base64ProPick = AppUtil.profilePickToBase64(bytesPick);
        }catch (Exception e) {
            e.printStackTrace();
        }
//        Build the Object
        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(userId);
        userDTO.setFirstName(firstName);
        userDTO.setLastName(lastName);
        userDTO.setEmail(email);
        userDTO.setPassword(password);
        userDTO.setProfilePic(base64ProPick);
        userService.updateUser(userDTO,userId);
    }
}
