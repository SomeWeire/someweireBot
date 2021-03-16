package com.bot.someweire.dao;

import com.bot.someweire.model.Lieu;
import com.bot.someweire.model.Region;
import com.bot.someweire.repository.RegionRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class RegionDao {

  @Autowired
  RegionRepository regionRepository;

  public List<Region> getAllRegions(){
    return regionRepository.findAll().collectList().block();
  }

  public List<Region> getRegionByName(String regionName){
    return regionRepository.findByNameLikeIgnoreCase(regionName).collectList().block();
  }

  public List<Region> getRegionByDescription(String regionDescription){
    return regionRepository.findByDescriptionLikeIgnoreCase(regionDescription).collectList().block();
  }

  public Region getById(Long id){
    return regionRepository.findById(id).block();
  }

}
