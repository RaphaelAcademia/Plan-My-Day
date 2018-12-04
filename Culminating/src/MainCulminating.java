/*In main, we instantiate the various classes needed for our 
application to function. Apart from instantiating the various classes,
MainCulminating also handles user input and user login through the userInput() method and
the userLogin() method respectively. */

import java.util.regex.*;
import java.awt.*;
import java.io.*;
import hsa.Console;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

public class MainCulminating {

    static Console c;               //Global variables as we use these variables in main and in methods
    static String user;
    static ManageCalendar Calendar;
    static Data account;
    static LoadAndSave ls;
    static Reminders r;

    public static void main(String[] args) throws IOException {

        c = new Console(40, 150);
        ls = new LoadAndSave();
        account = userLogin();       //dimensions are 950,650, console is 40,150
        ls.saveData(account, user + ".ser");  //Saving data after the user is determined to prevent a FileNotFound exception
        Calendar = new ManageCalendar(account.getUserData(), account.getUserData2(), c);
        Calendar.loadCalendar1();
        r = new Reminders(account.getReminderName(), account.getReminderDate(), account.getReminderTime(), c);             //Creating reminders and displaying them
        r.reminderGraphics();
        r.displayReminders();
        userInput(c);           //Run the method that deals with the user input

    }

    public static Data userLogin() throws IOException //Method that deals with user login (graphics + code)
    {
        LoadAndSave ls = new LoadAndSave();
        Font userLogin = new Font("High Tower Text", Font.PLAIN, 24);    //Font
        String loadData;
        Data userD = new Data();    //Class containing the user's data (ex: reminders, events, etc)
        int userNum = 0;

        c.setColor(Color.BLACK);
        c.drawRect(425, 175, 350, 350);        //Graphics for user login
        c.drawRect(426, 176, 348, 348);
        c.drawRect(427, 177, 346, 346);
        c.drawRect(428, 178, 345, 345);

        c.setFont(userLogin);
        c.drawString("Welcome to our calendar!", 480, 196);
        c.drawString("What user do you want to log in as?", 427, 215);
        c.drawString("Choose User1, User2, or User3", 450, 234);

        do //Error check (making sure user types in only User1, User2, or User3
        {
            c.setColor(Color.WHITE);
            c.fillRect(430, 238, 340, 25);
            c.setColor(Color.BLACK);
            c.drawRect(425, 175, 350, 350);   //Graphics for user login
            c.drawRect(426, 176, 348, 348);
            c.drawRect(427, 177, 346, 346);
            c.drawRect(428, 178, 345, 345);
            c.setCursor(13, 55);
            user = c.readString();
        } while (!((user.equals("User1")) || (user.equals("User2")) || (user.equals("User3"))));

        RandomAccessFile raf = new RandomAccessFile("User.bin", "rw");  //We use a binary file to determine whether or not a user has data saved

        if (user.equals("User1")) {         //This if structure checks if a User has data.
            raf.seek(0);
            userNum = raf.readInt();
        } else if (user.equals("User2")) {
            raf.seek(4);
            userNum = raf.readInt();
        } else if (user.equals("User3")) {
            raf.seek(8);
            userNum = raf.readInt();
        } else {
        }

        if (userNum == 1) { //If userNum == 1, that means that the user has data
            c.drawString(user + " has data in it.", 513, 272);
            c.drawString("Would you like to load the data?", 450, 291);
            c.drawString("(Answer Yes or No)", 505, 310);

            do {
                c.setColor(Color.WHITE);
                c.fillRect(430, 318, 340, 25);
                c.setColor(Color.BLACK);
                c.drawRect(425, 175, 350, 350);   //Graphics for user login
                c.drawRect(426, 176, 348, 348);
                c.drawRect(427, 177, 346, 346);
                c.drawRect(428, 178, 345, 345);
                c.setCursor(17, 55);
                loadData = c.readString();
            } while (!((loadData.equals("Yes") || (loadData.equals("No")))));

            if ((loadData.equals("Yes")) && (user.equals("User1"))) {
                userD = (Data) ls.loadData(user + ".ser");
                c.clear();
                raf.close();
                return userD;
            } else if ((loadData.equals("Yes")) && (user.equals("User2"))) {
                userD = (Data) ls.loadData(user + ".ser");
                c.clear();
                return userD;
            } else if ((loadData.equals("Yes")) && (user.equals("User3"))) {
                userD = (Data) ls.loadData(user + ".ser");
                c.clear();
                raf.close();
                return userD;
            } else {          //Assuming the user typed no
                c.clear();
                raf.close();
                return userD;   //Returns empty data
            }
        } else {       //Makes a new 2D array if the user has no data (that is userNum==0)

            if (user.equals("User1")) {
                raf.seek(0);
                raf.writeInt(1);
                raf.close();
                c.clear();

            } else if (user.equals("User2")) {
                raf.seek(4);
                raf.writeInt(1);
                raf.close();
                c.clear();
            } else if (user.equals("User3")) {
                raf.seek(8);
                raf.writeInt(1);
                raf.close();
                c.clear();
            }
            return userD;

        }

    }

    public static void userInput(Console c) throws IOException {     //The userInput method deals with all the user input

        String action = "", actionReminder = "", actionCalendar = "";       //Variables used 
        String[] allMonths = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        String deleteName = "";
        String addName = "";
        String addDate = "";
        String addTime = "";
        String saveYN = "";
        Pattern p;              //Pattern and matcher are used to error check the time inputted for the reminder
        Matcher m;
        String[] splitDate;    //Used in error check for reminders
        int days = 0;
        int dayNum = 0;

        String[] rNames = r.getReminderName();
        List<String> listNames = Arrays.asList(rNames); //Using list for the "contains" method (essentially a searching algorithim)

        do {        //Do-while that will deal with all the user input
            do {            //Error trap
                c.setColor(Color.WHITE);
                c.fillRect(4, 746, 1050, 12);
                drawUserBox();  //draws user box
                c.setCursor(38, 2);
                c.println("What would you like to do? (Enter 'Reminders', 'Calendar', or 'Exit'): ");
                c.setCursor(39, 2);
                drawUserBox();  //draws user box
                action = c.readLine();
            } while (!(action.equals("Reminders") || action.equals("reminders") || action.equals("Calendar") || action.equals("calendar") || action.equals("Exit") || action.equals("exit")));

            if (action.equals("Reminders") || (action.equals("reminders"))) {   //If the user has selected that they want to do something with the remidners
                do {                    //Error trap
                    listNames = Arrays.asList(rNames); //Just in case we make any changes
                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    drawUserBox();  //draws user box
                    c.setCursor(38, 2);
                    c.println("Would you like to add or delete a reminder? (Enter 'add' or 'delete'): ");
                    c.setCursor(39, 2);
                    drawUserBox();  //draws user box
                    actionReminder = c.readLine();
                } while (!(actionReminder.equals("add") || actionReminder.equals("Add") || actionReminder.equals("Delete") || actionReminder.equals("delete")));
                if (actionReminder.equals("Add") || actionReminder.equals("add")) { //If the user wants to add a reminder, this code is executed

                    if (r.getReminderName()[r.getReminderName().length - 1] != null) //If the list is full, no more reminders can be added
                    {
                        c.setColor(Color.WHITE);
                        c.fillRect(4, 746, 1050, 12);
                        drawUserBox();  //draws user box
                        c.setCursor(38, 2);
                        c.println("No more reminders can be added as the reminders list is full!(Maximum of 10 reminders only)");
                        delay(3000);

                    } else {    //If the list is not full, this structure is executed

                        do { //Error trap                  
                            c.setColor(Color.WHITE);
                            c.fillRect(4, 746, 1050, 12);
                            c.setCursor(39, 2);
                            drawUserBox();  //draws user box
                            do {
                                c.setColor(Color.WHITE);
                                c.fillRect(4, 746, 1050, 12);
                                drawUserBox();  //draws user box
                                c.setCursor(38, 2);
                                c.println("Enter the name of the reminder you wish to add (can only be 24 characters including spaces):");
                                drawUserBox();
                                c.setCursor(39, 2);
                                addName = c.readLine();
                                drawUserBox();  //draws user box
                                if (listNames.contains(addName)) {   //If the reminders already contain a reminder with that name, this structure is ran

                                    c.setCursor(38, 2);
                                    c.println("The list already contains a reminder with that name!");
                                    drawUserBox();  //draws user box
                                    delay(3000);
                                }

                            } while ((listNames.contains(addName)));

                        } while (!(addName.length() >= 1 && addName.length() <= 24));

                        do { // error checking time/day
                            c.setColor(Color.WHITE);
                            c.fillRect(4, 746, 1050, 12);
                            drawUserBox();  //draws user box
                            c.setCursor(38, 2);
                            c.println("Enter the date of the reminder you wish to add (with format: MONTH DAY, ex: January 5):");
                            drawUserBox();  //draws user box
                            c.setCursor(39, 2);
                            addDate = c.readLine();
                            c.setColor(Color.WHITE);
                            c.fillRect(4, 746, 1050, 12);
                            c.setCursor(38, 2);

                            splitDate = addDate.split(" ");
                            dayNum = Integer.parseInt(splitDate[1]);

                            if ((splitDate[0].equals("January")) || (splitDate[0].equals("March") || (splitDate[0].equals("May") || (splitDate[0].equals("July") || (splitDate[0].equals("August") || (splitDate[0].equals("October") || (splitDate[0].equals("December")))))))) {
                                days = 31;
                            } else if ((splitDate[0].equals("April")) || (splitDate[0].equals("June") || (splitDate[0].equals("September") || (splitDate[0].equals("November"))))) {
                                days = 30;
                            } else if ((splitDate[0].equals("February"))) {
                                days = 28;
                            } else {

                            }

                        } while (!(linearSearch(allMonths, splitDate[0]) != -1 && (dayNum <= days && dayNum >= 1))); // error check remidner date inputted by user to be a month and a day

                        /*To error check the time, we decided to use something known as regular expression ,or regex. Regex essentially allows
                    us to specify the format in which the user must input in. We acheive this by specifying the correct format for the time through
                   the use of regular expressions. Then, we check if the user has inputted the correct format through the use of the Pattern
                    class and the Matcher class*/
                        c.setColor(Color.WHITE);
                        c.fillRect(2, 746, 700, 12);
                        c.println("Enter the time of the reminder you wish to add. Enter the time like the following; '2:00 pm' or '10:00 am':");

                        do {//error trap
                            drawUserBox();  //draws user box
                            c.setCursor(39, 2);
                            p = Pattern.compile("(1[012]|[1-9]):([0-5][0-9])(\\ )(?i)(AM|PM)"); //Creates the correct "format" for the time
                            drawUserBox();  //draws user box
                            c.setCursor(39, 2);
                            addTime = c.readLine();
                            m = p.matcher(addTime); //This allows us to compare what the user entered with the correct format for the time
                        } while (m.matches() != true); //The m.matches() method checks if the time entered matches the correct format for the time      

                        c.setColor(Color.WHITE);
                        c.fillRect(2, 746, 700, 12);

                        r.addReminder(addName, addDate, addTime);
                        r.clearReminders();
                        r.displayReminders();

                        c.setColor(Color.WHITE);
                        c.fillRect(2, 746, 700, 30);
                        c.setCursor(38, 2);
                        c.println("The reminder was added!");
                        drawUserBox();
                        delay(3000);
                        ls.saveData(account, user + ".ser");        //Saving any changes
                    }
                } else if (actionReminder.equals("Delete") || actionReminder.equals("delete")) { //If the user wants to delete a reminder, this code is executed

                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    drawUserBox();  //draws user box
                    c.setCursor(38, 2);
                    c.println("Enter the name of the reminder that you wish to remove: ");
                    drawUserBox();
                    c.setCursor(39, 2);
                    deleteName = c.readLine();

                    if (!(listNames.contains(deleteName))) {    //If the list does not contain the reminder, this structure is ran
                        drawUserBox();  //draws user box
                        c.setColor(Color.WHITE);
                        c.fillRect(5, 746, 850, 12);
                        c.setCursor(38, 2);
                        c.println("The reminder was not found!");
                        drawUserBox();
                        c.setCursor(38, 2);
                        delay(3000);

                    } else if (listNames.contains(deleteName)) {    //If the list contains the reminder this structure is ran
                        r.deleteReminder(deleteName);
                        r.clearReminders();
                        r.displayReminders();
                        c.setColor(Color.WHITE);
                        c.fillRect(5, 746, 850, 30);
                        c.setCursor(38, 2);
                        c.println("The reminder was deleted!");
                        drawUserBox();  //draws user box
                        delay(3000);

                    } else {
                    }
                    ls.saveData(account, user + ".ser");     //Saving any changes
                } else {
                }

            } else if (action.equals("Calendar") || action.equals("calendar")) {         //If the user wants to use the calendar, this if structure is executed
                do {
                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    drawUserBox();  //draws user box
                    c.setCursor(38, 2);
                    c.println("Would you like to add/delete/modify an event (Enter 'add', 'delete', or 'modify') or would you like to view a new month/year? (Enter 'view'): ");         //Ask the user if they want to add/delete event or view a new month
                    drawUserBox();  //draws user box
                    c.setCursor(39, 2);
                    actionCalendar = c.readLine();

                } while (!(actionCalendar.equals("Add") || actionCalendar.equals("add") || actionCalendar.equals("Delete") || actionCalendar.equals("delete") || actionCalendar.equals("View") || actionCalendar.equals("view") || actionCalendar.equals("Modify") || actionCalendar.equals("modify")));

                if (actionCalendar.equals("Add") || actionCalendar.equals("add")) //If they want to add an event, the add event method is executed
                {
                    drawUserBox();  //draws user box
                    Calendar.addEvent();
                    ls.saveData(account, user + ".ser");     //Saving any changes
                } else if (actionCalendar.equals("Delete") || (actionCalendar.equals("delete"))) //If they want to delete an event, the delete event method is executed
                {
                    drawUserBox();  //draws user box
                    Calendar.deleteEvent();
                    ls.saveData(account, user + ".ser");     //Saving any changes
                } else if (actionCalendar.equals("View") || actionCalendar.equals("view")) //If they want to view a new month/year, the loadCalendar2 method is executed
                {
                    drawUserBox();  //draws user box
                    Calendar.loadCalendar2();
                    ls.saveData(account, user + ".ser");     //Saving any changes
                } else if (actionCalendar.equals("Modify") || actionCalendar.equals("modify")) {
                    drawUserBox();  //draws user box
                    Calendar.modifyEvent();
                    ls.saveData(account, user + ".ser");
                } else {
                }
            } else if ((action.equals("exit")) || action.equals("Exit")) {  //If the user wants to exit the application, this if structure is executed
                do {
                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    drawUserBox();  //draws user box
                    c.setCursor(38, 2);
                    c.println("Would you like to save your data? (Enter 'yes' or 'no')(NOTE: Not saving your data erases it)");
                    drawUserBox();  //draws user box
                    c.setCursor(39, 2);
                    saveYN = c.readLine();
                } while (!(saveYN.equals("Yes") || saveYN.equals("yes") || saveYN.equals("No") || saveYN.equals("no")));

                if ((saveYN.equals("Yes") || saveYN.equals("yes"))) {   //If the user wants to save their data, this is ran
                    RandomAccessFile raf = new RandomAccessFile("User.bin", "rw");
                    drawUserBox();  //draws user box
                    c.setCursor(38, 2);
                    ls.saveData(account, user + ".ser");
                    if (user.equals("User1")) {
                        raf.seek(0);
                        raf.writeInt(1);
                        raf.close();
                    } else if (user.equals("User2")) {
                        raf.seek(4);
                        raf.writeInt(1);
                        raf.close();
                    } else if (user.equals("User3")) {
                        raf.seek(8);
                        raf.writeInt(1);
                        raf.close();
                    }

                    drawUserBox();  //draws user box
                    c.setCursor(38, 2);
                    c.println("Data saved! You can now safely close the application");
                    drawUserBox(); //draws user box
                } else if ((saveYN.equals("No")) || (saveYN.equals("no"))) {
                    RandomAccessFile raf = new RandomAccessFile("User.bin", "rw");       //Opening the binary file that contains whether ot not a user is taken (i.e. whether or not a user has data)
                    c.setColor(Color.WHITE);
                    c.fillRect(4, 746, 1050, 12);
                    c.setCursor(38, 2);
                    drawUserBox();  //draws user box
                    Data emptyData = new Data();

                    if (user.equals("User1")) {
                        raf.seek(0);
                        raf.writeInt(0);
                        ls.saveData(emptyData, user + ".ser");     //Saving an empty data file 
                    } else if (user.equals("User2")) {
                        raf.seek(4);
                        raf.writeInt(0);
                        ls.saveData(emptyData, user + ".ser");     //Saving an empty data file
                    } else if (user.equals("User3")) {
                        raf.seek(8);
                        raf.writeInt(0);
                        ls.saveData(emptyData, user + ".ser");     //Saving an empty data file
                    } else {

                    }

                    c.setCursor(38, 2);
                    c.println("Okay, the data is not saved. You can now close the application");
                    drawUserBox();  //draws user box
                    raf.close();
                }

            } else {
            }

        } while (!(action.equals("Exit") || action.equals("exit")));

    }

    public static void delay(int millisecs) // Delay Method
    {
        try {
            Thread.currentThread().sleep(millisecs);
        } catch (InterruptedException e) {
        }
    }

    public static int linearSearch(String[] a, String b) { //Basic linear search
        int num = -1;

        for (int i = 0; i < a.length; i++) {
            if (a[i].equals(b)) {
                num = i;
                break;
            }
        }

        return num;
    }

    public static void drawUserBox() {  //Draws the user box

        c.setColor(Color.BLACK);
        c.setCursor(39, 2);
        c.print("                                                     ");
        c.drawRect(1, 735, 1197, 63);
        c.drawRect(2, 736, 1197, 63);
        c.drawRect(0, 737, 1197, 63);
        c.setCursor(39, 2);

    }

}
