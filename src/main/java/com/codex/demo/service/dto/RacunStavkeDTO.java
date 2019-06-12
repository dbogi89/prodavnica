package com.codex.demo.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.codex.demo.domain.RacunStavke} entity.
 */
public class RacunStavkeDTO implements Serializable {

    private Long id;

    @NotNull
    private Integer redniBrojStavke;

    private Integer kolicina;


    private Long artikalId;

    private Long racunId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRedniBrojStavke() {
        return redniBrojStavke;
    }

    public void setRedniBrojStavke(Integer redniBrojStavke) {
        this.redniBrojStavke = redniBrojStavke;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Long getArtikalId() {
        return artikalId;
    }

    public void setArtikalId(Long artikalId) {
        this.artikalId = artikalId;
    }

    public Long getRacunId() {
        return racunId;
    }

    public void setRacunId(Long racunId) {
        this.racunId = racunId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RacunStavkeDTO racunStavkeDTO = (RacunStavkeDTO) o;
        if (racunStavkeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), racunStavkeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RacunStavkeDTO{" +
            "id=" + getId() +
            ", redniBrojStavke=" + getRedniBrojStavke() +
            ", kolicina=" + getKolicina() +
            ", artikal=" + getArtikalId() +
            ", racun=" + getRacunId() +
            "}";
    }
}
