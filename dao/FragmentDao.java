package com.bot.someweire.dao;

import com.bot.someweire.model.Fragment;
import com.bot.someweire.repository.FragmentRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class FragmentDao {

  @Autowired
  private FragmentRepository fragmentRepository;

  public FragmentDao () {
  }

  public List<Fragment> getAllFragments(){
    return fragmentRepository.findAll().collectList().block();
  }

  public Fragment getById(Long id){
    return fragmentRepository.findById(id).block();
  }

  public List<Fragment> getByType (String type){
    return fragmentRepository.findByTypeLikeIgnoreCase(type).collectList().block();
  }

  public List<Fragment> getByQuantite (int quantite) {
    return fragmentRepository.findByQuantite(quantite).collectList().block();
  }

  public int getTotalFragmentsLeftByUserIdAndWorldId(String userId, Long worldId){
    return fragmentRepository.findTotalFragmentsLeftByUserIdAndWorldId(userId, worldId).block();
  }

  public boolean checkFragmentOwnedByPlayer(String userId, Long fragmentId){
    return fragmentRepository.checkFragmentOwnedByPlayer(userId, fragmentId).block();
  }
}
