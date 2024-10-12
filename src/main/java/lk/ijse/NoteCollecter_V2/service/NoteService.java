package lk.ijse.NoteCollecter_V2.service;

import lk.ijse.NoteCollecter_V2.dto.NoteStatus;
import lk.ijse.NoteCollecter_V2.dto.impl.NoteDTO;

import java.util.List;

public interface NoteService {
    void saveNote(NoteDTO noteDTO);
    List<NoteDTO> getAllNotes();
    NoteStatus getSelectedNote(String noteId);
    void updateNote(String noteId, NoteDTO noteDTO);
    void deleteNote(String noteId);
}
