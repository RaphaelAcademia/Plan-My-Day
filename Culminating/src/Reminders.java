/*The Reminders class handles everything related to the reminders section of the 
application. This means that this class is the "brains" of the reminders which handles everything
from displaying the graphics of the reminders, adding reminders, and deleting reminders. This
class is independent of the Calendar (i.e. the events listed in the Calendar are different from
the reminders found in this class */

import hsa.Console;
import java.awt.Color;
import java.awt.Font;

public class Reminders {

    Console c;

    String[] reminderName;
    String[] reminderDate;
    String[] reminderTime;

    public Reminders(String[] a, String[] b, String[] d, Console c) //constructor initializes the list of reminders with data
    {
        reminderName = a;
        reminderDate = b;
        reminderTime = d;
        this.c = c;

    }

    public void addReminder(String a, String b, String d) //method to add a reminder to the list of reminders
    {
        for (int i = 0; i < reminderName.length; i++) {//checks if the reminder name at each position of the array 
            if (reminderName[i] == null) {      //if empty, add the remidners that is trying to be added
                reminderName[i] = a;
                reminderDate[i] = b;
                reminderTime[i] = d;
                break;
            } else {    //if that position in the array is full, do nothing, continue loop
            }
        }
    }

    public void deleteReminder(String a) //method to delete reminders from the list by writing over its position in the array
    {
        String[] nameTemp = new String[1];
        String[] dateTemp = new String[1];
        String[] timeTemp = new String[1];
        for (int i = 0; i < reminderName.length; i++) { //loop to check each position in the array

            if (reminderName[i].equals(a)) {                        //set deleted reminder to null if it is found
                reminderName[i] = null;
                reminderDate[i] = null;
                reminderTime[i] = null;
                for (int j = i + 1; j < reminderName.length; j++) {     //swap deleted reminder (null) to end of list
                    nameTemp[0] = reminderName[i];
                    dateTemp[0] = reminderDate[i];
                    timeTemp[0] = reminderTime[i];

                    reminderName[i] = reminderName[j];          //making sure that the corresponding values in the other arrays get swapped to the end
                    reminderDate[i] = reminderDate[j];          //along with the name
                    reminderTime[i] = reminderTime[j];

                    reminderName[j] = nameTemp[0];
                    reminderDate[j] = dateTemp[0];
                    reminderTime[j] = timeTemp[0];

                    i++;
                }

            } else {    //if reminder that is being searched for is not found, do nothing
            }
        }

    }

    public void displayReminders() //print reminders on the screen for user
    {
        c.setColor(Color.BLACK);
        Font font = new Font("High Tower Text", Font.PLAIN, 16);
        c.setFont(font);
        c.setColor(Color.blue);
        int x = 1008;               //define x.y variables where the reminders will be printed
        int y = 100;
        this.c = c;

        for (int i = 0; i < reminderName.length; i++) {
            if (reminderName[i] != null) //loop prints full reminders list
            {
                c.drawString(reminderName[i], x, y);        //adjusting coordinates of printed information so that A. each reminder is in the proper format
                y += 20;                                                                                        // B. that the graphics of each remidner does not overlap
                c.drawString(reminderDate[i], x, y);
                x += 125;
                c.drawString(reminderTime[i], x, y);
                x = 1008;
                y += 35;

            } else {    //if list is empty, do nothing

            }

        }

    }

    public void reminderGraphics() {        //creates the black rectangle that surrounds the reminder list

        c.setColor(Color.BLACK);
        Font font = new Font("High Tower Text", Font.BOLD, 30);
        c.setFont(font);

        c.drawString("Reminders", 1025, 45);

        c.drawRect(1001, 51 + 15, 196, 650);
        c.drawRect(1002, 52 + 15, 196, 650);
        c.drawRect(1003, 53 + 15, 196, 650);
    }

    public void clearReminders() {  //Clears the graphics of the reminders
        c.setColor(Color.WHITE);
        c.fillRect(1004, 69, 193, 647);
    }

    public String[] getReminderName() {     //Getters
        return reminderName;
    }

    public String[] getReminderDate() {
        return reminderDate;
    }

    public String[] getReminderTime() {
        return reminderTime;
    }

    public void setReminderName(String[] a) {   //Setters
        reminderName = a;
    }

    public void setReminderDate(String[] a) {
        reminderDate = a;
    }

    public void setReminderTime(String[] a) {
        reminderTime = a;
    }

}
