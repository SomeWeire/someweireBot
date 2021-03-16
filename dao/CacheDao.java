package com.bot.someweire.dao;

import com.bot.someweire.model.Cache;
import com.bot.someweire.repository.CacheRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CacheDao {

  @Autowired
  CacheRepository cacheRepository;

  public CacheDao () {
  }

  public List<Cache> getAllCaches(){
    return cacheRepository.findAll().collectList().block();
  }

  public Cache getById(Long id){
    return cacheRepository.findById(id).block();
  }

}
