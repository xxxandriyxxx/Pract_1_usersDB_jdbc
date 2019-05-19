package ua.com.users;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {

        List<User> users = new ArrayList<>();

        users.add(new User(111, "Bogdan", "abc", "bogdan111@gmail.com"));
        users.add(new User(222, "Bogdan", "bcd", "bogdan222@gmail.com"));
        users.add(new User(333, "Andriy", "abc", "sndriy333@gmail.com"));
        users.add(new User(444, "Mykola", "cde", "mykola444@gmail.com"));
        users.add(new User(555, "Igor", "def", "igor555@gmail.com"));
        users.add(new User(567, "Igor", "efg", "igor567@gmail.com"));
        users.add(new User(777, "Bogdan", "bcd", "bogdan777@gmail.com"));
        users.add(new User(888, "Andriy", "abc", "andriy888@gmail.com"));
        users.add(new User(999, "Bogdan", "efg", "bogdan999@gmail.com"));
        users.add(new User(123, "Vova", "hij", "vova123@gmail.com"));

        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/",
                "root",
                "root"
        );
        PreparedStatement statement = connection.prepareStatement("create database if not exists " +
                "pract1 character set 'utf8'");
        statement.executeUpdate();
        statement.execute("use pract1");
        statement.execute("create table if not exists users(" +
                "id_auto int primary key auto_increment, " +
                "id int, " +
                "name varchar(30), " +
                "surname varchar(30), " +
                "email varchar(30), " +
                "login varchar(30), " +
                "password varchar(30), " +
                "phone int not null)");
        statement.execute("truncate table pract1.users"); //delete all data
        statement.executeUpdate();

        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
            User next = iterator.next();
            PreparedStatement ps = connection.prepareStatement("insert into pract1.users " +
                    "(id, name, surname, email, login, password, phone) values (?,?,?,?,?,?,?)");
            ps.setInt(1, next.getId());
            ps.setString(2, next.getName());
            ps.setString(3, next.getSurname());
            ps.setString(4, next.getEmail());
            ps.setString(5, next.getLogin());
            ps.setString(6, next.getPassword());
            ps.setInt(7, next.getPhone());
            ps.executeUpdate();
        }

        Scanner scan = new Scanner(System.in);
        String choice = "";

        while (true) {
            System.out.println("--------------------------------------------------------");
            System.out.println("1 - Show all users\n" +
                    "2 - Show the user by ID\n" +
                    "3 - Show users by name\n" +
                    "4 - Delete the user by ID\n" +
                    "5 - Delete users by name\n" +
                    "0 - Exit");
            System.out.println("--------------------------------------------------------");
            choice = scan.nextLine();
            switch (choice) {
                case "1":
                    printAll(connection, "pract1.users");
                    break;
                case "2":
                    printByID(connection, "pract1.users");
                    break;
                case "3":

                    printByName(connection, "pract1.users");
                    break;
                case "4":
                    deleteByID(connection, "pract1.users");
                    break;
                case "5":
                    deleteByName(connection, "pract1.users");
                    break;
                case "0":
                    System.out.println("Goodbye !!!");
                    System.out.println("--------------------------------------------------------");
                    return;
                default:
                    System.out.println("You entered the wrong symbol !!!");
                    break;
            }
        }

    }

    public static void printAll(Connection connection, String tableName) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select * from " + tableName);
        ResultSet resultSet = ps.executeQuery();
        System.out.println("id_auto  |  id  |  name  |  surname  |  email |  login  |  password  |  phone");
        while (resultSet.next()) {
            for (int i = 1; i <= 8; i++) {
                System.out.print(resultSet.getString(i) + " | ");
            }
            System.out.println();
        }
    }

    public static void printByName(Connection connection, String tableName) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user name:");
        String name = scan.nextLine();
        PreparedStatement ps = connection.prepareStatement("select * from " + tableName +
                " where name = '" + name + "'");
        ResultSet resultSet = ps.executeQuery();
        System.out.println("id_auto  |  id  |  name  |  surname  |  email |  login  |  password  |  phone");
        while (resultSet.next()) {
            for (int i = 1; i <= 8; i++) {
                System.out.print(resultSet.getString(i) + " | ");
            }
            System.out.println();
        }
    }

    public static void printByID(Connection connection, String tableName) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user's ID:");
        String id = scan.nextLine();
        PreparedStatement ps = connection.prepareStatement("select * from " + tableName +
                " where id = '" + id + "'");
        ResultSet resultSet = ps.executeQuery();
        System.out.println("id_auto  |  id  |  name  |  surname  |  email |  login  |  password  |  phone");
        while (resultSet.next()) {
            for (int i = 1; i <= 8; i++) {
                System.out.print(resultSet.getString(i) + " | ");
            }
            System.out.println();
        }
    }

    public static void deleteByName(Connection connection, String tableName) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user name:");
        String name = scan.nextLine();
        PreparedStatement ps = connection.prepareStatement("delete from " + tableName +
                " where name = '" + name + "'");
        int i = ps.executeUpdate();
        if (i == 0) {
            System.out.println("Not found users with such name !");
        } else {
            System.out.println("Deleted " + i + " users with name '" + name + "' !");
        }
    }

    public static void deleteByID(Connection connection, String tableName) throws SQLException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter user's ID:");
        int i = 0;
        try {
            int id = scan.nextInt();
            PreparedStatement ps = connection.prepareStatement("delete from " + tableName +
                    " where id = '" + id + "'");
            i = ps.executeUpdate();
            if (i == 0) {
                System.out.println("Not found users with such ID !");
            } else {
                System.out.println("Deleted " + i + " users with ID = " + id + " !");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }


}



