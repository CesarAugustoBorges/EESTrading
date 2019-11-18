package data;

import business.AtivoFinanceiro;
import business.Petroleo;

import java.sql.*;


import java.util.List;

public class AtivoFincanceiroDAOConcrete implements AtivoFinanceiroDAO {


    @Override
    public AtivoFinanceiro get(String id) {
        SQLConnection SQLConn = new SQLConnection();
        AtivoFinanceiro a = null;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from AtivoFinanceiro where Nome='" + id +"'");
            if(rs.next()){
                a = new AtivoFinanceiro(rs.getString("Nome"),rs.getDouble("ValorUnit")) {};
            }
            SQLConn.disconnect();
        }
        catch (SQLException e ){e.printStackTrace();}
        return a;
    }

    @Override
    public String put(AtivoFinanceiro obj) {
        SQLConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("delete from AtivoFinanceiro where Nome='" + obj.getCompany()+"'");
            String cmd = "insert into AtivoFinanceiro (Nome,ValorUnit) values('" + obj.getCompany() + "'," + obj.getValue() +")";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
        return obj.getCompany();
    }

    @Override
    public void delete(String id) {
        SQLConnection SQLConn = new SQLConnection();
        try {
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("delete from AtivoFinanceiro where Nome='" + id +"'");

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public void replace(String id, AtivoFinanceiro obj) {

    }

<<<<<<< HEAD
    //teste
    public static void main(String[] args) {
        AtivoFincanceiroDAOConcrete a = new AtivoFincanceiroDAOConcrete();

        AtivoFinanceiro af = new Petroleo("Petroleo",11);

        a.put(af);
        //a.get("Petroleo");
        //a.delete("Petroleo");
=======
    @Override
    public List<AtivoFinanceiro> getAll() {
        return null;
>>>>>>> 3e372a6b1260ea3b03bf1168d560eb0d7f0596dc
    }
}