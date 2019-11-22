package data;

import business.AtivoFinanceiro;
import business.CFD;
import business.Utilizador;

import java.sql.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CFDDAOConcrete implements CFDDAO {

    @Override
    public double sell(CFD cfd) {
        DBConnection SQLConn = new SQLConnection();
        UtilizadorDAOConcrete uDAO = new UtilizadorDAOConcrete();
        Utilizador u;
        double value=0;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CFD where Id="+ cfd.getId());
            if(rs.next()){
                u = uDAO.get(rs.getString("Utilizador_Nome"));
                value = rs.getDouble("ValorCompra");
                uDAO.addMoney(u,value);
            }
            delete(cfd.getId());
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
            ResultSet rs = stmt.executeQuery("select * from CFD inner join Utilizador on Utilizador.Nome=CFD.Utilizador_Nome where Utilizador.Nome='"+user.getUsername()+"' and not exists (select * from CFDVendido)");
            while (rs.next()) {
                u = uDAO.get(rs.getString("Utilizador_Nome"));
                a = afDAO.get(rs.getString("AtivoFinanceiro_Nome"));
                cfd = new CFD(rs.getDouble("Unidades"),rs.getDouble("TopProfit"),rs.getDouble("StopLoss"),rs.getInt("Id"),u,a,rs.getTimestamp("DataVenda").toLocalDateTime());
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
            String cmd = "insert into CFD (Id,ValorCompra,Unidades,TopProfit,StopLoss,Utilizador_Nome,AtivoFinanceiro_Nome) values ("+
                    obj.getId()+ "," +obj.getBoughtValue()+","+obj.getUnits()+","+obj.getTopProfit()+","+obj.getStopLoss()+",'"+obj.getUtilizador().getUsername()+"','"+ obj.getAtivoFinanceiro().getCompany() + "')";

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
                cfd = new CFD(rs.getDouble("Unidades"),rs.getDouble("TopProfit"),rs.getDouble("StopLoss"),rs.getInt("Id"),u,a,rs.getTimestamp("DataVenda").toLocalDateTime());
            }

            SQLConn.disconnect();

        }
        catch (SQLException e){e.printStackTrace();}
        return cfd;
    }

    public List<CFD> getVendidos(Utilizador u) {
        DBConnection SQLConn = new SQLConnection();
        List<CFD> portfolioList = new ArrayList<>();
        CFD cfd;
        AtivoFincanceiroDAOConcrete afDAO = new AtivoFincanceiroDAOConcrete();
        AtivoFinanceiro a;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CFDVendido inner join CFD on CFD.Id=CFDVendido.Id inner join Utilizador on Utilizador.Nome=CFD.Utilizador_Nome where Utilizador.Nome='"+u.getUsername()+"'");
            while (rs.next()) {
                a = afDAO.get(rs.getString("AtivoFinanceiro_Nome"));
                cfd = new CFD(rs.getDouble("Unidades"), rs.getDouble("TopProfit"), rs.getDouble("StopLoss"), rs.getInt("Id"), u, a, rs.getTimestamp("DataVenda").toLocalDateTime());
                portfolioList.add(cfd);
                System.out.println(rs.getString("AtivoFinanceiro_Nome"));
            }
            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}

        return portfolioList;
    }

    public List<CFD> getPortfolio(Utilizador u) {
        DBConnection SQLConn = new SQLConnection();
        List<CFD> portfolioList = new ArrayList<>();
        CFD cfd;
        AtivoFincanceiroDAOConcrete afDAO = new AtivoFincanceiroDAOConcrete();
        AtivoFinanceiro a;
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("select * from CFD inner join Utilizador on Utilizador.Nome = CFD.Utilizador_Nome where Utilizador.Nome='"+u.getUsername()+"' and Id not in (select Id from CFDVendido);");
            while (rs.next()) {
                a = afDAO.get(rs.getString("AtivoFinanceiro_Nome"));
                cfd = new CFD(rs.getDouble("Unidades"), rs.getDouble("TopProfit"), rs.getDouble("StopLoss"), rs.getInt("Id"), u, a,rs.getTimestamp("DataCompra").toLocalDateTime());
                portfolioList.add(cfd);
                System.out.println(rs.getString("AtivoFinanceiro_Nome"));
            }
            SQLConn.disconnect();
        }
        catch (SQLException e){e.printStackTrace();}

        return portfolioList;
    }

    @Override
    public void delete(Integer id) {
        DBConnection SQLConn = new SQLConnection();
        try{
            SQLConn.connect();
            Connection conn = SQLConn.getConn();
            Statement stmt = conn.createStatement();

            stmt.executeUpdate("Insert into CFDVendido (Id) values(" + id + ")");

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
        Utilizador u = new Utilizador("Fábio","111",0.0);
        AtivoFinanceiro af = new AtivoFinanceiro("Petroleo",11,"Petroleo") {};
        CFD cfd = new CFD(15,0.0,0.0,1,u,af) ;
        CFD cfd2 = new CFD(50,0.0,0.0,5,u,af) ;

        //cfd = cfdDAO.get(1);
        //System.out.println(cfd.getBoughtValue());

        //cfdDAO.put(cfd2);
        //cfdDAO.get(u);
        //cfdDAO.delete(1);
        //cfdDAO.sell(1);
        //cfdDAO.getPortfolio(u);
    }
}