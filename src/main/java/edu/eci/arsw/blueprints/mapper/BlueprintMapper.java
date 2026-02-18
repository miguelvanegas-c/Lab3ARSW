package edu.eci.arsw.blueprints.mapper;

import edu.eci.arsw.blueprints.model.entity.Blueprint;
import edu.eci.arsw.blueprints.model.dto.BlueprintDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {PointMapper.class})
public interface BlueprintMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "points", ignore = true)
    Blueprint toEntity(BlueprintDTO dto);


    BlueprintDTO toDto(Blueprint entity);


}
