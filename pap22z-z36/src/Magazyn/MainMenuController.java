package Magazyn;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class MainMenuController implements Initializable{

    @FXML
    private TableView<Product> ProductsTable;
    @FXML
    private TableColumn<Product, Integer> ID;
    @FXML
    private TableColumn<Product, String> Name;
    @FXML
    private TableColumn<Product, Integer> Quantity;
    @FXML
    private TableColumn<Product, Double> Price;
    @FXML
    private TableColumn<Product, Double> Tax;

    DBconnect db;
    EditConnectController ed;

    Connection connection = null;
    String query;
    PreparedStatement preparedStatement;
    ResultSet resultSet;
    Product product;
    ObservableList<Product> ProductList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle rb) {

            loadDate();

    }

    @FXML
    void setconnection(MouseEvent event) {
        String HOST ="";
        String USERNAME = "";
        String PASSWORD = "";
        Integer PORT = 0;
        String DB_NAME = "";

        DBparams dbpar = new DBparams();
        dbpar.readParams();

        HOST = dbpar.HOST;
        PORT = dbpar.PORT;
        USERNAME = dbpar.USERNAME;
        PASSWORD = dbpar.PASSWORD;
        DB_NAME = dbpar.DB_NAME;

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
        refreshTable();
    }



    private void loadDate()  {

        DBconnect.checkDatabase();
        connection = DBconnect.getConnect(true,false,false);

        ID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        Name.setCellValueFactory(new PropertyValueFactory<>("Name"));
        Quantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
        Price.setCellValueFactory(new PropertyValueFactory<>("Price"));
        Tax.setCellValueFactory(new PropertyValueFactory<>("Tax"));
        refreshTable();
        ProductsTable.setItems(ProductList);

    }

    @FXML
    public void refreshTable() {
        connection = DBconnect.getConnect(true,false,false);
        if (connection != null){
        try {
            ProductList.clear();

            query = "SELECT * FROM `Lista_Produktow`";
            preparedStatement = connection.prepareStatement(query);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                ProductList.add(new  Product(
                        resultSet.getString("NAME"),
                        resultSet.getInt("QUANTITY"),
                        resultSet.getDouble("TAX"),
                        resultSet.getDouble("PRICE"),
                        resultSet.getInt("ID")));
                ProductsTable.setItems(ProductList);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        }
    }

    private void removeProduct() {
        if(connection != null)
        {
            product = ProductsTable.getSelectionModel().getSelectedItem();
            if (product == null)
            {
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText(null);
                alert2.setContentText("Please select product to remove");
                alert2.showAndWait();
            }
            else{
            Alert alert =
            new Alert(AlertType.CONFIRMATION,
                "Delete selected product ?",
                 ButtonType.OK,
                 ButtonType.NO);
            alert.setTitle("Confirm deletion");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.get() == ButtonType.OK)
            {

            try {
      
            query = "DELETE FROM `Lista_Produktow` WHERE id  ="+product.getID();
            connection = DBconnect.getConnect(true,false,false);
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            refreshTable();
            

            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
        }
    }
    }

    private void editProduct() {
        product = ProductsTable.getSelectionModel().getSelectedItem();
        if (product == null)
        {
            Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
            alert2.setHeaderText(null);
            alert2.setContentText("Please select product to edit");
            alert2.showAndWait();
        }
        else{
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("AddProduct.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.out.println(ex);
        }

        AddProductController addProductController = loader.getController();
        addProductController.setUpdate(true);
        addProductController.setTextField(product.getID(), product.getName(),
                product.getQuantity(),product.getPrice(), product.getTax());
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.showAndWait();
        refreshTable();
    }
}

    @FXML
    private void add(MouseEvent event) {
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("AddProduct.fxml"));
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.showAndWait();
            refreshTable();
        } catch (Exception ex) {
            System.out.println(ex);
        }

    }
    @FXML
    void refresh(MouseEvent event) {
        refreshTable();
    }
    @FXML
    void remove(MouseEvent event) {
        removeProduct();
    }
    @FXML
    void edit(MouseEvent event) {
        editProduct();
    }

}
