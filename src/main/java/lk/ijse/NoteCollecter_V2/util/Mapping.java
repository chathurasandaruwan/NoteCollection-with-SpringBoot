package lk.ijse.NoteCollecter_V2.util;

import lk.ijse.NoteCollecter_V2.dto.impl.NoteDTO;
import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;
import lk.ijse.NoteCollecter_V2.entity.impl.Note;
import lk.ijse.NoteCollecter_V2.entity.impl.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Mapping {
    @Autowired
    private ModelMapper mapper;

//    for user mapping
    public UserDTO toUserDTO(User user){
        UserDTO userDTO = mapper.map(user,UserDTO.class);
        return userDTO;
    }

    public User toUseEntity(UserDTO userDTO){
        User user = mapper.map(userDTO,User.class);
        return user;
    }

//    for note mapping
    public NoteDTO toNoteDTO(Note note){
        NoteDTO noteDTO = mapper.map(note,NoteDTO.class);
        return noteDTO;
    }

    public Note toNoteEntity(NoteDTO noteDTO){
        Note note = mapper.map(noteDTO,Note.class);
        return note;
    }

    public List<UserDTO> asUserDTOList(List<User> userList){
        List<UserDTO> userDTOList = mapper.map(userList, new TypeToken<List<UserDTO>>(){}.getType());
        return userDTOList;
    }
    public List<NoteDTO> asNoteDTOList(List<Note> noteList){
        List<NoteDTO> noteDTOList = mapper.map(noteList, new TypeToken<List<NoteDTO>>(){}.getType());
        return noteDTOList;
    }
}
