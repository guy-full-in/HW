import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class Task1 {
	public static void Search(String dir, String exp){
		File f=new File(dir);
		
		if(f.exists()){
			if(f.isDirectory()){
				File [] folder=f.listFiles();
				for (File file  : folder) {
					Search(file.getPath(),exp);
				}
			}else{
				Pattern pattern = Pattern.compile("\\.(.*)$");
		        Matcher matcher = pattern.matcher(f.getName());
		        if(matcher.find()){
					if(matcher.group(1).equals(exp)){
						System.out.println(f.length()+" "+f.getPath());
					}
		        }
			}
		}else{
			System.out.println("File does not exist");
		}
	}
	
	public static void main(String args[]){
		Search("C:/Users/123/Desktop/Курс", "pptx");
	}
}
