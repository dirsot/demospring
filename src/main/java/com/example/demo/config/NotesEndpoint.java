package com.example.demo.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.endpoint.annotation.DeleteOperation;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Component
@Endpoint(id = "notes")
public class NotesEndpoint {

    private List<Note> notes = Arrays.asList(new Note("init"));

    @ReadOperation
    public List<Note> notes() {
        return notes;
    }

    @WriteOperation
    public List<Note> addNote(String text) {
        notes.add(new Note(text));
        return notes;
    }

    @DeleteOperation
    public List<Note> deleteNote(int index) {
        if (index < notes.size()) {
            notes.remove(index);
        }
        return notes;
    }

    @RequiredArgsConstructor
    private class Note {
        @Getter
        private final String text;
        @Getter
        private final Date time = new Date();
    }
}

