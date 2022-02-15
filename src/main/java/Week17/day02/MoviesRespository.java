package Week17.day02;

import javax.sql.DataSource;
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
             PreparedStatement stmt = connection.prepareStatement("SELECT movie_name FROM movies WHERE movie_name LIKE ?")) {
            stmt.setString(1, movie);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Movie movieName = rs.getName("movie_name");
                    movies.add(movieName);
                }
            }

        } catch (SQLException sqle) {
            throw new IllegalStateException("Update ERROR: ", sqle);
        }
        return movies;
    }
}
