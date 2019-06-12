package com.codex.demo.repository;

import com.codex.demo.domain.Adresa;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Adresa entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AdresaRepository extends JpaRepository<Adresa, Long> {

}
