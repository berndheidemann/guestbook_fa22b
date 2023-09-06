package de.szut.guestbook_fa22b.controller;


import de.szut.guestbook_fa22b.model.GuestbookEntry;
import de.szut.guestbook_fa22b.repository.GuestbookEntryRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/guestbook")
public class GuestbookController {

    private GuestbookEntryRepository repository;

    public GuestbookController(GuestbookEntryRepository repository) {
        this.repository = repository;
    }

    @PostMapping   // liefert Status 201
    public ResponseEntity<GuestbookEntry> createGuestbookEntry(@RequestBody GuestbookEntry entry) {
        return new ResponseEntity(repository.save(entry), HttpStatus.CREATED);
    }

//    @PostMapping  ---> liefert Status 200
//    public GuestbookEntry createGuestbookEntry(@RequestBody GuestbookEntry entry) {
//        return this.repository.save(entry);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<GuestbookEntry> getById(@PathVariable Long id) {
        var guestbookEntry=this.repository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND)
        );

        return new ResponseEntity<>(guestbookEntry, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<GuestbookEntry>>findAll(@RequestParam(required = false) Integer year) {
       // return new ResponseEntity<>(this.repository.findAll(), HttpStatus.OK);
       if (year==null) {
           return ResponseEntity.ok(this.repository.findAllByOrderById());
       } else {
//           var data=this.repository.findAllByOrderById();
//           var response= data
//                   .stream()
//                   .filter(g -> g.getDate().getYear()==year)
//                   .collect(Collectors.toList());
           return ResponseEntity.ok(this.repository.findAllByDateBetweenOrderByDateDesc(LocalDate.of(year, 1, 1), LocalDate.of(year, 12, 31)));
       }
    }

    // PathVariable --> localhost:8080/teacher/5 --> 5 ist die PathVariable
    // RequestBody --> da liegen die Daten im Body
    // RequestParam --> localhost:8080/teacher?subject=java


    @PutMapping
    public ResponseEntity<GuestbookEntry> update(@PathVariable Long id, @RequestBody GuestbookEntry toUpdate ) {
        var inDb=this.repository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND));
        inDb.setComment(toUpdate.getComment());
        inDb.setDate(toUpdate.getDate());
        inDb.setTitle(toUpdate.getTitle());
        inDb.setCommenter(toUpdate.getCommenter());
        return ResponseEntity.ok(this.repository.save(inDb));
    }
}
