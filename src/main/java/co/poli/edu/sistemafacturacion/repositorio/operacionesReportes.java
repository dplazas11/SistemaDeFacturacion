package co.poli.edu.sistemafacturacion.repositorio;

import co.poli.edu.sistemafacturacion.modelo.Cliente;
import co.poli.edu.sistemafacturacion.modelo.Factura;
import co.poli.edu.sistemafacturacion.servicios.conexionMongodb;
import com.mongodb.MongoException;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;

public class operacionesReportes {

    private final MongoCollection<Document> collectionFactura;
    private final MongoCollection<Document> collectionProducto;

    public operacionesReportes() {
        MongoDatabase db = conexionMongodb.getDatabase();
        this.collectionFactura = db.getCollection("factura");
        this.collectionProducto = db.getCollection("producto");

    }

    private Factura documentToFactura(Document doc) {
        Factura f = new Factura();
        f.setIdFactura(doc.getInteger("idfactura"));
        f.setIdVendedor(doc.getString("idvendedor"));
        f.setIdCliente(doc.getString("idcliente"));
        f.setFecha(doc.getString("fecha"));
        f.setTotalCant(doc.getInteger("canttotal"));
        f.setTotal(doc.getInteger("total"));
        return f;
    }

    public String CantidadPromedioProductosVendidos() {
        try {

            // Pipeline de agregación
            AggregateIterable<Document> resultado = collectionFactura.aggregate(
                    Arrays.asList(
                            new Document("$group",
                                    new Document("_id", null)
                                            .append("promedio", new Document("$avg", "$canttotal"))
                            )
                    )
            );

            Document doc = resultado.first();

            if (doc != null && doc.get("promedio") != null) {
                double prom = doc.getDouble("promedio");
                return "La cantidad promedio de productos vendidos por factura es de " + prom;
            } else {
                return "No hay facturas.";
            }

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }

    }

    public String clienteConMasCompras() {

        try {
            // Pipeline de agregación
            AggregateIterable<Document> resultado = collectionFactura.aggregate(
                    Arrays.asList(
                            // Agrupar por idcliente y contar cuántas compras tiene
                            new Document("$group",
                                    new Document("_id", "$idcliente")
                                            .append("total", new Document("$sum", 1))
                            ),
                            // Ordenar por mayor número de compras
                            new Document("$sort", new Document("total", -1)),
                            // Tomar sólo el primero
                            new Document("$limit", 1)
                    )
            );

            // Obtener el documento resultante
            Document doc = resultado.first();

            // Si no hay resultados
            if (doc == null) {
                return "No hay resultados";
            }
            String id_clien = doc.getString("_id");
            int cant  = doc.getInteger("total");
            operacionesCliente repo = new operacionesCliente();
            Cliente clien = (Cliente) repo.selectId(id_clien);

            return " El cliente " + clien.getNombre() + " con documento " + clien.getIdCliente() + " es el que más compras ha hecho con " + cant + " compras realizadas.";

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }
    }

    public String FacturaMasCostosa() {

        try {
            Document docfactura = collectionFactura.find()
                    .sort(Sorts.descending("total")) // Ordenar por total de mayor a menor
                    .limit(1)
                    .first();

            Factura facturaEnc = documentToFactura(docfactura);

            return "La factura " + facturaEnc.getIdFactura()
                    + " tuvo la compra más costosa con un valor de " + facturaEnc.getTotal() + " el dia "+ facturaEnc.getFecha();

        } catch (MongoException e) {
            return "Error al buscar: " + e.getMessage();
        }

    }

    public ArrayList<String> VentaPorCliente() {
        ArrayList<String> listaResultado = new ArrayList<>();

        try {
            AggregateIterable<Document> resultado = collectionFactura.aggregate(
                    Arrays.asList(
                            new Document("$group",
                                    new Document("_id", "$idcliente")
                                            .append("totalVentas", new Document("$sum", "$total"))
                            ),
                            new Document("$sort", new Document("totalVentas", -1)) // Opcional
                    )
            );

            for (Document doc : resultado) {
                listaResultado.add(doc.getString("_id")+ ": " + String.valueOf(doc.getInteger("totalVentas")));
            }

        } catch (Exception e) {
            System.out.println("Error al obtener las ventas por cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return listaResultado;

    }

    public String PromedioVentasDiario() {
        String promedioStr = "0";

        try {

            List<Bson> pipeline = Arrays.asList(
                    // 1. Agrupar por el string de la fecha
                    Aggregates.group("$fecha",
                            Accumulators.sum("totalDia", "$total")
                    ),
                    // 2. Calcular el promedio entre los días
                    Aggregates.group(null,
                            Accumulators.avg("promedio", "$totalDia")
                    )
            );

            Document result = collectionFactura.aggregate(pipeline).first();

            if (result != null) {
                double promedio = result.getDouble("promedio");
                promedioStr = "El promedio de ventas por dia es " + String.valueOf(promedio);
            }

        } catch (Exception e) {
            promedioStr = "Error: " + e.getMessage();
        }

        return promedioStr;

    }

    public String totalInventario() {

        String respuesta = "";

        try {

            List<Bson> pipeline = Arrays.asList(
                    Aggregates.group(null,
                            Accumulators.sum("totalCantidad", "$cantidad")
                    )
            );

            Document resultado = collectionProducto.aggregate(pipeline).first();

            if (resultado != null) {
                int total = resultado.getInteger("totalCantidad");
                respuesta = "El total de productos en el inventario es de " + total;

            }

        } catch (Exception e) {
            return "Error al obtener total de inventario: " + e.getMessage();
        }

        return respuesta;

    }

    public ArrayList<String> TotalVentasVendedor() {
        ArrayList<String> listaResultado = new ArrayList<>();

        try {
            AggregateIterable<Document> resultado = collectionFactura.aggregate(
                    Arrays.asList(
                            new Document("$group",
                                    new Document("_id", "$idvendedor")
                                            .append("totalVentas", new Document("$sum", "$total"))
                            ),
                            new Document("$sort", new Document("totalVentas", -1)) // Opcional
                    )
            );

            for (Document doc : resultado) {
                listaResultado.add(doc.getString("_id") + ": " + String.valueOf(doc.getInteger("totalVentas")));
            }

        } catch (Exception e) {
            System.out.println("Error al obtener las ventas por cliente: " + e.getMessage());
            e.printStackTrace();
        }

        return listaResultado;

    }

    public ArrayList<String> VentasPorDia() {

        ArrayList<String> listaResultado = new ArrayList<>();

        try {
            AggregateIterable<Document> resultado = collectionFactura.aggregate(
                    Arrays.asList(
                            new Document("$group",
                                    new Document("_id", "$fecha")
                                            .append("totalVentas", new Document("$sum", "$total"))
                            )
                            
                    )
            );

            for (Document doc : resultado) {
                listaResultado.add(doc.getString("_id")+ ": " + String.valueOf(doc.getInteger("totalVentas")));
            }

        } catch (Exception e) {
            System.out.println("Error al obtener las ventas por fecha: " + e.getMessage());
            e.printStackTrace();
        }

        return listaResultado;

    }

}
