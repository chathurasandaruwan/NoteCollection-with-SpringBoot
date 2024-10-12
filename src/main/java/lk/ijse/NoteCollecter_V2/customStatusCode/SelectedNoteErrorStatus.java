package lk.ijse.NoteCollecter_V2.customStatusCode;

import lk.ijse.NoteCollecter_V2.dto.NoteStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SelectedNoteErrorStatus implements NoteStatus {
    private int statusCode;
    private String StatusMessage;
}
