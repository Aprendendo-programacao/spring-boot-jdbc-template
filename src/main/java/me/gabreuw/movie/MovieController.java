package me.gabreuw.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping(path = "/api/v1/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping
    public ResponseEntity<List<Movie>> listMovies() {
        return ResponseEntity
                .ok()
                .body(movieService.getMovies());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieId(@PathVariable Integer id) {
        return ResponseEntity
                .ok()
                .body(movieService.getMovie(id));
    }

    @PostMapping
    public ResponseEntity<Void> addMovie(@RequestBody Movie movie) {
        movieService.addNewMovie(movie);

        return ResponseEntity
                .status(CREATED)
                .build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable Integer id) {
        movieService.deleteMovie(id);

        return ResponseEntity
                .noContent()
                .build();
    }

    // TODO: Update movie

}
