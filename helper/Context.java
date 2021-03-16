package com.bot.someweire.helper;

import com.bot.someweire.model.*;
import com.bot.someweire.model.Character;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Context {

    public enum objects{
        PLAYER,
        LIEU,
        REGION,
        WORLD,
        ENIGMES,
        SELECTEDENIGME,
        INDICES,
        FRAGMENTS,
        ROADS
    }

    private Character player;
    private Lieu lieu;
    private List<Lieu> roads;
    private Region region;
    private World world;
    private List<Enigme> enigmeList;
    private Enigme selectedEnigme;
    private List<Indice> indiceList;
    private List<Fragment> fragmentList;

    public Character getPlayer() {
        return player;
    }

    public void setPlayer(Character player) {
        this.player = player;
    }

    public Lieu getLieu() {
        return lieu;
    }

    public void setLieu(Lieu lieu) {
        this.lieu = lieu;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public List<Enigme> getEnigmeList() {
        return enigmeList;
    }

    public void setEnigmeList(List<Enigme> enigmeList) {
        this.enigmeList = enigmeList;
    }

    public Enigme getSelectedEnigme() {
        return selectedEnigme;
    }

    public void setSelectedEnigme(Enigme selectedEnigme) {
        this.selectedEnigme = selectedEnigme;
    }

    public List<Indice> getIndiceList() {
        return indiceList;
    }

    public void setIndiceList(List<Indice> indiceList) {
        this.indiceList = indiceList;
    }

    public List<Fragment> getFragmentList() {
        return fragmentList;
    }

    public void setFragmentList(List<Fragment> fragmentList) {
        this.fragmentList = fragmentList;
    }

    public List<Lieu> getRoads() {
        return roads;
    }

    public void setRoads(List<Lieu> roads) {
        this.roads = roads;
    }
}
