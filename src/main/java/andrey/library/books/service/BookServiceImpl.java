package andrey.library.books.service;

import andrey.library.books.dto.BookDto;
import andrey.library.books.exception.BookAlreadyExistsException;
import andrey.library.books.exception.BookNotFoundException;
import andrey.library.books.mapper.MapStructBookMapper;
import andrey.library.books.model.Author;
import andrey.library.books.model.Book;
import andrey.library.books.repository.AuthorRepository;
import andrey.library.books.repository.BooksRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {

    BooksRepository booksRepository;
    MapStructBookMapper bookMapper;
    AuthorRepository authorRepository;

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        throwExceptionIfBookExists(bookDto.getTitle());
        Book bookToSave = bookMapper.toBook(bookDto);
        Set<Author> existingAuthors = bookToSave.getAuthors().stream()
                .map(author ->
                        authorRepository.findByFirstNameAndLastName(
                                author.getFirstName(),
                                author.getLastName()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());
        existingAuthors.addAll(bookToSave.getAuthors());
        bookToSave.setAuthors(existingAuthors);

        return Optional.ofNullable(bookMapper.fromBook(booksRepository.save(bookToSave)))
                        .orElseThrow(() -> new RuntimeException("Couldn't save book."));
    }

    @Override
    public BookDto findByTitle(String title) {
        return bookMapper.fromBook(booksRepository.findByTitle(title)
                        .orElseThrow(() -> new BookNotFoundException(
                                String.format("Book with title %s not found", title))));
    }

    @Override
    @Transactional
    public void deleteByTitle(String title) {
        throwExceptionIfBookExists(title);
        booksRepository.deleteByTitle(title);
    }

    private void throwExceptionIfBookExists(String title) {
        booksRepository.findByTitle(title).ifPresent(book -> {
            throw new BookAlreadyExistsException(
                    String.format("Book title already exists. Title: %s", title));
        });
    }

}
