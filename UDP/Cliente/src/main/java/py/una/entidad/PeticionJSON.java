package py.una.entidad;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
public class PeticionJSON {

    public static String objetoString(Peticion datos) {
        JSONObject obj = new JSONObject();
        obj.put("estado", datos.getEstado());
        obj.put("mensaje", datos.getMensaje());
        obj.put("datos", datos.getDatos());
        obj.put("operacion", datos.getOperacion());

        return obj.toJSONString();
    }

    public static Peticion stringObjeto(String str) throws ParseException {
        Peticion peticion = new Peticion();
        
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(str);

        peticion.setEstado(Integer.parseInt(jsonObject.get("estado").toString()));
        peticion.setMensaje((String) jsonObject.get("mensaje"));
        peticion.setDatos((String) jsonObject.get("datos"));
        peticion.setOperacion(Integer.parseInt(jsonObject.get("operacion").toString()));

        return peticion;
    }

}
