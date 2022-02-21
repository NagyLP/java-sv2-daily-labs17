package Week17.day01;

import Week17.day04.Actor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ActorsRepository {

    private JdbcTemplate jdbcTemp;
    private DataSource dataSource;

    public ActorsRepository(DataSource dataSource) {
        jdbcTemp = new JdbcTemplate(dataSource);
    }

    public long saveActor(String name) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemp.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedSatement(Connection conn)
                    throws SQLException {
                PreparedStatement ps = conn.prepareStatement(
                        "INSERT INTO" +
                            " actors(actor_name)" +
                            " VALUES(?)",
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, name);
                return ps;
            }
        }, keyHolder
        );
        return keyHolder.getKey().longValue();
    }

//    public long saveActor(String name) {
//        try (Connection connection = dataSource.getConnection();
//             PreparedStatement stmt =
//                     connection.prepareStatement("insert into actors(actor_name) values(?)",
//                             Statement.RETURN_GENERATED_KEYS)
//        ) {
//            stmt.setString(1, name);
//            stmt.executeUpdate();
//            return executeAndGetGeneratedKey(stmt);
//
//        } catch (SQLException sqle) {
//            throw new IllegalStateException("Update ERROR: " + name, sqle);
//        }
//    }

    public Optional<Actor> findActorByName(String name) {
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM actors WHERE actor_name=?")
        ) {
            stmt.setString(1, name);
            return processSelectStatement(stmt);

        } catch (SQLException sqle) {
            throw new IllegalStateException("ERROR name");
        }
    }

    private Optional<Actor> processSelectStatement(PreparedStatement statement) throws SQLException {
        try (ResultSet rs = statement.executeQuery()) {
            if (rs.next()) {
                return Optional.of(new Actor(rs.getLong("id"), rs.getString("actor_name")));
            }
            return Optional.empty();
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
}
