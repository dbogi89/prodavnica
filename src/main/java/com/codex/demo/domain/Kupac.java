package com.codex.demo.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A Kupac.
 */
@Entity
@Table(name = "kupac")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Kupac implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "naziv_kupca")
    private String nazivKupca;

    @Column(name = "pib")
    private String pib;

    @OneToOne
    @JoinColumn(unique = true)
    private Adresa adresa;

    @OneToMany(mappedBy = "kupac")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Racun> racuns = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNazivKupca() {
        return nazivKupca;
    }

    public Kupac nazivKupca(String nazivKupca) {
        this.nazivKupca = nazivKupca;
        return this;
    }

    public void setNazivKupca(String nazivKupca) {
        this.nazivKupca = nazivKupca;
    }

    public String getPib() {
        return pib;
    }

    public Kupac pib(String pib) {
        this.pib = pib;
        return this;
    }

    public void setPib(String pib) {
        this.pib = pib;
    }

    public Adresa getAdresa() {
        return adresa;
    }

    public Kupac adresa(Adresa adresa) {
        this.adresa = adresa;
        return this;
    }

    public void setAdresa(Adresa adresa) {
        this.adresa = adresa;
    }

    public Set<Racun> getRacuns() {
        return racuns;
    }

    public Kupac racuns(Set<Racun> racuns) {
        this.racuns = racuns;
        return this;
    }

    public Kupac addRacun(Racun racun) {
        this.racuns.add(racun);
        racun.setKupac(this);
        return this;
    }

    public Kupac removeRacun(Racun racun) {
        this.racuns.remove(racun);
        racun.setKupac(null);
        return this;
    }

    public void setRacuns(Set<Racun> racuns) {
        this.racuns = racuns;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Kupac)) {
            return false;
        }
        return id != null && id.equals(((Kupac) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Kupac{" +
            "id=" + getId() +
            ", nazivKupca='" + getNazivKupca() + "'" +
            ", pib='" + getPib() + "'" +
            "}";
    }
}
