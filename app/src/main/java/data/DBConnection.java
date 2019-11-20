package data;

import java.sql.Connection;

public interface DBConnection {

	void connect();

	void disconnect();

	Connection getConn();

}