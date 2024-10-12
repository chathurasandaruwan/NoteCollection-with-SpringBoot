package lk.ijse.NoteCollecter_V2.dao;

import lk.ijse.NoteCollecter_V2.entity.impl.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteDAO extends JpaRepository<Note, String> {
}
