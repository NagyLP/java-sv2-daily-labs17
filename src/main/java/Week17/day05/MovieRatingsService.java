package Week17.day05;

import Week17.day02.MoviesRepository;
import Week17.services.SqlQuery;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;

public class MovieRatingsService {

    private MoviesRepository moviesRepository;
    private RatingsRepository ratingsRepository;
    private DataSource dataSource;

    public MovieRatingsService(MoviesRepository moviesRepository,
                               RatingsRepository ratingsRepository) {
        this.moviesRepository = moviesRepository;
        this.ratingsRepository = ratingsRepository;
    }

    public void addRatingsByTitle(String title, Integer... ratings) {
        addRatingsByTitle(title, ratings);
    }

    public double getAverageRatingById(long movieId) {
        try (SqlQuery query = new SqlQuery(dataSource.getConnection())) {
            query.setPreparedStatement(query.connection()
                    .prepareStatement("SELECT " +
                            "SUM(movie_id) AS 'Össz. film', AVG(rating) AS 'Átl. pontérték' FROM ratings"));
           query.result();
           return query;
        } catch (SQLException sqle) {
            throw new IllegalStateException("Not found rating", sqle);
        }
    }

    public List<Integer> getRatingsById(long movieId) {
        return ratingsRepository.fetchRatingsByMovieId(movieId);
    }

    public List<Integer> getRatingsByTitle(String title) {
        return getRatingsByTitle(title);
    }


}
