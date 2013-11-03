

public class CharPassword extends Thread{
	String hash;
	boolean result;
	

	public CharPassword(String hash) {
		this.hash = hash;
		this.result = false;
	}

	public void run() {
		for(int i1=97;i1<122;i1++){
			for(int i2=97;i2<122;i2++){
				for(int i3=97;i3<122;i3++){
					for(int i4=97;i4<122;i4++){
						String password = String.valueOf(new char[]{(char)i1,(char)i2,(char)i3,(char)i4});
						if(hash.equals(MD5.get(password))){
							System.out.println("Совпадение найдено. Пароль: " + password);
							result = true;
						}
					}
				}
			}
		}
	}

}
