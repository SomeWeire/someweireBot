package com.bot.someweire.dao;

import com.bot.someweire.model.Enigme;
import com.bot.someweire.repository.EnigmeRepository;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EnigmeDao {

  @Autowired
  EnigmeRepository enigmeRepository;

  public EnigmeDao () {
  }

  public List<Enigme> getAllEnigmes(){
    return enigmeRepository.findAll().collectList().block();
  }

  public Enigme getById(Long id){
    return enigmeRepository.findById(id).block();
  }

  public Optional<Enigme> getByNumero (int numero, Long worldId){
    return enigmeRepository.findByNumero(numero, worldId).blockOptional();
  }

  public List<Enigme> getAllUnsolvedEnigmesByCharacter (String userId) {
    return enigmeRepository.findAllUnsolvedEnigmesByCharacter(userId).collectList().block();
  }


  public List<Enigme> getAllSolvedEnigmesByCharacter (String userId) {
    return enigmeRepository.findAllSolvedEnigmesByCharacter(userId).collectList().block();
  }

  public List<Enigme> getAllEnigmesByCharacterAndWorld (String userId, Long worldId) {
    List<Enigme> linkedList = new LinkedList<Enigme>();
    enigmeRepository.findAllEnigmesByCharacterAndWorld(userId, worldId).
        collectList().
        block().
        stream().
        sorted(Comparator.comparingInt(Enigme::getNumero)).
        forEach(linkedList::add);
    return linkedList;
  }

  public Boolean checkEnigmeSolvedByCharacter (String userid, Long enigmeid) {
    return enigmeRepository.HasSolvedByCharacter(userid, enigmeid).block();
  }

  public Boolean checkEnigmeOwnedByCharacter (String userid, Long enigmeid) {
    return enigmeRepository.HasEnigmeByCharacter(userid, enigmeid).block();
  }

  public List<Enigme> getAllEnigmesByWorld (Long worldId) {
    List<Enigme> linkedList = new LinkedList<Enigme>();
    enigmeRepository.findAllEnigmesByWorld(worldId).
        collectList().
        block().
        stream().
        sorted(Comparator.comparingInt(Enigme::getNumero)).
        forEach(linkedList::add);
    return linkedList;
  }

  public boolean trySolveEnigme(String solution, Long enigmeId, String userId){
    return enigmeRepository.trySolveEnigme(solution, enigmeId, userId).block();
  }

  public int getPlayerTries(String userId, Long enigmeId){
    return enigmeRepository.findTriesByCharacter(userId, enigmeId).block();
  }

  public int updatePlayerTries(String userId, Long enigmeId, int playerTries){
    return enigmeRepository.updateTriesByCharacter(userId, enigmeId, playerTries).block();
  }

  public boolean forfeitByCharacter(String userId, Long enigmeId){
    return enigmeRepository.forfeitByCharacter(userId, enigmeId).block();
  }

  public String getEnigmeSolution(Long enigmeId){
    return enigmeRepository.findEnigmeSolution(enigmeId).block();
  }

}
