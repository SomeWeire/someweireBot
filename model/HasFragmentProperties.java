package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

@RelationshipProperties
public class HasFragmentProperties {

  @Id
  @GeneratedValue
  Long id;
  int left;

  public HasFragmentProperties (int left) {
    this.id = null;
    this.left = left;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public int getLeft () {
    return left;
  }

  public void setLeft (int left) {
    this.left = left;
  }
}
