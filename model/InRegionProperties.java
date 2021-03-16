package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

@RelationshipProperties
public class InRegionProperties {

  @Id
  @GeneratedValue
  private Long id;
  private String location;
  private int distance;

  public InRegionProperties (String location, int distance) {
    this.id=null;
    this.location = location;
    this.distance = distance;
  }

  public Long getId () {
    return id;
  }

  public String getLocation () {
    return location;
  }

  public int getDistance () {
    return distance;
  }

  public void setLocation (String location) {
    this.location = location;
  }

  public void setDistance (int distance) {
    this.distance = distance;
  }

  public void setId (Long id) {
    this.id = id;
  }
}
