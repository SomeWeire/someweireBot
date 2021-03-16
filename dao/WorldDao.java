package com.bot.someweire.dao;

import com.bot.someweire.model.World;
import com.bot.someweire.repository.WorldRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorldDao {

  @Autowired
  WorldRepository worldRepository;

  public WorldDao () {
  }

  public List<World> getByName (String name){
    return worldRepository.findByNameLikeIgnoreCase(name).collectList().block();
  }

  public List<World> getByDescription (String description){
    return worldRepository.findByDescriptionLikeIgnoreCase(description).collectList().block();
  }

  public World getById(Long id) {
    return worldRepository.findById(id).block();
  }

  public World getByRegionId(Long regionId) {
    return worldRepository.getByRegion(regionId).block();
  }

  public World getByLieuId(Long lieuId){
    return worldRepository.getByLieu(lieuId).block();
  }

  public World getByUser(String userId){
    return worldRepository.getByUser(userId).block();
  }

}
