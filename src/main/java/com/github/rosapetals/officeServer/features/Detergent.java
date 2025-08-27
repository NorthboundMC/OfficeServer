package com.github.rosapetals.officeServer.features;

import com.github.rosapetals.officeServer.utils.CC;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionType;

public enum Detergent {
        LOW_GRADE_DETERGENT(CC.translate("&f&9Low-Grade Detergent"), new DetergentData(1.25, 0.2, CC.translate("&f&9Low-Grade Detergent"), new PotionData(PotionType.WEAKNESS, false, false), "", 0)),
        STAIN_BLASTER_AND_REFRESHER(CC.translate("&f&9Stain-Blaster & Refresher"), new DetergentData(2, 0.5, CC.translate("&f&9LowStain-Blaster & Refresher"), new PotionData(PotionType.SLOW_FALLING, false, false), "", 1)),
        SUPER_CLEANER_DETERGENT(CC.translate("&f&9Super-Cleaner™ Detergent"), new DetergentData(5, 1.15, CC.translate("&f&9LowSuper-Cleaner™ Detergent"), new PotionData(PotionType.SPEED, false, false), "", 2)),
        ONE_WASH_AWAY_ULTRA_CLEAN(CC.translate("&f&9One-Wash-Away Ultra-Clean"), new DetergentData(10, 2, CC.translate("&f&9LowOne-Wash-Away Ultra-Clean"), new PotionData(PotionType.NIGHT_VISION, false, false), "", 3)),
        CLEANBLASTER_WITH_ULTRA_SCENT(CC.translate("&f&9Cleanblaster with Ultra-Scent"), new DetergentData(25, 4.5, CC.translate("&f&9LowCleanblaster with Ultra-Scent"), new PotionData(PotionType.REGEN, false, false), "", 4)),
        SOFT_AND_COMFY_GRANDMAS_EMBRACE(CC.translate("&f&9Soft" + "&f&9&"  + "&f&9Comfy Grandma's Embrace"), new DetergentData(100, 15, CC.translate("&f&9Soft" + "&f&9&"  + "&f&9Comfy Grandma's Embrace"), new PotionData(PotionType.LUCK, false, false), "", 5)),
        MEGA_FRESH_PREMIUM_ULTRA(CC.translate("&f&9Mega Fresh Premium Ultra"), new DetergentData(500, 50, CC.translate("&f&9LowMega Fresh Premium Ultra"), new PotionData(PotionType.STRENGTH, false, false), "", 6)),
        REDICAL_LYFE(CC.translate("&f&9Redical Lyfe"), new DetergentData(1000, 90, CC.translate("&f&9Redical Lyfe"), new PotionData(PotionType.INSTANT_HEAL, false, false), "", 7)),
        CLEANER_X(CC.translate("&f&9Cleaner X"), new DetergentData(50000, 20000, CC.translate("&f&9Cleaner X"), null, "", 8)),
        MAGICLEAN(CC.translate("&f&9MagiClean"), new DetergentData(0.1, 100000, CC.translate("&f&9MagiClean"), null, "", 22));


    @Getter
    private final String nameID;
    @Getter
    private final DetergentData detergentData;




    Detergent(String nameID, DetergentData foodData) {
        this.nameID = nameID;
        this.detergentData = foodData;

    }

    public static DetergentData fromName(String nameID) {
        for (Detergent e : values()) {
            if (e.nameID.equals(CC.translate(nameID))) {
                return e.detergentData;
            }
        }


        return null;
    }

    public DetergentData getDetergentData() {
        return detergentData;
    }


}
