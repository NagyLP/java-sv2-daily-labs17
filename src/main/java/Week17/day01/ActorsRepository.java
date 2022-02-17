package Week17.day01;

import org.mariadb.jdbc.Statement;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ActorsRepository {

    private final DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public long saveActorWithIdShow(String name) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt =
                     connection.prepareStatement("insert into actors(actor_name) values(?)",
                     Statement.RETURN_GENERATED_KEYS)
        ) {
            stmt.setString(1, name);
            stmt.executeUpdate();
            return executeAndGetGeneratedKey(stmt);
        } catch (SQLException sqle) {
            throw new IllegalStateException("Update ERROR: " + name, sqle);
        }
    }

    private long executeAndGetGeneratedKey(PreparedStatement stmt) {
        try (
                ResultSet rs = stmt.getGeneratedKeys()
        ) {
            if (rs.next()) {
                return rs.getLong(1);
            } else {
                throw new SQLException("No key has generated");
            }
        } catch (SQLException sqle) {
            throw new IllegalArgumentException("Update ERROR", sqle);
        }
    }

    public List<String> findActorsWithPrefix(String prefix) {
        List<String> result = new ArrayList<>();
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("SELECT actor_name FROM actors WHERE actor_name LIKE ?")) {
            stmt.setString(1, prefix + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String actorName = rs.getString("actor_name");
                    result.add(actorName);
                }
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Update ERROR: ", sqle);
        }
        return result;
    }
}
