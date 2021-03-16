package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

import java.util.HashSet;
import java.util.Set;

@Node
public class Indice {

  @Id
  @GeneratedValue
  private Long id;
  private int numero;
  private String titre;

  @Relationship(type = "TO_ENIGME", direction = Direction.OUTGOING)
  private Set<Enigme> enigmes = new HashSet<>();

  public Indice (int numero, String titre) {
    this.id = null;
    this.numero = numero;
    this.titre = titre;
  }

  public Long getId () {
    return id;
  }

  public int getNumero () {
    return numero;
  }

  public String getTitre () {
    return titre;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public void setNumero (int numero) {
    this.numero = numero;
  }

  public void setTitre (String titre) {
    this.titre = titre;
  }

  public Set<Enigme> getEnigmes() {
    return enigmes;
  }

  public void setEnigmes(Set<Enigme> enigmes) {
    this.enigmes = enigmes;
  }

  @Override
  public String toString() {
    return "Indice{" +
            "id=" + id +
            ", numero=" + numero +
            ", titre='" + titre + '\'' +
            ", enigmes=" + enigmes +
            '}';
  }
}
