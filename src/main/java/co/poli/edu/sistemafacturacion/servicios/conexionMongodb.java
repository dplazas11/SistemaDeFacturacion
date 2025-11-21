package co.poli.edu.sistemafacturacion.servicios;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class conexionMongodb {

    private static MongoClient mongoClient;
    private static MongoDatabase database;
    
    private static final String URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "Facturacion";

    public conexionMongodb() {
    }
    

   public static MongoDatabase getDatabase() {
        if (mongoClient == null) {
            mongoClient = MongoClients.create(URI);
            database = mongoClient.getDatabase(DB_NAME);
            System.out.println("Conectado a MongoDB correctamente");
        }
        return database;
    }

    
}
