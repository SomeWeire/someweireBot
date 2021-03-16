package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "player")
public class PlayerProperties {

  public PlayerProperties () {
  }

  private String soldier;
  private String knight;
  private String hero;
  private String male;
  private String female;
  private String thumbSoldierMale;
  private String thumbSoldierFemale;
  private String thumbKnightMale;
  private String thumbKnightFemale;
  private String thumbHeroMale;
  private String thumbHeroFemale;
  private String thumbPath;
  private String thumbNoImage;
  private String soldierPower;
  private String knightPower;
  private String HeroPower;
  private String profilGender;
  private String profilClasstype;
  private String profilDescription;
  private String profilPower;
  private String profilFragments;
  private String profilPowersLeft;
  private String soldierDescription;
  private String knightDescription;
  private String HeroDescription;
  private String EnigmeSortedRanking;
  private String FragmentsSortedRanking;
  private String RankingNameColumn;
  private String RankingSolvedEnigmeNumberColumn;
  private String RankingFragmentLeftNumberColumn;
  private String RankingRankColumn;
  private String RankingEnigmeListMessage;
  private String RankingFragmentListMessage;

  public String getSoldier () {
    return soldier;
  }

  public void setSoldier (String soldier) {
    this.soldier = soldier;
  }

  public String getKnight () {
    return knight;
  }

  public void setKnight (String knight) {
    this.knight = knight;
  }

  public String getHero () {
    return hero;
  }

  public void setHero (String hero) {
    this.hero = hero;
  }

  public String getThumbSoldierMale () {
    return thumbSoldierMale;
  }

  public void setThumbSoldierMale (String thumbSoldierMale) {
    this.thumbSoldierMale = thumbSoldierMale;
  }

  public String getThumbSoldierFemale () {
    return thumbSoldierFemale;
  }

  public void setThumbSoldierFemale (String thumbSoldierFemale) {
    this.thumbSoldierFemale = thumbSoldierFemale;
  }

  public String getThumbKnightMale () {
    return thumbKnightMale;
  }

  public void setThumbKnightMale (String thumbKnightMale) {
    this.thumbKnightMale = thumbKnightMale;
  }

  public String getThumbKnightFemale () {
    return thumbKnightFemale;
  }

  public void setThumbKnightFemale (String thumbKnightFemale) {
    this.thumbKnightFemale = thumbKnightFemale;
  }

  public String getThumbHeroMale () {
    return thumbHeroMale;
  }

  public void setThumbHeroMale (String thumbHeroMale) {
    this.thumbHeroMale = thumbHeroMale;
  }

  public String getThumbHeroFemale () {
    return thumbHeroFemale;
  }

  public void setThumbHeroFemale (String thumbHeroFemale) {
    this.thumbHeroFemale = thumbHeroFemale;
  }

  public String getThumbPath () {
    return thumbPath;
  }

  public void setThumbPath (String thumbPath) {
    this.thumbPath = thumbPath;
  }

  public String getThumbNoImage () {
    return thumbNoImage;
  }

  public void setThumbNoImage (String thumbNoImage) {
    this.thumbNoImage = thumbNoImage;
  }

  public String getMale () {
    return male;
  }

  public void setMale (String male) {
    this.male = male;
  }

  public String getFemale () {
    return female;
  }

  public void setFemale (String female) {
    this.female = female;
  }

  public String getSoldierPower() {
    return soldierPower;
  }

  public void setSoldierPower(String soldierPower) {
    this.soldierPower = soldierPower;
  }

  public String getKnightPower() {
    return knightPower;
  }

  public void setKnightPower(String knightPower) {
    this.knightPower = knightPower;
  }

  public String getHeroPower() {
    return HeroPower;
  }

  public void setHeroPower(String heroPower) {
    HeroPower = heroPower;
  }

  public String getProfilGender() {
    return profilGender;
  }

  public void setProfilGender(String profilGender) {
    this.profilGender = profilGender;
  }

  public String getProfilDescription() {
    return profilDescription;
  }

  public void setProfilDescription(String profilDescription) {
    this.profilDescription = profilDescription;
  }

  public String getProfilPower() {
    return profilPower;
  }

  public void setProfilPower(String profilPower) {
    this.profilPower = profilPower;
  }

  public String getProfilFragments() {
    return profilFragments;
  }

  public void setProfilFragments(String profilFragments) {
    this.profilFragments = profilFragments;
  }

  public String getSoldierDescription() {
    return soldierDescription;
  }

  public void setSoldierDescription(String soldierDescription) {
    this.soldierDescription = soldierDescription;
  }

  public String getKnightDescription() {
    return knightDescription;
  }

  public void setKnightDescription(String knightDescription) {
    this.knightDescription = knightDescription;
  }

  public String getHeroDescription() {
    return HeroDescription;
  }

  public void setHeroDescription(String heroDescription) {
    HeroDescription = heroDescription;
  }

  public String getProfilClasstype() {
    return profilClasstype;
  }

  public void setProfilClasstype(String profilClasstype) {
    this.profilClasstype = profilClasstype;
  }

  public String getProfilPowersLeft() {
    return profilPowersLeft;
  }

  public void setProfilPowersLeft(String profilPowersLeft) {
    this.profilPowersLeft = profilPowersLeft;
  }

  public String getEnigmeSortedRanking() {
    return EnigmeSortedRanking;
  }

  public void setEnigmeSortedRanking(String enigmeSortedRanking) {
    EnigmeSortedRanking = enigmeSortedRanking;
  }

  public String getFragmentsSortedRanking() {
    return FragmentsSortedRanking;
  }

  public void setFragmentsSortedRanking(String fragmentsSortedRanking) {
    FragmentsSortedRanking = fragmentsSortedRanking;
  }

  public String getRankingNameColumn() {
    return RankingNameColumn;
  }

  public void setRankingNameColumn(String rankingNameColumn) {
    RankingNameColumn = rankingNameColumn;
  }

  public String getRankingSolvedEnigmeNumberColumn() {
    return RankingSolvedEnigmeNumberColumn;
  }

  public void setRankingSolvedEnigmeNumberColumn(String rankingSolvedEnigmeNumberColumn) {
    RankingSolvedEnigmeNumberColumn = rankingSolvedEnigmeNumberColumn;
  }

  public String getRankingFragmentLeftNumberColumn() {
    return RankingFragmentLeftNumberColumn;
  }

  public void setRankingFragmentLeftNumberColumn(String rankingFragmentLeftNumberColumn) {
    RankingFragmentLeftNumberColumn = rankingFragmentLeftNumberColumn;
  }

  public String getRankingRankColumn() {
    return RankingRankColumn;
  }

  public void setRankingRankColumn(String rankingRankColumn) {
    RankingRankColumn = rankingRankColumn;
  }

  public String getRankingEnigmeListMessage() {
    return RankingEnigmeListMessage;
  }

  public void setRankingEnigmeListMessage(String rankingEnigmeListMessage) {
    RankingEnigmeListMessage = rankingEnigmeListMessage;
  }

  public String getRankingFragmentListMessage() {
    return RankingFragmentListMessage;
  }

  public void setRankingFragmentListMessage(String rankingFragmentListMessage) {
    RankingFragmentListMessage = rankingFragmentListMessage;
  }
}
