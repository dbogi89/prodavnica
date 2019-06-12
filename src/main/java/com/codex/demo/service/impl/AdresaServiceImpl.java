package com.codex.demo.service.impl;

import com.codex.demo.service.AdresaService;
import com.codex.demo.domain.Adresa;
import com.codex.demo.repository.AdresaRepository;
import com.codex.demo.service.dto.AdresaDTO;
import com.codex.demo.service.mapper.AdresaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Service Implementation for managing {@link Adresa}.
 */
@Service
@Transactional
public class AdresaServiceImpl implements AdresaService {

    private final Logger log = LoggerFactory.getLogger(AdresaServiceImpl.class);

    private final AdresaRepository adresaRepository;

    private final AdresaMapper adresaMapper;

    public AdresaServiceImpl(AdresaRepository adresaRepository, AdresaMapper adresaMapper) {
        this.adresaRepository = adresaRepository;
        this.adresaMapper = adresaMapper;
    }

    /**
     * Save a adresa.
     *
     * @param adresaDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AdresaDTO save(AdresaDTO adresaDTO) {
        log.debug("Request to save Adresa : {}", adresaDTO);
        Adresa adresa = adresaMapper.toEntity(adresaDTO);
        adresa = adresaRepository.save(adresa);
        return adresaMapper.toDto(adresa);
    }

    /**
     * Get all the adresas.
     *
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public List<AdresaDTO> findAll() {
        log.debug("Request to get all Adresas");
        return adresaRepository.findAll().stream()
            .map(adresaMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }


    /**
     * Get one adresa by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AdresaDTO> findOne(Long id) {
        log.debug("Request to get Adresa : {}", id);
        return adresaRepository.findById(id)
            .map(adresaMapper::toDto);
    }

    /**
     * Delete the adresa by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Adresa : {}", id);
        adresaRepository.deleteById(id);
    }
}
