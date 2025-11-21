package co.poli.edu.sistemafacturacion.repositorio;

import co.poli.edu.sistemafacturacion.modelo.Factura;
import co.poli.edu.sistemafacturacion.servicios.conexionMongodb;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;

public class operacionesFactura implements Operaciones<Factura> {

    private final MongoCollection<Document> collection;

    public operacionesFactura() {
        MongoDatabase db = conexionMongodb.getDatabase();
        this.collection = db.getCollection("factura");

    }

    // Convertir Factura → Document
    private Document facturaToDocument(Factura factura) {
        return new Document("idfactura", factura.getIdFactura())
                .append("idvendedor", factura.getIdVendedor())
                .append("idcliente", factura.getIdCliente())
                .append("fecha", factura.getFecha())
                .append("total", factura.getTotal());
    }

    // Convertir Document → Factura
    private Factura documentToFactura(Document doc) {
        Factura f = new Factura();
        f.setIdFactura(doc.getString("idfactura"));
        f.setIdVendedor(doc.getString("idvendedor"));
        f.setIdCliente(doc.getString("idcliente"));
        f.setFecha(doc.getString("fecha"));
        f.setTotal(doc.getDouble("total"));
        return f;
    }

    @Override
    public String insertar(Factura entidad) {
        try {
            Document doc = facturaToDocument(entidad);
            collection.insertOne(doc);
            return "Factura insertada correctamente en la base de datos.";
        } catch (MongoException e) {
            return "Error al insertar: " + e.getMessage();
        }
    }

    @Override
    public String eliminar(String id) {
        try {
            DeleteResult result = collection.deleteOne(eq("idfactura", id));
            if (result.getDeletedCount() == 0) {
                return "No se encontró la factura para eliminar.";
            }
            return "Factura eliminada correctamente de la base de datos.";

        } catch (MongoException e) {
            return "Error al eliminar: " + e.getMessage();
        }
    }

    @Override
    public String actualizar(Factura factura) {
        try {
            UpdateResult result = collection.updateOne(
                    eq("idfactura", factura.getIdFactura()),
                    combine(
                            set("idvendedor", factura.getIdVendedor()),
                            set("idcliente", factura.getIdCliente()),
                            set("fecha", factura.getFecha()),
                            set("total", factura.getTotal())
                    )
            );

            if (result.getMatchedCount() == 0) {
                return "No se encontró la factura para actualizar.";
            }
            return "Factura actualizada correctamente en la base de datos.";

        } catch (MongoException e) {
            return "Error al actualizar: " + e.getMessage();
        }
    }

    @Override
    public Object selectId(String id) {
        Factura facturaBuscada = null;

        try {
            Document doc = collection.find(eq("idfactura", id)).first();
            if (doc != null) {
                facturaBuscada = documentToFactura(doc);
                return facturaBuscada;
            }
            return null;

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }
    }

    @Override
    public Object selectAll() {

        List<Factura> lista = new ArrayList<>();
        try (MongoCursor<Document> cursor = collection.find().iterator()) {
            while (cursor.hasNext()) {
                lista.add(documentToFactura(cursor.next()));
            }
            return lista;
        } catch (MongoException e) {
            return "Error al listar facturas: " + e.getMessage();
        }
    }
}

