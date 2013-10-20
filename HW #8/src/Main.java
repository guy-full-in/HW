import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {

    public static void main(String[] args) {

        Connection connection = null;
        
        String url = "jdbc:mysql://localhost:3306/kino";

        String name = "root";
        String password = "root";

        try {
        	
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url, name, password);
            Statement statement = null;
            statement = connection.createStatement();
            
            
            ResultSet resultImdb = statement.executeQuery(
                    "SELECT * FROM imdb");
            
            Set <Movie> moviesImdb = new TreeSet<Movie>();
            while (resultImdb.next()) {
                Movie newMovie = new Movie(resultImdb.getNString(1), resultImdb.getInt(2), resultImdb.getDouble(3));
                moviesImdb.add(newMovie);
            }
            
            
            
            ResultSet resultKinopoisk = statement.executeQuery(
                    "SELECT * FROM kinopoisk");
            
            Set <Movie> moviesKinopoisk = new TreeSet<Movie>();
            while (resultKinopoisk.next()) {
                Movie newMovie = new Movie(resultKinopoisk.getNString(1), resultKinopoisk.getInt(2), resultKinopoisk.getDouble(3));
                moviesKinopoisk.add(newMovie);
            }
            
            
            
            Set <Movie> movies = new TreeSet<Movie>();
            
            Iterator<Movie> it1 = moviesImdb.iterator();
            while(it1.hasNext()){
            	Movie fstMovie = it1.next();
            	Iterator<Movie> it2 = moviesKinopoisk.iterator();
            	boolean f = true;
            	while(f && it2.hasNext()){
            		Movie sndMovie = it2.next();
            		if(fstMovie.name.equals(sndMovie.name) && (fstMovie.year == sndMovie.year)){
            			fstMovie.rating = (double) Math.round(((fstMovie.rating+sndMovie.rating)/2)*10)/10;
            			movies.add(fstMovie);
            			it2.remove();
            			f = false;
            		}
            	}
          
        		if(f){
        			fstMovie.rating = (double) Math.round((fstMovie.rating/2)*10)/10;
        			movies.add(fstMovie);
        		}
            }
            
            Iterator<Movie> it2 = moviesKinopoisk.iterator();
            while(it2.hasNext()){
            	Movie movie = it2.next();
    			movie.rating = (double) Math.round((movie.rating/2)*10)/10;
    			movies.add(movie);
            }
            
//            Iterator<Movie> itR = movies.iterator();
//            int j=1;
//            while (itR.hasNext()) {
//				System.out.println(j+" "+itR.next());
//				j++;
//			}
            
            
            System.out.println();
            
        	Scanner sc = new Scanner(System.in);
        	
        	System.out.print("¬ведите номер в общем рейтинге:");
        	int position = sc.nextInt();
        	
            Iterator<Movie> it = movies.iterator();
            for (int i = 0; i < position-1; i++) {
				it.next();
			}
            
            System.out.println(it.next().toString());
            
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        

    }
}


class Movie implements Comparable<Movie>{
	String name;
	int year;
	double rating;
	Movie(String name,int year, double rating){
		this.name = name;
		this.year = year;
		this.rating = rating;
	}

	public int compareTo(Movie o) {
		if(this.rating<o.rating){
			return 1;
		}else if(this.rating>=o.rating){
			return -1;
		}
		return 0;
	}
	

	public String toString() {
		return name +" ("+ year +") - "+ rating;
	}
}