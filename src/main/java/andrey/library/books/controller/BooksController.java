package andrey.library.books.controller;

import andrey.library.books.dto.BookDto;
import andrey.library.books.service.BookServiceImpl;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BooksController {

    BookServiceImpl bookService;
    final String bookTitleRegExpPattern = "(\\w+\\D+)+";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto addBook(@RequestBody BookDto bookDto) {
        return bookService.save(bookDto);
    }

    @GetMapping("/{bookTitle}")
    @ResponseStatus(HttpStatus.OK)
    public BookDto getBookByTitle(@Valid @Pattern(regexp = bookTitleRegExpPattern)
                                  @PathVariable String bookTitle) {
        return bookService.findByTitle(bookTitle);
    }

    @DeleteMapping("/{bookTitle}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBookByTitle(@Valid @Pattern(regexp = bookTitleRegExpPattern)
                                  @PathVariable String bookTitle) {
        bookService.deleteByTitle(bookTitle);
    }
}
