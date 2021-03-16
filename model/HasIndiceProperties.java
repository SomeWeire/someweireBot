package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

@RelationshipProperties
public class HasIndiceProperties {

  @Id
  @GeneratedValue
  Long id;
  boolean powered;

  public HasIndiceProperties (boolean powered) {
    this.id = null;
    this.powered = powered;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public boolean isPowered () {
    return powered;
  }

  public void setPowered (boolean powered) {
    this.powered = powered;
  }

  @Override
  public String toString () {
    return "HasIndiceProperties{" +
        "id=" + id +
        ", powered=" + powered +
        '}';
  }
}
