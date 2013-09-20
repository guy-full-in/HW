import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class Task2 {
	
	private static boolean validator(String number){
		try{
			Pattern pattern = Pattern.compile("\\+ ?\\d{1,3} ?\\(\\d{2,8}\\) ?\\d{1,6}\\-\\d{1,6}\\-\\d{1,6}");
			Matcher matcher = pattern.matcher(number);
			
			boolean result = false;
			
			if(matcher.find()){
				result=true;
			}
			
			return result;
		}catch (PatternSyntaxException e) {
			System.out.println("Wrong regexp pattern");
			return false;
		}
	}
	
	
    private static String interformat (String number){
        try{
            //Pattern pattern = Pattern.compile(" |\\(|\\)|-");
            Pattern pattern = Pattern.compile("[^\\d\\+]");
            Matcher matcher = pattern.matcher(number);
            
            String result="0";
            
            if(validator(number)){
                while (matcher.find()){
	                result = matcher.replaceAll("");	                
	            }
            }else{
            	System.out.println("Not valid number");
            	return "0";
            }

            return result;
        } catch (PatternSyntaxException e) {
            System.out.println("Wrong regexp pattern");
            return "0";
        }
    }
	
	public static void main(String[] args){
		String number = "+7 (904)66-35-462";
		System.out.println(validator(number));
		System.out.println(interformat(number));
				
	}

}
