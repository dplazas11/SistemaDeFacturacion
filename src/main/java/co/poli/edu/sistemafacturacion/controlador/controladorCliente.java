package co.poli.edu.sistemafacturacion.controlador;

import co.poli.edu.sistemafacturacion.modelo.Cliente;
import co.poli.edu.sistemafacturacion.repositorio.operacionesCliente;
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

public class controladorCliente {

    @FXML
    private TextField Direccion;

    @FXML
    private TextField Idcliente;

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
    private TextField nombreCliente;
    
    operacionesCliente repo = new operacionesCliente();

    
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
    void clickEliminarCliente(ActionEvent event) {
        
        String id_cliente = Idcliente.getText();
                
        String respuesta = repo.eliminar(id_cliente);
        crearAlerta(respuesta);
        LimpiarDatos(1);

    }

    @FXML
    void clickGuardarCliente(ActionEvent event) {
        
        String id_cliente = Idcliente.getText();
        String nomCliente = nombreCliente.getText();
        String clienteDireccion = Direccion.getText();
        String clienteCorreo = correo.getText();
        
        
        Object clienteBuscado = repo.selectId(id_cliente);
        
        if (clienteBuscado == null){
            
            Cliente clienteNuevo = new Cliente(id_cliente, nomCliente, clienteDireccion, clienteCorreo);
            String respuesta = repo.insertar(clienteNuevo);
            crearAlerta(respuesta);
            LimpiarDatos(1);
        }else {
            crearAlerta("Este Cliente ya esta creado. Por favor, guarde un nuevo cliente.");
        }
        
        
        

    }

    @FXML
    void clickModificarCliente(ActionEvent event) {
        
        String id_cliente = Idcliente.getText();
        String nomCliente = nombreCliente.getText();
        String clienteDireccion = Direccion.getText();
        String clienteCorreo = correo.getText();
        
        Cliente clienteModificado = new Cliente(id_cliente, nomCliente, clienteDireccion, clienteCorreo);
        
        String respuesta = repo.actualizar(clienteModificado);
        crearAlerta(respuesta); 
        LimpiarDatos(1);

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
    void filtrarIdCliente(ActionEvent event) {
        String id_cliente = Idcliente.getText();
        
        Object clienteBuscado = repo.selectId(id_cliente);
        
        if (clienteBuscado != null){
            Cliente cliente = (Cliente)clienteBuscado;
            
            nombreCliente.setText(cliente.getNombre());
            Direccion.setText(cliente.getDireccion());
            correo.setText(cliente.getCorreo());
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
     
     void LimpiarDatos (int cod){
         switch (cod) {
             case 1: 
                 Idcliente.setText("");
                 nombreCliente.setText("");
                 Direccion.setText("");
                 correo.setText("");
             case 2: 
                 nombreCliente.setText("");
                 Direccion.setText("");
                 correo.setText("");
                 
                 break;
             default:
                 throw new AssertionError();
         }
     }
    
    
}
