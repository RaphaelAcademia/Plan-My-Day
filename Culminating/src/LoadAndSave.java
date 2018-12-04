/*This class handles loading and saving data through serialization, which is
the idea of saving the instance of a class (or primitive types) by "serializing" the instance
of the class (i.e. turning the instance of the class into bytes so it can be saved
on local storage). To load our data, we deserialize the saved bytes into an instance of
Object and cast the object to type Data. Then, we just call the getters of the Data 
class so that we can access the data for use in methods/other classes*/

import java.io.ObjectInputStream; //Allows us to read the data that we saved
import java.io.ObjectOutputStream; //Allows us to save the data
import java.io.*;

public class LoadAndSave {

    //This class does not need getters or setters as there are no variables
    
    public void saveData(Object data, String fileName) throws IOException { //This method allows us to save user data
        try {       //The try block allows us to run code that potentailly throws an exception. If it does, we "catch" the exception and we handle it
            FileOutputStream fileSave = new FileOutputStream(fileName); //Allows us to output data to a file
            ObjectOutputStream save = new ObjectOutputStream(fileSave); //Allows us to save objects to a file
            save.writeObject(data);
            save.close();                 
            fileSave.close();       //Similar to how we would have to close a binary file after accessing it.

        } catch (FileNotFoundException ex) {    //If the file was not found, run this code
            ex.printStackTrace();     //Prints information about the exception (for example, what line it occured)
        }
    }

    public Object loadData(String fileName) throws IOException {    //This method allows us to load user data
        Object data = null;
        try {    //The try block allows us to run code that potentailly throws an exception. If it does, we "catch" the exception and we handle it
            FileInputStream fileLoad = new FileInputStream(fileName);   //Allows us to load data from a file
            ObjectInputStream load = new ObjectInputStream(fileLoad);   //Allows us to load objects from a file
            data = load.readObject();
            fileLoad.close();                 //Similar to how we would close a binary file after accessing it.
            load.close();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();            //Prints information about the exception (for example, what line it occured)
        }
        return data;
    }

}
