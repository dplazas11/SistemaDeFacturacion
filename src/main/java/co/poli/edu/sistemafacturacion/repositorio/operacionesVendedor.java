
package co.poli.edu.sistemafacturacion.repositorio;


import co.poli.edu.sistemafacturacion.modelo.Vendedor;
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

public class operacionesVendedor implements Operaciones<Vendedor>{
    
     private final MongoCollection<Document> collection;

    public operacionesVendedor() {
        MongoDatabase db = conexionMongodb.getDatabase();
        this.collection = db.getCollection("vendedor");

    }
    
     // Convertir Factura → Document
    private Document vendedorToDocument(Vendedor vendedor) {
        return new Document("idvendedor", vendedor.getIdVendedor())
                .append("nombre", vendedor.getNombre())
                .append("direccion", vendedor.getDireccion())
                .append("correo", vendedor.getCorreo());
                
    }

    // Convertir Document → Factura
    private Vendedor documentToVendedor(Document doc) {
        Vendedor V = new Vendedor();
        V.setIdVendedor(doc.getString("idvendedor"));
        V.setNombre(doc.getString("nombre"));
        V.setDireccion(doc.getString("direccion"));
        V.setCorreo(doc.getString("correo"));
        return V;
    }

    @Override
    public String insertar(Vendedor entidad) {
        try {
            Document doc = vendedorToDocument(entidad);
            collection.insertOne(doc);
            return "Vendedor insertado correctamente en la base de datos.";
        } catch (MongoException e) {
            return "Error al insertar: " + e.getMessage();
        }
        
       }

    @Override
    public String eliminar(String id) {
        try {
            DeleteResult result = collection.deleteOne(eq("idvendedor", id));
            if (result.getDeletedCount() == 0) {
                return "No se encontró el vendedor para eliminar.";
            }
            return "Vendedor eliminado correctamente de la base de datos.";

        } catch (MongoException e) {
            return "Error al eliminar: " + e.getMessage();
        }
        }

    @Override
    public String actualizar(Vendedor vendedor) {
        try {
            UpdateResult result = collection.updateOne(
                    eq("idvendedor", vendedor.getIdVendedor()),
                    combine(
                            set("nombre", vendedor.getNombre()),
                            set("direccion", vendedor.getDireccion()),
                            set("correo", vendedor.getCorreo())
                            
                    )
            );

            if (result.getMatchedCount() == 0) {
                return "No se encontró el vendedor para actualizar.";
            }
            return "Vendedor actualizado correctamente en la base de datos.";

        } catch (MongoException e) {
            return "Error al actualizar: " + e.getMessage();
        }
        
        }

    @Override
    public Object selectId(String id) {
        Vendedor vendedorBuscado = null;

        try {
            Document doc = collection.find(eq("idvendedor", id)).first();
            if (doc != null) {
                vendedorBuscado = documentToVendedor(doc);
                return vendedorBuscado;
            }
            return null;

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }
        }

    @Override
    public Object selectAll() {
        List<Vendedor> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(documentToVendedor(cursor.next()));
            }
            return lista;
        } catch (MongoException e) {
            return "Error al listar vendedores: " + e.getMessage();
        }
        }
    
}
