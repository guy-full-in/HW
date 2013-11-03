

public class NumericPassword extends Thread {
	
	String hash;
	boolean result;

	public NumericPassword(String hash) {
		this.hash = hash;
		this.result = false;
	}

	public void run() {
		for (int i=0;i<10000;i++){
			String password = String.valueOf(i);
            while (password.length() < 4) {
                password = "0" + password;
            }
			if(hash.equals(MD5.get(password))){
				System.out.println("Совпадение найдено. Пароль: " + password);
				result = true;
			}
		}
	}
	
}
