package com.bot.someweire.model;

import java.util.HashSet;
import java.util.Set;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class Fragment {

  @Id
  @GeneratedValue
  private Long id;

  private String type;

  private int quantite;

  public Fragment (String type, int quantite) {
    this.id = null;
    this.type = type;
    this.quantite = quantite;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public String getType () {
    return type;
  }

  public void setType (String type) {
    this.type = type;
  }

  public int getQuantite () {
    return quantite;
  }

  public void setQuantite (int quantite) {
    this.quantite = quantite;
  }

  @Override
  public String toString () {
    return "Fragment{" +
        "id=" + id +
        ", type='" + type + '\'' +
        ", quantite=" + quantite +
        '}';
  }
}
