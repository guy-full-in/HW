import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

class User implements Serializable{
        String login, password;
        Date lastlogintime;
        Gender gender;
        
        
        public String getGender() {
                return gender.name();
        }

        User(String login, String password, Gender gender, Date date){
                this.login = login;
                this.password = password;
                this.lastlogintime = date;
                this.gender = gender;
        }

        public String getDate() {
                SimpleDateFormat dateFormat = new SimpleDateFormat("kk:mm dd.MM.yyyy");
        return dateFormat.format(lastlogintime);
        }
}

enum Gender{
        WOMAN,MAN;
}