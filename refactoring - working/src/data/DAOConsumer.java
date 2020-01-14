package data;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface DAOConsumer {
    void accept(PreparedStatement preparedStatement) throws SQLException;
}
