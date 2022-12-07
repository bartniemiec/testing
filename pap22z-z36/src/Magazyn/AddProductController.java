package Magazyn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class AddProductController implements Initializable {

    @FXML
    private TextField nameFld;
    @FXML
    private TextField quantityFld;
    @FXML
    private TextField PriceFld;
    @FXML
    private TextField TaxFld;

    @FXML
    private Button saveBtn;
    
    String query = null;
    Connection connection = null;
    ResultSet resultSet = null;
    PreparedStatement preparedStatement;
    Product product = null;
    private boolean update;
    int ProductId;
    URL url1;
    ResourceBundle rb1;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
 
    }

    @FXML
    private void save(MouseEvent event) {

        connection = DBconnect.getConnect(true,false,false);
        String name = nameFld.getText();
        String quantity = String.valueOf(quantityFld.getText());
        String price = PriceFld.getText();
        String tax = TaxFld.getText();

        try{

            int q = Integer.valueOf(quantityFld.getText());
            Double p = Double.valueOf(PriceFld.getText());
            Double t = Double.valueOf(TaxFld.getText());

            if (q < 0){
                Alert alert1 = new Alert(Alert.AlertType.ERROR);
                alert1.setHeaderText(null);
                alert1.setContentText("Quantity must be positive");
                alert1.showAndWait();
            }
            else if (p < 0){
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText(null);
                alert2.setContentText("Price must be positive");
                alert2.showAndWait();
            }
            else if (t < 0 || t > 100){
                Alert alert3 = new Alert(Alert.AlertType.ERROR);
                alert3.setHeaderText(null);
                alert3.setContentText("Incorect tax value");
                alert3.showAndWait();
            }
            else if (name.isEmpty() || quantity.isEmpty() || price.isEmpty() || tax.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("All data must be fill");
                alert.showAndWait();
            }
            else{
                getQuery();
                insert();
                clean();
                Stage stage = (Stage) saveBtn.getScene().getWindow();
                stage.close();
            }
        }
        catch(Exception ex){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Incorrect data type");
            alert.showAndWait();
        }  
    }

    @FXML
    private void clean() {
        nameFld.setText(null);
        quantityFld.setText(null);
        PriceFld.setText(null);
        TaxFld.setText(null); 
    }

    private void getQuery() {

        if (update == false) {
            
            query = "INSERT INTO `Lista_Produktow`( `NAME`, `QUANTITY`, `PRICE`, `TAX`) VALUES (?,?,?,?)";
        }
        else {
            query = "UPDATE `Lista_Produktow` SET "
                    + "`NAME`=?,"
                    + "`QUANTITY`=?,"
                    + "`PRICE`=?,"
                    + "`TAX`= ? WHERE ID = '"+ProductId+"'";
        }
    }

    private void insert() {
        try {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, nameFld.getText());
            preparedStatement.setString(2, String.valueOf(quantityFld.getText()));
            preparedStatement.setString(3, PriceFld.getText());
            preparedStatement.setString(4, TaxFld.getText());
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(AddProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void setTextField(int id, String name, int quantity, Double Price, Double Tax) {

        ProductId = id;
        nameFld.setText(name);
        quantityFld.setText(Integer.toString(quantity));
        PriceFld.setText(Double.toString(Price));
        TaxFld.setText(Double.toString(Tax));

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

}
