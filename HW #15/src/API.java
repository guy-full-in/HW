import java.util.Iterator;
import java.util.LinkedList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;


public class API {
	static String key="trnsl.1.1.20131123T173551Z.f9b782d6eab1fc5a.c36dbde5067eddae790c5b9d121001c5e4a07771";
	
	public static LinkedList<LinkedList<String>> getLangList() {
		LinkedList<LinkedList<String>> langs = new LinkedList<LinkedList<String>>();
		Get get = new Get();
        String response = get.executeGet("https://translate.yandex.net/api/v1.5/tr.json/getLangs?key="+key);


        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response, JsonObject.class);
        JsonObject post = json.getAsJsonObject();
        JsonArray arr = post.get("dirs").getAsJsonArray();
        Iterator<JsonElement> it = arr.iterator();

    	String lang = it.next().getAsString();
        while(it.hasNext()){
        	LinkedList<String> list = new LinkedList<String>();
        	String head = lang.substring(0,2);
        	list.add(head);
        	list.add(lang.substring(3,5));
        	while(it.hasNext() && (lang = it.next().getAsString()).startsWith(head)){
        		list.add(lang.substring(3,5));
        	}
        	langs.add(list);
        }
        return langs;
//        Iterator <LinkedList<String>> its = langs.iterator();
//        while(its.hasNext()){
//        	LinkedList<String> list = its.next();
//        	Iterator<String> itss= list.iterator();
//        	while(itss.hasNext()){
//        		System.out.print(itss.next()+" ");
//        	}
//        	System.out.println();
//        }
	}

	public static String getTranslete(String text, String langFrom,
			String langTo) {

		LinkedList<LinkedList<String>> langs = new LinkedList<LinkedList<String>>();
		Get get = new Get();
        String response = get.executeGet("https://translate.yandex.net/api/v1.5/tr.json/translate?key="+key+"&text="+text.replaceAll(" ", "%20")+"&lang="+langFrom+"-"+langTo);


        Gson gson = new Gson();
        JsonObject json = gson.fromJson(response, JsonObject.class);
        JsonObject post = json.getAsJsonObject();
        JsonArray arr = post.get("text").getAsJsonArray();
        Iterator<JsonElement> it = arr.iterator();

		return it.next().getAsString();
	}
}
