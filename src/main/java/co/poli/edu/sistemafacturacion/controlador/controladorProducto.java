package co.poli.edu.sistemafacturacion.controlador;

import co.poli.edu.sistemafacturacion.modelo.Producto;
import co.poli.edu.sistemafacturacion.repositorio.operacionesProducto;
import java.io.IOException;
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

public class controladorProducto {

    @FXML
    private TextField IdProducto;

    @FXML
    private Button btnCrearCliente;

    @FXML
    private Button btnCrearProducto;

    @FXML
    private Button btnCrearVendedor;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnGuardar;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnReportes;

    @FXML
    private Button btnVolver;

    @FXML
    private TextField cantidad;

    @FXML
    private TextField descripcion;

    @FXML
    private TextField precio;

    operacionesProducto repo = new operacionesProducto();

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
    void clickEliminarProducto(ActionEvent event) {
        String id_producto = IdProducto.getText();
        
        String respuesta = repo.eliminar(id_producto);
        crearAlerta(respuesta);
        LimpiarDatos(1);
        

    }

    @FXML
    void clickGuardarProducto(ActionEvent event) {

        String id_producto = IdProducto.getText();
        String descrip = descripcion.getText();
        int cant = Integer.parseInt(cantidad.getText());
        double precioUnt = Double.parseDouble(precio.getText());

        Object productoBuscado = repo.selectId(id_producto);

        if (productoBuscado == null) {
            Producto prod = new Producto(id_producto, descrip, cant, precioUnt);

            String respuesta = repo.insertar(prod);
            crearAlerta(respuesta);
            LimpiarDatos(1);
        }
        crearAlerta("El producto ya existe. Por favor, ingrese un producto nuevo.");
    }

    @FXML
    void clickModificarProducto(ActionEvent event) {

        if (IdProducto.getText().isBlank() || descripcion.getText().isBlank()
                || cantidad.getText().isBlank() || precio.getText().isBlank()) {
            crearAlerta("Por favor, ingrese todos los valores completos.");
        } else {
            String id_prod = IdProducto.getText();
            String descrip = descripcion.getText();
            int cant = Integer.parseInt(cantidad.getText());
            double precioUnt = Double.parseDouble(precio.getText());
            Producto prod = new Producto(id_prod, descrip, cant, precioUnt);
            
            String prodMod = repo.actualizar(prod);
            crearAlerta(prodMod);
            LimpiarDatos(1);
        }

        

    }

    @FXML
    void clickReportes(ActionEvent event) {

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

    @FXML
    void filtrarIdProducto(ActionEvent event) {

        String id_prod = IdProducto.getText();

        Object productoBuscado = repo.selectId(id_prod);

        if (productoBuscado != null) {
            Producto productoEncontrado = (Producto) productoBuscado;

            descripcion.setText(productoEncontrado.getDescripcion());
            cantidad.setText(String.valueOf(productoEncontrado.getCantidad()));
            precio.setText(String.valueOf(productoEncontrado.getPrecioUnitario()));
        } else {
            LimpiarDatos(2);
        }

    }

    //METODOS ADICIONALES
    void crearAlerta(String respuesta) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText("Estado");
        alerta.setContentText(respuesta); // directamente en el cuerpo
        alerta.getDialogPane().setMinWidth(500);
        alerta.showAndWait();

    }

    void LimpiarDatos(int cod) {
        switch (cod) {
            case 1:
                IdProducto.setText("");
                descripcion.setText("");
                cantidad.setText("");
                precio.setText("");
            case 2:
                descripcion.setText("");
                cantidad.setText("");
                precio.setText("");

                break;
            default:
                throw new AssertionError();
        }
    }
}
