package Week17.day04;

import Week17.day01.ActorsRepository;
import Week17.day02.MoviesRespository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class ActorsMoviesService {

    private ActorsRepository actorsRepository;
    private MoviesRespository moviesRespository;
    private ActorsMoviesRepository actorsMoviesRepository;

    public ActorsMoviesService(ActorsRepository actorsRepository, MoviesRespository moviesRespository, ActorsMoviesRepository actorsMoviesRepository) {
        this.actorsRepository = actorsRepository;
        this.moviesRespository = moviesRespository;
        this.actorsMoviesRepository = actorsMoviesRepository;
    }

    public void insertMovieWithActors(String title, LocalDate releaseDate, List<String> actorsNames) {
        long movieId = moviesRespository.saveMovie(title, releaseDate);
        for (String item : actorsNames) {
            long actorId = actorsRepository.saveActor(actorsNames.toString());
// VALIDATE!!!
            Optional<Actor> found = actorsRepository.findActorByName(item);

            if (found.isPresent()) {
                actorId = found.get().getId();
            } else {
                actorId = actorsRepository.saveActor(item);
            }
            actorsMoviesRepository.insertActorAndMovieId(actorId, movieId);
        }
    }

}
