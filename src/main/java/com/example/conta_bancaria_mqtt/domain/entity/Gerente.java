package com.example.conta_bancaria_mqtt.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = "gerentes")
public class Gerente extends Usuario {

}