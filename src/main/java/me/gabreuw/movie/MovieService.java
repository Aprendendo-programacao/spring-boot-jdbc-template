package me.gabreuw.movie;

import lombok.RequiredArgsConstructor;
import me.gabreuw.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieDao movieDao;

    public List<Movie> getMovies() {
        return movieDao.selectMovies();
    }

    public void addNewMovie(Movie movie) {
        movieDao
                .selectMovieById(movie.id())
                .ifPresent(existingMovie -> {
                    throw new IllegalArgumentException(String.format(
                            "Movie with ID %s already exists",
                            existingMovie.id()
                    ));
                });

        int result = movieDao.insertMovie(movie);

        if (result != 1) {
            throw new IllegalStateException("oops something went wrong");
        }
    }

    public void deleteMovie(Integer id) {
        Optional<Movie> movies = movieDao.selectMovieById(id);

        movies.ifPresentOrElse(movie -> {
                    int result = movieDao.deleteMovie(id);

                    if (result != 1) {
                        throw new IllegalStateException("oops could not delete movie");
                    }
                },
                () -> {
                    throw new NotFoundException("Movie with id %s not found", id);
                }
        );
    }

    public Movie getMovie(int id) {
        return movieDao
                .selectMovieById(id)
                .orElseThrow(() -> new NotFoundException("Movie with id %s not found", id));
    }
}
