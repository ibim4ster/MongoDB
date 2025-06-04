package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.*;
import org.bson.Document;

/**
 *
 * @author Diurno
 */
public class DocumentRepository {

    private MongoCollection<Document> documentos;

    public DocumentRepository(MongoCollection<Document> documentos) {
        this.documentos = documentos;
    }

    public MongoCollection<Document> getDocumentos() {
        return documentos;
    }

    public void setDocumentos(MongoCollection<Document> documentos) {
        this.documentos = documentos;
    }

    public String insertarDocumentoInstrumento(Document d) {

        String id = "";

        if (d != null) {
            InsertOneResult insertOneResult = documentos.insertOne(d);
            id = insertOneResult.getInsertedId().toString();
        }

        return id;
    }
}
