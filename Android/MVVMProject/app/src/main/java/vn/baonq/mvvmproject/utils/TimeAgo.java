package vn.baonq.mvvmproject.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeAgo {
    public static final List<Long> times = Arrays.asList(
//            TimeUnit.DAYS.toMillis(365),
//            TimeUnit.DAYS.toMillis(30),
           TimeUnit.DAYS.toMillis(1),
            TimeUnit.HOURS.toMillis(1),
            TimeUnit.MINUTES.toMillis(1),
            TimeUnit.SECONDS.toMillis(1) );
    public static final List<String> timesString = Arrays.asList("ngày","giờ","phút","giây");

    public static String toDuration(long time) {

        StringBuffer res = new StringBuffer();
        long duration = Calendar.getInstance().getTime().getTime() - time;
        long temp= duration/TimeAgo.times.get(0);;
        if(temp<=0){
        for(int i=1;i< TimeAgo.times.size(); i++) {
            Long current = TimeAgo.times.get(i);
             temp = duration/current;
            if(temp>0) {
                res.append(temp).append(" ").append( TimeAgo.timesString.get(i) ).append(" trước");
                break;
            }
        }}
        if("".equals(res.toString()))
        {
            Date date = new Date(time);
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
        return formatter.format(date);}
        else
            return res.toString();


    }


}
