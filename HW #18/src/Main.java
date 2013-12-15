import java.util.Scanner;


public class Main {
	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(System.in);
			System.out.println("Введите template_name:");
			String name = sc.next();
			System.out.println("Введите template_surname:");
			String surname = sc.next();
			String path = "template.docx";
			
			DOCXParser docx = new DOCXParser(name,surname,path);
			docx.unzip();
			docx.parse();
			docx.zip();
			
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
