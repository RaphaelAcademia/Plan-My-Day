/*This class handles drawing the "base" calendar (i.e. it draws a calendar
with no events. Displaying events on the calendar is handled in ManageCalendar)
Some of the noteworthy things accomplished in this class is the use of Zeller's congruence
to find what day (i.e. Monday, Tuesday....) a month begins*/

import java.io.*;
import java.awt.*;
import hsa.Console;
import java.util.Calendar;

public class CalendarGraphics {

    Console c;
    int month;
    int year;

    public CalendarGraphics(int m, int y, Console c) {  //Constructor
        this.month = m;                                                 
        this.year = y;                                               
        this.c = c;
    }

    public int getMonth() {
        return month;                       //Getter for month
    }

    public void setMonth(int m) {
        month = m;                  //Setter for month
    }

    public int getYear() {
        return year;                //Getter for year
    }

    public void setYear(int y) {
        year = y;                   //Setter for year
    }

    public String toString() {
        return "The month is :" + month + " in the year of " + year;            //String to display month and year to user
    }

    public void drawCalendar() {
        Font font = new Font("High Tower Text", Font.BOLD, 30);                 //Font for calendar dates
        Font dates = new Font("Calibri Light", Font.BOLD, 20);                  //Font for numbers
        Font event = new Font("Candara", Font.PLAIN, 16);                       //Font for events
        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String m = allMonths[month];
        c.setFont(font);
        c.setColor(Color.BLACK);

        String y = Integer.toString(year);                      //Changes the year to a string, so we can print it

        c.drawRect(1, 66, 950, 650);                       //Draw the outline of the calendar
        c.drawRect(2, 67, 950, 650);                       //Additional lines bold it
        c.drawRect(3, 68, 950, 650);
        c.drawLine(1, 715, 950, 715);

        c.drawLine(136, 66, 136, 716);
        c.drawLine(272, 66, 272, 716);                      //Vertical lines to create columns on calendar
        c.drawLine(408, 66, 408, 716);
        c.drawLine(544, 66, 544, 716);
        c.drawLine(680, 66, 680, 716);
        c.drawLine(816, 66, 816, 716);

        c.drawLine(1, 174, 950, 174);
        c.drawLine(1, 282, 950, 282);
        c.drawLine(1, 390, 950, 390);                          //Horizontal lines to create columns on calendar
        c.drawLine(1, 498, 950, 498);
        c.drawLine(1, 606, 950, 606);
        c.drawLine(1, 714, 950, 714);

        c.drawString(y, 325, 20);               //Showing year
        c.drawString(m, 400, 20);               //Showing month

        c.setFont(dates);
        c.drawString("Sunday", 30, 60);             //Writes the days of the week on the top of the calendar
        c.drawString("Monday", 170, 60);
        c.drawString("Tuesday", 305, 60);
        c.drawString("Wednesday", 430, 60);
        c.drawString("Thursday", 570, 60);
        c.drawString("Friday", 720, 60);
        c.drawString("Saturday", 850, 60);

        int q = 1;              //Setting the day of the week we want to find to the first day of the month
        int mo = month + 1;   //As for zeller's congruence, we need January to start at 1

        if ((mo == 1) && (year == 2017)) {                //Special cases for Zeller's congruency (January and February)
            mo = 13;
            year = 2016;
        }
        if ((mo == 2) && (year == 2017)) {
            mo = 14;
            year = 2016;
        }
        if ((mo == 1) && (year == 2018)) {
            mo = 13;
            year = 2017;
        }
        if ((mo == 2) && (year == 2018)) {
            mo = 14;
            year = 2017;
        } else {

        }

        int k = year % 100;     //Finding K value for Zeller's congruence
        int j = (int) year / 100;   //Determining J value for Zeller's congruence

        int day = (q + (int) ((13 * (mo + 1)) / 5) + k + (int) (k / 4) + (int) (j / 4) + (5 * j)) % 7;          //Full Zeller's Congruence formula

        int x1 = 0;                 //x value for the first day (top left  box)
        int y1 = 85;                //y value for the first day (top left box)

        /*The purpose of the using the calendar class below is to allow us to find the maximum number of days in a given month.
            By implementing this class below, we can have one for loop that handles drawing the days onto the calendar
        (instead of hard coding the graphics for the months with 31 days, months with 30 days, etc)*/
        
        Calendar currentCal = Calendar.getInstance();       //Though it may seem weird that we are not calling the constructor of Calendar, it is an abstract class 
        currentCal.set(year, month, 1);                       //We can only call the classes' current instance
        int daysInMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int x = 2; x < daysInMonth + 1; x++) //We set x to 2 so that after we print the first day, we can immediately print the second day
        {

            if (day == 1) {
                x1 = 5;
                c.drawString("1", x1, y1);            //Sunday
                x1 += 136;
                day = 10;
            } else if (day == 2) {
                x1 += 141;
                c.drawString("1", x1, y1);          //Monday
                x1 += 136;
                day = 10;
            } else if (day == 3) {
                x1 += 277;
                c.drawString("1", x1, y1);          //Tuesday
                x1 += 136;
                day = 10;
            } else if (day == 4) {
                x1 += 413;
                c.drawString("1", x1, y1);          //Wednesday
                x1 += 136;
                day = 10;
            } else if (day == 5) {
                x1 += 549;
                c.drawString("1", x1, y1);           //Thursday
                x1 += 136;
                day = 10;
            } else if (day == 6) {
                x1 += 685;
                c.drawString("1", x1, y1);           //Friday
                x1 += 136;
                day = 10;
            } else if (day == 0) {
                x1 += 821;
                c.drawString("1", x1, y1);         //Saturday
                x1 = 5;
                y1 = 193;
                day = 10;
            }

            c.drawString(Integer.toString(x), x1, y1);
            x1 += 136;

            if ((x1 == 957) && (y1 == 85)) {   //If the calendar reaches Saturday, move the coordinates to Sunday
                x1 = 5;
                y1 = 193;
            } else if ((x1 == 957) && (y1 == 193)) {
                x1 = 5;
                y1 = 301;
            } else if ((x1 == 957) && (y1 == 301)) {
                x1 = 5;
                y1 = 409;

            } else if ((x1 == 957) && (y1 == 409)) {
                x1 = 5;
                y1 = 517;

            } else if ((x1 == 957) && (y1 == 517)) {
                x1 = 5;
                y1 = 625;

            }
        }

    }

}
