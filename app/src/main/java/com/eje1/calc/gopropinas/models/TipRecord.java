package com.eje1.calc.gopropinas.models;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Bryan G on 18/03/2017.
 */
public class TipRecord {
    private double bill;
    private int percentage;
    private Date timestamp;

    public double getBill() {
        return bill;
    }

    public void setBill(double bill) {
        this.bill = bill;
    }

    public int getPercentage() {
        return percentage;
    }

    public void setPercentage(int percentage) {
        this.percentage = percentage;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
    public double getTip(){
        return bill*(percentage/100d);
    }


    public String getDateFormatted(){
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM dd,yyyy HH:mm");
        return simpleDateFormat.format(timestamp);
    }
}
