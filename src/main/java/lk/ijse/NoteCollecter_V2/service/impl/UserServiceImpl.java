package lk.ijse.NoteCollecter_V2.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.NoteCollecter_V2.customStatusCode.SelectedUserErrorStatus;
import lk.ijse.NoteCollecter_V2.dao.UserDAO;
import lk.ijse.NoteCollecter_V2.dto.UserStatus;
import lk.ijse.NoteCollecter_V2.dto.impl.UserDTO;
import lk.ijse.NoteCollecter_V2.entity.impl.User;
import lk.ijse.NoteCollecter_V2.exeption.DataPersistException;
import lk.ijse.NoteCollecter_V2.exeption.UserNotFoundException;
import lk.ijse.NoteCollecter_V2.service.UserService;
import lk.ijse.NoteCollecter_V2.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;
    @Autowired
    private Mapping mapping;
    @Override
    public void saveUser(UserDTO userDTO) {
        User saveUser = userDAO.save(mapping.toUseEntity(userDTO));
        if (saveUser==null){
            throw new DataPersistException("User not Saved");
        }
    }

    @Override
    public List<UserDTO> getAllUsers() {
        return mapping.asUserDTOList(userDAO.findAll());
    }

    @Override
    public UserStatus getSelectedUser(String userId) {
        if (!(userDAO.existsById(userId))){
            return new SelectedUserErrorStatus(2,"User Id is Not Valid");
        }
        return mapping.toUserDTO(userDAO.getReferenceById(userId));
    }

    @Override
    public void deleteUser(String userId) {
        Optional<User> existUser = userDAO.findById(userId);
        if (!existUser.isPresent()){
            throw new UserNotFoundException("User Not Found");
        }else {
            userDAO.deleteById(userId);
        }
    }

    @Override
    public void updateUser(UserDTO userDTO,String userId) {
//        userDAO.saveAndFlush(mapping.toUseEntity(userDTO));
        Optional<User> tmpUser = userDAO.findById(userId);
        if (tmpUser.isPresent()){
            tmpUser.get().setFirstName(userDTO.getFirstName());
            tmpUser.get().setLastName(userDTO.getLastName());
            tmpUser.get().setEmail(userDTO.getEmail());
            tmpUser.get().setPassword(userDTO.getPassword());
            tmpUser.get().setProfilePic(userDTO.getProfilePic());
        }
    }
}
