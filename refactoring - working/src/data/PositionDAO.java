package data;

import business.Position;
import javafx.geometry.Pos;

import java.sql.*;
import java.util.*;

public class PositionDAO implements Map<Integer, Position> {
    private Connection conn;

    @Override
    public int size() {
        try{
            return Connect.executeQuery(conn, "SELECT COUNT(*) FROM Position", (rs)-> {
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
            PreparedStatement ppstt = conn.prepareStatement("SELECT * FROM Position WHERE idPosition = ?");
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
        if (value instanceof Position) {
            Position p = (Position) value;
            int id = p.getIdPosition();
            Position position = this.get(id);
            if (position.equals(p)) res = true;
        }
        return res;
    }

    @Override
    public Position get(Object key) {
        Position position = new Position();

        try {
            conn = Connect.connect();
            PreparedStatement ppstt = conn.prepareStatement("SELECT * FROM Position WHERE idPosition= ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(conn, ppstt, rs -> {
                if(rs.next()){
                    position.setIdPosition(rs.getInt("idPosition"));
                    position.setType(rs.getString("type"));
                    position.setIdUser(rs.getInt("idUser"));
                    position.setMarketstock_id(rs.getInt("idStock"));
                    position.setAmount(rs.getInt("amount"));
                    position.setStop_loss(rs.getFloat("stop_loss"));
                    position.setTake_profit(rs.getFloat("take_profit"));
                    position.setStatus(rs.getString("status"));
                    position.setDeal_value(rs.getFloat("dealValue"));
                }
                return null;
            });
        }catch (SQLException e){
            System.out.printf(e.getMessage());
        }
        return position;
    }

    @Override
    public Position put(Integer integer, Position position) {
        return null;
    }

    @Override
    public Position remove(Object key) {
        Position position = this.get((Integer) key);
        try {
            conn = Connect.connect();
            PreparedStatement preparedStatement= conn.prepareStatement("DELETE FROM Position WHERE idPosition = ?");
            preparedStatement.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(conn, preparedStatement, rs->null);
        }
        catch (SQLException e) {
            throw new NullPointerException(e.getMessage());
        }
        return position;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends Position> map) {
        for(Position position : map.values()){
            put(position.getIdPosition(), position);
        }
    }

    @Override
    public void clear() {
        try{
            conn = Connect.connect();
            Connect.executeQuery(conn, "DELETE FROM Position", rs->null);
        }
        catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> set = null;

        try {
            conn = Connect.connect();
            set = Connect.executeQuery(conn, "SELECT * FROM Position", rs -> {
                Set<Integer> tempSet = new TreeSet<>();
                while(rs.next()){
                    tempSet.add(rs.getInt("idPosition"));
                }
                return tempSet;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return set;
    }

    @Override
    public Collection<Position> values() {
        Collection<Position> collect = new TreeSet<>();
        try {
            conn = Connect.connect();
            Connect.executeQuery(conn, "SELECT * FROM Position", rs-> {
                while(rs.next()){
                    Position pst = new Position();
                    pst.setIdPosition(rs.getInt("idPosition"));
                    pst.setType(rs.getString("type"));
                    pst.setIdUser(rs.getInt("idUser"));
                    pst.setMarketstock_id(rs.getInt("idStock"));
                    pst.setAmount(rs.getInt("amount"));
                    pst.setStop_loss(rs.getFloat("stop_loss"));
                    pst.setTake_profit(rs.getFloat("take_profit"));
                    pst.setStatus((rs.getString("status")));
                    pst.setDeal_value(rs.getFloat("dealValue"));

                    collect.add(pst);
                }
                return null;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return collect;
    }

    @Override
    public Set<Entry<Integer, Position>> entrySet() {
        Set<Integer> keys = new TreeSet<>(this.keySet());

        TreeMap<Integer,Position> map = new TreeMap<>();
        for(Integer key : keys){
            map.put(key,this.get(key));
        }
        return map.entrySet();
    }
}
