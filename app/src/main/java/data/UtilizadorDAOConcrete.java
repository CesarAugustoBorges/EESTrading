package data;

import business.CFD;
import business.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class UtilizadorDAOConcrete implements UtilizadorDAO {

    //Não são necessárias
    @Override
    public double addCFD(Utilizador user, CFD cfd) {
        return 0;
    }

    @Override
    public double removeCFD(Utilizador user, CFD cfd) {
        return 0;
    }

    //Feito no delete do CFD
    @Override
    public void putPortfolio(Utilizador user, CFD cfd) {

    }

    //Adicionar tempo ao CFD
    @Override
    public List<CFD> getLastCFDBought(Utilizador user, int maxSize) {
        return null;
    }

    @Override
    public boolean login(String username, String password) {
        boolean ret=false;
        DBConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Utilizador where Nome='"+username+"' and Password='" + password + "'");
            if(rs.next()){
                ret=true;
            }
            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}

        return ret;
    }

    @Override
    public void addMoney(Utilizador user, double value) {
        DBConnection SQLConn = new SQLConnection();
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
    public void removeMoney(Utilizador user, double value) {
        DBConnection SQLConn = new SQLConnection();
        try {
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Update Utilizador set  Saldo = if(Saldo> +" + value + ",Saldo-"+ value + ",Saldo) where Nome='" + user.getUsername() + "'");

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }


    @Override
    public String put(Utilizador obj) {
        DBConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from Utilizador where Nome='"+obj.getUsername()+"'");
            if(rs.next()){
                return null;
            }
            //stmt.executeUpdate("delete from Utilizador where Nome='" + obj.getUsername()+"'");
            String cmd = "insert into Utilizador (Nome,Password,Saldo) values('" + obj.getUsername() + "','" + obj.getPassword() +"','"+ obj.getMoney() +"')";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
        return obj.getUsername();
    }

    @Override
    public Utilizador get(String id) {
        DBConnection SQLConn = new SQLConnection();
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
        DBConnection SQLConn = new SQLConnection();
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
        //uc.put(u);
        //uc.addMoney(u,100);
        //uc.removeMoney(u,50);
        //System.out.println(uc.login("Fábio","111"));
    }
}