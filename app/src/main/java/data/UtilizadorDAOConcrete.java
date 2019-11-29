package data;

import business.AtivoFinanceiro;
import business.CFD;
import business.Petroleo;
import business.Utilizador;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

public class UtilizadorDAOConcrete implements UtilizadorDAO {
    DBConnection SQLConn = new SQLConnection();

    @Override
    public boolean login(String username, String password) {
        boolean ret=false;
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
        try {
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Update Utilizador set  Saldo = if(Saldo>= +" + value + ",Saldo-"+ value + ",Saldo) where Nome='" + user.getUsername() + "'");

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }


    @Override
    public String put(Utilizador obj) {
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            ResultSet rs = stmt.executeQuery("select * from Utilizador where Nome='"+obj.getUsername()+"'");
            if(rs.next()){
                return null;
            }
            String cmd = "insert into Utilizador (Nome,Password,Saldo) values('" + obj.getUsername() + "','" + obj.getPassword() +"','"+ obj.getMoney() +"')";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
        return obj.getUsername();
    }

    @Override
    public Utilizador get(String id) {
        Utilizador u = null;
        AtivoFinanceiro a;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from Utilizador where Nome='"+id+"'");
            if (rs.next()) {
                u = new Utilizador(rs.getString("Nome"), rs.getString("Password"), rs.getDouble("Saldo"));

            }

            ResultSet rs1 = stmt.executeQuery("select * from AtivosPreferidos inner join AtivoFinanceiro on AtivosPreferidos.AtivoFinanceiro=AtivoFinanceiro.Nome where Utilizador='"+u.getUsername()+"'");
            while(rs1.next()){
                a = new AtivoFinanceiro(rs.getString("Nome"),rs.getDouble("ValorUnit"),rs.getString("Type")) {};
                u.addFavorito(a);
            }

            SQLConn.disconnect();
        }
        catch (SQLException e) {e.printStackTrace();}
        return u;
    }

    @Override
    public void delete(String id) {
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

    @Override
    public void addPreferido( Utilizador u,AtivoFinanceiro a){
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            String cmd = "insert into AtivosPreferidos (AtivoFinanceiro,Utilizador) values('" + a.getCompany() + "','" + u.getUsername() +"')";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public void removePreferido(Utilizador u, AtivoFinanceiro a){
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            String cmd = "delete from AtivosPreferidos where Utilizador ='" + u.getUsername() + "' and AtivoFinanceiro='" + a.getCompany() + "'";
            System.out.println(cmd);
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    public void setValorPref(Utilizador u,AtivoFinanceiro a,double val){
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            String cmd = "update  AtivosPreferidos set Valor=" + val  +"where Utilizador='" + u.getUsername()+"' and AtivoFinanceiro='" + a.getCompany() +"'";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    public static void main(String[] args) {
        UtilizadorDAOConcrete uc = new UtilizadorDAOConcrete();
        AtivoFincanceiroDAOConcrete ac = new AtivoFincanceiroDAOConcrete();
        Petroleo p = new Petroleo("pet",20.0);
        Utilizador u = new Utilizador("fabio","111",10000.0);

        uc.put(u);
        ac.put(p);

        //uc.addPreferido(u,p);

        uc.removePreferido(u,p);

    }
    
}