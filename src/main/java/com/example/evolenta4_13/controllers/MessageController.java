package com.example.evolenta4_13.controllers;

import com.example.evolenta4_13.models.Message;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/messages")
public class MessageController {
    private List<Message> messages;

    {
        messages = new ArrayList<>();
        messages.add(new Message(1, "hello", "Hello, Java", LocalDateTime.of(2023, 8, 12, 12, 3)));
        messages.add(new Message(2, "bye", "Good night!", LocalDateTime.of(2023, 8, 12, 22, 30)));
        messages.add(new Message(3, "moon", "How are you?", LocalDateTime.of(2023, 8, 13, 10, 1)));
        messages.add(new Message(4, "Name", "What is your name?", LocalDateTime.of(2022, 12, 28, 10, 3)));
    }

    @GetMapping()
    public Iterable<Message> getMessage() {
        return messages;
    }

    @GetMapping("/{id}")
    public Optional<Message> findMessageById(@PathVariable int id) {
        return messages.stream().filter(p -> p.getId() == id).findFirst();
    }

    @PostMapping()
    public Message addMessage(@RequestBody Message message) {
        messages.add(message);
        return message;
    }

    @PutMapping("/{id}")
    public ResponseEntity<Message> updateMessage(@PathVariable int id, @RequestBody Message message) {
        int index = -1;
        for (Message p : messages) {
            if (p.getId() == id) {
                index = messages.indexOf(p);
                messages.set(index, message);
            }
        }
        return index == -1
                ? new ResponseEntity<>(addMessage(message), HttpStatus.CREATED)
                : new ResponseEntity<>(message, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public void deleteMessage(@PathVariable int id) {
        messages.removeIf(p -> p.getId() == id);
    }
}
