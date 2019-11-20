package data;

import java.sql.*;

public class SQLConnection implements DBConnection{

	private Connection conn;

	public Connection getConn() {
		return conn;
	}

	public SQLConnection(){
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			this.conn = null;
		}
		catch (ClassNotFoundException e){}
	}

	public void connect() {
		try{
			this.conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/tradingplatform?useTimezone=true&serverTimezone=UTC","root","1234");
		}
		catch (SQLException e){
			e.printStackTrace(); System.out.println("Não conseguiu conectar!");}
	}

	public void disconnect() {
		try{
			this.conn.close();
		}
		catch(SQLException e){e.printStackTrace();}
	}

	//Main de teste de conexão
	public static void main(String args[]){
		SQLConnection SQLConn = new SQLConnection();


		try{
			SQLConn.connect();
			Statement stmt = SQLConn.conn.createStatement();
			ResultSet rs=stmt.executeQuery("select * from AtivoFinanceiro");
			while(rs.next())
				System.out.println(rs.getString("Nome")+"  "+rs.getInt("ValorUnit"));

			SQLConn.disconnect();
		}
		catch(Exception e){ System.out.println(e);}
	}

}