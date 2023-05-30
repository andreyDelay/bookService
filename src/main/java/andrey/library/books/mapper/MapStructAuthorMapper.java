package andrey.library.books.mapper;

import andrey.library.books.dto.AuthorDto;
import andrey.library.books.model.Author;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MapStructAuthorMapper {

    MapStructAuthorMapper MAPPER = Mappers.getMapper(MapStructAuthorMapper.class);

    Author toAuthor(AuthorDto authorDto);

    @InheritInverseConfiguration
    AuthorDto fromAuthor(Author author);
}
