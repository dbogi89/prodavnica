package com.codex.demo.service.mapper;

import com.codex.demo.domain.*;
import com.codex.demo.service.dto.RacunDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Racun} and its DTO {@link RacunDTO}.
 */
@Mapper(componentModel = "spring", uses = {KupacMapper.class})
public interface RacunMapper extends EntityMapper<RacunDTO, Racun> {

    @Mapping(source = "kupac.id", target = "kupacId")
    RacunDTO toDto(Racun racun);

    @Mapping(source = "kupacId", target = "kupac")
    @Mapping(target = "racunStavkes", ignore = true)
    @Mapping(target = "removeRacunStavke", ignore = true)
    Racun toEntity(RacunDTO racunDTO);

    default Racun fromId(Long id) {
        if (id == null) {
            return null;
        }
        Racun racun = new Racun();
        racun.setId(id);
        return racun;
    }
}
