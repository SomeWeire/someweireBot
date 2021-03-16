package com.bot.someweire.dao;

import com.bot.someweire.model.Portail;
import com.bot.someweire.repository.PortalRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PortalDao {

  @Autowired
  PortalRepository portalRepository;

  public PortalDao () {
  }

  public List<Portail> getAll() {
    return portalRepository.findAll().collectList().block();
  }

  public Portail getById(Long id) {
    return portalRepository.findById(id).block();
  }
}
