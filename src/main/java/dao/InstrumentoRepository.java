package dao;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.InsertManyResult;
import com.mongodb.client.result.UpdateResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import modelo.Instrumento;
import org.bson.BsonValue;
import org.bson.conversions.Bson;

/**
 *
 * @author Diurno
 */
public class InstrumentoRepository {

    private MongoCollection<Instrumento> instrumentos;

    public InstrumentoRepository(MongoCollection<Instrumento> instrumentos) {
        this.instrumentos = instrumentos;
    }

    public MongoCollection<Instrumento> getInstrumentos() {
        return instrumentos;
    }

    public void setInstrumentos(MongoCollection<Instrumento> instrumentos) {
        this.instrumentos = instrumentos;
    }

    public List<String> insertarVariosInstrumentos(List<Instrumento> listaInstrumentos) {

        List<String> listaIds = new ArrayList<>();

        if (!listaInstrumentos.isEmpty()) {
            InsertManyResult insertManyResult = instrumentos.insertMany(listaInstrumentos);
            Map<Integer, BsonValue> mapaIds = insertManyResult.getInsertedIds();

            for (Map.Entry<Integer, BsonValue> entry : mapaIds.entrySet()) {
                BsonValue value = entry.getValue();

                listaIds.add(value.asObjectId().toString());

            }
        }

        return listaIds;
    }

    public long borrarInstrumento(String nombre) {

        long id;

        Bson condicion = Filters.eq("nombre", nombre);

        DeleteResult deleteResult = instrumentos.deleteOne(condicion);

        id = deleteResult.getDeletedCount();

        return id;

    }

    public long incrementarPrecio(String nombre, int incremento) {

        long id;

        Bson condicion = Filters.eq("nombre", nombre);

        Bson nuevo = Updates.inc("precio", incremento);

        UpdateResult updateResult = instrumentos.updateMany(condicion, nuevo);

        id = updateResult.getModifiedCount();

        return id;
    }

    public List<Instrumento> consultarTipoInstrumentos(String tipo) {

        List<Instrumento> listaInstrumentos = new ArrayList<>();

        Bson condicion = Filters.eq("tipo", tipo);

        MongoCursor<Instrumento> cursor = instrumentos.find(condicion).iterator();

        while (cursor.hasNext()) {
            Instrumento i = cursor.next();
            listaInstrumentos.add(i);
        }

        return listaInstrumentos;
    }

    public List<Instrumento> consultarTodosLosInstrumentos() {
        List<Instrumento> listaInstrumentos = new ArrayList<>();

        MongoCursor cursor = instrumentos.find().cursor();

        while (cursor.hasNext()) {
            Instrumento i = (Instrumento) cursor.next();
            listaInstrumentos.add(i);
        }

        return listaInstrumentos;
    }

}
