package lk.ijse.NoteCollecter_V2.service.impl;

import jakarta.transaction.Transactional;
import lk.ijse.NoteCollecter_V2.customStatusCode.SelectedNoteErrorStatus;
import lk.ijse.NoteCollecter_V2.dao.NoteDAO;
import lk.ijse.NoteCollecter_V2.dto.NoteStatus;
import lk.ijse.NoteCollecter_V2.dto.impl.NoteDTO;
import lk.ijse.NoteCollecter_V2.entity.impl.Note;
import lk.ijse.NoteCollecter_V2.exeption.DataPersistException;
import lk.ijse.NoteCollecter_V2.exeption.NoteNotFoundException;
import lk.ijse.NoteCollecter_V2.service.NoteService;
import lk.ijse.NoteCollecter_V2.util.AppUtil;
import lk.ijse.NoteCollecter_V2.util.Mapping;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class NoteServiceImpl implements NoteService {
    @Autowired
    private NoteDAO noteDAO;
    @Autowired
    private Mapping mapping ;

    @Override
    public void saveNote(NoteDTO noteDTO) {
        noteDTO.setNoteId(AppUtil.generateNoteId());

        Note savedNote = noteDAO.save(mapping.toNoteEntity(noteDTO));
        if (savedNote == null) {
            throw new DataPersistException("Note Not Saved");
        }
    }

    @Override
    public List<NoteDTO> getAllNotes() {
        List<Note> notes = noteDAO.findAll();
        return mapping.asNoteDTOList(notes);
    }

    @Override
    public NoteStatus getSelectedNote(String noteId) {
        if (!(noteDAO.existsById(noteId))) {
            return new SelectedNoteErrorStatus(2,"User Id is Not Valid");
        }
        return mapping.toNoteDTO(noteDAO.getReferenceById(noteId));
    }

    @Override
    public void updateNote(String noteId, NoteDTO noteDTO) {
        Optional<Note> findNote = noteDAO.findById(noteId);
        if (!findNote.isPresent()) {
            throw new NoteNotFoundException("Note not found");
        }else {
            findNote.get().setNoteTitle(noteDTO.getNoteTitle());
            findNote.get().setNoteDesc(noteDTO.getNoteDesc());
            findNote.get().setCreateDate(noteDTO.getCreateDate());
            findNote.get().setPriorityLevel(noteDTO.getPriorityLevel());
        }
    }

    @Override
    public void deleteNote(String noteId) {
        Optional<Note> SelectedNote = noteDAO.findById(noteId);
        if (!SelectedNote.isPresent()) {
            throw new NoteNotFoundException("Note Not Found");
        } else {
            noteDAO.deleteById(noteId);
        }
    }
}
