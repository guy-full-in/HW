import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


public class SearchYear extends Thread {
    private City city;
    private Semaphore mSemaphore = null;

    public SearchYear(City city, Semaphore semaphore) {
        this.city = city;
        mSemaphore = semaphore;
    }

    public void run() {
        try {
            mSemaphore.acquire();
            getYear();
        } catch (InterruptedException ex) {
//        	ex.printStackTrace();
        } finally {
            mSemaphore.release();
        }
    }
    
	public void getYear(){
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpGet httpGet = new HttpGet("http://ru.m.wikipedia.org" + city.url);
		BufferedReader in = null;
		BufferedWriter out = null;
		try{
			HttpResponse response1 = httpclient.execute(httpGet);
			HttpEntity entity1 = response1.getEntity();
			in = new BufferedReader(new InputStreamReader(entity1.getContent()));		
			String row = null;
			String content = "";
			while((row = in.readLine())!=null && !row.contains("Площадь")){
				content += row;
			}
			searchYear(content);
			
		}catch (Exception e){
//			e.printStackTrace();
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
	
	private void searchYear(String content) {
        try{
        	int year1=2013;
        	int year2=2013;
        	Pattern pattern = Pattern.compile("<td.*?>Первое упоминание</td><td.*?><div><a.*?>([0-9]{3,4}).*?</a></div></td>");
        	Matcher matcher = pattern.matcher(content);
        	if(matcher.find()) {
        		year1 = Integer.valueOf(matcher.group(1));
        	}
        	pattern = Pattern.compile("<td.*?>Основан</td><td.*?><div><a.*?>([0-9]{3,4}).*?</a></div></td>");
        	matcher = pattern.matcher(content);
        	if(matcher.find()) {
        		year2 = Integer.valueOf(matcher.group(1));
        	}
        	
        	
        	if(year1<year2){
        		//System.out.println(city.name+": "+year1);
        		city.year = year1;
        	}else if(year1>year2){
        		//System.out.println(city.name+": "+year2);
        		city.year = year2;
        	}else if(year1==2013){
        		//System.out.println("Город не найден: "+city.name);
        	}else{
        		//System.out.println(city.name+": "+year1);
        		city.year = year1;
        	}
        } catch (PatternSyntaxException e) {
            System.out.println("Wrong regexp pattern");
        }
	}
}