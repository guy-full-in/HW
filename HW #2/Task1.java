import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class Task1 {
	
	private static double format(String text){
        try{
            Pattern pattern = Pattern.compile("\\,");
            Matcher matcher = pattern.matcher(text);
            
            double result=Double.parseDouble(matcher.replaceAll("\\."));            
        
            return result;
        } catch (PatternSyntaxException e) {
            System.out.println("Wrong regexp pattern");
            return 0;
        }
	}
	
	private static double convertor(String cash){
		try {
            Pattern pattern = Pattern.compile("^([$ˆ])(\\d*([\\.,]\\d{1,2})?)$");
            Matcher matcher = pattern.matcher(cash);
            
            double dollar=32.29;
            double euro=43.16;
            
            double result=0;
            
        	while(matcher.find()){
        		if(matcher.group(1).equals("$")){
        			result=(double) Math.round(format(matcher.group(2))*dollar*100)/100;
        		}else{
        			result=(double) Math.round(format(matcher.group(2))*euro*100)/100;
        		}
        	}
            
            return result;
        } catch (PatternSyntaxException e) {
            System.out.println("Wrong regexp pattern");
            return 0;
        }
	}
	
	
	public static void main(String args[]){
		String cash="$100";
		System.out.println(cash+" = "+convertor(cash)+" RUR");
		
	}
}
