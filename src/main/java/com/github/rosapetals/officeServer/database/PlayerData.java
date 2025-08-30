package com.github.rosapetals.officeServer.database;

import lombok.Getter;
import lombok.Setter;

public class PlayerData {

    @Getter
    @Setter
    private int uuid;

    @Getter
    @Setter
    private int washingMachineLevel;

    @Getter
    @Setter
    private int dryerLevel;



}
