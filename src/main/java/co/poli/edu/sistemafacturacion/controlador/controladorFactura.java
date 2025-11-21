package co.poli.edu.sistemafacturacion.controlador;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorFactura {

    @FXML
    private Button btnCrearCliente;

    @FXML
    private Button btnCrearProducto;

    @FXML
    private Button btnCrearVendedor;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnReportes;

    @FXML
    private TextField cantProd1;

    @FXML
    private TextField cantProd2;

    @FXML
    private TextField cantProd3;

    @FXML
    private TextField descrProd1;

    @FXML
    private TextField descrProd2;

    @FXML
    private TextField descrProd3;

    @FXML
    private TextField fechExp;

    @FXML
    private TextField fecha;

    @FXML
    private TextField idCliente;

    @FXML
    private TextField idFactura;

    @FXML
    private TextField idProd1;

    @FXML
    private TextField idProd2;

    @FXML
    private TextField idProd3;

    @FXML
    private TextField nombreCliente;

    @FXML
    private TextField nombreVendedor;

    @FXML
    private TextField precioProd1;

    @FXML
    private TextField precioProd2;

    @FXML
    private TextField precioProd3;

    @FXML
    private TextField subtProd1;

    @FXML
    private TextField subtProd2;

    @FXML
    private TextField subtProd3;

    @FXML
    private TextField totalFactura;

    @FXML
    void clickCrearCliente(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/co/poli/edu/sistemafacturacion/vista/crearCliente.fxml"));

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.show();

        // Cerrar la ventana actual
        Stage actual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        actual.close();

    }

    @FXML
    void clickCrearProducto(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/co/poli/edu/sistemafacturacion/vista/crearProducto.fxml"));

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.show();

        // Cerrar la ventana actual
        Stage actual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        actual.close();

    }

    @FXML
    void clickCrearVendedor(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/co/poli/edu/sistemafacturacion/vista/crearVendedor.fxml"));

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.show();

        // Cerrar la ventana actual
        Stage actual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        actual.close();

    }

    @FXML
    void clickGuardarFactura(ActionEvent event) {

    }

    @FXML
    void clickReportes(ActionEvent event) {

    }

    @FXML
    void filtrarId(ActionEvent event) {

    }

    @FXML
    void filtrarIdCliente(ActionEvent event) {

    }

    @FXML
    void filtrarIdFactura(ActionEvent event) {

    }

    @FXML
    void filtrarIdProducto(ActionEvent event) {

    }

}

