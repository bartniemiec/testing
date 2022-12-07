package Magazyn;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class DBparams {
    
public String HOST;
public Integer PORT;
public String USERNAME;
public String PASSWORD;
public String DB_NAME;

public void readParams() {

    String filePath = new File("").getAbsolutePath();
    File f = new File(filePath+"/dbPar.json");

    if (!f.exists())
    {
        String HOST ="localhost";
        String USERNAME = "root";
        String PASSWORD = "";
        Integer PORT = 3306;
        String DB_NAME = "Magazyn";
         
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("EditConnect.fxml"));
        try {
                loader.load();
            } 
        catch (Exception ex) {
            System.out.println(ex);
            }
        
        EditConnectController editconnectController = loader.getController();
        editconnectController.setTextField(HOST, PORT, DB_NAME, USERNAME, PASSWORD); 
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.setTitle("Set database connection");
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();

    }

    if (!f.exists())
    {
        System.out.println("Configuration file dbpar.json not exists!");
        System.exit(0);
    }


        JSONParser parser = new JSONParser();
        {
        
        try (FileReader reader = new FileReader(filePath+"/dbPar.json")) 
        {
            Object obj = parser.parse(reader);
            JSONArray parArr =  (JSONArray) obj;
            parArr.forEach( parObj -> parseObject( (JSONObject) parObj ) );
    
        } 
        catch (FileNotFoundException e) {
            e.printStackTrace();}

        catch (IOException e) {
        
            e.printStackTrace();}
        catch (ParseException e) {
        
            e.printStackTrace();} 
        catch (Exception e) {
            e.printStackTrace();
        }    
    }
}

@SuppressWarnings("unchecked")
public  void saveParams()
{
    {
       
        JSONObject dbpar = new JSONObject();
        dbpar.put("host", HOST);
        String port_s = String.valueOf(PORT);
        dbpar.put("port", port_s);
        dbpar.put("username", USERNAME);
        dbpar.put("password", PASSWORD);
        dbpar.put("db_name", DB_NAME);
        JSONObject database = new JSONObject(); 
        database.put("database", dbpar);  
       
        JSONArray dblist = new JSONArray();
        dblist.add(dbpar);
       
        //Write JSON file
        String filePath = new File("").getAbsolutePath();
        try (FileWriter file = new FileWriter(filePath+"/dbPar.json")) {
            file.write(dblist.toString()); 
            file.flush();
 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

private  void parseObject(JSONObject parObj) 
    {
        HOST = (String) parObj.get("host");  
        PORT = Integer.valueOf((String) parObj.get("port"));
        USERNAME = (String) parObj.get("username");  
        PASSWORD = (String) parObj.get("password");  
        DB_NAME =  (String) parObj.get("db_name");
    }
}

