package com.codex.demo.service.mapper;

import com.codex.demo.domain.*;
import com.codex.demo.service.dto.AdresaDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Adresa} and its DTO {@link AdresaDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AdresaMapper extends EntityMapper<AdresaDTO, Adresa> {



    default Adresa fromId(Long id) {
        if (id == null) {
            return null;
        }
        Adresa adresa = new Adresa();
        adresa.setId(id);
        return adresa;
    }
}
