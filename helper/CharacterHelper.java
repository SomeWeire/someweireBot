package com.bot.someweire.helper;

import com.bot.someweire.configuration.EnigmeProperties;
import com.bot.someweire.configuration.PlayerProperties;
import com.bot.someweire.model.*;
import com.bot.someweire.model.Character;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class CharacterHelper {

  @Autowired
  protected PlayerProperties playerProperties;

  @Autowired
  protected EnigmeProperties enigmeProperties;

  @Autowired
  protected WorldHelper worldHelper;

  public Lieu getPlayerPosition(Character player){
    Lieu position = null;
    try{
      position = player
          .getPosition()
          .stream()
          .findFirst()
          .get();
    } catch (Exception e) {
      System.out.println(e);
    }
    return position;
  }

  public Region getPlayerRegion(Character player){
    Region region = null;
    try {
      region = player
          .getPosition()
          .stream()
          .findFirst()
          .get()
          .getRegions()
          .keySet()
          .stream()
          .findFirst()
          .get();
    } catch (Exception e) {
      System.out.println(e);
    }
    return region;
  }

  public List<Lieu> getPlayerRoads(Character player){
    List<Lieu> roads = null;
    try{
      roads = new ArrayList<>(this
          .getPlayerPosition(player)
          .getRoads());
    } catch (Exception e) {
      System.out.println(e);
    }
    return roads;
  }

  public Cache getPlayerPositionCache(Character player){
    Cache cache = null;
    try {
      cache = getPlayerPosition(player)
              .getCaches()
              .stream()
              .findFirst()
              .get();
    } catch (Exception e){
      System.out.println(e);
    }
    return cache;
  }

  public Fragment getFragmentFromPlayerPositionCache(Character player){
    Fragment fragment = null;
    try {
      fragment = getPlayerPositionCache(player)
              .getFragments()
              .stream()
              .findFirst()
              .get();
    } catch (Exception e){
      System.out.println(e);
    }
    return fragment;
  }

  public Lieu updateLieuFromPortal(Character player){
    Lieu lieu = null;
    try {
      lieu = getPlayerPosition(player)
              .getPortals()
              .stream()
              .findFirst()
              .get()
              .getLieux()
              .stream()
              .filter(newLieu ->
                !newLieu.getId().equals(getPlayerPosition(player).getId()))
              .findFirst().get();
    } catch (Exception e){
      System.out.println(e);
    }
    return lieu;
  }


  public World getPlayerWorld(Character player){
    World world = null;
    try {
      world = this.getPlayerRegion(player)
          .getWorlds()
          .keySet()
          .stream()
          .findFirst()
          .get();
    } catch (Exception e){
      System.out.println(e);
    }
    return world;
  }

  public List<Fragment> getPlayerFragments(Character player){
    List<Fragment> fragments = null;
    try{
      fragments = new ArrayList<>(player
          .getFragments()
          .keySet());
    } catch (Exception e){
      System.out.println(e);
    }
    return fragments;
  }

  public boolean playerHasFragment(Character player, Fragment fragment){
    boolean hasFragment = false;
    try{
      hasFragment = this.getPlayerFragments(player)
              .stream()
              .anyMatch(playerFragment -> playerFragment.getId().equals(fragment.getId()));
    } catch (Exception e){
      System.out.println(e);
    }
    return hasFragment;
  }

  public List<Fragment> getPlayerFragmentsByWorld(Character player, String fragmentType){
    List<Fragment> fragments = null;
    try {
      fragments = this.getPlayerFragments(player)
          .stream()
          .filter(fragment -> fragment.getType().equals(fragmentType))
          .collect(Collectors.toList());
    } catch (Exception e){
      System.out.println(e);
    }
    return fragments;
  }


  public int getPlayerFragmentsLeftByWorld(Character player, String fragmentType){
    Integer fragmentsLeft= 0;
    try {
      for (Fragment fragment:this.getPlayerFragmentsByWorld(player, fragmentType)){
        fragmentsLeft += this.getPlayerFragmentProperties(player, fragment.getId()).getLeft();
      }
    } catch (Exception e){
      fragmentsLeft = null;
      System.out.println(e);
    }
    return fragmentsLeft;
  }

  public HasFragmentProperties getPlayerFragmentProperties(Character player, Long fragmentId){
    HasFragmentProperties fragmentProperties = null;
    try {
      Fragment fragmentFromList = this
          .getPlayerFragments(player)
          .stream()
          .filter(fragment -> fragment.getId().equals(fragmentId))
          .findFirst()
          .get();
      fragmentProperties = player.getFragments().get(fragmentFromList);
    } catch (Exception e){
      System.out.println(e);
    }
    return fragmentProperties;
  }

  public Map<Fragment, HasFragmentProperties> spendFragment(Character player, String fragmentType, int amountToPay){
    Map<Fragment, HasFragmentProperties> map = new HashMap<>();

    List<Fragment> fragmentsToSpend = this.getPlayerFragmentsByWorld(player, fragmentType);
    int leftToPay = amountToPay;

    for (Fragment fragment : fragmentsToSpend) {
      final Long fragmentId = fragment.getId();
      final int remainingOnFragment = this.getPlayerFragmentProperties(player, fragmentId).getLeft();
      if (remainingOnFragment > 0) {
        if (leftToPay - remainingOnFragment > 0) {
          leftToPay -= remainingOnFragment;
          map.put(fragment, new HasFragmentProperties(0));
        } else if (leftToPay - remainingOnFragment <= 0) {
          map.put(fragment, new HasFragmentProperties(remainingOnFragment-leftToPay));
          break;
        }
      }
    }
    return map;
  }

  public List<Enigme> getPlayerEnigmes(Character player){
    List<Enigme> enigmes = null;
    try {
      enigmes = new ArrayList<>(player
          .getEnigmes()
          .keySet()
      );
    }catch (Exception e){
      System.out.println(e);
    }
    return  enigmes;
  }

  public List<Enigme> getPlayerSolvedEnigmesByWorld(Character player, World world){
    List<Enigme> enigmes = null;
    try {
      enigmes = getPlayerSolvedEnigmes(player)
              .stream()
              .filter(enigme -> enigme.getMonde() == worldHelper.getWorldNumberByName(world.getName()))
              .collect(Collectors.toList());
    }catch (Exception e){
      System.out.println();
    }
    return enigmes;
  }

  public List<Enigme> getPlayerSolvedEnigmes(Character player){
    List<Enigme> enigmes = null;
    try {
      enigmes = player
              .getEnigmes()
              .keySet()
              .stream()
              .filter(enigme -> player.getEnigmes()
                      .get(enigme)
                      .isSolved()).collect(Collectors.toList());
    }catch (Exception e){
      System.out.println(e);
    }
    return  enigmes;
  }

  public boolean playerHasEnigme(Character player, Enigme enigme){
    boolean hasEnigme = false;
    try{
      hasEnigme = this.getPlayerEnigmes(player).stream()
          .anyMatch(cacheEnigme -> cacheEnigme.getId().equals(enigme.getId()));
    } catch (Exception e){
      System.out.println(e);
    }
    return hasEnigme;
  }

  public Enigme getEnigmeFromPlayerPositionCache(Character player){
    Enigme enigme = null;
    try {
      enigme = getPlayerPositionCache(player)
              .getEnigme()
              .stream()
              .findFirst()
              .get();
    } catch (Exception e){
      System.out.println(e);
    }
    return enigme;
  }

  public HasEnigmeProperties getPlayerEnigmeProperties(Character player, Enigme enigme){
    HasEnigmeProperties enigmeProperties = null;
    try {
      Enigme enigmeFromList = this
          .getPlayerEnigmes(player)
          .stream()
          .filter(cacheEnigme -> cacheEnigme.getId().equals(enigme.getId()))
          .findFirst()
          .get();
      enigmeProperties = player.getEnigmes().get(enigmeFromList);
    } catch (Exception e){
      System.out.println(e);
    }
    return enigmeProperties;
  }

  public List<Indice> getPlayerIndices(Character player){
    List<Indice> indices = null;
    try {
      indices = new ArrayList<>(player
          .getIndices()
          .keySet()
      );
    }catch (Exception e){
      System.out.println(e);
    }
    return indices;
  }

  public HasIndiceProperties getPlayerIndiceProperties(Character player, Long indiceId){
    HasIndiceProperties indiceProperties = null;
    try {
      Indice indiceFromList = this
          .getPlayerIndices(player)
          .stream()
          .filter(indice -> indice.getId().equals(indiceId))
          .findFirst()
          .get();
      indiceProperties = player.getIndices().get(indiceFromList);
    } catch (Exception e){
      System.out.println(e);
    }
    return indiceProperties;
  }

  public List<Indice> getPlayerIndicesByEnigme(Character player, Enigme enigme){
    List<Indice> indices = null;
    try {
      indices = getIndicesByPlayerEnigme(player, enigme)
              .stream()
              .filter(enigmeIndice -> getPlayerIndices(player)
                      .stream()
                      .anyMatch(playerIndice -> enigmeIndice.getId().equals(playerIndice.getId())))
              .collect(Collectors.toList());
    }catch (Exception e){
      System.out.println(e);
    }
    return indices;
  }

  public List<Indice> getIndicesByPlayerEnigme(Character player, Enigme enigme){
    List<Indice> indices = null;
    try {
      indices = new LinkedList<>(
          player
              .getEnigmes()
              .keySet()
              .stream()
              .filter(playerEnigme -> playerEnigme.getId().equals(enigme.getId()))
              .findFirst()
              .get()
              .getIndices()
              .stream()
              .sorted(Comparator.comparingInt(Indice::getNumero))
              .collect(Collectors.toList()));
    }catch (Exception e){
      System.out.println(e);
    }
    return indices;
  }


  public String getThumbCharacter(Character character){
    String thumb = "";
    final String classType = character.getClassType();
    final String gender = character.getGender();
    final boolean isMale = gender.toLowerCase().equals(playerProperties.getMale().toLowerCase());
    if(classType.toLowerCase().equals(playerProperties.getSoldier().toLowerCase())) {
      if(isMale){
        thumb = playerProperties.getThumbSoldierMale();
      } else {
        thumb = playerProperties.getThumbSoldierFemale();
      }
    } else if(classType.toLowerCase().equals(playerProperties.getKnight().toLowerCase())){
      if(isMale){
        thumb = playerProperties.getThumbKnightMale();
      } else {
        thumb = playerProperties.getThumbKnightFemale();
      }
    } else if(classType.toLowerCase().equals(playerProperties.getHero().toLowerCase())) {
      if(isMale){
        thumb = playerProperties.getThumbHeroMale();
      } else {
        thumb = playerProperties.getThumbHeroFemale();
      }
    } else {
      if(character.getName().equals("Sumanguru")){
        thumb = "sumanguruthumb.jpg";
      } else {
        thumb = playerProperties.getThumbNoImage();
      }
    }
    return thumb;
  }

  public String getCharacterDescription(Character player){
    String description = "";
    final String classType = player.getClassType();
    if(classType.toLowerCase().equals(playerProperties.getSoldier().toLowerCase())) {
      return playerProperties.getSoldierDescription();
    } else if(classType.toLowerCase().equals(playerProperties.getKnight().toLowerCase())){
      return playerProperties.getKnightDescription();
    } else if(classType.toLowerCase().equals(playerProperties.getHero().toLowerCase())) {
      return playerProperties.getHeroDescription();
    } else {
      if(player.getName().equals("Sumanguru")){
        return "...";
      }
    }
    return description;
  }

  public String getCharacterPowerDescription(Character player){
    String powerDescription = "";
    final String classType = player.getClassType();
    if(classType.toLowerCase().equals(playerProperties.getSoldier().toLowerCase())) {
      return playerProperties.getSoldierPower();
    } else if(classType.toLowerCase().equals(playerProperties.getKnight().toLowerCase())){
      return playerProperties.getKnightPower();
    } else if(classType.toLowerCase().equals(playerProperties.getHero().toLowerCase())) {
      return playerProperties.getHeroPower();
    } else {
      if(player.getName().equals("Sumanguru")){
        return "Tout";
      }
    }
    return powerDescription;
  }

  public String getCharacterFragmentsDescription(Character player){
    String fragments = "";
    fragments += enigmeProperties.getNameWeivellite();
    fragments += " : ";
    fragments += getPlayerFragmentsLeftByWorld(player, enigmeProperties.getNameWeivellite());
    fragments += "\n";
    fragments += enigmeProperties.getNameWeither();
    fragments += " : ";
    fragments += getPlayerFragmentsLeftByWorld(player, enigmeProperties.getNameWeither());
    fragments += "\n";
    fragments += enigmeProperties.getNameWeillenium();
    fragments += " : ";
    fragments += getPlayerFragmentsLeftByWorld(player, enigmeProperties.getNameWeillenium());
    fragments += "\n";
    return fragments;
  }

}
