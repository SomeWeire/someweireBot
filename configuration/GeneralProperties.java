package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "general")
public class GeneralProperties {

  private String retry;
  private String error;
  private String prefix;
  private String help;
  private String helpContent;
  private String noPlayer;
  private String startCommand;
  private String startTitle;
  private String startContent;
  private String startNoName;
  private String startSuccess;
  private String startErase;
  private String startPrivateChannel;

  public GeneralProperties () {
  }

  public String getRetry () {
    return retry;
  }

  public void setRetry (String retry) {
    this.retry = retry;
  }

  public String getError () {
    return error;
  }

  public void setError (String error) {
    this.error = error;
  }

  public String getPrefix () {
    return prefix;
  }

  public void setPrefix (String prefix) {
    this.prefix = prefix;
  }

  public String getHelp () {
    return help;
  }

  public void setHelp (String help) {
    this.help = help;
  }

  public String getHelpContent () {
    return helpContent;
  }

  public void setHelpContent (String helpContent) {
    this.helpContent = helpContent;
  }

  public String getNoPlayer () {
    return noPlayer;
  }

  public void setNoPlayer (String noPlayer) {
    this.noPlayer = noPlayer;
  }

  public String getStartCommand () {
    return startCommand;
  }

  public void setStartCommand (String startCommand) {
    this.startCommand = startCommand;
  }

  public String getStartTitle () {
    return startTitle;
  }

  public void setStartTitle (String startTitle) {
    this.startTitle = startTitle;
  }

  public String getStartContent () {
    return startContent;
  }

  public void setStartContent (String startContent) {
    this.startContent = startContent;
  }

  public String getStartNoName () {
    return startNoName;
  }

  public void setStartNoName (String startNoName) {
    this.startNoName = startNoName;
  }

  public String getStartSuccess () {
    return startSuccess;
  }

  public void setStartSuccess (String startSuccess) {
    this.startSuccess = startSuccess;
  }

  public String getStartErase () {
    return startErase;
  }

  public void setStartErase (String startErase) {
    this.startErase = startErase;
  }

  public String getStartPrivateChannel () {
    return startPrivateChannel;
  }

  public void setStartPrivateChannel (String startPrivateChannel) {
    this.startPrivateChannel = startPrivateChannel;
  }
}
