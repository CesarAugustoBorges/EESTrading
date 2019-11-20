package data;

import business.AtivoFinanceiro;
import business.Petroleo;

import java.sql.*;


import java.util.ArrayList;
import java.util.List;

public class AtivoFincanceiroDAOConcrete implements AtivoFinanceiroDAO {


    @Override
    public AtivoFinanceiro get(String id) {
        DBConnection SQLConn = new SQLConnection();
        AtivoFinanceiro a = null;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from AtivoFinanceiro where Nome='" + id +"'");
            if(rs.next()){
                a = new AtivoFinanceiro(rs.getString("Nome"),rs.getDouble("ValorUnit"),"") {};
            }
            SQLConn.disconnect();
        }
        catch (SQLException e ){e.printStackTrace();}
        return a;
    }

    @Override
    public String put(AtivoFinanceiro obj) {
        DBConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("delete from AtivoFinanceiro where Nome='" + obj.getCompany()+"'");

            String cmd = "insert into AtivoFinanceiro (Nome,ValorUnit,Type) values('" + obj.getCompany() + "'," + obj.getValue() +",'" +obj.getType() +"')";
            stmt.executeUpdate(cmd);

            SQLConn.disconnect();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return obj.getCompany();
    }

    @Override
    public void delete(String id) {
        DBConnection SQLConn = new SQLConnection();
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
        obj.setCompany(id);
        put(obj);
    }

    @Override
    public List<AtivoFinanceiro> getAll(){
        List<AtivoFinanceiro> ativos = new ArrayList<>();
        DBConnection SQLConn = new SQLConnection();
        AtivoFinanceiro a = null;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery("select * from AtivoFinanceiro");
            while(rs.next()){
                a = new AtivoFinanceiro(rs.getString("Nome"),rs.getDouble("ValorUnit"),rs.getString("Type")) {};
                ativos.add(a);
            }
            SQLConn.disconnect();
        }
        catch (SQLException e ){e.printStackTrace();}
        return ativos;
    }
    
    //teste
    public static void main(String[] args) {
        AtivoFincanceiroDAOConcrete a = new AtivoFincanceiroDAOConcrete();

        AtivoFinanceiro af = new Petroleo("Petroleo",11);
        List<AtivoFinanceiro> ativos = new ArrayList<>();


        //a.put(af);
        //a.get("Petroleo");
        //a.delete("Petroleo");
        a.getAll();
    }


}