/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.codecs.configuration.*;
import org.bson.codecs.pojo.*;

/**
 *
 * @author Diurno
 */
public class ConexionMongo {
    
    private String uri = "mongodb://localhost:27017";
    private PojoCodecProvider codecProvider;
    private CodecRegistry codecRegistry;
    private MongoClient cliente = null;
    private MongoDatabase db;
    
    public ConexionMongo() {
    }
    
    public void conectar(String bd) {
        
        codecProvider = PojoCodecProvider.builder().automatic(true).build();
        codecRegistry = CodecRegistries.fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), CodecRegistries.fromProviders(codecProvider));
        
        cliente = MongoClients.create(uri);
        
        if (cliente != null) {
            db = cliente.getDatabase(bd).withCodecRegistry(codecRegistry);
        }
        
    }
    
    public <T> MongoCollection<T> getCollection(String nombre, Class clase){
        return db.getCollection(nombre, clase);
    }
    
    
    public void desconectar(){
        if(cliente != null){
            cliente.close();
        }
    }
    
}
