package andrey.library.books.service;

import andrey.library.books.dto.BookDto;
import andrey.library.books.exception.BookNotFoundException;
import andrey.library.books.mapper.MapStructBookMapper;
import andrey.library.books.model.Book;
import andrey.library.books.repository.BooksRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {

    BooksRepository booksRepository;
    MapStructBookMapper bookMapper;

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        Book bookToSave = booksRepository.save(bookMapper.toBook(bookDto));
        return Optional.ofNullable(bookMapper.fromBook(bookToSave))
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
        booksRepository.deleteByTitle(title);
    }

}
