package com.bot.someweire.helper;

import com.bot.someweire.configuration.EnigmeProperties;
import com.bot.someweire.configuration.WorldsProperties;
import com.bot.someweire.model.Enigme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WorldHelper {

    @Autowired
    EnigmeProperties enigmeProperties;

    @Autowired
    WorldsProperties worldsProperties;

    public String getWorldCurrency(String worldName){
        String currency = "";

        if(worldName.equals(worldsProperties.getNameSatorion())){
            currency = enigmeProperties.getNameWeivellite();
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            currency = enigmeProperties.getNameWeither();
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            currency = enigmeProperties.getNameWeillenium();
        }
        return currency;
    }

    public String getWorldColor(String worldName){
        String color = "";

        if(worldName.equals(worldsProperties.getNameSatorion())){
            color = worldsProperties.getColorSatorion();
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            color = worldsProperties.getColorSzasgard();
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            color = worldsProperties.getColorFinn();
        }
        return color;
    }

    public String getWorldLieuThumb(String worldName){
        String lieuThumb = "";

        if(worldName.equals(worldsProperties.getNameSatorion())){
            lieuThumb = worldsProperties.getThumbSatorion();
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            lieuThumb = worldsProperties.getThumbSzasgard();
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            lieuThumb = worldsProperties.getThumbFinn();
        }
        return lieuThumb;
    }

    public String getWorldCurrencyThumb(String worldName){
        String currencyThumb = "";

        if(worldName.equals(worldsProperties.getNameSatorion())){
            currencyThumb = enigmeProperties.getThumbWeivellite();
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            currencyThumb = enigmeProperties.getThumbWeither();
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            currencyThumb = enigmeProperties.getThumbWeillenium();
        }
        return currencyThumb;
    }

    public String getWorldEnigmeThumbPath(String worldName){
        String enigmeThumbPath = "";

        if(worldName.equals(worldsProperties.getNameSatorion())){
            enigmeThumbPath = enigmeProperties.getThumbPathSatorion();
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            enigmeThumbPath = enigmeProperties.getThumbPathSzasgard();
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            enigmeThumbPath = enigmeProperties.getThumbPathFinn();
        }
        return enigmeThumbPath;
    }

    public String getWorldLieuThumbPath(String worldName){
        String lieuThumbPath = "";

        if(worldName.equals(worldsProperties.getNameSatorion())){
            lieuThumbPath = worldsProperties.getThumbPathSatorion();
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            lieuThumbPath = worldsProperties.getThumbPathSzasgard();
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            lieuThumbPath = worldsProperties.getThumbPathFinn();
        }
        return lieuThumbPath;
    }

    public int getWorldNumberByName(String worldName){
        int worldNumber = 0;

        if(worldName.equals(worldsProperties.getNameSatorion())){
            worldNumber = 1;
        } else if(worldName.equals(worldsProperties.getNameSzasgard())) {
            worldNumber = 2;
        } else if(worldName.equals(worldsProperties.getNameFinn())) {
            worldNumber = 3;
        }
        return worldNumber;
    }
}
