package Week17.day05;

import Week17.day02.MoviesRespository;

import java.util.List;

public class MovieRatingsService {

    private MoviesRespository moviesRespository;
    private RatingsRepository ratingsRepository;

    public MovieRatingsService(MoviesRespository moviesRespository,
                               RatingsRepository ratingsRepository) {
        this.moviesRespository = moviesRespository;
        this.ratingsRepository = ratingsRepository;
    }

    public void addRatingsByTitle(String title, Integer... ratings){
        addRatingsByTitle(title, ratings);
    }

    public List<Integer> getRatingsByTitle(String title){
        return getRatingsByTitle(title);
    }
}
