package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

@RelationshipProperties
public class InWorldProperties {

  @Id
  @GeneratedValue
  private Long id;
  private String location;

  public InWorldProperties (String location) {
    this.id=null;
    this.location = location;
  }

  public Long getId () {
    return id;
  }

  public String getLocationInWorld () {
    return location;
  }

  public void setLocationInWorld (String location) {
    this.location = location;
  }

  public void setId (Long id) {
    this.id = id;
  }
}
