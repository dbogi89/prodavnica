package com.codex.demo.domain;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;

/**
 * A RacunStavke.
 */
@Entity
@Table(name = "racun_stavke")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class RacunStavke implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "redni_broj_stavke", nullable = false)
    private Integer redniBrojStavke;

    @Column(name = "kolicina")
    private Integer kolicina;

    @ManyToOne
    @JsonIgnoreProperties("racunStavkes")
    private Artikal artikal;

    @ManyToOne
    @JsonIgnoreProperties("racunStavkes")
    private Racun racun;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getRedniBrojStavke() {
        return redniBrojStavke;
    }

    public RacunStavke redniBrojStavke(Integer redniBrojStavke) {
        this.redniBrojStavke = redniBrojStavke;
        return this;
    }

    public void setRedniBrojStavke(Integer redniBrojStavke) {
        this.redniBrojStavke = redniBrojStavke;
    }

    public Integer getKolicina() {
        return kolicina;
    }

    public RacunStavke kolicina(Integer kolicina) {
        this.kolicina = kolicina;
        return this;
    }

    public void setKolicina(Integer kolicina) {
        this.kolicina = kolicina;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public RacunStavke artikal(Artikal artikal) {
        this.artikal = artikal;
        return this;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Racun getRacun() {
        return racun;
    }

    public RacunStavke racun(Racun racun) {
        this.racun = racun;
        return this;
    }

    public void setRacun(Racun racun) {
        this.racun = racun;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RacunStavke)) {
            return false;
        }
        return id != null && id.equals(((RacunStavke) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "RacunStavke{" +
            "id=" + getId() +
            ", redniBrojStavke=" + getRedniBrojStavke() +
            ", kolicina=" + getKolicina() +
            "}";
    }
}
