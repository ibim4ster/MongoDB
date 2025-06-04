package controlador;

import com.mongodb.client.MongoCollection;
import dao.DocumentRepository;
import dao.InstrumentoRepository;
import java.util.ArrayList;
import java.util.List;
import modelo.ConexionMongo;
import modelo.Instrumento;
import org.bson.Document;
import org.bson.types.ObjectId;
import vista.Vista;

/**
 *
 * @author Diurno
 */
public class Controller {
    
    private Vista v;
    private InstrumentoRepository ir;
    private DocumentRepository dr;
    private ConexionMongo conexion;
    private List<Instrumento> listaInstrumentos = new ArrayList<>();

    public Controller(Vista v) {
        this.v = v;

        conexion = new ConexionMongo();

        conexion.conectar("bd_musica");

        MongoCollection<Instrumento> listadoInstrumentos = conexion.getCollection("instrumentos", Instrumento.class);
        MongoCollection<Document> listadoDocumentos = conexion.getCollection("instrumentos", Document.class);

        ir = new InstrumentoRepository(listadoInstrumentos);
        dr = new DocumentRepository(listadoDocumentos);

        //insertarInstrumentos();
        //insertarInstrumentoByDocument();
        //subirPrecio();
        //borrarInstrumento();
        //consultarInstrumentosByTipo();
        //mostrarTodosLosInstrumentos();

        conexion.desconectar();
    }

    public void insertarInstrumentos() {

        List<String> listadoIds;

        Instrumento i = new Instrumento("flautita", "viento", 50);
        Instrumento i1 = new Instrumento("guitarrita", "cuerda", 100);
        Instrumento i2 = new Instrumento("oboecito", "viento", 300);

        listaInstrumentos.add(i);
        listaInstrumentos.add(i1);
        listaInstrumentos.add(i2);

        listadoIds = ir.insertarVariosInstrumentos(listaInstrumentos);

        for (String listadoId : listadoIds) {
            v.mostrarTexto(listadoId);
        }

    }

    public void insertarInstrumentoByDocument() {

        Document d = new Document("_id", new ObjectId());
        d.append("nombre", "flautaza");
        d.append("tipo","viento");
        d.append("precio",55);

        v.mostrarTexto(dr.insertarDocumentoInstrumento(d));

    }

    public void subirPrecio() {

        v.mostrarTexto(String.valueOf(ir.incrementarPrecio("flauta", 1)));

    }
    
    public void borrarInstrumento(){
        v.mostrarTexto(String.valueOf(ir.borrarInstrumento("guitarra")));
    }
    
    public void consultarInstrumentosByTipo(){
        
        List<Instrumento> tiposInstrumentos;
        
        tiposInstrumentos = ir.consultarTipoInstrumentos("viento");
        
        for (Instrumento tiposInstrumento : tiposInstrumentos) {
            v.mostrarTexto(tiposInstrumento.toString());
        }
    }
    
    public void mostrarTodosLosInstrumentos(){
        
        List<Instrumento> todosLosInstrumentos;
        
        todosLosInstrumentos = ir.consultarTodosLosInstrumentos();
        
        for (Instrumento todosLosInstrumento : todosLosInstrumentos) {
            v.mostrarTexto(todosLosInstrumento.toString());
        }
         
    }

}
