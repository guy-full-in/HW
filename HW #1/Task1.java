import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;


public class Task1 {
	public static boolean validator(String number){
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
	
	public static void main(String[] args){
		System.out.println(validator("+7 (904)66-35-462"));
	}
}
