package ua.com.users;


import lombok.*;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@EqualsAndHashCode
@ToString
@Data
@Getter
public class User {
    int id;
    String name;
    String surname;
    int phone;
    String email;
    String login;
    String password;

    public User(int id, String name, String surname, String email) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.phone = 1000000 + (int) (Math.random() * ((9999999 - 1000000) + 1));
        this.login = this.name + "." + this.surname + "." + Integer.toString(this.phone).substring(5);
        this.password = new StringBuffer(this.name).reverse().toString();
    }


    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
        this.login = this.name + "." + this.surname + "." + Integer.toString(this.phone).substring(5);
        this.password = new StringBuffer(this.name).reverse().toString();
    }

    public void setSurname(String surname) {
        this.surname = surname;
        this.login = this.name + "." + this.surname + "." + Integer.toString(this.phone).substring(5);
    }

    public void setPhone(int phone) {
        this.phone = 1000000 + (int) (Math.random() * ((9999999 - 1000000) + 1));
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
