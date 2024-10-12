package lk.ijse.NoteCollecter_V2.service;

import lk.ijse.NoteCollecter_V2.dto.UserStatus;
import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;

import java.util.List;

public interface UserService {
    void saveUser(UserDTO userDTO);
    List<UserDTO> getAllUsers();
    UserStatus getSelectedUser(String userId);
    void deleteUser(String userId);
    void updateUser(UserDTO userDTO ,String userId);
}
