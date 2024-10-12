package lk.ijse.NoteCollecter_V2.customStatusCode;

import lk.ijse.NoteCollecter_V2.dto.UserStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedUserErrorStatus implements UserStatus {
    private int statusCode;
    private String StatusMessage;
}
