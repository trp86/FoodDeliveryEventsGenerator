package com.foodapp.eventgenerator.tools;

import java.text.ParseException;
import java.util.Date;
import java.util.UUID;
import java.text.SimpleDateFormat;

public class ReusableUtils {


    UUID uuid;

    public String generateUUID()
    {
        uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public String generateSystemTime(String dateFormat)
    {
        final SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        return sdf.format(new Date());
    }


    public Long getTimeDifference(String fromTime) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat(Constants.EVENT_DATE_FORMAT);
        Date fromDateTime=format.parse(fromTime);

        Date toDateTime=format.parse(generateSystemTime(Constants.EVENT_DATE_FORMAT));
        return (toDateTime.getTime()-fromDateTime.getTime())/1000;

    }

}
