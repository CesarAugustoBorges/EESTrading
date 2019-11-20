package data;

import business.AtivoFinanceiro;
import business.CFD;
import business.Utilizador;

import java.sql.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CFDDAOConcrete implements CFDDAO {

    @Override
    public double sell(int id) {
        DBConnection SQLConn = new SQLConnection();
        UtilizadorDAOConcrete uDAO = new UtilizadorDAOConcrete();
        Utilizador u;
        double value=0;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CFD where Id="+ id);
            if(rs.next()){
                u = uDAO.get(rs.getString("Utilizador_Nome"));
                value = rs.getDouble("ValorCompra");
                uDAO.addMoney(u,value);
            }
            delete(id);
            SQLConn.disconnect();

        }
        catch (SQLException e){e.printStackTrace();}
        return value;
    }

    @Override
    public double getValue(int id) {
        return 0;
    } //decidimos não fazer pois ja está na classe

    @Override
    public List<CFD> get(Utilizador user) {
        List<CFD> CFDs= new ArrayList<>();
        DBConnection SQLConn = new SQLConnection();
        CFD cfd = null;
        UtilizadorDAOConcrete uDAO = new UtilizadorDAOConcrete();
        Utilizador u;
        AtivoFincanceiroDAOConcrete afDAO = new AtivoFincanceiroDAOConcrete();
        AtivoFinanceiro a;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CFD inner join Utilizador on Utilizador.Nome=CFD.Utilizador_Nome where Utilizador.Nome='"+user.getUsername()+"'");
            while (rs.next()) {
                u = uDAO.get(rs.getString("Utilizador_Nome"));
                a = afDAO.get(rs.getString("AtivoFinanceiro_Nome"));
                cfd = new CFD(rs.getDouble("Unidades"),rs.getDouble("TopProfit"),rs.getDouble("StopLoss"),rs.getInt("Id"),u,a);
                System.out.println("Adicionou CFD com Id:"+cfd.getId());
                CFDs.add(cfd);
            }

            SQLConn.disconnect();

        }
        catch (SQLException e){e.printStackTrace();}
        return CFDs;
    }

    @Override
    public Integer put(CFD obj) {
        DBConnection SQLConn = new SQLConnection();
        int i=0;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("delete from CFD where Id=" + obj.getId());
            String cmd = "insert into CFD (Id,ValorCompra,Unidades,TopProfit,StopLoss,Utilizador_Nome,AtivoFinanceiro_Nome,Portfolio) values ("+
                    obj.getId()+ "," +obj.getBoughtValue()+","+obj.getUnits()+","+obj.getTopProfit()+","+obj.getStopLoss()+",'"+obj.getUtilizador().getUsername()+"','"+ obj.getAtivoFinanceiro().getCompany() + "',0)";

            i=stmt.executeUpdate(cmd);

            SQLConn.disconnect();

        }
        catch (SQLException e){e.printStackTrace();}
        return i;
    }

    @Override
    public CFD get(Integer id) {
        DBConnection SQLConn = new SQLConnection();
        CFD cfd = null;
        UtilizadorDAOConcrete uDAO = new UtilizadorDAOConcrete();
        Utilizador u;
        AtivoFincanceiroDAOConcrete afDAO = new AtivoFincanceiroDAOConcrete();
        AtivoFinanceiro a;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CFD where Id='"+id+"'");
            if (rs.next()) {
                u = uDAO.get(rs.getString("Utilizador_Nome"));
                a = afDAO.get(rs.getString("AtivoFinanceiro_Nome"));
                cfd = new CFD(rs.getDouble("Unidades"),rs.getDouble("TopProfit"),rs.getDouble("StopLoss"),rs.getInt("Id"),u,a);
            }

            SQLConn.disconnect();

        }
        catch (SQLException e){e.printStackTrace();}
        return cfd;
    }

    @Override
    public void delete(Integer id) {
        DBConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Update CFD set Portfolio=1 where Id=" + id);

            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}
    }

    @Override
    public void replace(Integer id, CFD obj) {
        obj.setId(id);
        put(obj);
    }

    public static void main(String[] args) {
        CFDDAOConcrete cfdDAO = new CFDDAOConcrete();
        Utilizador u = new Utilizador("Fábio","111",250.20);
        AtivoFinanceiro af = new AtivoFinanceiro("Petroleo",11) {};
        CFD cfd = new CFD(15,0.0,0.0,1,u,af) ;
        CFD cfd2 = new CFD(50,0.0,0.0,2,u,af) ;

        //cfd = cfdDAO.get(1);
        //System.out.println(cfd.getBoughtValue());

        //cfdDAO.put(cfd2);
        //cfdDAO.get(u);
        //cfdDAO.delete(1);
        cfdDAO.sell(1);
    }
}