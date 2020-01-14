package data;

import business.Notification;

import java.sql.*;
import java.util.*;

public class NotificationDAO implements Map<Integer, Notification> {
    private Connection conn;
    @Override
    public int size() {
        conn = Connect.connect();
        try{
            return Connect.executeQuery(conn, "SELECT COUNT(*) FROM Notification", (rs)-> {
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
        return this.size() == 0;
    }

    @Override
    public boolean containsKey(Object key) {
        boolean res = false;
        try {
            conn = Connect.connect();
            PreparedStatement ppstt = conn.prepareStatement("SELECT * FROM Notification WHERE idNotification = ?");
            ppstt.setString(1, Integer.toString((Integer) key));
            res = Connect.executeQuery( conn, ppstt, ResultSet::next);
        } catch (SQLException e) {
        System.out.println(e.getMessage());
        }
        return res;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        if (value instanceof Notification) {
            Notification notic = (Notification) value;
            int id = notic.getId_notification();
            Notification notificn = this.get(id);
            if (notificn.equals(notic)) res = true;
        }
        return res;
    }

    @Override
    public Notification get(Object key) {
        Notification noti = new Notification();
        try {
            conn = Connect.connect();
            PreparedStatement ppstt = conn.prepareStatement("SELECT * FROM Notification WHERE idNotification = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(conn, ppstt, (rs) -> {
                if(rs.next()){
                    noti.setId_notification(rs.getInt("idNotification"));
                    noti.setNotific_user_id(rs.getInt("notific_user_id"));
                    noti.setInfo(rs.getString("info"));
                }
                return null;
            });
        }catch (SQLException e){
            System.out.printf(e.getMessage());
        }
        return noti;
    }

    @Override
    public Notification put(Integer key, Notification value) {
        Notification ntf;
        if (this.containsKey(key)){
            ntf = this.get(key);
        }
        else ntf = value;

        try {
            conn = Connect.connect();
            PreparedStatement ppstt = conn.prepareStatement("DELETE FROM Notification WHERE idNotification = ?");
            ppstt.setString(1,Integer.toString((Integer)key));
            Connect.executeQuery(conn, ppstt, rs->null);

            conn = Connect.connect();
            ppstt = conn.prepareStatement("INSERT INTO Notification(idNotification,notific_user_id,info) VALUES (?,?,?)");
            ppstt.setString(1,Integer.toString((Integer) key));
            ppstt.setString(2,Integer.toString(value.getNotific_user_id()));
            ppstt.setString(3,value.getInfo());
            Connect.executeQuery(conn, ppstt, rs->null);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return ntf;
    }

    @Override
    public Notification remove(Object key) {
        Notification ntf = this.get((Integer) key);
        try {
            conn = Connect.connect();
            PreparedStatement ppstt = conn.prepareStatement("DELETE FROM Notification WHERE idNotification = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(conn, ppstt, rs->null);
        } catch (SQLException e) {
            throw new NullPointerException(e.getMessage());
        }
        return ntf;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Notification> map) {
        for(Notification ntf : map.values()){
            put(ntf.getId_notification(),ntf);
        }
    }

    @Override
    public void clear() {
        try{
            conn = Connect.connect();
            Connect.executeQuery(conn, "DELETE FROM Notification", rs->null);
        }catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> st = null;
        try {
            conn = Connect.connect();
            st = Connect.executeQuery(conn, "SELECT * FROM Notification", rs -> {
                Set<Integer> tempSet = new TreeSet<>();
                while(rs.next()){
                    tempSet.add(rs.getInt("idNotification"));
                }
                return tempSet;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return st;
    }

    @Override
    public Collection<Notification> values() {
        Collection<Notification> collect = new TreeSet<>();
        try {
            conn = Connect.connect();
            Connect.executeQuery(conn, "SELECT * FROM Notification", rs -> {
                while(rs.next()){
                    Notification ntf = new Notification();
                    ntf.setId_notification(rs.getInt("idNotification"));
                    ntf.setNotific_user_id(rs.getInt("notific_user_id"));
                    ntf.setInfo((rs.getString("info")));

                    collect.add(ntf);
                }
                return null;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return collect;
    }

    @Override
    public Set<Entry<Integer, Notification>> entrySet() {
        Set<Integer> keys = new TreeSet<>(this.keySet());

        TreeMap<Integer,Notification> map = new TreeMap<>();
        for(Integer key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
}
