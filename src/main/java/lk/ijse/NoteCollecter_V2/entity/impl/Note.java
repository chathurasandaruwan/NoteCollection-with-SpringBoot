package lk.ijse.NoteCollecter_V2.entity.impl;

import jakarta.persistence.*;
import lk.ijse.NoteCollecter_V2.entity.SuperEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "note")
public class Note implements SuperEntity {
    @Id
    private String noteId;
    private String noteTitle;
    private String noteDesc;
    private String createDate;
    private String priorityLevel;

    @ManyToOne
    @JoinColumn(name = "userId",nullable = false)
    private User user;
}
