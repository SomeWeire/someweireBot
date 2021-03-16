package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "lieu")
public class LieuProperties {

  private String error;
  private String infoTitle;
  private String infoCommand;
  private String infoMonde;
  private String infoDescription;
  private String infoRegion;
  private String infoLocalisation;
  private String infoRoutes;
  private String infoRoutesDistance;
  private String infoRoutesUnite;
  private String infoPortail;
  private String infoPublicMessage;
  private String voyageTitle;
  private String voyageCommand;
  private String voyageError;
  private String voyageSuccess;
  private String portailTitle;
  private String portailCommand;
  private String portailEmpty;
  private String portailSuccess;
  private String portailPrivateChannel;
  private String command;
  private String title;
  private String help;
  private String helpContent;
  private String errorArguments;


  public String getError () {
    return error;
  }

  public void setError (String error) {
    this.error = error;
  }

  public String getInfoTitle () {
    return infoTitle;
  }

  public void setInfoTitle (String infoTitle) {
    this.infoTitle = infoTitle;
  }

  public String getInfoMonde () {
    return infoMonde;
  }

  public void setInfoMonde (String infoMonde) {
    this.infoMonde = infoMonde;
  }

  public String getInfoDescription () {
    return infoDescription;
  }

  public String getVoyageError () {
    return voyageError;
  }

  public void setVoyageError (String voyageError) {
    this.voyageError = voyageError;
  }

  public void setInfoDescription (String infoDescription) {
    this.infoDescription = infoDescription;
  }

  public String getInfoCommand () {
    return infoCommand;
  }

  public void setInfoCommand (String infoCommand) {
    this.infoCommand = infoCommand;
  }

  public String getInfoRegion () {
    return infoRegion;
  }

  public void setInfoRegion (String infoRegion) {
    this.infoRegion = infoRegion;
  }

  public String getInfoLocalisation () {
    return infoLocalisation;
  }

  public void setInfoLocalisation (String infoLocalisation) {
    this.infoLocalisation = infoLocalisation;
  }

  public String getInfoRoutes () {
    return infoRoutes;
  }

  public void setInfoRoutes (String infoRoutes) {
    this.infoRoutes = infoRoutes;
  }

  public String getInfoRoutesDistance () {
    return infoRoutesDistance;
  }

  public void setInfoRoutesDistance (String infoRoutesDistance) {
    this.infoRoutesDistance = infoRoutesDistance;
  }

  public String getInfoRoutesUnite () {
    return infoRoutesUnite;
  }

  public void setInfoRoutesUnite (String infoRoutesUnite) {
    this.infoRoutesUnite = infoRoutesUnite;
  }

  public String getVoyageSuccess () {
    return voyageSuccess;
  }

  public void setVoyageSuccess (String voyageSuccess) {
    this.voyageSuccess = voyageSuccess;
  }

  public String getVoyageTitle () {
    return voyageTitle;
  }

  public void setVoyageTitle (String voyageTitle) {
    this.voyageTitle = voyageTitle;
  }

  public String getPortailCommand () {
    return portailCommand;
  }

  public void setPortailCommand (String portailCommand) {
    this.portailCommand = portailCommand;
  }

  public String getPortailTitle () {
    return portailTitle;
  }

  public void setPortailTitle (String portailTitle) {
    this.portailTitle = portailTitle;
  }

  public String getPortailEmpty () {
    return portailEmpty;
  }

  public void setPortailEmpty (String portailEmpty) {
    this.portailEmpty = portailEmpty;
  }

  public String getPortailSuccess () {
    return portailSuccess;
  }

  public void setPortailSuccess (String portailSuccess) {
    this.portailSuccess = portailSuccess;
  }

  public String getInfoPortail () {
    return infoPortail;
  }

  public void setInfoPortail (String infoPortail) {
    this.infoPortail = infoPortail;
  }

  public String getCommand () {
    return command;
  }

  public void setCommand (String command) {
    this.command = command;
  }

  public String getTitle () {
    return title;
  }

  public void setTitle (String title) {
    this.title = title;
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

  public String getErrorArguments () {
    return errorArguments;
  }

  public void setErrorArguments (String errorArguments) {
    this.errorArguments = errorArguments;
  }

  public String getVoyageCommand () {
    return voyageCommand;
  }

  public void setVoyageCommand (String voyageCommand) {
    this.voyageCommand = voyageCommand;
  }

  public String getPortailPrivateChannel () {
    return portailPrivateChannel;
  }

  public void setPortailPrivateChannel (String portailPrivateChannel) {
    this.portailPrivateChannel = portailPrivateChannel;
  }

  public String getInfoPublicMessage() {
    return infoPublicMessage;
  }

  public void setInfoPublicMessage(String infoPublicMessage) {
    this.infoPublicMessage = infoPublicMessage;
  }
}
