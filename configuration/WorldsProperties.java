package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "world")
public class WorldsProperties {

  private String colorSatorion;
  private String colorSzasgard;
  private String colorFinn;
  private String colorDefault;
  private String nameSatorion;
  private String nameSzasgard;
  private String nameFinn;
  private String thumbSatorion;
  private String thumbSzasgard;
  private String thumbFinn;
  private String thumbPath;
  private String thumbPathSatorion;
  private String thumbPathSzasgard;
  private String thumbPathFinn;
  private String thumbPathExtension;

  public WorldsProperties () {
  }

  public String getColorSatorion () {
    return colorSatorion;
  }

  public String getColorSzasgard () {
    return colorSzasgard;
  }

  public String getColorFinn () {
    return colorFinn;
  }

  public String getNameSatorion () {
    return nameSatorion;
  }

  public String getNameSzasgard () {
    return nameSzasgard;
  }

  public String getNameFinn () {
    return nameFinn;
  }

  public String getThumbSatorion () {
    return thumbSatorion;
  }

  public String getThumbSzasgard () {
    return thumbSzasgard;
  }

  public String getThumbFinn () {
    return thumbFinn;
  }

  public String getThumbPath () {
    return thumbPath;
  }

  public String getColorDefault () {
    return colorDefault;
  }

  public void setColorSatorion (String colorSatorion) {
    this.colorSatorion = colorSatorion;
  }

  public void setColorSzasgard (String colorSzasgard) {
    this.colorSzasgard = colorSzasgard;
  }

  public void setColorFinn (String colorFinn) {
    this.colorFinn = colorFinn;
  }

  public void setNameSatorion (String nameSatorion) {
    this.nameSatorion = nameSatorion;
  }

  public void setNameSzasgard (String nameSzasgard) {
    this.nameSzasgard = nameSzasgard;
  }

  public void setNameFinn (String nameFinn) {
    this.nameFinn = nameFinn;
  }


  public void setThumbFinn (String thumbFinn) {
    this.thumbFinn = thumbFinn;
  }


  public void setThumbSzasgard (String thumbSzasgard) {
    this.thumbSzasgard = thumbSzasgard;
  }


  public void setThumbSatorion (String thumbSatorion) {
    this.thumbSatorion = thumbSatorion;
  }

  public void setThumbPath (String thumbPath) {
    this.thumbPath = thumbPath;
  }

  public void setColorDefault (String colorDefault) {
    this.colorDefault = colorDefault;
  }

  public String getThumbPathSatorion() {
    return thumbPathSatorion;
  }

  public void setThumbPathSatorion(String thumbPathSatorion) {
    this.thumbPathSatorion = thumbPathSatorion;
  }

  public String getThumbPathSzasgard() {
    return thumbPathSzasgard;
  }

  public void setThumbPathSzasgard(String thumbPathSzasgard) {
    this.thumbPathSzasgard = thumbPathSzasgard;
  }

  public String getThumbPathFinn() {
    return thumbPathFinn;
  }

  public void setThumbPathFinn(String thumbPathFinn) {
    this.thumbPathFinn = thumbPathFinn;
  }

  public String getThumbPathExtension() {
    return thumbPathExtension;
  }

  public void setThumbPathExtension(String thumbPathExtension) {
    this.thumbPathExtension = thumbPathExtension;
  }
}
