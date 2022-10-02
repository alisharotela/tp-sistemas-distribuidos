package py.una.entidad;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NisJSON {

    public static String objetoString(Nis nis) {
        JSONObject obj = new JSONObject();
        obj.put("id", nis.getId());
        obj.put("consumo", nis.getConsumo());
        obj.put("esActivo", nis.getEsActivo());

        return obj.toJSONString();
    }
    
    public static Nis stringObjeto(String str) throws ParseException {
        Nis nis = new Nis();
        JSONParser parser = new JSONParser();

        JSONObject jsonObject = (JSONObject) parser.parse(str);
        nis.setId(Integer.parseInt(jsonObject.get("id").toString()));
        nis.setConsumo((Long)jsonObject.get("consumo"));
        nis.setEsActivo((Boolean)jsonObject.get("esActivo"));

        return nis;
    }
}
