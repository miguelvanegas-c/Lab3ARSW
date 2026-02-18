package edu.eci.arsw.blueprints.mapper;

import edu.eci.arsw.blueprints.model.dto.PointDTO;
import edu.eci.arsw.blueprints.model.entity.Point;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PointMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "blueprint", ignore = true)
    Point toEntity(PointDTO dto);

    PointDTO toDto(Point entity);
}
