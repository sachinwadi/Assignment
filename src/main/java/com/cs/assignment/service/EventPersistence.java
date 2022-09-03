package com.cs.assignment.service;

import java.io.IOException;

public interface EventPersistence {

    void persistEvents(String file) throws IOException;
}
