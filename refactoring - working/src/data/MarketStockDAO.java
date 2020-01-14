package data;

import business.MarketStock;

import java.sql.*;
import java.util.*;

public class MarketStockDAO implements Map<Integer, MarketStock> {
    private Connection connection;
    @Override
    public int size() {
        connection = Connect.connect();
        try{
            return Connect.executeQuery(connection, "SELECT COUNT(*) FROM MarketStock", (rs)-> {
                if (rs.next()) {
                    int size = rs.getInt(1);
                    return size;
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
        try{
            connection = Connect.connect();
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM MarketStock WHERE idStock = ?");
            preparedStatement.setString(1, Integer.toString((Integer) key));
            return Connect.executeQuery( connection, preparedStatement, ResultSet::next);
        }
        catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        boolean res = false;
        if(value instanceof MarketStock){
            MarketStock stock = (MarketStock) value;
            int idStock = stock.getId_stock();
            MarketStock thisStock = this.get(idStock);
            if(thisStock.equals(stock)) res = true;
        }
        return res;
    }

    @Override
    public MarketStock get(Object key) {
        MarketStock stock = new MarketStock();
        try {
            connection = Connect.connect();
            PreparedStatement ppstt = connection.prepareStatement("SELECT * FROM MarketStock WHERE idStock = ?");
            ppstt.setString(1,Integer.toString((Integer)key));
            Connect.executeQuery(connection, ppstt, (rs) -> {
                if(rs.next()){
                    stock.setId_stock(rs.getInt("idStock"));
                    stock.setName(rs.getString("name"));
                    stock.setOwner(rs.getString("owner"));
                    stock.setCfd_buy(rs.getFloat("cfdBuy"));
                    stock.setCfd_sale(rs.getFloat("cfdSale"));
                    stock.setPrice(rs.getFloat("price"));
                }
                return null;
            });
        }catch (SQLException e){
            System.out.printf(e.getMessage());
        }
        return stock;
    }

    @Override
    public MarketStock put(Integer key, MarketStock value) {
        MarketStock stock;
        if (this.containsKey(key)){
            stock = this.get(key);
        }
        else stock = value;
        try {
            connection = Connect.connect();
            PreparedStatement ppstt = connection.prepareStatement("DELETE FROM MarketStock WHERE idStock = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(connection, ppstt, rs->null);

            connection = Connect.connect();
            ppstt = connection.prepareStatement("INSERT INTO MarketStock(idStock, name, owner, cfdBuy, cfdSale, Price) VALUES (?,?,?,?,?,?)");
            ppstt.setString(2,value.getName());
            ppstt.setString(3,value.getOwner());
            ppstt.setString(4,Float.toString(value.getCfd_Buy()));
            ppstt.setString(5,Float.toString(value.getCfd_Sale()));
            ppstt.setString(6,Float.toString(value.getPrice()));
            Connect.executeQuery(connection, ppstt, rs->null);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return stock;
    }

    @Override
    public MarketStock remove(Object key) {
        MarketStock stock = this.get((Integer) key);
        try {
            connection = Connect.connect();
            PreparedStatement ppstt = connection.prepareStatement("DELETE FROM MarketStock WHERE idStock = ?");
            ppstt.setString(1,Integer.toString((Integer) key));
            Connect.executeQuery(connection, ppstt, rs->null);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stock;
    }

    @Override
    public void putAll(Map<? extends Integer, ? extends MarketStock> map) {
        for(MarketStock s : map.values()){
            put(s.getId_stock(), s);
        }
    }

    @Override
    public void clear() {
        try{
            connection = Connect.connect();
            Connect.executeQuery(connection,"DELETE FROM MarketStock", rs ->null);
        }catch (Exception e){
            throw new NullPointerException(e.getMessage());
        }
    }

    @Override
    public Set<Integer> keySet() {
        Set<Integer> set = null;
        try {
            connection = Connect.connect();
            set = Connect.executeQuery(connection, "SELECT * FROM MarketStock", rs -> {
                Set<Integer> tempSet = new TreeSet<>();
                while(rs.next()){
                    tempSet.add(rs.getInt("idStock"));
                }
                return tempSet;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return set;
    }

    @Override
    public Collection<MarketStock> values() {
        Collection<MarketStock> collection = new TreeSet<>();
        try {
            connection = Connect.connect();
            Connect.executeQuery(connection, "SELECT * FROM MarketStock", rs -> {
                while(rs.next()){
                    MarketStock s = new MarketStock();
                    s.setId_stock(rs.getInt("idStock"));
                    s.setName(rs.getString("name"));
                    s.setOwner(rs.getString("owner"));
                    s.setCfd_buy(rs.getFloat("cfdBuy"));
                    s.setCfd_sale(rs.getFloat("cfdSale"));
                    s.setPrice(rs.getFloat("price"));
                    collection.add(s);
                }
                return null;
            });
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return collection;
    }

    @Override
    public Set<Entry<Integer, MarketStock>> entrySet() {
        Set<Integer> keys = new TreeSet<>(this.keySet());
        TreeMap<Integer, MarketStock> map = new TreeMap<>();
        keys.stream().forEach(e-> map.put(e,this.get(e)));
        return map.entrySet();
    }
}
