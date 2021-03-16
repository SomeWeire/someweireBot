package com.bot.someweire.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "menu")
public class MenuProperties {

    private String profil;
    private String help;
    private String helpContent;
    private String command;
    private String title;
    private String errorArguments;
    private String notPublic;

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
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

    public String getNotPublic() {
        return notPublic;
    }

    public void setNotPublic(String notPublic) {
        this.notPublic = notPublic;
    }
}
