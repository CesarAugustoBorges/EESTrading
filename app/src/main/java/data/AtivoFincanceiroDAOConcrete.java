package data;

import business.AtivoFinanceiro;
import business.CFD;
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

            ResultSet rs=stmt.executeQuery("select * from AtivoFinanceiro where Nome='" + obj.getCompany() +"'");
            if(rs.next()){
                stmt.executeUpdate("Update AtivoFinanceiro set ValorUnit=" + obj.getValue()+ ", Type='" + obj.getType()+ "'" +
                        " where Nome ='" + obj.getCompany() +"'");
            }
            else {
                stmt.executeUpdate("delete from AtivoFinanceiro where Nome='" + obj.getCompany() + "'");

                String cmd = "insert into AtivoFinanceiro (Nome,ValorUnit,Type) values('" + obj.getCompany() + "'," + obj.getValue() + ",'" + obj.getType() + "')";
                //System.out.println(obj.getType());
                stmt.executeUpdate(cmd);

            }
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

    @Override
    public List<CFD> getCFDs(AtivoFinanceiro ativoFinanceiro) {
        List<CFD> cfds = new ArrayList<>();
        DBConnection SQLConn = new SQLConnection();
        CFD cfd;
        CFDDAO cfddao = DAOFactory.getFactory().newCFDDAO();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs=stmt.executeQuery("select Id from CFD inner join AtivoFinanceiro on CFD.AtivoFinanceiro_Nome = AtivoFinanceiro.Nome where AtivoFinanceiro.Nome='"+ ativoFinanceiro.getCompany() + "' and Id not in (select Id from CFDVendido);");
            while(rs.next()){
                cfd = cfddao.get(rs.getInt("Id"));
                cfds.add(cfd);
            }
            SQLConn.disconnect();
        }
        catch (SQLException e ){e.printStackTrace();}
        return cfds;
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