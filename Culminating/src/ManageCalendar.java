/*This class can be thought of as the "brains" of the calendar; 
it contains all the methods that allow the Calendar to function.
The most important methods in this class are addEvent, deleteEvent,
modifyEvent, and loadCalendar2*/

import java.awt.*;
import hsa.Console;
import java.util.Calendar;

public class ManageCalendar {

    private String[][] calendarData;            //calendarData will hold the first event in a day
    private String[][] calendarData2;           //calendarData2 will hold the second event in a day
    Console c;
    private int month;
    private int year;

    public ManageCalendar(String[][] a, String[][] b, Console d) {  //Constructor
        calendarData = a;
        calendarData2 = b;
        c = d;
        month = 0;
        year = 0;
    }

    public String[][] getCalendarData() {   //Getters 
        return calendarData;
    }

    public String[][] getCalendarData2() {
        return calendarData2;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    public void setCalendarData(String[][] a) { //Setters
        calendarData = a;
    }

    public void setCalendarData2(String[][] a) {
        calendarData2 = a;
    }

    public void setMonth(int a) {
        month = a;
    }

    public void setYear(int a) {
        year = a;
    }

    public String toString() {
        return "The month is " + month + " while the year is " + year;
    }

    public void loadCalendar1() {    //First load calendar method. This method is ran when the user first logins.

        Font userLogin = new Font("High Tower Text", Font.PLAIN, 24);    //Font
        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //All of the months
        c.setColor(Color.BLACK);
        c.drawRect(425, 175, 350, 350);        //Graphics for user login
        c.drawRect(426, 176, 348, 348);
        c.drawRect(427, 177, 346, 346);
        c.drawRect(428, 178, 345, 345);

        String monthName = "";
        c.setFont(userLogin);

        c.drawString("What year would you like to load?", 435, 198);
        c.drawString("(Enter 2017 or 2018)", 510, 223);

        do {                //Error trap
            c.setColor(Color.WHITE);
            c.fillRect(430, 238, 340, 25);
            c.setColor(Color.BLACK);
            c.drawRect(425, 175, 350, 350);   //Graphics for user login
            c.drawRect(426, 176, 348, 348);
            c.drawRect(427, 177, 346, 346);
            c.drawRect(428, 178, 345, 345);
            c.setCursor(13, 56);
            year = c.readInt();
        } while ((year != 2017) && (year != 2018));

        c.drawString("What month would you like to load?", 427, 280);
        c.drawString("Input the month capitalizing the ", 447, 310);
        c.drawString("first letter (ex: April): ", 493, 335);

        do {                        //Error trap
            c.setColor(Color.WHITE);
            c.fillRect(430, 340, 340, 25);
            c.setColor(Color.BLACK);
            c.drawRect(425, 175, 350, 350);   //Graphics for user login
            c.drawRect(426, 176, 348, 348);
            c.drawRect(427, 177, 346, 346);
            c.drawRect(428, 178, 345, 345);
            c.setCursor(18, 56);
            monthName = c.readString();
        } while (!(monthName.equals("January") || monthName.equals("February") || monthName.equals("March") || monthName.equals("April") || monthName.equals("May") || monthName.equals("June") || monthName.equals("July") || monthName.equals("August") || monthName.equals("September") || monthName.equals("October") || monthName.equals("November") || monthName.equals("December")));

        month = linearSearch(allMonths, monthName); //We search the allMonths String to get the month number we need

        c.clear();

        CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
        Calendar.drawCalendar();
        displayEvents(); //Display events

    }

    public void loadCalendar2() //Second load calendar method (this is the method that is executed if the user wants to view a new calendar)
    {

        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //All of the months
        String monthName = "";

        drawUserBox(); //draws box for user input
        c.setCursor(38, 2);
        c.println("What year would you like to load (Input 2017 or 2018): "); //Asking the user what year would they like to load
        drawUserBox(); //draws box for user input

        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            year = c.readInt();
        } while (year != 2017 && year != 2018);

        drawUserBox(); //draws box for user input
        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        c.setCursor(38, 2);
        c.println("What month would you like to load? Input the month capitalizing the firt letter (ex: August): ");
        drawUserBox(); //draws box for user input

        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            monthName = c.readString();
        } while (!(monthName.equals("January") || monthName.equals("February") || monthName.equals("March") || monthName.equals("April") || monthName.equals("May") || monthName.equals("June") || monthName.equals("July") || monthName.equals("August") || monthName.equals("September") || monthName.equals("October") || monthName.equals("November") || monthName.equals("December")));

        month = linearSearch(allMonths, monthName); //We search the allMonths String to get the month number we need

        c.setColor(Color.WHITE);
        c.fillRect(0, 0, 951, 706);                //Clear the screen, so we can create a new calendar

        CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
        Calendar.drawCalendar();

        displayEvents(); //Display events
    }

    public void addEvent() {    //This is the method that is ran if the user wants to add an event to the calendar
        int yearEvent = 0, numMonthEvent = 0, dayEvent = 0;    //Along with an integer representation of the year, we also have an integer representation of the month
        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //All of the months
        String monthEvent = "", event = "";
        drawUserBox(); //draws box for user input
        c.setCursor(38, 2);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);

        c.println("Which year would you like to add the event? (enter 2017 or 2018): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            yearEvent = c.readInt();
        } while (yearEvent != 2017 && yearEvent != 2018);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);

        c.setCursor(38, 2);
        c.println("Which month would you like to add the event? Input the month capitalizing the first letter (ex: 'April'): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            monthEvent = c.readString();
        } while (!(monthEvent.equals("January") || monthEvent.equals("February") || monthEvent.equals("March") || monthEvent.equals("April") || monthEvent.equals("May") || monthEvent.equals("June") || monthEvent.equals("July") || monthEvent.equals("August") || monthEvent.equals("September") || monthEvent.equals("October") || monthEvent.equals("November") || monthEvent.equals("December")));

        numMonthEvent = linearSearch(allMonths, monthEvent);

        /* Using the Calendar Class again just like in the CalendarGraphics class. 
        As stated before, this class allows us to get the number of days in a given month and year.*/
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(yearEvent, numMonthEvent, 1);
        int daysInMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (yearEvent == 2018) //We need to add 12 to month if 2018 as we store the String event in a two dimensional array, 
        {                       //Where the "row" represents the 24 months between 2017 and 2018
            numMonthEvent += 12;
        } else {
        }

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);

        c.setCursor(38, 2);
        c.println("Which day would you like to add the event? (can only be between 1 and " + daysInMonth + "): ");

        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            dayEvent = c.readInt();

        } while (!(dayEvent >= 1 && dayEvent <= daysInMonth));

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);

        c.setCursor(38, 2);
        c.println("Enter the event that you would like to add (can only be a maximum of 20 characters including spaces): ");

        do //Error trap
        {
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            event = c.readLine();
            drawUserBox(); //draws box for user input
        } while (!(event.length() >= 0 && event.length() <= 20));

        c.setColor(Color.WHITE);
        c.fillRect(0, 746, 850, 12);

        if (!(calendarData[numMonthEvent][dayEvent - 1].equals(""))) {  //If there is an event already, check if there is another event
            if (calendarData2[numMonthEvent][dayEvent - 1].equals("")) {
                calendarData2[numMonthEvent][dayEvent - 1] = event;
                c.setCursor(38, 2);
                drawUserBox(); //draws box for user input
                c.setCursor(38, 2);
                c.println("The event has been added!");
                displayEvents();
                drawUserBox();
                delay(3000);

            } else {
                c.setCursor(38, 2);
                drawUserBox(); //draws box for user input
                c.setCursor(38, 2);
                c.println("The event can't be added as you already have two events on that day!");
                displayEvents();
                delay(3000);
            }

        } else {
            calendarData[numMonthEvent][dayEvent - 1] = event;
            c.setCursor(38, 2);
            drawUserBox(); //draws box for user input
            c.setCursor(38, 2);
            c.println("The event has been added!");
            displayEvents();
            delay(3000);
        }
        displayEvents();

    }

    public void deleteEvent() { //This is the method that is executed if the user wants to delete an event from the calendar
        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //All of the months
        String monthName = "", event = "", firstOrSecond = "";
        int yearNum = 0, monthNum = 0, dayNum = 0;

        int eventDeleted = 0;       //This integer keeps track if we have deleted an event or not

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);        //Clear the part where the user types just in case there is something there
        drawUserBox(); //draws box for user input 

        c.setCursor(38, 2);
        c.println("Enter the event that you would like to delete (has to be exactly the same as the event that you have entered): ");

        drawUserBox(); //draws box for user input
        c.setCursor(39, 2);
        event = c.readLine();

        c.setCursor(38, 2);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        c.println("Which year is the event located in? (input 2017 or 2018): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            yearNum = c.readInt();
        } while (yearNum != 2017 && yearNum != 2018);

        c.setCursor(38, 2);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        c.println("Which month is the event located in? Input the month capitalizing the first letter (ex: April): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            monthName = c.readString();
        } while (!(monthName.equals("January") || monthName.equals("February") || monthName.equals("March") || monthName.equals("April") || monthName.equals("May") || monthName.equals("June") || monthName.equals("July") || monthName.equals("August") || monthName.equals("September") || monthName.equals("October") || monthName.equals("November") || monthName.equals("December")));

        monthNum = linearSearch(allMonths, monthName);

        /* Using the Calendar Class again just like in the CalendarGraphics class and the addEvent method.
          As stated before, this class allows us to get the number of days in a given month and year.*/
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(yearNum, monthNum, 1);
        int daysInMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (yearNum == 2018) //Need to add 12 if 2018 (as we hold data from months 0 to 24, which represents January 2017 to December 2018)
        {
            monthNum += 12;
        } else {
        }
        c.setCursor(38, 2);

        c.setColor(Color.WHITE);
        c.fillRect(0, 746, 850, 12);
        c.println("Which day is the event located in? (can only be between 1 and " + daysInMonth + "): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            dayNum = c.readInt();

        } while (!(dayNum >= 1 && dayNum <= daysInMonth));

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        if ((calendarData[monthNum][dayNum - 1].equals(event) && (!(event.equals("")))) || (calendarData2[monthNum][dayNum - 1].equals(event) && (!(event.equals(""))))) {  //If the event is in either data structure and the event is not equals to an empty string
            if ((calendarData[monthNum][dayNum - 1].equals(event) && calendarData2[monthNum][dayNum - 1].equals(event)) && (!(event.equals("")))) { //If there are two events of the same name in the same day, this if structure is executed
                c.setCursor(38, 2);
                c.println("There are two events with the same name in that day. Would you like to delete the first event or second event? (Enter 'first' or 'second')");
                drawUserBox(); //draws box for user input

                do { //Error trap
                    drawUserBox(); //draws box for user input
                    c.setCursor(39, 2);
                    firstOrSecond = c.readString();
                    drawUserBox(); //draws box for user input
                } while (!(firstOrSecond.equals("first") || firstOrSecond.equals("First") || firstOrSecond.equals("Second") || firstOrSecond.equals("second")));

                if (firstOrSecond.equals("first") || firstOrSecond.equals("First")) {
                    calendarData[monthNum][dayNum - 1] = "";
                    c.setCursor(38, 2);
                    c.println("The event has been deleted!");
                    drawUserBox(); //draws box for user input
                    c.setColor(Color.WHITE);
                    c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                    CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                    Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have deleted an event
                    displayEvents();                            //Display the events again
                    delay(3000);
                } else if (firstOrSecond.equals("Second") || firstOrSecond.equals("second")) {
                    calendarData2[monthNum][dayNum - 1] = "";
                    c.setCursor(38, 2);
                    c.println("The event has been deleted!");
                    drawUserBox(); //draws box for user input
                    c.setColor(Color.WHITE);
                    c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it
                    CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                    Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have deleted an event
                    displayEvents();                            //Display the events again
                    delay(3000);
                }

            } else if (calendarData[monthNum][dayNum - 1].equals(event) && (!(event.equals("")))) {     //If the event is found in the first data structure, this if structure is executed
                calendarData[monthNum][dayNum - 1] = "";
                c.setCursor(38, 2);
                c.println("The event has been deleted!");
                drawUserBox(); //draws box for user input
                c.setColor(Color.WHITE);
                c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have deleted an event
                displayEvents();                            //Display the events again
                delay(3000);
            } else if (calendarData2[monthNum][dayNum - 1].equals(event) && (!(event.equals("")))) {     //If the event is found in the second data structure, this if structure is executed
                calendarData2[monthNum][dayNum - 1] = "";
                c.setCursor(38, 2);
                c.println("The event has been deleted!");
                drawUserBox(); //draws box for user input
                c.setColor(Color.WHITE);
                c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have deleted an event
                displayEvents();                            //Display the events again
                delay(3000);
            }
        } else {
            c.setCursor(38, 2);
            c.println("The event was not found!");
            drawUserBox(); //draws box for user input
            delay(3000);
        }
    }

    public void modifyEvent() { //Allows user to modify the name of an event (basically the deleteEvent method with slight changes)
        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; //All of the months
        String monthName = "", event = "", firstOrSecond = "", newEvent = "";
        int yearNum = 0, monthNum = 0, dayNum = 0;

        int eventDeleted = 0;       //This integer keeps track if we have deleted an event or not

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);        //Clear the part where the user types just in case there is something there
        drawUserBox(); //draws box for user input

        c.setCursor(38, 2);
        c.println("Enter the event that you would like to modify (has to be exactly the same as the event that you have entered): ");
        drawUserBox(); //draws box for user input

        c.setCursor(39, 2);
        event = c.readLine();
        drawUserBox(); //draws box for user input

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);        //Clear the part where the user types just in case there is something there

        c.setCursor(38, 2);
        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        drawUserBox(); //draws box for user input
        c.setCursor(38, 2);
        c.println("Enter the new name of the event (can only be a maximum of 20 characters includng spaces): ");
        do //Error trap
        {
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            newEvent = c.readLine();
        } while (!(newEvent.length() >= 0 && newEvent.length() <= 20));

        c.setCursor(38, 2);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        c.println("Which year is the event located in? (input 2017 or 2018): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            yearNum = c.readInt();
        } while (yearNum != 2017 && yearNum != 2018);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);
        c.setCursor(38, 2);
        c.println("Which month is the event located in? Input the month capitalizing the first letter (ex: April): ");
        do {    //Error trap
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            drawUserBox(); //draws box for user input
            c.setCursor(39, 2);
            monthName = c.readString();
        } while (!(monthName.equals("January") || monthName.equals("February") || monthName.equals("March") || monthName.equals("April") || monthName.equals("May") || monthName.equals("June") || monthName.equals("July") || monthName.equals("August") || monthName.equals("September") || monthName.equals("October") || monthName.equals("November") || monthName.equals("December")));

        monthNum = linearSearch(allMonths, monthName);

        /* Using the Calendar Class again just like in the CalendarGraphics class, the addEvent method, and deleteEvent method.
          As stated before, this class allows us to get the number of days in a given month and year.*/
        Calendar currentCal = Calendar.getInstance();
        currentCal.set(yearNum, monthNum, 1);
        int daysInMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        if (yearNum == 2018) //Need to add 12 if 2018 (as we hold data from months 0 to 24, which represents January 2017 to December 2018)
        {
            monthNum += 12;
        } else {
        }
        c.setCursor(38, 2);

        c.setColor(Color.WHITE);
        c.fillRect(4, 746, 1050, 12);

        c.println("Which day is the event located in? (can only be between 1 and " + daysInMonth + "): ");
        do {    //Error trap
            drawUserBox();
            c.setCursor(39, 2);
            dayNum = c.readInt();
        } while (!(dayNum >= 1 && dayNum <= daysInMonth));

        if ((calendarData[monthNum][dayNum - 1].equals(event) && (!(event.equals("")))) || (calendarData2[monthNum][dayNum - 1].equals(event) && (!(event.equals(""))))) {  //If the event is in either data structure and the event is not equals to an empty string
            if ((calendarData[monthNum][dayNum - 1].equals(event) && calendarData2[monthNum][dayNum - 1].equals(event)) && (!(event.equals("")))) { //If there are two events of the same name in the same day (special case), this if structure is executed
                c.setColor(Color.WHITE);
                c.fillRect(4, 746, 1050, 12);
                c.setCursor(38, 2);
                c.println("There are two events with the same name in that day. Would you like to modify the first event or second event? (Enter 'first' or 'second'):");
                drawUserBox(); //draws box for user input

                do { //Error trap
                    drawUserBox(); //draws box for user input
                    c.setCursor(39, 2);
                    drawUserBox(); //draws box for user input
                    firstOrSecond = c.readString();
                } while (!(firstOrSecond.equals("first") || firstOrSecond.equals("First") || firstOrSecond.equals("Second") || firstOrSecond.equals("second")));

                if (firstOrSecond.equals("first") || firstOrSecond.equals("First")) {
                    calendarData[monthNum][dayNum - 1] = newEvent;
                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    c.setCursor(38, 2);
                    c.println("The event has been modified!");
                    drawUserBox(); //draws box for user input
                    c.setColor(Color.WHITE);
                    c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                    CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                    Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have deleted an event
                    displayEvents();                            //Display the events again
                    delay(3000);
                } else if (firstOrSecond.equals("Second") || firstOrSecond.equals("second")) {
                    calendarData2[monthNum][dayNum - 1] = newEvent;
                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    c.setCursor(38, 2);
                    c.println("The event has been modified!");
                    drawUserBox(); //draws box for user input
                    c.setColor(Color.WHITE);
                    c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                    CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                    Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have modified an event
                    displayEvents();                            //Display the events again
                    delay(3000);
                }

            } else if (calendarData[monthNum][dayNum - 1].equals(event) && (!(event.equals("")))) {     //If the event is found in the first data structure, this if structure is executed
                calendarData[monthNum][dayNum - 1] = newEvent;
                c.setCursor(38, 2);
                c.println("The event has been modified!");
                drawUserBox(); //draws box for user input
                c.setColor(Color.WHITE);
                c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have modified an event
                displayEvents();                            //Display the events again
                delay(3000);
            } else if (calendarData2[monthNum][dayNum - 1].equals(event) && (!(event.equals("")))) {     //If the event is found in the second data structure, this if structure is executed
                calendarData2[monthNum][dayNum - 1] = newEvent;
                c.setCursor(38, 2);
                c.println("The event has been modified!");
                drawUserBox(); //draws box for user input
                c.setColor(Color.WHITE);
                c.fillRect(1, 66, 950, 650);                //Clear the screen, so we can create a new calendar to update it.
                CalendarGraphics Calendar = new CalendarGraphics(month, year, c);
                Calendar.drawCalendar();                    //Creating a new a calendar to update the fact that we have modified an event
                displayEvents();                            //Display the events again
                delay(3000);
            }
        } else {
            c.setColor(Color.WHITE);
            c.fillRect(4, 746, 1050, 12);
            c.setCursor(38, 2);
            c.println("The event was not found!");
            drawUserBox(); //draws box for user input
            delay(3000);
        }
    }

    public void displayEvents() {       //Method to display the events of the current month
        Font eventFont = new Font("Segoe UI", Font.PLAIN, 12);
        c.setFont(eventFont);
        c.setColor(Color.red);

        int monthAdj = 0, monthZ = 0, x1 = 0, y1 = 0, yearAdj = 0; //monthZ is the month used in Zeller's congruence
        monthZ = month + 1;                         //monthAdj (monthAdjusted) is the month that has been adjusted according to the year
        monthAdj = month;
        yearAdj = year;

        if (year == 2018) {
            monthAdj = month + 12; //This is why we need a monthAdjusted variable (as we store the data in a two dimensional array, with the "column" representing the # of months between 2017 and 2018)
        }

        for (int i = 0; i < calendarData[monthAdj].length; i++) //Go through the month's data to see if there is an event. If there is, we print display the event on the corresponding coordinates 
        {
            /*If there is an event, we use code similar to CalendarGraphics to find the coordinates 
            of the particular day that the event is on. Then, we print the event*/
            if (!(calendarData[monthAdj][i].equals("")));
            {

                if ((monthZ == 1) && (year == 2017)) {                //Special cases for Zeller's congruency (January and February)
                    monthZ = 13;
                    yearAdj = 2016;
                }
                if ((monthZ == 2) && (year == 2017)) {
                    monthZ = 14;
                    yearAdj = 2016;
                }
                if ((monthZ == 1) && (year == 2018)) {
                    monthZ = 13;
                    yearAdj = 2017;
                }
                if ((monthZ == 2) && (year == 2018)) {
                    monthZ = 14;
                    yearAdj = 2017;
                } else {

                }

                int k = yearAdj % 100;     //Finding K value for Zeller's congruence
                int j = (int) yearAdj / 100;   //Determining J value for Zeller's congruence

                int day = (1 + (int) ((13 * (monthZ + 1)) / 5) + k + (int) (k / 4) + (int) (j / 4) + (5 * j)) % 7;          //Full Zeller's Congruence formula

                x1 = 0;                 //x value for the first day (top left  box)
                y1 = 85;                //y value for the first day (top left box)

                Calendar currentCal = Calendar.getInstance();       //Though it may seem weird that we are not calling the constructor of Calendar, it is an abstract class 
                currentCal.set(year, month, 1);                       //We can only call the classes' current instance
                int daysInMonth = currentCal.getActualMaximum(Calendar.DAY_OF_MONTH);

                if (day == 1) {         //Finding what day the month starts.
                    x1 = 5;
                    day = 10;
                } else if (day == 2) {
                    x1 += 141;
                    day = 10;
                } else if (day == 3) {
                    x1 += 277;
                    day = 10;
                } else if (day == 4) {
                    x1 += 413;
                    day = 10;
                } else if (day == 5) {
                    x1 += 549;
                    day = 10;
                } else if (day == 6) {
                    x1 += 685;
                    day = 10;
                } else if (day == 0) {
                    x1 += 821;
                    day = 10;
                }

                for (int x = 0; x < daysInMonth + 1; x++) {
                    if (x == i) {   //When the coordinates is equal to the day that we need, break

                        break;
                    }
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
                y1 += 18;

                if (!(calendarData[monthAdj][i]).equals("")) //If structure that deals with the first event of the day
                {
                    c.setColor(Color.red);
                    c.fillRect(x1, y1, 127, 20);
                    c.setColor(Color.white);
                    c.drawString(calendarData[monthAdj][i], x1 + 3, y1 + 15);         //Prints the events of the day.                     
                }

                if (!(calendarData2[monthAdj][i].equals(""))) //If structure that deals with the second event of the day
                {
                    c.setColor(Color.red);
                    c.fillRect(x1, y1 + 25, 127, 20);
                    c.setColor(Color.white);
                    c.drawString(calendarData2[monthAdj][i], x1 + 3, y1 + 40);      //Prints the events of the day
                }

            }

        }

    }

    public int linearSearch(String[] a, String b) { //Basic linear search
        int num = -1;
        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(b)) {
                num = i;
                break;
            }
        }

        return num;
    }

    public void delay(int millisecs) // Delay Method
    {
        try {
            Thread.currentThread().sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }

    public void drawUserBox() { //draws box for User input
        c.setColor(Color.BLACK);
        c.setCursor(39, 2);
        c.print("                                                     ");
        c.drawRect(1, 735, 1197, 63);
        c.drawRect(2, 736, 1197, 63);
        c.drawRect(0, 737, 1197, 63);
        c.setCursor(39, 2);
    }

}
