package com.codex.demo.service.mapper;

import com.codex.demo.domain.*;
import com.codex.demo.service.dto.RacunStavkeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link RacunStavke} and its DTO {@link RacunStavkeDTO}.
 */
@Mapper(componentModel = "spring", uses = {ArtikalMapper.class, RacunMapper.class})
public interface RacunStavkeMapper extends EntityMapper<RacunStavkeDTO, RacunStavke> {

    @Mapping(source = "artikal.id", target = "artikalId")
    @Mapping(source = "racun.id", target = "racunId")
    RacunStavkeDTO toDto(RacunStavke racunStavke);

    @Mapping(source = "artikalId", target = "artikal")
    @Mapping(source = "racunId", target = "racun")
    RacunStavke toEntity(RacunStavkeDTO racunStavkeDTO);

    default RacunStavke fromId(Long id) {
        if (id == null) {
            return null;
        }
        RacunStavke racunStavke = new RacunStavke();
        racunStavke.setId(id);
        return racunStavke;
    }
}
