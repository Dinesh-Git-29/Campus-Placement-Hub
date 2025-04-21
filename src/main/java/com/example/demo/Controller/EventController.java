package com.example.demo.Controller;

import com.example.demo.Repository.EventRepository;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Model.Event;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/events")
@CrossOrigin("*") // Allow frontend to access backend
public class EventController {

    private final EventRepository eventRepository;

    public EventController(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    // 📌 Add an Event (Admin)
    @PostMapping("/add")
    public Event addEvent(@RequestBody Event event) {
        return eventRepository.save(event);
    }

    // 📌 Get All Events (Student)
    @GetMapping("/all")
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    // 📌 Get Only Future Events
    @GetMapping("/upcoming")
    public List<Event> getUpcomingEvents() {
        LocalDate today = LocalDate.now();
        return eventRepository.findAll().stream()
                .filter(event -> LocalDate.parse(event.getEventDate()).isAfter(today))
                .toList();
    }

    // 📌 Delete an Event by ID
    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable Long id) {
        eventRepository.deleteById(id);
    }
}
