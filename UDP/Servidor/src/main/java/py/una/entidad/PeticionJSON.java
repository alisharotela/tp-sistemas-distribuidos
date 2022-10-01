package py.una.entidad;

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

    // public static String listaString(ListaNis lista) {
    //     JSONObject obj = new JSONObject();
    //     obj.put("estado", lista.getEstado());
    //     obj.put("mensaje", lista.getMensaje());
    //     obj.put("operacion", lista.getOperacion());

    //     JSONArray array = new JSONArray();
    //     for (Nis nis : lista.getLista()) {
    //         array.add(NisJSON.objetoString(nis));
    //     }
    //     obj.put("datos", array);

    //     return obj.toJSONString();
    // }

    // public static ListaNis stringLista(String str) {
    //     ListaNis lista = new ListaNis();
    //     JSONObject jsonObject = new JSONObject(str);

    //     lista.setEstado((Integer) jsonObject.get("estado"));
    //     lista.setMensaje((String) jsonObject.get("mensaje"));
    //     lista.setOperacion((Integer) jsonObject.get("operacion"));

    //     JSONArray array = (JSONArray) jsonObject.get("datos");
    //     Iterator<String> iterator = array.iterator();
    //     while (iterator.hasNext()) {
    //         lista.getLista().add(NisJSON.stringObjeto(iterator.next()));
    //     }

    //     return lista;
    // }

}
