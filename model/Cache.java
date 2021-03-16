package com.bot.someweire.model;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class Cache {

  @Id
  @GeneratedValue
  private Long id;

  @Relationship(type = "IN_CACHE", direction = Direction.INCOMING)
  private Set<Enigme> enigme = new HashSet<Enigme>();

  @Relationship(type = "IN_CACHE", direction = Direction.INCOMING)
  private Set<Fragment> fragments = new HashSet<Fragment>();

  @Relationship(type = "IN_LIEU", direction = Direction.OUTGOING)
  private Set<Lieu> lieu = new HashSet<Lieu>();


  public Cache () {
    this.id = null;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public Set<Enigme> getEnigme () {
    return enigme;
  }

  public void setEnigme (Set<Enigme> enigme) {
    this.enigme = enigme;
  }

  public Set<Fragment> getFragments () {
    return fragments;
  }

  public void setFragments (Set<Fragment> fragments) {
    this.fragments = fragments;
  }

  public Set<Lieu> getLieu () {
    return lieu;
  }

  public void setLieu (Set<Lieu> lieu) {
    this.lieu = lieu;
  }

  @Override
  public String toString () {
    return "Cache{" +
        "id=" + id +
        ", enigme=" + enigme +
        ", fragments=" + fragments +
        ", lieu=" + lieu +
        '}';
  }
}
