package com.foodapp.eventgenerator.service;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import com.foodapp.eventgenerator.model.Data;
import com.foodapp.eventgenerator.model.FoodAppEvent;
import com.foodapp.eventgenerator.tools.Constants;
import com.foodapp.eventgenerator.tools.ReusableUtils;
import com.google.gson.Gson;

public class GenerateEventsServiceImpl implements GenerateEventsService{

    private static Set<String> orderIds=new HashSet<String>();
    //private static List<FoodAppEvent> foodAppEvents= new ArrayList<FoodAppEvent>();
    Gson gson = new Gson();
    ReusableUtils reusableUtils=new ReusableUtils();
    BufferedWriter bufferedWriter;
    int numberOfOrders,batchSize;
    Long interval;
    String outputDirectory;

    @Override
    public String generateEvents(int numberOfOrders,int batchSize,Long interval,String outputDirectory) throws IOException, ParseException {

        try
        {
            this.numberOfOrders=numberOfOrders;
            this.batchSize=batchSize;
            this.interval=interval;
            this.outputDirectory=outputDirectory;

            String orderID;
            int batchSizeCounter=0;

            //Create the buffered file writer object
            bufferedWriter=createFileWriterObject();

            //Start Time
            String fromTime=reusableUtils.generateSystemTime(Constants.EVENT_DATE_FORMAT);

            for (int i=1;i<=numberOfOrders;i++)
            {

                if (batchSizeCounter==batchSize || reusableUtils.getTimeDifference(fromTime) == interval)
                {
                    bufferedWriter.close();
                    bufferedWriter=createFileWriterObject();
                    batchSizeCounter=0;
                }

                orderID=generateOrderId();
                System.out.println(orderID);
                String event=createFoodAppEvents(orderID,Constants.ORDER_TYPE_PLACED);
                bufferedWriter.write(event);
                bufferedWriter.newLine();
                event="";
                batchSizeCounter++;

                if (batchSizeCounter==batchSize || reusableUtils.getTimeDifference(fromTime) == interval)
                {
                    bufferedWriter.close();
                    bufferedWriter=createFileWriterObject();
                    batchSizeCounter=0;
                }

                if (i%5==0)
                {
                    event=createFoodAppEvents(orderID,Constants.ORDER_TYPE_CANCELLED);
                }
                else
                {
                    event=createFoodAppEvents(orderID,Constants.ORDER_TYPE_DELIVERED);
                }

                bufferedWriter.write(event);
                bufferedWriter.newLine();
                event="";
                batchSizeCounter++;
            }

        }
        finally
        {
            bufferedWriter.close();
        }

        return null;
    }

    @Override
    public String generateOrderId() {

        String orderId=reusableUtils.generateUUID();

        while(!checkOrderIdExists(orderId))
        {
            orderId=reusableUtils.generateUUID();
        }
        orderIds.add(orderId);
        return orderId;
    }

    private Boolean checkOrderIdExists(String orderId)
    {
        if (orderIds.contains(orderId))
        {
            return false;
        }
        return true;
    }

    @Override
    public BufferedWriter createFileWriterObject() throws IOException
    {
        String fileName=generateFileName();
        System.out.println(fileName);
        BufferedWriter bufferedWriter=new BufferedWriter(new FileWriter(fileName,true));
        return bufferedWriter;
    }

    private String generateFileName()
    {
        return outputDirectory+"orders-"+reusableUtils.generateSystemTime(Constants.FILE_DATE_FORMAT)+".json";
    }


    public String createFoodAppEvents(String orderId,String orderType)
    {
        String timestampUtc=reusableUtils.generateSystemTime(Constants.EVENT_DATE_FORMAT);

        Data data =new Data(orderId,timestampUtc);
        FoodAppEvent foodAppEvent=new FoodAppEvent(orderType,data);
        //foodAppEvents.add(foodAppEvent);

        return gson.toJson(foodAppEvent);
    }



}
