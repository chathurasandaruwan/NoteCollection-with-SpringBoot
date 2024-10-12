package lk.ijse.NoteCollecter_V2.controller;

import lk.ijse.NoteCollecter_V2.customStatusCode.SelectedNoteErrorStatus;
import lk.ijse.NoteCollecter_V2.dto.NoteStatus;
import lk.ijse.NoteCollecter_V2.dto.impl.NoteDTO;
import lk.ijse.NoteCollecter_V2.exeption.DataPersistException;
import lk.ijse.NoteCollecter_V2.exeption.NoteNotFoundException;
import lk.ijse.NoteCollecter_V2.service.NoteService;
import lk.ijse.NoteCollecter_V2.util.RegexProcess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/notes")
public class NoteController {
    @Autowired
    private NoteService noteService;
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> saveNote(@RequestBody NoteDTO noteDTO) {
        try {
            noteService.saveNote(noteDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }catch (DataPersistException e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping(value = "/{noteId}",produces = MediaType.APPLICATION_JSON_VALUE)
    public NoteStatus getSelectedNote(@PathVariable("noteId") String noteId) {
        if (!RegexProcess.noteIdMatcher(noteId)) {
            return new SelectedNoteErrorStatus(1,"Invalid Note Id");
        }
        return noteService.getSelectedNote(noteId);

    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<NoteDTO> getAllNotes() {
        return noteService.getAllNotes();
    }
    @PutMapping(value = "/{noteId}")
    public ResponseEntity<Void> updateNote(@PathVariable("noteId") String noteId,@RequestBody NoteDTO noteDTO) {
        try {
            if(!RegexProcess.noteIdMatcher(noteId) || noteDTO == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            noteService.updateNote(noteId,noteDTO);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }catch (NoteNotFoundException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
   @DeleteMapping("/{noteId}")
    public ResponseEntity<Void> deleteNote(@PathVariable("noteId") String noteId) {
       try {
           if (!RegexProcess.noteIdMatcher(noteId)) {
               return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
           } else {
               noteService.deleteNote(noteId);
               return new ResponseEntity<>(HttpStatus.OK);
           }
       } catch (NoteNotFoundException e) {
           e.printStackTrace();
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }catch (Exception e){
           return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

}
