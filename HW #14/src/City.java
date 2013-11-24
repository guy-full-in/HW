
public class City implements Comparable<City>{
	String url;
	String name;
	int year;
	
	public City(String url,String name){
		this.name = name;
		this.url = url;
		this.year = 2013;
	}

	public int compareTo(City o) {
		if(this.year>o.year){
			return 1;
		}else if (this.year<o.year){
			return -1;
		}
		return 0;
	}
}
