package com.bot.someweire.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class Lieu {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String description;
  private String thumb;

  @Relationship(type = "LEADS_TO", direction = Direction.OUTGOING)
  private Set<Lieu> roads = new LinkedHashSet<>();

  @Relationship(type = "IN_REGION", direction = Direction.OUTGOING)
  private Map<Region, InRegionProperties> regions = new HashMap<>();

  @Relationship(type = "IN_LIEU", direction = Direction.INCOMING)
  private Set<Character> characters = new HashSet<>();

  @Relationship(type = "IN_LIEU", direction = Direction.INCOMING)
  private Set<Portail> portals = new HashSet<>();

  @Relationship(type = "IN_LIEU", direction = Direction.INCOMING)
  private Set<Cache> caches = new HashSet<>();


  public Lieu (String name, String description, String thumb) {
    this.id = null;
    this.name = name;
    this.description = description;
    this.thumb = thumb;
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

  public String getName () {
    return name;
  }

  public String getDescription () {
    return description;
  }

  public Set<Lieu> getRoads () {
    return roads;
  }

  public Set<Character> getPlayersIn () {
    return characters;
  }

  public Set<Portail> getPortals () {
    return portals;
  }

  public void setRoads (Set<Lieu> roads) {
    this.roads = roads;
  }

  public void setRegions (
      Map<Region, InRegionProperties> regions) {
    this.regions = regions;
  }

  public Set<Character> getCharacters () {
    return characters;
  }

  public void setCharacters (Set<Character> characters) {
    this.characters = characters;
  }

  public void setPortals (Set<Portail> portals) {
    this.portals = portals;
  }

  public Set<Cache> getCaches () {
    return caches;
  }

  public void setCaches (Set<Cache> caches) {
    this.caches = caches;
  }

  public Map<Region, InRegionProperties> getRegions () {
    return regions;
  }

  public String getThumb() {
    return thumb;
  }

  public void setThumb(String thumb) {
    this.thumb = thumb;
  }

  @Override
  public String toString() {
    return "Lieu{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", description='" + description + '\'' +
            ", thumb='" + thumb + '\'' +
            ", roads=" + roads +
            ", regions=" + regions +
            ", characters=" + characters +
            ", portals=" + portals +
            ", caches=" + caches +
            '}';
  }
}
