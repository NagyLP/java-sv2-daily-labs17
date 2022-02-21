package Week17.day01;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mariadb.jdbc.MariaDbDataSource;

import java.sql.SQLException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ActorsRepositoryTest {

    ActorsRepository actorsRepository;
    MariaDbDataSource dataSource = new MariaDbDataSource();

    @BeforeEach
    void init() throws SQLException {
        dataSource.setUrl("jdbc:mariadb://localhost:3306/movies-actors?useUnicode=true");
        dataSource.setUser("***");
        dataSource.setPassword("***");
        Flyway flyway = Flyway.configure().dataSource(dataSource).load();

        // Db kiürítése és az adattartalom újra létrehozása
        flyway.clean();
        flyway.migrate();

    }

    @Test
    void testInsertThanQuery() throws SQLException {
        ActorsRepository actorsRepository = new ActorsRepository(dataSource);
        actorsRepository.saveActor("Kállai Ferenc");
//        ResultSet resultSet = dataSource.getConnection().createStatement().executeQuery("SELECT * FROM actors");
        assertEquals(Arrays.asList("Kállai Ferenc"), actorsRepository.findActorsWithPrefix("Káll"));
    }

}