import java.util.Scanner;


public class Main {
	public static void main(String[] args) throws InterruptedException {
		Scanner sc = new Scanner(System.in);
		System.out.print("¬ведите хеш: ");
		String password = sc.next();
		NumericPassword np = new NumericPassword(password);
		np.start();
		CharPassword cp = new CharPassword(password);
		cp.start();
		MixPassword mp = new MixPassword(password);
		mp.start();
		
		np.join();
		cp.join();
		mp.join();
		
		if(!np.result && !cp.result && !mp.result){
			System.out.println("—овпадений не найдено");
		}
	}
}
