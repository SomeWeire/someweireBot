package com.bot.someweire.model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class World {

  @Id
  @GeneratedValue
  private  Long id;
  private  String name;
  private  String description;


  @Relationship(type = "IN_WORLD", direction = Direction.INCOMING)
  private Map<Region, InWorldProperties> regions = new HashMap<>();


  public World (String name, String description) {
    this.id = null;
    this.name = name;
    this.description = description;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public void setName (String name) {
    this.name = name;
  }

  public void setDescription (String description) {
    this.description = description;
  }

  public void setRegions (Map<Region, InWorldProperties> regions) {
    this.regions = regions;
  }

  public String getName () {
    return name;
  }

  public String getDescription () {
    return description;
  }

  public Map<Region, InWorldProperties> getRegions () {
    return regions;
  }

  @Override
  public String toString () {
    return "World{" +
        "name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", regions=" + regions +
        '}';
  }
}
