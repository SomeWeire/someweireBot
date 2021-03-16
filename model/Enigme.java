package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

import java.util.HashSet;
import java.util.Set;

@Node
public class Enigme {

  @Id
  @GeneratedValue
  private Long id;
  private int numero;
  private String titre;
  private int prix;
  private int monde;

  @Relationship(type = "IN_CACHE", direction = Direction.OUTGOING)
  private Set<Cache> cache = new HashSet<Cache>();

  @Relationship(type = "TO_ENIGME", direction = Direction.INCOMING)
  private Set<Indice> indices = new HashSet<>();

  public Enigme (int numero, String titre, int prix, int monde) {
    this.id = null;
    this.numero = numero;
    this.titre = titre;
    this.prix = prix;
    this.monde = monde;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public int getNumero () {
    return numero;
  }

  public void setNumero (int numero) {
    this.numero = numero;
  }

  public String getTitre () {
    return titre;
  }

  public void setTitre (String titre) {
    this.titre = titre;
  }

  public int getPrix () {
    return prix;
  }

  public void setPrix (int prix) {
    this.prix = prix;
  }

  public Set<Cache> getCache () {
    return cache;
  }

  public void setCache (Set<Cache> cache) {
    this.cache = cache;
  }

  public Set<Indice> getIndices() {
    return indices;
  }

  public void setIndices(Set<Indice> indices) {
    this.indices = indices;
  }

  public int getMonde() {
    return monde;
  }

  public void setMonde(int monde) {
    this.monde = monde;
  }

  @Override
  public String toString() {
    return "Enigme{" +
            "id=" + id +
            ", numero=" + numero +
            ", titre='" + titre + '\'' +
            ", prix=" + prix +
            ", monde=" + monde +
            ", cache=" + cache +
            ", indices=" + indices +
            '}';
  }
}
