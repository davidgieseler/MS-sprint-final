package br.com.pb.msorder.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "adresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cep;

    @Column(name = "street")
    @JsonProperty("logradouro")
    private String street;

    @Column(name = "number")
    @JsonProperty("numero")
    private String number;

    @Column(name = "district")
    @JsonProperty("bairro")
    private String district;

    @Column(name = "location")
    @JsonProperty("localidade")
    private String location;

    private String uf;
}
