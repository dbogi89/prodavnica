package com.codex.demo.repository;

import com.codex.demo.domain.Racun;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Racun entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacunRepository extends JpaRepository<Racun, Long> {

}
