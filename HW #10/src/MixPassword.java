
public class MixPassword extends Thread{
	String hash;
	boolean result;

	public MixPassword(String hash) {
		this.hash = hash;
		this.result = false;
	}

	public void run() {
		for(int i1=48;i1<90;i1++){
			for(int i2=48;i2<90;i2++){
				for(int i3=48;i3<90;i3++){
					String password = String.valueOf(new char[]{(char)i1,(char)i2,(char)i3});
					if(hash.equals(MD5.get(password))){
						System.out.println("Совпадение найдено. Пароль: " + password);
						result = true;
					}
				}
			}
		}
	}
}
