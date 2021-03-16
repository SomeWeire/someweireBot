package com.bot.someweire.dao;

import com.bot.someweire.model.Enigme;
import com.bot.someweire.model.Indice;
import com.bot.someweire.repository.IndiceRepository;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class IndiceDao {

  @Autowired
  private IndiceRepository indiceRepository;

  public Indice findById(Long id){
    return indiceRepository.findById(id).block();
  }

  public Indice findByEnigmeAndNumero(Long enigmeId, int numero){
    return indiceRepository.findByEnigmeAndNumero(enigmeId, numero).block();
  }

  public List<Indice> findByEnigme(Long enigmeId){
    return indiceRepository.findByEnigme(enigmeId).collectList().block();
  }

  public List<Indice> findByCharacterAndEnigme(Long enigmeId, String userId){
    LinkedList list = new LinkedList();
    indiceRepository.findByCharacterAndEnigme(enigmeId, userId).
        collectList().
        block().
        stream().
        sorted(Comparator.comparingInt(Indice::getNumero)).
        forEach(list::add);
    return list;
  }

  public List<Indice> userForfeitPowerForPlayerWhenHasNotAllIndices(String userId, Long enigmeId){
    LinkedList list = new LinkedList();
    indiceRepository.useForfeitPowerByCharacterAndEnigmeHasNotAllIndices(userId, enigmeId).
        distinct(Indice::getId).
        collectList().
        block().
        stream().
        sorted(Comparator.comparingInt(Indice::getNumero)).
        forEach(list::add);
    return list;
  }


  public List<Indice> userForfeitPowerForPlayerWhenHasAllIndices(String userId, Long enigmeId){
    LinkedList list = new LinkedList();
    indiceRepository.useForfeitPowerByCharacterAndEnigmeHasAllIndices(userId, enigmeId).
        distinct(Indice::getId).
        collectList().
        block().
        stream().
        sorted(Comparator.comparingInt(Indice::getNumero)).
        forEach(list::add);
    return list;
  }

  public List<Indice> deleteIndicesByCharacterAndEnigme(String userId, Long enigmeId){
    LinkedList list = new LinkedList();
    indiceRepository.deleteIndicesByCharacterAndEnigme(userId, enigmeId).
        collectList().
        block().
        stream().
        sorted(Comparator.comparingInt(Indice::getNumero)).
        forEach(list::add);
    return list;
  }

}
