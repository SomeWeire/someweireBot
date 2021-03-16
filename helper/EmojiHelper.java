package com.bot.someweire.helper;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Component
public class EmojiHelper {

    public String getPortailEmoji(){return "\uD83C\uDF00";}

    public String getPowerEmoji(){return "✨";}

    public String getForfeitPowerEmoji(){return "\uD83C\uDF1F";}

    public String getForfeitEmoji(){return "\uD83C\uDFF3";}

    public String getConfirmEmoji(){return "✅";}

    public String getCancelEmoji(){return "❎";}

    public String getIndiceEmoji(){return "\uD83D\uDD2E";}

    public String getFragmentEmoji(){return "\uD83D\uDC8E";}

    public String getEnigmeListEmoji(){return "\uD83D\uDCDC";}

    public String getUpEmoji(){return "⬆";}

    public String getDownEmoji(){return "⬇";}

    public String getLeftEmoji(){return "⬅";}

    public String getRightEmoji(){return "➡";}

    public String getBackEmoji(){return "↩";}

    public String getDetectCommandEmoji(){return "\uD83D\uDCE1";}

    public String getRankingmoji(){return "\uD83C\uDFC6";}

    public List<String> getRoadChoiceEmojis(){
        LinkedList<String> emojis = new LinkedList<>(Arrays.asList(
                "1️⃣",
                "2️⃣",
                "3️⃣",
                "4️⃣",
                "5️⃣",
                "6️⃣",
                "7️⃣",
                "8️⃣",
                "9️⃣",
                "\uD83D\uDD1F"));
        return emojis;
    }

    public String getProfileEmoji(){ return "\uD83D\uDC64"; }

    public String getFirstfPlaceEmoji(){return "\uD83E\uDD47"; }

    public String getSecondPlaceEmoji(){return "\uD83E\uDD48"; }

    public String getThirdPlaceEmoji(){return "\uD83E\uDD49"; }

}
