package com.codex.demo.repository;

import com.codex.demo.domain.RacunStavke;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RacunStavke entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RacunStavkeRepository extends JpaRepository<RacunStavke, Long> {

}
