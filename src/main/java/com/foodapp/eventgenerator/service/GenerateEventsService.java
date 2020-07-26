package com.foodapp.eventgenerator.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.text.ParseException;

public interface GenerateEventsService {

    public String generateEvents(int numberOfOrders,int batchSize,Long interval,String outputDirectory) throws IOException, ParseException;

    public String generateOrderId();

    public BufferedWriter createFileWriterObject() throws IOException;



}
