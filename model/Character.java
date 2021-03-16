package com.bot.someweire.model;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.Node;
import org.neo4j.springframework.data.core.schema.Relationship;
import org.neo4j.springframework.data.core.schema.Relationship.Direction;

@Node
public class Character {

  @Id
  @GeneratedValue
  private Long id;
  private String name;
  private String classType;
  private String userId;
  private String gender;
  private int niveau;
  private int powers;
  private boolean forfeitPower;

  public enum classTypeEnum{
    SOLDIER,
    KNIGHT,
    HERO
  }

  public enum genderEnum{
    MALE,
    FEMALE
  }

  @Relationship(type = "IN_LIEU", direction = Direction.OUTGOING)
  private Set<Lieu> position = new HashSet<>();

  @Relationship(type = "HAS", direction = Direction.OUTGOING)
  private Map<Fragment, HasFragmentProperties> fragments = new LinkedHashMap<>();

  @Relationship(type = "HAS", direction = Direction.OUTGOING)
  private Map<Enigme, HasEnigmeProperties> enigmes = new LinkedHashMap<>();

  @Relationship(type = "HAS", direction = Direction.OUTGOING)
  private Map<Indice, HasIndiceProperties>  indices = new LinkedHashMap<>();

  public Character (String name, String classType, String userId, String gender) {
    this.id = null;
    this.name = name;
    this.classType = classType;
    this.userId = userId;
    this.gender = gender;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public String getUserId () {
    return userId;
  }

  public void setName (String name) {
    this.name = name;
  }

  public void setClassType (String classType) {
    this.classType = classType;
  }

  public void setPosition (Set<Lieu> position) {
    this.position = position;
  }

  public String getName () {
    return name;
  }

  public String getClassType () {
    return classType;
  }

  public Set<Lieu> getPosition () {
    return position;
  }

  public int getNiveau () {
    return niveau;
  }

  public int getPowers () {
    return powers;
  }

  public void setUserId (String userId) {
    this.userId = userId;
  }

  public Map<Fragment, HasFragmentProperties> getFragments () {
    return fragments;
  }

  public void setFragments (Map<Fragment, HasFragmentProperties> fragments) {
    this.fragments = fragments;
  }

  public Map<Enigme, HasEnigmeProperties> getEnigmes () {
    return enigmes;
  }

  public void setEnigmes (Map<Enigme, HasEnigmeProperties> enigmes) {
    this.enigmes = enigmes;
  }

  public String getGender () {
    return gender;
  }

  public void setGender (String gender) {
    this.gender = gender;
  }

  public void setNiveau (int niveau) {
    this.niveau = niveau;
  }

  public void setPowers (int powers) {
    this.powers = powers;
  }

  public Map<Indice, HasIndiceProperties> getIndices () {
    return indices;
  }

  public void setIndices (Map<Indice, HasIndiceProperties> indices) {
    this.indices = indices;
  }

  public boolean isForfeitPower () {
    return forfeitPower;
  }

  public void setForfeitPower (boolean forfeitUsed) {
    this.forfeitPower = forfeitUsed;
  }

  @Override
  public String toString () {
    return "Character{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", classType='" + classType + '\'' +
        ", userId='" + userId + '\'' +
        ", gender='" + gender + '\'' +
        ", niveau=" + niveau +
        ", powers=" + powers +
        ", forfeitPower=" + forfeitPower +
        ", position=" + position +
        ", fragments=" + fragments +
        ", enigmes=" + enigmes +
        ", indices=" + indices +
        '}';
  }
}