
package co.poli.edu.sistemafacturacion.repositorio;


import co.poli.edu.sistemafacturacion.modelo.Producto;
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


public class operacionesProducto implements Operaciones<Producto>{
    
     private final MongoCollection<Document> collection;

    public operacionesProducto() {
        MongoDatabase db = conexionMongodb.getDatabase();
        this.collection = db.getCollection("producto");

    }
    
     // Convertir Factura → Document
    private Document productoToDocument(Producto producto) {
        return new Document("idproducto", producto.getIdProducto())
                .append("descripcion", producto.getDescripcion())
                .append("cantidad", producto.getCantidad())
                .append("preciounitario", producto.getPrecioUnitario());
                
    }

    // Convertir Document → Factura
    private Producto documentToProducto(Document doc) {
        Producto f = new Producto();
        f.setIdProducto(doc.getString("idproducto"));
        f.setDescripcion(doc.getString("descripcion"));
        f.setCantidad(doc.getInteger("cantidad"));
        f.setPrecioUnitario(doc.getInteger("preciounitario"));
        return f;
    }

    @Override
    public String insertar(Producto entidad) {
         try {
            Document doc = productoToDocument(entidad);
            collection.insertOne(doc);
            return "Producto insertado correctamente en la base de datos.";
        } catch (MongoException e) {
            return "Error al insertar: " + e.getMessage();
        }
        
        }

    @Override
    public String eliminar(String id) {
        try {
            DeleteResult result = collection.deleteOne(eq("idproducto", id));
            if (result.getDeletedCount() == 0) {
                return "No se encontró el producto para eliminar.";
            }
            return "Producto eliminado correctamente de la base de datos.";

        } catch (MongoException e) {
            return "Error al eliminar: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(Producto producto) {
         try {
            UpdateResult result = collection.updateOne(
                    eq("idproducto", producto.getIdProducto()),
                    combine(
                            set("descripcion", producto.getDescripcion()),
                            set("cantidad", producto.getCantidad()),
                            set("preciounitario", producto.getPrecioUnitario())
                            
                    )
            );

            if (result.getMatchedCount() == 0) {
                return "No se encontró el producto para actualizar.";
            }
            return "Producto actualizado correctamente en la base de datos.";

        } catch (MongoException e) {
            return "Error al actualizar: " + e.getMessage();
        }
        
        }

    @Override
    public Object selectId(String id) {
        Producto productoBuscado = null;

        try {
            Document doc = collection.find(eq("idproducto", id)).first();
            if (doc != null) {
                productoBuscado = documentToProducto(doc);
                return productoBuscado;
            }
            return null;

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }
    }

    @Override
    public Object selectAll() {
        List<Producto> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(documentToProducto(cursor.next()));
            }
            return lista;
        } catch (MongoException e) {
            return "Error al listar Productos: " + e.getMessage();
        }
        }
    
}
