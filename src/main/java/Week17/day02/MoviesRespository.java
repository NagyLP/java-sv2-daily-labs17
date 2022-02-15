package Week17.day02;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class MoviesRespository {

    private DataSource dataSource;

    public MoviesRespository(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public void saveActor(String title, LocalDate relasedate) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement stmt = connection.prepareStatement("insert into movies(title, relase_date) values(?,?)")) {
            stmt.setString(1, title);
            stmt.setDate(2, Date.valueOf(relasedate));
            stmt.executeUpdate();
        } catch (SQLException sqle) {
            throw new IllegalStateException("Connect ERROR...", sqle);
        }
    }

    public List<Movie> findAllMovies(String movie) {
        List<Movie> movies = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
// ELEGENDŐ a Statement, nem kell PrepareState..., mert nem paraméterezett)
             Statement stmt = connection.prepareStatement("SELECT * FROM movies")) {
            ResultSet rs = stmt.executeQuery()){
            }
        }
        return processResultSet();
    }

    private List<Movie> processResultSet(ResultSet rs) throws Exception {
        List<Movie> movies = new ArrayList<>();
        while (rs.next()) {
            long id = rs.getLong("id");
            String title = rs.getString("title");
            LocalDate relaseDate = rs.getDate("release_date").toLocalDate();
            movies.add(new Movie(id, title, relaseDate));
        }

    }

}
