import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class ReadPage {
	LinkedList<City> cities = new LinkedList<City>();
	
	public void getRaiting(){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://ru.m.wikipedia.org/wiki/Список_городов_России_с_населением_более_100_тысяч_жителей");
		BufferedReader in = null;
		BufferedWriter out = null;
		try{
			System.out.println("Идет парсинг главной страницы...");
			HttpResponse response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			in = new BufferedReader(new InputStreamReader(entity1.getContent()));		
			String row = null;
			while((row = in.readLine())!=null){
				searchCity(row);
			}
			System.out.println("Найдено " + cities.size() + " городов");
		    Semaphore mSemaphor = new Semaphore(3);
		    
		    Iterator<City> it = cities.iterator();
		    
		    SearchYear [] sys = new SearchYear[cities.size()];
		    
		    System.out.println("Идет подсчет рейтинга...");
		    
		    int i = 0;
		    while(it.hasNext()){
		    	City city = it.next();
		    	sys[i] = new SearchYear(city, mSemaphor);
		    	sys[i].start();
		    	i++;
		    }
		    
		    for(i = 0;i<sys.length;i++){
		    	sys[i].join();
		    }

		    Collections.sort(cities);
		    
		    System.out.println("Рейтинг ТОП-10 старейших городов России с населением более 100 тыс человек:");
		    it = cities.iterator();
		    for (i = 1;i<=10;i++){
		    	City city = it.next();
		    	System.out.println(i+". " + city.name +": "+ city.year);
		    }
			
		}catch (Exception e){
			e.printStackTrace();
		}finally{
			try{
				if (in != null)
					in.close();
				if (out != null)
					out.close();
			}catch (Exception e){
				e.printStackTrace();
			}
		}
	}
	
	
	private void searchCity(String content) {
        try{
        	
        	Pattern pattern = Pattern.compile("<td>(<b>)?<a href=\"(.*?)\".*?>(.*?)</a><?/?b?>?</td>");
        	Matcher matcher = pattern.matcher(content);
        	while (matcher.find()) {
        		City newCity = new City(matcher.group(2), matcher.group(3));
        		cities.add(newCity);
        	}
        } catch (PatternSyntaxException e) {
            System.out.println("Wrong regexp pattern");
        }
	}
}
