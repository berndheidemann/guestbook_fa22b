package de.szut.guestbook_fa22b.repository;

import de.szut.guestbook_fa22b.model.GuestbookEntry;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

public interface GuestbookEntryRepository extends JpaRepository<GuestbookEntry, Long> {

    public List<GuestbookEntry> findAllByOrderById();

    public List<GuestbookEntry> findAllByDateBetweenOrderByDateDesc(LocalDate first, LocalDate second);

}
