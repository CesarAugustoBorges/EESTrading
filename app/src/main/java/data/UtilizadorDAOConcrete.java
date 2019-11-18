package data;

import business.CFD;
import business.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UtilizadorDAOConcrete implements UtilizadorDAO {


    @Override
    public double addCFD(Utilizador user, CFD cfd) {
        return 0;
    }

    @Override
    public double removeCFD(Utilizador user, CFD cfd) {
        return 0;
    }

    @Override
    public void putPortfolio(Utilizador user, CFD cfd) {

    }

    @Override
    public List<CFD> getLastCFDBought(Utilizador user, int maxSize) {
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public void addMoney(Utilizador user, double value) {
        SQLConnection SQLConn = new SQLConnection();
        try {
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Update Utilizador set Saldo =Saldo +" + value + " where Nome='" + user.getUsername() + "'");

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public boolean removeMoney(Utilizador user, double value) {
        SQLConnection SQLConn = new SQLConnection();
        try {
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Update Utilizador set  Saldo = if(Saldo> +" + value + ",Saldo-"+ value + ",Saldo) where Nome='" + user.getUsername() + "'");

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
        return false;
    }


    @Override
    public String put(Utilizador obj) {
        SQLConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("delete from Utilizador where Nome='" + obj.getUsername()+"'");
            String cmd = "insert into Utilizador (Nome,Password,Saldo) values('" + obj.getUsername() + "','" + obj.getPassword() +"','"+ obj.getMoney() +"')";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
        return obj.getUsername();
    }

    @Override
    public Utilizador get(String id) {
        SQLConnection SQLConn = new SQLConnection();
        Utilizador u = null;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Utilizador where Nome='"+id+"'");
            if (rs.next()) {
                u = new Utilizador(rs.getString("Nome"), rs.getString("Password"), rs.getDouble("Saldo"));
            }
            SQLConn.disconnect();
        }
        catch (SQLException e) {e.printStackTrace();}
        return u;
    }

    @Override
    public void delete(String id) {
        SQLConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("delete from Utilizador where Nome='" + id +"'");

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}

    }

    @Override
    public void replace(String id, Utilizador obj) {

    }

    public static void main(String[] args) {
        UtilizadorDAOConcrete uc = new UtilizadorDAOConcrete();
        Utilizador u = new Utilizador("Fábio","111",200.20);

        //uc.get("Fábio");
        //uc.delete("Fábio");
        uc.put(u);
        uc.addMoney(u,100);
        uc.removeMoney(u,50);
    }
}