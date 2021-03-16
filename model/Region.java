package com.bot.someweire.model;

import java.util.HashMap;
import java.util.Map;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class Region {

  @Id
  @GeneratedValue
  private  Long id;
  private  String name;
  private  String description;

  @Relationship(type = "IN_WORLD", direction = Direction.OUTGOING)
  private Map<World, InWorldProperties> worlds = new HashMap<>();

  @Relationship(type = "IN_REGION", direction = Direction.INCOMING)
  private Map<Lieu, InRegionProperties> lieux = new HashMap<>();

  public Region (String name, String description) {
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

  public String getName () {
    return name;
  }

  public void setName (String name) {
    this.name = name;
  }

  public String getDescription () {
    return description;
  }

  public void setDescription (String description) {
    this.description = description;
  }

  public Map<World, InWorldProperties> getWorlds () {
    return worlds;
  }

  public void setWorlds (Map<World, InWorldProperties> worlds) {
    this.worlds = worlds;
  }

  public Map<Lieu, InRegionProperties> getLieux () {
    return lieux;
  }

  public void setLieux (Map<Lieu, InRegionProperties> lieux) {
    this.lieux = lieux;
  }

  @Override
  public String toString () {
    return "Region{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", description='" + description + '\'' +
        ", worlds=" + worlds +
        ", lieux=" + lieux +
        '}';
  }
}
