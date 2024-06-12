package com.bestrookie.springframework.dto.cachedto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * @Author bestrookie
 * @Date 2024/6/5 14:49
 * @Desc
 */
public class Husband {


    private String wifiName;

    private LocalDate marriageDate;

    public String getWifiName() {
        return wifiName;
    }

    public void setWifiName(String wifiName) {
        this.wifiName = wifiName;
    }

    public LocalDate getMarriageDate() {
        return marriageDate;
    }

    public void setMarriageDate(LocalDate marriageDate) {
        this.marriageDate = marriageDate;
    }

    @Override
    public String toString() {
        return "Husband{" +
                "wifiName='" + wifiName + '\'' +
                ", marriageDate=" + marriageDate +
                '}';
    }

}

