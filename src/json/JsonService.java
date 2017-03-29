package json;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonService {
	public static String responseStringJson(int reqState,String message,JSONObject data){
		JSONObject jsonObject = new JSONObject();
		if(reqState == 0){
			jsonObject.put("code", 400);
		}else if(reqState ==1){
			jsonObject.put("code", 200);
		}
		jsonObject.put("message", message);
		jsonObject.put("data", data);
		return jsonObject.toString();
	}
	public static String responseStringJson(int reqState,String message,JSONArray data){
		JSONObject jsonObject = new JSONObject();
		if(reqState == 0){
			jsonObject.put("code", 400);
		}else if(reqState ==1){
			jsonObject.put("code", 200);
		}
		jsonObject.put("message", message);
		jsonObject.put("data", data);
		return jsonObject.toString();
	}
}
