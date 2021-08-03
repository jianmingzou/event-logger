package com.example.technicalchallenge.eventlogger.service;

import java.io.File;

public interface EventProcessService {

    void processInput(File input) throws Exception;
}
