package Magazyn;

import java.sql.Connection;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



public class EditConnectController{
    Connection connection;

    @FXML
    private Label Label;

    @FXML
    private TextField hostFld;

    @FXML
    private PasswordField passFld;

    
    @FXML
    private TextField portFld;

    @FXML
    private TextField userFld;

    @FXML
    private TextField db_nameFld;

    @FXML
    private Button saveBtn;

    @FXML
    void exit(MouseEvent event) {
        
    }
    

    @FXML
    private void close(){
        Stage stage = (Stage) saveBtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void save(MouseEvent event) {

        String host = hostFld.getText();
        Integer port = Integer.valueOf(portFld.getText());
        String user = userFld.getText();
        String pass = passFld.getText();
        String db_name = db_nameFld.getText();

        DBconnect.HOST = host;
        DBconnect.PORT = port;
        DBconnect.DB_NAME = db_name;
        DBconnect.USERNAME = user;
        DBconnect.PASSWORD = pass;

        connection = DBconnect.getConnect(false,true,true);
        if (connection != null )
        {   
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            DBparams dbpar = new DBparams();
            dbpar.HOST = host;
            dbpar.PORT = port;
            dbpar.DB_NAME = db_name;
            dbpar.USERNAME = user;
            dbpar.PASSWORD = pass;
            dbpar.saveParams();
            DBconnect.checkDatabase();
            close();
        }
    }

    @FXML
    void check(MouseEvent event) {

        String host = hostFld.getText();
        Integer port = Integer.valueOf(portFld.getText());
        String user = userFld.getText();
        String pass = passFld.getText();
        String db_name = db_nameFld.getText();

        DBconnect.HOST = host;
        DBconnect.PORT = port;
        DBconnect.DB_NAME = db_name;
        DBconnect.USERNAME = user;
        DBconnect.PASSWORD = pass;
        connection = DBconnect.getConnect(false,true,true);
    }

    public void paramsDatabase(String HOST, Integer PORT, String DB_NAME, String USERNAME, String PASSWORD) {

        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("EditConnect.fxml"));
        try {
                loader.load();
            } 
        catch (Exception ex) {
            System.out.println(ex);
            }
        
        EditConnectController editconnectController = loader.getController();
        //editconnectController.setUpdate(true);
        editconnectController.setTextField(HOST, PORT, DB_NAME, USERNAME, PASSWORD); 
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
    }


    void setTextField(String host, Integer port, String db_name, String user, String pass) {

        hostFld.setText(host);
        portFld.setText(Integer.toString(port));
        userFld.setText(user);
        passFld.setText(pass);
        db_nameFld.setText(db_name);
    }
}
