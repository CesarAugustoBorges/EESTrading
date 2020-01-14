package data;

import java.sql.SQLException;

public interface DAOConsumer<T> {
    void accept(T obj) throws SQLException;
}
