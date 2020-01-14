package data;

import java.sql.SQLException;

public interface DAOFunction<T1, T2> {
    T2 apply(T1 obj) throws SQLException;
}
