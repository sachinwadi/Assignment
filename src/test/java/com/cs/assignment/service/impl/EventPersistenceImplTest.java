package com.cs.assignment.service.impl;

import com.cs.assignment.repository.EventsLogRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(OutputCaptureExtension.class)
public class EventPersistenceImplTest {
    @InjectMocks
    @Autowired
    private EventPersistenceImpl persistenceService;

    @Mock
    private EventsLogRepository repository;

    @Test
     void whenValidFile_ThenPersistData(CapturedOutput output) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Log.txt").getFile());
        Assertions.assertDoesNotThrow(() -> persistenceService.persistEvents(file.getAbsolutePath()));
        verify(repository, atMostOnce()).saveAll(anyCollection());
        assertTrue(output.getOut().contains("File import process completed successfully"));
    }

    @Test
      void whenInvalidFilepath_ThenError(){
        FileNotFoundException exception = assertThrows(FileNotFoundException.class,()->persistenceService.persistEvents("C$$$$\\Data\\Log.txt"));
        assertEquals("C$$$$\\Data\\Log.txt (The system cannot find the path specified)", exception.getMessage());
    }

    @Test
    public void whenInvalidRecord_ThenLogMessageAndContinue(CapturedOutput output){
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("Log.txt").getFile());
        Assertions.assertDoesNotThrow(() -> persistenceService.persistEvents(file.getAbsolutePath()));
        verify(repository, atMostOnce()).saveAll(anyCollection());
        assertTrue(output.getOut().contains("Invalid record 3"));
        assertTrue(output.getOut().contains("File import process completed successfully"));
    }
}