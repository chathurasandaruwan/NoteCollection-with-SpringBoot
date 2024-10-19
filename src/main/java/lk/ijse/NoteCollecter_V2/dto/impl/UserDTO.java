package lk.ijse.NoteCollecter_V2.dto.impl;

import lk.ijse.NoteCollecter_V2.dto.UserStatus;
import lk.ijse.NoteCollecter_V2.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO implements UserStatus {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String profilePic;
    private Role role;
    private List<NoteDTO> notes;

}
