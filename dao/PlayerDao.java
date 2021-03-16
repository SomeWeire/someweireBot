package com.bot.someweire.dao;

import com.bot.someweire.model.Character;
import com.bot.someweire.model.Enigme;
import com.bot.someweire.model.HasEnigmeProperties;
import com.bot.someweire.model.HasFragmentProperties;
import com.bot.someweire.repository.PlayerRepository;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PlayerDao {

  @Autowired
  PlayerRepository playerRepository;

  public PlayerDao () {
  }

  public List<Character> getAll(){
    return playerRepository.findAll().collectList().block();
  }


  public Character getById(Long id){
    return playerRepository.findById(id).block();
  }

  public List<Character> getAllByClass(String classType) {
    return playerRepository.findByClassTypeIgnoreCase(classType).collectList().block();
  }

  public Character getByUserId (String userId) {
    return playerRepository.findByUserId(userId).block();
  }

  public Character saveCharacter(Character character){
    return playerRepository.save(character).block();
  }

  public Character updatePlayerLocation(Long userId, Long previouslocationid, Long newlocationid){
    return playerRepository.updatePlayerLocation(userId, previouslocationid, newlocationid).block();
  }

  public Character updatePlayerFragments(String userId, Long fragmentid, int quantite, int powers){
    return playerRepository.updatePlayerFragments(userId, fragmentid, quantite, powers).block();
  }

  public String getPlayerName(String userId){
    return playerRepository.findPlayerName(userId).block();
  }

  public Character spendPlayerFragment(String userId, Long fragmentId, int numberToSpend){
    return playerRepository.spendPlayerFragment(userId, fragmentId, numberToSpend).block();
  }

  public Character solveEnigme(String userId, Long enigmeId){
    return playerRepository.updateSolvedPlayerEnigme(userId, enigmeId).block();
  }

  public Character solveEnigmeByPower(String userId, Long enigmeId, int tries, int powers){
    return playerRepository.updateSolvedPlayerEnigmeByPower(userId, enigmeId, tries, powers).block();
  }

  public Character ownEnigme(String userId, Long enigmeId){
    return playerRepository.updateOwnedPlayerEnigme(userId, enigmeId).block();
  }

  public Character ownEnigmeByPower(String userId, Long enigmeId, int powers){
    return playerRepository.updateOwnPlayerEnigmeByPower(userId, enigmeId, powers).block();
  }

  public Character setValueLeftOnFragmentForPlayer(String userId, Long fragmentId, int valueLeft){
   return playerRepository.setValueLeftOnFragmentForPlayer(userId, fragmentId, valueLeft).block();
  }

  public boolean isPlayerExists(String userId){
    Boolean exists = playerRepository.
        playerExists(userId)
        .blockOptional()
        .isPresent();
    return exists;
  }

  public boolean createPlayer(String name, String classType, String gender, String userId, String worldName){
    return playerRepository.createPlayer(name, classType, gender, userId, worldName)
        .blockOptional().orElse(false);
  }

  public boolean resetPlayer(String name, String classType, String gender, String userId, String worldName){
    return playerRepository.resetPlayer(name, classType, gender, userId, worldName)
        .blockOptional().orElse(false);
  }

  public Character ownIndice(String userId, Long indiceId){
    return playerRepository.updateOwnedPlayerIndice(userId, indiceId).block();
  }

  public Character updateForfeitPower(String userId, boolean forfeitPower){
    return playerRepository.updateForfeitPower(userId, forfeitPower).block();
  }

  public Character updatePlayerPowers(String userId, int powers){
    return playerRepository.updatePlayerPowers(userId, powers).block();
  }

  public Character ownPoweredIndice(String userId, Long indiceId, int powers){
    return playerRepository.updateOwnedPlayerPowerIndice(userId, indiceId, powers).block();
  }

  public Character powerOwnedIndice(String userId, Long indiceId, int powers){
    return playerRepository.updatePlayerPowerOwnedIndice(userId, indiceId, powers).block();
  }

  public Character forfeitByCharacter(String userId, Long enigmeId){
    return playerRepository.forfeitByCharacter(userId, enigmeId).block();
  }

  public List<Character> AllCharactersByWorld(Long worldId){
    return playerRepository.findAllCharactersInWorld(worldId).collectList().block();
  }
}
