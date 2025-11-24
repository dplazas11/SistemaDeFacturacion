package co.poli.edu.sistemafacturacion.controlador;

import co.poli.edu.sistemafacturacion.modelo.Vendedor;
import co.poli.edu.sistemafacturacion.repositorio.operacionesVendedor;
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

public class controladorVendedor {

    @FXML
    private TextField Direccion;

    @FXML
    private TextField IdVendedor;

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
    private TextField correo;

    @FXML
    private TextField nombreVendedor;

    operacionesVendedor repo = new operacionesVendedor();

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
    void clickEliminarVendedor(ActionEvent event) {

        String id_vend = IdVendedor.getText();
        
        String respuesta = repo.eliminar(id_vend);
        crearAlerta(respuesta);
        LimpiarDatos(1);
    }

    @FXML
    void clickGuardarVendedor(ActionEvent event) {

        String id_vend = IdVendedor.getText();
        String nomVend = nombreVendedor.getText();
        String direcc = Direccion.getText();
        String correoVend = correo.getText();

        Object vendBuscado = repo.selectId(id_vend);
        if (vendBuscado == null) {
            Vendedor vend = new Vendedor(id_vend, nomVend, direcc, correoVend);
            String respuesta = repo.insertar(vend);
            crearAlerta(respuesta);
            LimpiarDatos(1);

        }else {
            crearAlerta("No se puede guardar este vendedor porque ya existe. \n Por favor, ingrese un vendedor nuevo.");
            LimpiarDatos(1);
        }

    }

    @FXML
    void clickModificarVendedor(ActionEvent event) {
        
        String id_vend = IdVendedor.getText();
        String nomVend = nombreVendedor.getText();
        String direcc = Direccion.getText();
        String correoVend = correo.getText();
        
        Vendedor vendCambiado = new Vendedor(id_vend, nomVend, direcc, correoVend);
        String respuesta  = repo.actualizar(vendCambiado);
        crearAlerta(respuesta);
        LimpiarDatos(1);
        

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
    void filtrarIdVendedor(ActionEvent event) {

        String id_vend = IdVendedor.getText();

        Object vendBuscado = repo.selectId(id_vend);

        if (vendBuscado != null) {
            Vendedor vend = (Vendedor) vendBuscado;
            nombreVendedor.setText(vend.getNombre());
            Direccion.setText(vend.getDireccion());
            correo.setText(vend.getCorreo());
        }else{
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
                IdVendedor.setText("");
                nombreVendedor.setText("");
                Direccion.setText("");
                correo.setText("");
            case 2:
                nombreVendedor.setText("");
                Direccion.setText("");
                correo.setText("");

                break;
            default:
                throw new AssertionError();
        }
    }

}
