package co.poli.edu.sistemafacturacion.controlador;

import co.poli.edu.sistemafacturacion.repositorio.operacionesReportes;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import org.bson.Document;

public class controladorReportes {

    @FXML
    private Button btnCantProdVend;

    @FXML
    private Button btnClienMasComp;

    @FXML
    private Button btnCrearCliente;

    @FXML
    private Button btnCrearProducto;

    @FXML
    private Button btnCrearVendedor;

    @FXML
    private Button btnFactMasCos;

    @FXML
    private Button btnVentasComp;

    @FXML
    private Button btnPromVenDia;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnTotalInv;

    @FXML
    private Button btnVenDia;

    @FXML
    private Button btnVentVend;

    @FXML
    private Button btnVolver;

    @FXML
    private TextArea txtcuadrotexto;

    operacionesReportes opRepo = new operacionesReportes();

    @FXML
    void clickCantidadPromedioProductosVendidos(ActionEvent event) {
        String respuesta = opRepo.CantidadPromedioProductosVendidos();
        txtcuadrotexto.setText(respuesta);

    }

    @FXML
    void clickClienteConMasCompras(ActionEvent event) {
        String respuesta = opRepo.clienteConMasCompras();
        txtcuadrotexto.setText(respuesta);

    }

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
    void clickFacturaMasCostosa(ActionEvent event) {

        String respuesta = opRepo.FacturaMasCostosa();
        txtcuadrotexto.setText(respuesta);

    }

    @FXML
    void clickVentaPorCliente(ActionEvent event) {
        ArrayList<Document> respuesta = opRepo.VentaPorCliente();
        String mensaje = "Ventas por cliente: \n";
        for (Document document : respuesta) {
            mensaje = mensaje + document.toString() + "\n";

        }
        txtcuadrotexto.setText(mensaje);
    }

    @FXML
    void clickPromedioVentasDiario(ActionEvent event) {
        String respuesta = opRepo.PromedioVentasDiario();
        txtcuadrotexto.setText(respuesta);
    }

    @FXML
    void clickTotalInventario(ActionEvent event) {

        String respuesta = opRepo.totalInventario();
        txtcuadrotexto.setText(respuesta);

    }

    @FXML
    void clickTotalVentasVendedor(ActionEvent event) {
        ArrayList<Document> respuesta = opRepo.TotalVentasVendedor();
        String mensaje = "Ventas por vendedor : \n";
        for (Document document : respuesta) {
            mensaje = mensaje + document.toString() + "\n";

        }
        txtcuadrotexto.setText(mensaje);
    }

    @FXML
    void clickVentasPorDia(ActionEvent event) {

        ArrayList<Document> respuesta = opRepo.VentasPorDia();
        String mensaje = "Ventas por dia: \n";
        for (Document document : respuesta) {
            mensaje = mensaje + document.toString() + "\n";

        }
        txtcuadrotexto.setText(mensaje);

    }

    @FXML
    void clickVolver(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/co/poli/edu/sistemafacturacion/vista/facturacion.fxml"));

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.show();

        // Cerrar la ventana actual
        Stage actual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        actual.close();
    }

    //METODOS ADICIONALES
    void crearAlerta(String respuesta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText("Estado");
        alerta.setContentText(respuesta); // directamente en el cuerpo
        alerta.getDialogPane().setMinWidth(500);
        alerta.showAndWait();

    }
}
