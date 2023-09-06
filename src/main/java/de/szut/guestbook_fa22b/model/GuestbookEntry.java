package de.szut.guestbook_fa22b.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="Entry")
public class GuestbookEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String title;

    private String comment;

    private String commenter;

    @Column(name="date_of_entry", nullable = false, updatable = false)
    private LocalDate date= LocalDate.now();


    public void update

}
