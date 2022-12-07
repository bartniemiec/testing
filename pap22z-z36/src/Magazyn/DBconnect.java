package Magazyn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.StageStyle;




public class DBconnect {

    public static String HOST = "127.0.0.1";
    public static int PORT = 3306;
    public static String DB_NAME = "Magazyn1";
    public static String USERNAME = "root";
    public static String PASSWORD = "root1234";
    public static Boolean silent_mode = true;

    private static Connection connection ;
    static String query;
    static PreparedStatement preparedStatement;
    static ResultSet resultSet;
    

    public static Connection getConnect (Boolean silentmode, Boolean noparam, Boolean nodb_name){
        connection = null;
        if (noparam == false)
        {
        DBparams dbpar = new DBparams();
        dbpar.readParams();
        HOST = dbpar.HOST;
        PORT = dbpar.PORT;
        USERNAME = dbpar.USERNAME;
        PASSWORD = dbpar.PASSWORD;
        DB_NAME = dbpar.DB_NAME;
        }
        try {
        if (nodb_name) 
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d", HOST,PORT),USERNAME,PASSWORD);
        else     
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
        if (!connection.isClosed()){
            if (!silentmode){
            msgBox("Connection OK", "Database", "Success!");
            }
        }
        else
        {
            msgBox("Connection Error", "Database", "Error!");
        }    
        
    } catch (SQLException ex) {

    }
        if (connection == null)
        {
            msgBox("Connection Error", "Database", "Error!");
        }   
        
        return connection;
    }


    public static void checkDatabase() {
        
        DBparams dbpar = new DBparams();
        dbpar.readParams();
        HOST = dbpar.HOST;
        PORT = dbpar.PORT;
        USERNAME = dbpar.USERNAME;
        PASSWORD = dbpar.PASSWORD;
        DB_NAME = dbpar.DB_NAME;
        try {
            connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d", HOST,PORT),USERNAME,PASSWORD);
            if (connection.isClosed()){
                msgBox("Connection Error", "Database", "Error!");
            }
            
        }
        catch (SQLException ex) {

        }
        if (connection  == null)    
        {
            HOST = dbpar.HOST;
            PORT = dbpar.PORT;
            USERNAME = dbpar.USERNAME;
            PASSWORD = dbpar.PASSWORD;
            DB_NAME = dbpar.DB_NAME;
            
            FXMLLoader loader = new FXMLLoader ();
            loader.setLocation(Main.class.getResource("EditConnect.fxml"));
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
            stage.setTitle("Set database connection");
            stage.setScene(new Scene(parent));
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();

        }

        //sprawdź czy baza utworzona
        try
        {
            query = String.format("SELECT SCHEMA_NAME FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME ='%s'",DB_NAME);
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();
            int rowCount=0;
            while (resultSet.next())
            {
                rowCount += 1;
            }  
            if (rowCount == 1)
            {
                System.out.println("Baza "+DB_NAME+" juz istnieje");
            }
            else 
            {
                Alert alert = 
                new Alert(AlertType.WARNING, 
                    "Create new database: " +DB_NAME+" ?",
                     ButtonType.OK, 
                     ButtonType.NO);
                alert.setTitle("New database");
                Optional<ButtonType> result = alert.showAndWait();
        
                if (result.get() == ButtonType.OK) {
        
                try{
                query = String.format("CREATE DATABASE %s",DB_NAME);
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();   
                }
                catch (SQLException ex){
                    System.out.println(ex);
                }        
                
                
                connection.close();

                try {
                    connection = DriverManager.getConnection(String.format("jdbc:mysql://%s:%d/%s", HOST,PORT,DB_NAME),USERNAME,PASSWORD);
                    if (connection.isClosed()){
                        msgBox("Connection Error", "Database", "Error!");
                    }
                    
                }
                catch (SQLException ex) {
                    
                }

                try{
                    query = "DROP TABLE IF EXISTS Lista_Produktow";
                    preparedStatement = connection.prepareStatement(query);
                    preparedStatement.executeUpdate();   
                }
                catch (SQLException ex){
                        System.out.println(ex);
                }  

                try{
                query = "CREATE TABLE Lista_Produktow (" +
                        "ID int NOT NULL AUTO_INCREMENT," +
                        "NAME varchar(255),"+
                        "QUANTITY int DEFAULT NULL," +
                        "PRICE decimal(15,2) DEFAULT NULL," +
                        "TAX decimal(15,2) DEFAULT NULL," +
                        "PRIMARY KEY (ID))";
               preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();  
                }
                catch (SQLException ex){
                    System.out.println(ex);
            }  
        }

            };
        } 
        catch (SQLException ex){
            System.out.println(ex);
        }
    
    }

    public static void msgBox(String infoMessage, String titleBar, String headerMessage)
    {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(titleBar);
        alert.setHeaderText(headerMessage);
        alert.setContentText(infoMessage);
        alert.showAndWait();
    }


    }



