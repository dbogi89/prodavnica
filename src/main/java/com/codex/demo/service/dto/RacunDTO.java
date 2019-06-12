package com.codex.demo.service.dto;
import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.codex.demo.domain.Racun} entity.
 */
@ApiModel(description = "not an ignored comment")
public class RacunDTO implements Serializable {

    private Long id;

    private String brojRacuna;

    private LocalDate datum;


    private Long kupacId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrojRacuna() {
        return brojRacuna;
    }

    public void setBrojRacuna(String brojRacuna) {
        this.brojRacuna = brojRacuna;
    }

    public LocalDate getDatum() {
        return datum;
    }

    public void setDatum(LocalDate datum) {
        this.datum = datum;
    }

    public Long getKupacId() {
        return kupacId;
    }

    public void setKupacId(Long kupacId) {
        this.kupacId = kupacId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RacunDTO racunDTO = (RacunDTO) o;
        if (racunDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), racunDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RacunDTO{" +
            "id=" + getId() +
            ", brojRacuna='" + getBrojRacuna() + "'" +
            ", datum='" + getDatum() + "'" +
            ", kupac=" + getKupacId() +
            "}";
    }
}
