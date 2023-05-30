package andrey.library.books.mapper;

import andrey.library.books.dto.BookDto;
import andrey.library.books.model.Book;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(uses = { MapStructAuthorMapper.class })
public interface MapStructBookMapper {

    MapStructBookMapper MAPPER = Mappers.getMapper(MapStructBookMapper.class);

    @Mapping(source = "authors", target = "authors") //Check if working without this
    @Mapping(source = "quantityInStock", target = "quantityInStock") //Check if working without this
    @Mapping(source = "title", target = "title") //Check if working without this
    public Book toBook(BookDto bookDto);

    @InheritInverseConfiguration
    BookDto fromBook(Book book);
}
