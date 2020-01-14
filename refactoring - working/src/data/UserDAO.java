package data;

import business.User;

import java.util.*;
import java.sql.*;

public class UserDAO implements Map<Integer, User> {
    Connection connection;

    @Override
    public int size() {
        connection = Connect.connect();
        try{
            return Connect.executeQuery(connection, "SELECT COUNT(*) from USER", (rs)-> {
                if (rs.next()) {
                    return rs.getInt(1);
                }
                return -1;
            });
        } catch (Exception e){
            System.out.println(e.getMessage());
            return -1;
        }

    }

    @Override
    public boolean isEmpty() {
        return this.size()==0;
    }

    @Override
    public boolean containsKey(Object key) {

        boolean res = false;
        try {
            connection = Connect.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM user WHERE idUser = ?");
            preparedStatement.setString(1, Integer.toString((Integer) key));
            res = Connect.executeQuery( connection, preparedStatement, ResultSet::next);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        if(value instanceof User){
            User user = (User) value;
            int id = user.getIdUser();
            User thisUser = this.get(id);
            if(thisUser.equals(user)) res = true;
        }
        return res;
    }

    @Override
    public User get(Object key) {
        User usr = new User();

        try {
            connection = Connect.connect();
            PreparedStatement ppstt = connection.prepareStatement("SELECT * FROM User WHERE idUser = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(connection, ppstt, rs ->{
                if(rs.next()){
                    usr.setIdUser(rs.getInt("idUser"));
                    usr.setUsername(rs.getString("username"));
                    usr.setName(rs.getString("name"));
                    usr.setEmail(rs.getString("email"));
                    usr.setPassword(rs.getString("password"));
                    usr.setAccount_balance(rs.getFloat("account_balance"));
                }
                return null;
            });
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return usr;
    }

    @Override
    public User put(Integer key, User value) {
        User usr;

        if (this.containsKey(key)){
            usr = this.get(key);
        }
        else usr = value;
        try {
            connection = Connect.connect();
            PreparedStatement ppstt = connection.prepareStatement("DELETE FROM User WHERE idUser = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(connection, ppstt, rs->null);

            connection = Connect.connect();
            ppstt = connection.prepareStatement("INSERT INTO User(idUser,username,name,email,password,account_balance) VALUES (?,?,?,?,?,?)");
            ppstt.setString(1,Integer.toString((Integer) key));
            ppstt.setString(2,value.getUsername());
            ppstt.setString(3,value.getName());
            ppstt.setString(4,value.getEmail());
            ppstt.setString(5,value.getPassword());
            ppstt.setString(6,Float.toString(value.getAccount_balance()));
            Connect.executeQuery(connection, ppstt, rs->null);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return usr;
    }

    @Override
    public User remove(Object key) {
        User usr = this.get((Integer) key);
        try {
            connection = Connect.connect();
            PreparedStatement ppstt = connection.prepareStatement("DELETE FROM User WHERE idUser = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(connection, ppstt, rs->null);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return usr;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends User> user) {
        for(User usr : user.values()){
            user.entrySet().stream().forEach(e->this.put(e.getKey(),e.getValue()));
        }
    }

    @Override
    public void clear() {
        try{
            connection = Connect.connect();
            Connect.executeQuery(connection, "DELETE FROM User", rs->null);
        }catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> st = null;

        try {
            connection = Connect.connect();
            st = Connect.executeQuery(connection, "SELECT * FROM User", rs-> {
                Set<Integer> tempSet = new TreeSet<>();
                while(rs.next()){
                    tempSet.add(rs.getInt("idUser"));
                }
                return tempSet;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return st;
    }

    @Override
    public Collection<User> values() {
        Collection<User> collect = new TreeSet<>();
        try {
            connection = Connect.connect();
            Connect.executeQuery(connection,"SELECT * FROM User", rs-> {
                while(rs.next()){
                    User usr = new User();
                    usr.setIdUser(rs.getInt("idUser"));
                    usr.setUsername(rs.getString("username"));
                    usr.setName(rs.getString("name"));
                    usr.setEmail(rs.getString("email"));
                    usr.setPassword(rs.getString("password"));
                    usr.setAccount_balance(rs.getFloat("account_balance"));

                    collect.add(usr);
                }
                return null;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return collect;
    }

    @Override
    public Set<Entry<Integer, User>> entrySet() {
        Set<Integer> keys = new TreeSet<>(this.keySet());

        TreeMap<Integer,User> map = new TreeMap<>();
        keys.stream().forEach(e->map.put(e,this.get(e)));
        return map.entrySet();
    }
}
