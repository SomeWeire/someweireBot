package com.bot.someweire.dao;

import com.bot.someweire.model.Lieu;
import com.bot.someweire.repository.LieuRepository;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LieuDao {

  @Autowired
  LieuRepository lieuRepository;

  public LieuDao () {
  }

  public List<Lieu> getAllLieu(){
    return lieuRepository.findAll().collectList().block();
  }

  public List<Lieu> getLieuByName(String lieuName){
    return lieuRepository.findByNameLikeIgnoreCase(lieuName).collectList().block();
  }

  public List<Lieu> getLieuByDescription(String lieuDescription){
    return lieuRepository.findByDescriptionLikeIgnoreCase(lieuDescription).collectList().block();

  }

  public List<Lieu> getIncomingRoadsFromId(Long id){
    return new LinkedList<>(
        Objects.requireNonNull(lieuRepository.findIncomingRoadsById(id).collectList().block()));
  }

  public Lieu getById(Long id){
    return lieuRepository.findById(id).block();
  }


  public Lieu saveLieu(Lieu lieu){
    return lieuRepository.save(lieu).block();
  }

  public Lieu getWithCachesByUserId(String userId){
    return lieuRepository.findWithCachesByCharacterUserId(userId).block();
  }
}
