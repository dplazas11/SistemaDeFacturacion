/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.poli.edu.sistemafacturacion.repositorio;

import co.poli.edu.sistemafacturacion.modelo.Cliente;
import co.poli.edu.sistemafacturacion.servicios.conexionMongodb;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

/**
 *
 * @author LENOVO
 */
public class operacionesCliente implements Operaciones<Cliente> {
    
     private final MongoCollection<Document> collection;
     
     public operacionesCliente() {
        MongoDatabase db = conexionMongodb.getDatabase();
        this.collection = db.getCollection("cliente");

    }

    // Convertir Factura → Document
    private Document clienteToDocument(Cliente cliente) {
        return new Document("idcliente", cliente.getIdCliente())
                .append("nombre", cliente.getNombre())
                .append("direccion", cliente.getDireccion())
                .append("correo", cliente.getCorreo());
    }

    // Convertir Document → Factura
    private Cliente documentToCliente(Document doc) {
        Cliente c = new Cliente();
        c.setIdCliente(doc.getString("idcliente"));
        c.setNombre(doc.getString("nombre"));
        c.setDireccion(doc.getString("direccion"));
        c.setCorreo(doc.getString("correo"));
        return c;
    }

    @Override
    public String insertar(Cliente entidad) {
         try {
            Document doc = clienteToDocument(entidad);
            collection.insertOne(doc);
            return "Cliente insertada correctamente en la base de datos.";
        } catch (MongoException e) {
            return "Error al insertar: " + e.getMessage();
        }
        
        }

    @Override
    public String eliminar(String id) {
         try {
            DeleteResult result = collection.deleteOne(eq("idcliente", id));
            if (result.getDeletedCount() == 0) {
                return "No se encontró al cliente para eliminar.";
            }
            return "Cliente eliminado correctamente de la base de datos.";

        } catch (MongoException e) {
            return "Error al eliminar: " + e.getMessage();
        }
       }

    @Override
    public String actualizar(Cliente cliente) {
         try {
            UpdateResult result = collection.updateOne(
                    eq("idcliente", cliente.getIdCliente()),
                    combine(
                            set("nombre", cliente.getNombre()),
                            set("direccion", cliente.getDireccion()),
                            set("correo", cliente.getCorreo())
                            
                    )
            );

            if (result.getMatchedCount() == 0) {
                return "No se encontró al cliente para actualizar.";
            }
            return "Cliente actualizado correctamente en la base de datos.";

        } catch (MongoException e) {
            return "Error al actualizar: " + e.getMessage();
        }
        }

    @Override
    public Object selectId(String id) {
         Cliente clienteBuscado = null;

        try {
            Document doc = collection.find(eq("idcliente", id)).first();
            if (doc != null) {
                clienteBuscado = documentToCliente(doc);
                return clienteBuscado;
            }
            return null;

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }}

    @Override
    public Object selectAll() {        
        List<Cliente> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(documentToCliente(cursor.next()));
            }
            return lista;
        } catch (MongoException e) {
            return "Error al listar Clientes: " + e.getMessage();
        }
        }

    

}
