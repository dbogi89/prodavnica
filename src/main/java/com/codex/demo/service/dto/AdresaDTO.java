package com.codex.demo.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.codex.demo.domain.Adresa} entity.
 */
public class AdresaDTO implements Serializable {

    private Long id;

    private String ulica;

    private Long ptt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUlica() {
        return ulica;
    }

    public void setUlica(String ulica) {
        this.ulica = ulica;
    }

    public Long getPtt() {
        return ptt;
    }

    public void setPtt(Long ptt) {
        this.ptt = ptt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AdresaDTO adresaDTO = (AdresaDTO) o;
        if (adresaDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), adresaDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AdresaDTO{" +
            "id=" + getId() +
            ", ulica='" + getUlica() + "'" +
            ", ptt=" + getPtt() +
            "}";
    }
}
