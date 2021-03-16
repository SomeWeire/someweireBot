package com.bot.someweire.model;

import org.neo4j.springframework.data.core.schema.GeneratedValue;
import org.neo4j.springframework.data.core.schema.Id;
import org.neo4j.springframework.data.core.schema.RelationshipProperties;

@RelationshipProperties
public class HasEnigmeProperties {

  @Id
  @GeneratedValue
  private Long id;
  private boolean solved;
  private boolean forfeit;
  private boolean powered;
  private int tries;

  public HasEnigmeProperties (boolean solved, boolean forfeit, boolean powered, int tries) {
    this.id = null;
    this.solved = solved;
    this.forfeit = forfeit;
    this.powered = powered;
    this.tries = tries;
  }

  public Long getId () {
    return id;
  }

  public void setId (Long id) {
    this.id = id;
  }

  public boolean isSolved () {
    return solved;
  }

  public void setSolved (boolean solved) {
    this.solved = solved;
  }

  public int getTries () {
    return tries;
  }

  public void setTries (int tries) {
    this.tries = tries;
  }

  public boolean isForfeit () {
    return forfeit;
  }

  public void setForfeit (boolean forfeit) {
    this.forfeit = forfeit;
  }

  public boolean isPowered() {
    return powered;
  }

  public void setPowered(boolean powered) {
    this.powered = powered;
  }
}
