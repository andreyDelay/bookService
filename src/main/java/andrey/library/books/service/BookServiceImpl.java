package andrey.library.books.service;

import andrey.library.books.dto.BookDto;
import andrey.library.books.exception.BookNotFoundException;
import andrey.library.books.model.Book;
import andrey.library.books.repository.BooksRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static andrey.library.books.mapper.MapStructBookMapper.MAPPER;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class BookServiceImpl implements BookService {

    BooksRepository booksRepository;

    @Override
    @Transactional
    public BookDto save(BookDto bookDto) {
        Book bookToSave = booksRepository.save(MAPPER.toBook(bookDto));
        return Optional.ofNullable(MAPPER.fromBook(bookToSave))
                        .orElseThrow(() -> new RuntimeException("Couldn't save book."));
    }

    @Override
    public BookDto findByTitle(String title) {
        return MAPPER.fromBook(booksRepository.findByTitle(title)
                        .orElseThrow(() -> new BookNotFoundException(
                                String.format("Book with title %s not found", title))));
    }

    @Override
    @Transactional
    //Без @Transactional падало с ошибкой No EntityManager with actual transaction available for current thread -
    //cannot reliably process 'remove' call
    public void deleteByTitle(String title) {
        booksRepository.deleteByTitle(title);
    }

}