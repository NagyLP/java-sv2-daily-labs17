package Week17.day05;

import Week17.day02.MoviesRepository;

import java.util.List;

public class MovieRatingsService {

    private MoviesRepository moviesRepository;
    private RatingsRepository ratingsRepository;

    public MovieRatingsService(MoviesRepository moviesRepository,
                               RatingsRepository ratingsRepository) {
        this.moviesRepository = moviesRepository;
        this.ratingsRepository = ratingsRepository;
    }

    public void addRatingsByTitle(String title, Integer... ratings) {
        addRatingsByTitle(title, ratings);
    }

    public double getAverageRatingById(long movieId) {

        return ;
    }

    public List<Integer> getRatingsById(long movieId) {
        return ratingsRepository.fetchRatingsByMovieId(movieId);
    }

    public List<Integer> getRatingsByTitle(String title) {
        return getRatingsByTitle(title);
    }
}
