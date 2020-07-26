package com.foodapp.eventgenerator.Controller;

import com.foodapp.eventgenerator.service.GenerateEventsService;
import com.foodapp.eventgenerator.service.GenerateEventsServiceImpl;
import picocli.CommandLine;

@CommandLine.Command(name = "generate-events", header = "%n@|green Generate Test Events for food delivery App|@")
public class GenerateEventsController implements Runnable{

    @CommandLine.Option(names = {"--number-of-orders"}, required = true, description = "Number of orders to generate (nb. each order produces two events).")
    int numberOfOrders;

    @CommandLine.Option(names = {"--batch-size"}, required = true, description = "Number of events per file")
    int batchSize;

    @CommandLine.Option(names = {"--interval"}, required = true, description = "Interval in seconds between each file being created.")
    Long interval;

    @CommandLine.Option(names = {"--output-directory"}, required = true, description = " Output directory for all created files")
    String outputDirectory;

    GenerateEventsService generateEventsService = new GenerateEventsServiceImpl();


    public void run() {
        System.out.println("numberOfOrders:::--"+numberOfOrders);
        System.out.println("batchSize:::--"+batchSize);
        System.out.println("interval:::--"+interval);
        System.out.println("outputDirectory:::--"+outputDirectory);

        try
        {
            generateEventsService.generateEvents(numberOfOrders,batchSize,interval,outputDirectory);
        }

        catch (Exception exception)
        {
            exception.printStackTrace();
        }

    }

    public static void main(String... args) {
        CommandLine.run(new GenerateEventsController(), System.err, args);
    }


}
