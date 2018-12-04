/*We created the Data class so that instead of serializing 
several arrays, we serialize the instance of this class. The
explanation for serialization can be found in the "LoadAndSave" class*/

import java.io.Serializable.*;
import java.io.*;

public class Data implements Serializable {      //We need to implement serialiazble so that we can serialize the class

    private String[] reminderName;
    private String[] reminderDate;
    private String[] reminderTime;
    private String[][] userData;    //userData will hold the first event in a day
    private String[][] userData2;   //userData2 will hold the second event in a day

    public Data() {                 //New data (i.e. empty data) is generated each time this class is called
     
        reminderName = new String[11];      //Data strucutres for reminders
        reminderDate = new String[11];
        reminderTime = new String[11];
        reminderName[0] = null;
        reminderDate[0] = null;
        reminderTime[0] = null;
        userData = new String[24][31];          //Data structure that holds the first event of the day
        for (int i = 0; i < userData.length; i++) {
            for (int j = 0; j < userData[i].length; j++) {
                userData[i][j] = "";                    //Setting each event to be empty
            }
        }
        userData2 = new String[24][31];             //Data structure that holds the second event of the day
        for (int i = 0; i < userData2.length; i++) {
            for (int j = 0; j < userData2[i].length; j++) {
                userData2[i][j] = "";                    //Setting each event to be empty
            }
        }

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

    public void setUserData(String[][] a) {
        userData = a;
    }

    public void setUserData2(String[][] a) {
        userData2 = a;
    }

    public String[] getReminderName() { //Getters
        return reminderName;
    }

    public String[] getReminderDate() {
        return reminderDate;
    }

    public String[] getReminderTime() {
        return reminderTime;
    }

    public String[][] getUserData() {
        return userData;
    }

    public String[][] getUserData2() {
        return userData2;
    }

}
