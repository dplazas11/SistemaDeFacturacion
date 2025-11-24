package co.poli.edu.sistemafacturacion.controlador;

import co.poli.edu.sistemafacturacion.modelo.Cliente;
import co.poli.edu.sistemafacturacion.modelo.Factura;
import co.poli.edu.sistemafacturacion.modelo.Producto;
import co.poli.edu.sistemafacturacion.modelo.Vendedor;
import co.poli.edu.sistemafacturacion.repositorio.operacionesCliente;
import co.poli.edu.sistemafacturacion.repositorio.operacionesFactura;
import co.poli.edu.sistemafacturacion.repositorio.operacionesProducto;
import co.poli.edu.sistemafacturacion.repositorio.operacionesVendedor;
import java.io.IOException;
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class controladorFactura {

    @FXML
    private TextField IdVendedor;

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
    private TextField cantTotal;

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

    operacionesFactura repoFact = new operacionesFactura();
    operacionesCliente repoClien = new operacionesCliente();
    operacionesProducto repoProd = new operacionesProducto();
    operacionesVendedor repoVend = new operacionesVendedor();

    public void initialize() {
        Platform.runLater(() -> {
            idCliente.requestFocus();
            String idSiguiente = repoFact.obtenerIdfactSiguiente();
            idFactura.setText(idSiguiente);

            idCliente.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {  // perdió el foco
                    if (idCliente.getText().isBlank()) {
                        idCliente.setText("1");
                        filtrarIdCliente();
                    } else {
                        filtrarIdCliente();
                    }
                }
            });
            IdVendedor.focusedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) {  // perdió el foco
                    if (IdVendedor.getText().isBlank()) {
                        IdVendedor.setText("1");
                        clickFiltrarIdVendedor();
                    } else {
                        clickFiltrarIdVendedor();
                    }
                }
            });

            fecha.setText(LocalDate.now().toString());

            //Busqueda productos
            idProd1.focusedProperty().addListener((obs, oldV, newV) -> {
                if (!newV) { // perdió el foco
                    filtrarIdProducto(idProd1, descrProd1, precioProd1);
                }
            });

            idProd2.focusedProperty().addListener((obs, oldV, newV) -> {
                if (!newV) { // perdió el foco
                    filtrarIdProducto(idProd2, descrProd2, precioProd2);
                }
            });

            idProd3.focusedProperty().addListener((obs, oldV, newV) -> {
                if (!newV) { // perdió el foco
                    filtrarIdProducto(idProd3, descrProd3, precioProd3);
                }
            });

            //Calculo de subtotal 
            cantProd1.focusedProperty().addListener((obs, oldV, newV) -> {
                if (!newV) { // perdió el foco
                    definirSubtotal(precioProd1, cantProd1, subtProd1);
                    definirCantTotal(cantProd1, cantProd2, cantProd3);
                    calcularTotal(subtProd1, subtProd2, subtProd3);
                }
            });

            cantProd2.focusedProperty().addListener((obs, oldV, newV) -> {
                if (!newV) { // perdió el foco
                    definirSubtotal(precioProd2, cantProd2, subtProd2);
                    definirCantTotal(cantProd1, cantProd2, cantProd3);
                    calcularTotal(subtProd1, subtProd2, subtProd3);
                }
            });

            cantProd3.focusedProperty().addListener((obs, oldV, newV) -> {
                if (!newV) { // perdió el foco
                    definirSubtotal(precioProd3, cantProd3, subtProd3);
                    definirCantTotal(cantProd1, cantProd2, cantProd3);
                    calcularTotal(subtProd1, subtProd2, subtProd3);
                }
            });
        });

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
    void clickGuardarFactura(ActionEvent event) {

        String id_fact = idFactura.getText();
        String fech = fecha.getText();
        String id_clie = idCliente.getText();
        String id_vend = IdVendedor.getText();
        int cant_Total = Integer.parseInt(cantTotal.getText());
        int total = Integer.parseInt(totalFactura.getText());

        Object factBuscada = repoFact.selectId(id_fact);

        if (factBuscada == null) {

            Factura factNueva = new Factura(id_fact, fech, id_clie, id_vend,cant_Total, total);
            String respuesta = repoFact.insertar(factNueva);
            crearAlerta(respuesta);
            LimpiarDatos();
            String idSiguiente = repoFact.obtenerIdfactSiguiente();
            idFactura.setText(idSiguiente);
        } else {
            crearAlerta("Esta factura ya ha sido creada. Por favor, guarde un nuevo cliente.");
        }

    }

    @FXML
    void clickFiltrarIdVendedor() {

        String id_vend = IdVendedor.getText();

        Object VendBuscado = repoVend.selectId(id_vend);

        if (VendBuscado != null) {
            Vendedor vendEncontrado = (Vendedor) VendBuscado;
            nombreVendedor.setText(vendEncontrado.getNombre());
        }

    }

    @FXML
    void clickReportes(ActionEvent event) throws IOException {
         Parent root = FXMLLoader.load(getClass().getResource("/co/poli/edu/sistemafacturacion/vista/reportes.fxml"));

        Stage nuevaVentana = new Stage();
        nuevaVentana.setScene(new Scene(root));
        nuevaVentana.show();

        // Cerrar la ventana actual
        Stage actual = (Stage) ((Node) event.getSource()).getScene().getWindow();
        actual.close();

    }

    @FXML
    void filtrarIdCliente() {

        String id_clien = idCliente.getText();

        Object clienBuscado = repoClien.selectId(id_clien);

        if (clienBuscado != null) {
            Cliente clienEncontrado = (Cliente) clienBuscado;
            nombreCliente.setText(clienEncontrado.getNombre());

        }

    }

    void filtrarIdProducto(TextField idprod, TextField descrip, TextField precio) {

        String id_prod = idprod.getText();

        Object prodBuscado = repoProd.selectId(id_prod);

        if (prodBuscado != null) {
            Producto pordEncontrado = (Producto) prodBuscado;
            descrip.setText(pordEncontrado.getDescripcion());
            precio.setText(String.valueOf(pordEncontrado.getPrecioUnitario()));

        }

    }

    void definirSubtotal(TextField cant, TextField precio, TextField subt) {

        int subtotal = Integer.parseInt(cant.getText()) * Integer.parseInt(precio.getText());
        subt.setText(String.valueOf(subtotal));

    }
    
    void definirCantTotal(TextField cant1, TextField cant2, TextField cant3){
        
        if (cant2.getText().isBlank() && cant3.getText().isBlank()) {
            int total = Integer.parseInt(cant1.getText());
            cantTotal.setText(String.valueOf(total));
        } else if (cant3.getText().isBlank()) {
            int total = Integer.parseInt(cant1.getText()) + Integer.parseInt(cant2.getText());
            cantTotal.setText(String.valueOf(total));
        } else {
            int total = Integer.parseInt(cant1.getText()) + Integer.parseInt(cant2.getText()) + Integer.parseInt(cant3.getText());
            cantTotal.setText(String.valueOf(total));
        }
        
    }

    void calcularTotal(TextField subt1, TextField subt2, TextField subt3) {

        if (subt2.getText().isBlank() && subt3.getText().isBlank()) {
            int total = Integer.parseInt(subt1.getText());
            totalFactura.setText(String.valueOf(total));
        } else if (subt3.getText().isBlank()) {
            int total = Integer.parseInt(subt1.getText()) + Integer.parseInt(subt2.getText());
            totalFactura.setText(String.valueOf(total));
        } else {
            int total = Integer.parseInt(subt1.getText()) + Integer.parseInt(subt2.getText()) + Integer.parseInt(subt3.getText());
            totalFactura.setText(String.valueOf(total));
        }
    }

    //METODOS ADICIONALES
    void crearAlerta(String respuesta
    ) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText("Estado");
        alerta.setContentText(respuesta); // directamente en el cuerpo
        alerta.getDialogPane().setMinWidth(500);
        alerta.showAndWait();

    }

    void LimpiarDatos() {

        idCliente.setText("");
        nombreCliente.setText("");
        IdVendedor.setText("");
        nombreVendedor.setText("");
        idProd1.setText("");
        descrProd1.setText("");
        cantProd1.setText("");
        precioProd1.setText("");
        subtProd1.setText("");
        idProd2.setText("");
        descrProd2.setText("");
        cantProd2.setText("");
        precioProd2.setText("");
        subtProd2.setText("");
        idProd3.setText("");
        descrProd3.setText("");
        cantProd3.setText("");
        precioProd3.setText("");
        subtProd3.setText("");
        totalFactura.setText("");

    }
}
