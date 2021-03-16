package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "cache")
public class EnigmeProperties {

    private String detectCommand;
    private String detectTitle;
    private String detectNoCache;
    private String detectFound;
    private String detectIsEnigme;
    private String detectIsEnigmeBuyMessage;
    private String detectIsEnigmeNotSolvedMessage;
    private String detectWinFragments;
    private String detectWinFragmentsDescriptionQuantite;
    private String detectWinFragmentsDescriptionType;
    private String worldListCommand;
    private String worldListTitle;
    private String thumbWeivellite;
    private String thumbWeither;
    private String thumbWeillenium;
    private String nameWeivellite;
    private String nameWeither;
    private String nameWeillenium;
    private String thumbPathSatorion;
    private String thumbPathSzasgard;
    private String thumbPathFinn;
    private String thumbEnigmePrefix;
    private String thumbEnigmeSuffix;
    private String worldListContent;
    private String worldListMessage;
    private String worldListNumeroColumn;
    private String worldListTitresColumn;
    private String worldListPrixColumn;
    private String playerListResolvedColumn;
    private String playerListTitle;
    private String playerListMessage;
    private String playerListResolvedContentSolved;
    private String playerListResolvedContentNotSolved;
    private String selectTitle;
    private String selectCommand;
    private String selectError;
    private String selectConfirmBuy;
    private String selectConfirmedBuy;
    private String selectPrivateChannel;
    private String selectPublicChannel;
    private String selectNotEnoughFunds;
    private String selectAlreadySolved;
    private String selectEnigmeTitle;
    private String selectEnigmePrivateMessage;
    private String selectEnigmeMessage;
    private String selectPublicEnigmeMessage;
    private String selectEnigmeWrongSolution;
    private String selectEnigmeRightSolution;
    private String selectEnigmeRightSolutionWithBonus;
    private String selectEnigmeRightSolutionWithBonusError;
    private String help;
    private String helpContent;
    private String command;
    private String title;
    private String errorArguments;
    private String indiceConfirm;
    private String indicesNumber;
    private String indiceMax;
    private String indicePoweredMax;
    private String indicePoweredAvailable;
    private String indiceNew;
    private String indicePresentation;
    private String indicePowered;
    private String triesMore;
    private String triesForfeit;
    private String selectEnigmeTriesMore;
    private String selectEnigmeTriesForfeit;
    private String forfeitConfirm;
    private String forfeit;
    private String forfeitEnigmeSolution;
    private String selectAlreadyForfeit;
    private String forfeitPowerConfirm;
    private String forfeitPower;
    private String forfeitPowerDisplay;
    private String powerConfirmIndice;
    private String powerConfirmPassEnigme;
    private String powerConfirmGetEnigme;
    private String powerNotEnough;
    private String powerDisplay;
    private String powerPassEnigme;
    private String powerIndice;
    private String indiceDetectNumber;
    private String indiceDetectCost;
    private String indiceDetectPowered;
    private String powersDetectUpdated;
    private String detectForfeit;
    private String indicesCost;
    private String powerConfirmIndiceMax;
    private String bonusPercentage;
    private String bonusPowers;
    private String winPower;
    private String powerMaxLevel1;
    private String bonusTries;
    private String passEnigmePowerCost;
    private String indicePowerCost;
    private String buyEnigmePowerCost;

    public EnigmeProperties() {
    }

    public String getDetectCommand() {
        return detectCommand;
    }

    public void setDetectCommand(String detectCommand) {
        this.detectCommand = detectCommand;
    }

    public String getDetectTitle() {
        return detectTitle;
    }

    public void setDetectTitle(String detectTitle) {
        this.detectTitle = detectTitle;
    }

    public String getDetectNoCache() {
        return detectNoCache;
    }

    public void setDetectNoCache(String detectNoCache) {
        this.detectNoCache = detectNoCache;
    }

    public String getDetectIsEnigme() {
        return detectIsEnigme;
    }

    public void setDetectIsEnigme(String detectIsEnigme) {
        this.detectIsEnigme = detectIsEnigme;
    }

    public String getDetectIsEnigmeBuyMessage() {
        return detectIsEnigmeBuyMessage;
    }

    public void setDetectIsEnigmeBuyMessage(String detectIsEnigmeBuyMessage) {
        this.detectIsEnigmeBuyMessage = detectIsEnigmeBuyMessage;
    }

    public String getDetectIsEnigmeNotSolvedMessage() {
        return detectIsEnigmeNotSolvedMessage;
    }

    public void setDetectIsEnigmeNotSolvedMessage(String detectIsEnigmeNotSolvedMessage) {
        this.detectIsEnigmeNotSolvedMessage = detectIsEnigmeNotSolvedMessage;
    }

    public String getThumbWeivellite() {
        return thumbWeivellite;
    }

    public void setThumbWeivellite(String thumbWeivellite) {
        this.thumbWeivellite = thumbWeivellite;
    }

    public String getThumbWeither() {
        return thumbWeither;
    }

    public void setThumbWeither(String thumbWeither) {
        this.thumbWeither = thumbWeither;
    }

    public String getThumbWeillenium() {
        return thumbWeillenium;
    }

    public void setThumbWeillenium(String thumbWeillenium) {
        this.thumbWeillenium = thumbWeillenium;
    }

    public String getNameWeivellite() {
        return nameWeivellite;
    }

    public void setNameWeivellite(String nameWeivellite) {
        this.nameWeivellite = nameWeivellite;
    }

    public String getNameWeither() {
        return nameWeither;
    }

    public void setNameWeither(String nameWeither) {
        this.nameWeither = nameWeither;
    }

    public String getNameWeillenium() {
        return nameWeillenium;
    }

    public void setNameWeillenium(String nameWeillenium) {
        this.nameWeillenium = nameWeillenium;
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

    public String getDetectWinFragments() {
        return detectWinFragments;
    }

    public void setDetectWinFragments(String detectWinFragments) {
        this.detectWinFragments = detectWinFragments;
    }

    public String getDetectWinFragmentsDescriptionQuantite() {
        return detectWinFragmentsDescriptionQuantite;
    }

    public void setDetectWinFragmentsDescriptionQuantite(
            String detectWinFragmentsDescriptionQuantite) {
        this.detectWinFragmentsDescriptionQuantite = detectWinFragmentsDescriptionQuantite;
    }

    public String getDetectWinFragmentsDescriptionType() {
        return detectWinFragmentsDescriptionType;
    }

    public void setDetectWinFragmentsDescriptionType(String detectWinFragmentsDescriptionType) {
        this.detectWinFragmentsDescriptionType = detectWinFragmentsDescriptionType;
    }

    public String getWorldListCommand() {
        return worldListCommand;
    }

    public void setWorldListCommand(String worldListCommand) {
        this.worldListCommand = worldListCommand;
    }

    public String getWorldListContent() {
        return worldListContent;
    }

    public void setWorldListContent(String worldListContent) {
        this.worldListContent = worldListContent;
    }

    public String getWorldListTitle() {
        return worldListTitle;
    }

    public void setWorldListTitle(String worldListTitle) {
        this.worldListTitle = worldListTitle;
    }

    public String getWorldListNumeroColumn() {
        return worldListNumeroColumn;
    }

    public void setWorldListNumeroColumn(String worldListNumeroColumn) {
        this.worldListNumeroColumn = worldListNumeroColumn;
    }

    public String getWorldListTitresColumn() {
        return worldListTitresColumn;
    }

    public void setWorldListTitresColumn(String worldListTitresColumn) {
        this.worldListTitresColumn = worldListTitresColumn;
    }

    public String getWorldListPrixColumn() {
        return worldListPrixColumn;
    }

    public void setWorldListPrixColumn(String worldListPrixColumn) {
        this.worldListPrixColumn = worldListPrixColumn;
    }

    public String getPlayerListResolvedColumn() {
        return playerListResolvedColumn;
    }

    public void setPlayerListResolvedColumn(String playerListResolvedColumn) {
        this.playerListResolvedColumn = playerListResolvedColumn;
    }

    public String getPlayerListTitle() {
        return playerListTitle;
    }

    public void setPlayerListTitle(String playerListTitle) {
        this.playerListTitle = playerListTitle;
    }

    public String getPlayerListResolvedContentSolved() {
        return playerListResolvedContentSolved;
    }

    public void setPlayerListResolvedContentSolved(String playerListResolvedContentSolved) {
        this.playerListResolvedContentSolved = playerListResolvedContentSolved;
    }

    public String getPlayerListResolvedContentNotSolved() {
        return playerListResolvedContentNotSolved;
    }

    public void setPlayerListResolvedContentNotSolved(String playerListResolvedContentNotSolved) {
        this.playerListResolvedContentNotSolved = playerListResolvedContentNotSolved;
    }

    public String getSelectTitle() {
        return selectTitle;
    }

    public void setSelectTitle(String selectTitle) {
        this.selectTitle = selectTitle;
    }

    public String getSelectCommand() {
        return selectCommand;
    }

    public void setSelectCommand(String selectCommand) {
        this.selectCommand = selectCommand;
    }

    public String getSelectError() {
        return selectError;
    }

    public void setSelectError(String selectError) {
        this.selectError = selectError;
    }

    public String getSelectConfirmBuy() {
        return selectConfirmBuy;
    }

    public void setSelectConfirmBuy(String selectConfirmBuy) {
        this.selectConfirmBuy = selectConfirmBuy;
    }

    public String getSelectConfirmedBuy() {
        return selectConfirmedBuy;
    }

    public void setSelectConfirmedBuy(String selectConfirmedBuy) {
        this.selectConfirmedBuy = selectConfirmedBuy;
    }

    public String getSelectNotEnoughFunds() {
        return selectNotEnoughFunds;
    }

    public void setSelectNotEnoughFunds(String selectNotEnoughFunds) {
        this.selectNotEnoughFunds = selectNotEnoughFunds;
    }

    public String getThumbEnigmePrefix() {
        return thumbEnigmePrefix;
    }

    public void setThumbEnigmePrefix(String thumbEnigmePrefix) {
        this.thumbEnigmePrefix = thumbEnigmePrefix;
    }

    public String getSelectAlreadySolved() {
        return selectAlreadySolved;
    }

    public void setSelectAlreadySolved(String selectAlreadySolved) {
        this.selectAlreadySolved = selectAlreadySolved;
    }

    public String getThumbEnigmeSuffix() {
        return thumbEnigmeSuffix;
    }

    public void setThumbEnigmeSuffix(String thumbEnigmeSuffix) {
        this.thumbEnigmeSuffix = thumbEnigmeSuffix;
    }

    public String getSelectEnigmeTitle() {
        return selectEnigmeTitle;
    }

    public void setSelectEnigmeTitle(String selectEnigmeTitle) {
        this.selectEnigmeTitle = selectEnigmeTitle;
    }

    public String getSelectEnigmeMessage() {
        return selectEnigmeMessage;
    }

    public void setSelectEnigmeMessage(String selectEnigmeMessage) {
        this.selectEnigmeMessage = selectEnigmeMessage;
    }

    public String getSelectEnigmeWrongSolution() {
        return selectEnigmeWrongSolution;
    }

    public void setSelectEnigmeWrongSolution(String selectEnigmeWrongSolution) {
        this.selectEnigmeWrongSolution = selectEnigmeWrongSolution;
    }

    public String getSelectEnigmeRightSolution() {
        return selectEnigmeRightSolution;
    }

    public void setSelectEnigmeRightSolution(String selectEnigmeRightSolution) {
        this.selectEnigmeRightSolution = selectEnigmeRightSolution;
    }

    public String getHelp() {
        return help;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public String getHelpContent() {
        return helpContent;
    }

    public void setHelpContent(String helpContent) {
        this.helpContent = helpContent;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getErrorArguments() {
        return errorArguments;
    }

    public void setErrorArguments(String errorArguments) {
        this.errorArguments = errorArguments;
    }

    public String getDetectFound() {
        return detectFound;
    }

    public void setDetectFound(String detectFound) {
        this.detectFound = detectFound;
    }

    public String getIndiceConfirm() {
        return indiceConfirm;
    }

    public void setIndiceConfirm(String indiceConfirm) {
        this.indiceConfirm = indiceConfirm;
    }

    public String getIndicesNumber() {
        return indicesNumber;
    }

    public void setIndicesNumber(String indicesNumber) {
        this.indicesNumber = indicesNumber;
    }

    public String getIndiceMax() {
        return indiceMax;
    }

    public void setIndiceMax(String indiceMax) {
        this.indiceMax = indiceMax;
    }

    public String getIndiceNew() {
        return indiceNew;
    }

    public void setIndiceNew(String indiceNew) {
        this.indiceNew = indiceNew;
    }

    public String getIndicePresentation() {
        return indicePresentation;
    }

    public void setIndicePresentation(String indicePresentation) {
        this.indicePresentation = indicePresentation;
    }


    public String getForfeit() {
        return forfeit;
    }

    public void setForfeit(String forfeit) {
        this.forfeit = forfeit;
    }

    public String getForfeitConfirm() {
        return forfeitConfirm;
    }

    public void setForfeitConfirm(String forfeitConfirm) {
        this.forfeitConfirm = forfeitConfirm;
    }

    public String getSelectEnigmeTriesMore() {
        return selectEnigmeTriesMore;
    }

    public void setSelectEnigmeTriesMore(String selectEnigmeTriesMore) {
        this.selectEnigmeTriesMore = selectEnigmeTriesMore;
    }

    public String getSelectEnigmeTriesForfeit() {
        return selectEnigmeTriesForfeit;
    }

    public void setSelectEnigmeTriesForfeit(String selectEnigmeTriesForfeit) {
        this.selectEnigmeTriesForfeit = selectEnigmeTriesForfeit;
    }

    public String getTriesMore() {
        return triesMore;
    }

    public void setTriesMore(String triesMore) {
        this.triesMore = triesMore;
    }

    public String getTriesForfeit() {
        return triesForfeit;
    }

    public void setTriesForfeit(String triesForfeit) {
        this.triesForfeit = triesForfeit;
    }

    public String getForfeitEnigmeSolution() {
        return forfeitEnigmeSolution;
    }

    public void setForfeitEnigmeSolution(String forfeitEnigmeSolution) {
        this.forfeitEnigmeSolution = forfeitEnigmeSolution;
    }

    public String getSelectAlreadyForfeit() {
        return selectAlreadyForfeit;
    }

    public void setSelectAlreadyForfeit(String selectAlreadyForfeit) {
        this.selectAlreadyForfeit = selectAlreadyForfeit;
    }

    public String getForfeitPowerConfirm() {
        return forfeitPowerConfirm;
    }

    public void setForfeitPowerConfirm(String forfeitPowerConfirm) {
        this.forfeitPowerConfirm = forfeitPowerConfirm;
    }

    public String getForfeitPower() {
        return forfeitPower;
    }

    public void setForfeitPower(String forfeitPower) {
        this.forfeitPower = forfeitPower;
    }

    public String getForfeitPowerDisplay() {
        return forfeitPowerDisplay;
    }

    public void setForfeitPowerDisplay(String forfeitPowerDisplay) {
        this.forfeitPowerDisplay = forfeitPowerDisplay;
    }

    public String getPowerConfirmIndice() {
        return powerConfirmIndice;
    }

    public void setPowerConfirmIndice(String powerConfirmIndice) {
        this.powerConfirmIndice = powerConfirmIndice;
    }

    public String getPowerConfirmPassEnigme() {
        return powerConfirmPassEnigme;
    }

    public void setPowerConfirmPassEnigme(String powerConfirmPassEnigme) {
        this.powerConfirmPassEnigme = powerConfirmPassEnigme;
    }

    public String getPowerNotEnough() {
        return powerNotEnough;
    }

    public void setPowerNotEnough(String powerNotEnough) {
        this.powerNotEnough = powerNotEnough;
    }

    public String getPowerDisplay() {
        return powerDisplay;
    }

    public void setPowerDisplay(String powerDisplay) {
        this.powerDisplay = powerDisplay;
    }

    public String getPowerPassEnigme() {
        return powerPassEnigme;
    }

    public void setPowerPassEnigme(String powerPassEnigme) {
        this.powerPassEnigme = powerPassEnigme;
    }

    public String getPowerIndice() {
        return powerIndice;
    }

    public void setPowerIndice(String powerIndice) {
        this.powerIndice = powerIndice;
    }

    public String getIndiceDetectNumber() {
        return indiceDetectNumber;
    }

    public void setIndiceDetectNumber(String indiceDetectNumber) {
        this.indiceDetectNumber = indiceDetectNumber;
    }

    public String getIndiceDetectCost() {
        return indiceDetectCost;
    }

    public void setIndiceDetectCost(String indiceDetectCost) {
        this.indiceDetectCost = indiceDetectCost;
    }

    public String getIndiceDetectPowered() {
        return indiceDetectPowered;
    }

    public void setIndiceDetectPowered(String indiceDetectPowered) {
        this.indiceDetectPowered = indiceDetectPowered;
    }

    public String getDetectForfeit() {
        return detectForfeit;
    }

    public void setDetectForfeit(String detectForfeit) {
        this.detectForfeit = detectForfeit;
    }

    public String getIndicesCost() {
        return indicesCost;
    }

    public void setIndicesCost(String indicesCost) {
        this.indicesCost = indicesCost;
    }

    public String getPowerConfirmIndiceMax() {
        return powerConfirmIndiceMax;
    }

    public void setPowerConfirmIndiceMax(String powerConfirmIndiceMax) {
        this.powerConfirmIndiceMax = powerConfirmIndiceMax;
    }

    public String getBonusPercentage() {
        return bonusPercentage;
    }

    public void setBonusPercentage(String bonusPercentage) {
        this.bonusPercentage = bonusPercentage;
    }

    public String getBonusPowers() {
        return bonusPowers;
    }

    public void setBonusPowers(String bonusPowers) {
        this.bonusPowers = bonusPowers;
    }

    public String getBonusTries() {
        return bonusTries;
    }

    public void setBonusTries(String bonusTries) {
        this.bonusTries = bonusTries;
    }

    public String getSelectEnigmeRightSolutionWithBonus() {
        return selectEnigmeRightSolutionWithBonus;
    }

    public void setSelectEnigmeRightSolutionWithBonus(String selectEnigmeRightSolutionWithBonus) {
        this.selectEnigmeRightSolutionWithBonus = selectEnigmeRightSolutionWithBonus;
    }

    public String getSelectEnigmeRightSolutionWithBonusError() {
        return selectEnigmeRightSolutionWithBonusError;
    }

    public void setSelectEnigmeRightSolutionWithBonusError(String selectEnigmeRightSolutionWithBonusError) {
        this.selectEnigmeRightSolutionWithBonusError = selectEnigmeRightSolutionWithBonusError;
    }

    public String getPassEnigmePowerCost() {
        return passEnigmePowerCost;
    }

    public void setPassEnigmePowerCost(String passEnigmePowerCost) {
        this.passEnigmePowerCost = passEnigmePowerCost;
    }

    public String getIndicePowerCost() {
        return indicePowerCost;
    }

    public void setIndicePowerCost(String indicePowerCost) {
        this.indicePowerCost = indicePowerCost;
    }

    public String getPowerConfirmGetEnigme() {
        return powerConfirmGetEnigme;
    }

    public void setPowerConfirmGetEnigme(String powerConfirmGetEnigme) {
        this.powerConfirmGetEnigme = powerConfirmGetEnigme;
    }

    public String getBuyEnigmePowerCost() {
        return buyEnigmePowerCost;
    }

    public void setBuyEnigmePowerCost(String buyEnigmePowerCost) {
        this.buyEnigmePowerCost = buyEnigmePowerCost;
    }

    public String getSelectPrivateChannel() {
        return selectPrivateChannel;
    }

    public void setSelectPrivateChannel(String selectPrivateChannel) {
        this.selectPrivateChannel = selectPrivateChannel;
    }

    public String getSelectPublicChannel() {
        return selectPublicChannel;
    }

    public void setSelectPublicChannel(String selectPublicChannel) {
        this.selectPublicChannel = selectPublicChannel;
    }

    public String getSelectPublicEnigmeMessage() {
        return selectPublicEnigmeMessage;
    }

    public void setSelectPublicEnigmeMessage(String selectPublicEnigmeMessage) {
        this.selectPublicEnigmeMessage = selectPublicEnigmeMessage;
    }

    public String getIndicePowered() {
        return indicePowered;
    }

    public void setIndicePowered(String indicePowered) {
        this.indicePowered = indicePowered;
    }

    public String getIndicePoweredAvailable() {
        return indicePoweredAvailable;
    }

    public void setIndicePoweredAvailable(String indicePoweredNotMax) {
        this.indicePoweredAvailable = indicePoweredNotMax;
    }

    public String getIndicePoweredMax() {
        return indicePoweredMax;
    }

    public void setIndicePoweredMax(String indicePoweredMax) {
        this.indicePoweredMax = indicePoweredMax;
    }

    public String getWinPower() {
        return winPower;
    }

    public void setWinPower(String winPower) {
        this.winPower = winPower;
    }

    public String getPowerMaxLevel1() {
        return powerMaxLevel1;
    }

    public void setPowerMaxLevel1(String powerMaxLevel1) {
        this.powerMaxLevel1 = powerMaxLevel1;
    }

    public String getPowersDetectUpdated() {
        return powersDetectUpdated;
    }

    public void setPowersDetectUpdated(String powersDetectUpdated) {
        this.powersDetectUpdated = powersDetectUpdated;
    }

    public String getWorldListMessage() {
        return worldListMessage;
    }

    public void setWorldListMessage(String worldListMessage) {
        this.worldListMessage = worldListMessage;
    }

    public String getPlayerListMessage() {
        return playerListMessage;
    }

    public void setPlayerListMessage(String playerListMessage) {
        this.playerListMessage = playerListMessage;
    }

    public String getSelectEnigmePrivateMessage() {
        return selectEnigmePrivateMessage;
    }

    public void setSelectEnigmePrivateMessage(String selectEnigmePrivateMessage) {
        this.selectEnigmePrivateMessage = selectEnigmePrivateMessage;
    }
}