package com.codex.demo.repository;

import com.codex.demo.domain.Artikal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Artikal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ArtikalRepository extends JpaRepository<Artikal, Long> {

}
