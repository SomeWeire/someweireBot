package com.bot.someweire.model;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class Portail {

  @Id
  @GeneratedValue
  private Long id;

  @Relationship(type = "IN_LIEU", direction = Direction.OUTGOING)
  private Set<Lieu> lieux = new HashSet<>();

  public Portail () {
    this.id= null;
  }

  public Set<Lieu> getLieux () {
    return lieux;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public void setLieux (Set<Lieu> lieux) {
    this.lieux = lieux;
  }

  @Override
  public String toString () {
    return "Portail{" +
        "lieux=" + lieux +
        '}';
  }
}
