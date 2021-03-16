package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "discord")
public class DiscordProperties {

  private String roleVisitor;
  private String roleSatorion;
  private String roleSzasgard;
  private String roleFinn;
  private String channelGeneral;
  private String channelFinn;
  private String channelSzasgard;
  private String channelSatorion;
  private String channelPresentation;
  private String channelPortail;
  private String channelAide;

  public DiscordProperties () {
  }

  public String getRoleVisitor () {
    return roleVisitor;
  }

  public void setRoleVisitor (String roleVisitor) {
    this.roleVisitor = roleVisitor;
  }

  public String getRoleSatorion () {
    return roleSatorion;
  }

  public void setRoleSatorion (String roleSatorion) {
    this.roleSatorion = roleSatorion;
  }

  public String getRoleSzasgard () {
    return roleSzasgard;
  }

  public void setRoleSzasgard (String roleSzasgard) {
    this.roleSzasgard = roleSzasgard;
  }

  public String getRoleFinn () {
    return roleFinn;
  }

  public void setRoleFinn (String roleFinn) {
    this.roleFinn = roleFinn;
  }

  public String getChannelGeneral () {
    return channelGeneral;
  }

  public void setChannelGeneral (String channelGeneral) {
    this.channelGeneral = channelGeneral;
  }

  public String getChannelFinn () {
    return channelFinn;
  }

  public void setChannelFinn (String channelFinn) {
    this.channelFinn = channelFinn;
  }

  public String getChannelSzasgard () {
    return channelSzasgard;
  }

  public void setChannelSzasgard (String channelSzasgard) {
    this.channelSzasgard = channelSzasgard;
  }

  public String getChannelSatorion () {
    return channelSatorion;
  }

  public void setChannelSatorion (String channelSatorion) {
    this.channelSatorion = channelSatorion;
  }

  public String getChannelPresentation () {
    return channelPresentation;
  }

  public void setChannelPresentation (String channelPresentation) {
    this.channelPresentation = channelPresentation;
  }

  public String getChannelPortail () {
    return channelPortail;
  }

  public void setChannelPortail (String channelPortail) {
    this.channelPortail = channelPortail;
  }

  public String getChannelAide () {
    return channelAide;
  }

  public void setChannelAide (String channelAide) {
    this.channelAide = channelAide;
  }
}
