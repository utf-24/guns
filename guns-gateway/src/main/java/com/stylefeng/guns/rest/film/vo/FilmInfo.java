package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

/**
 * @author young
 * @description 影片对象
 * @date 2018/11/11 13:59
 **/
@Data
public class FilmInfo {
    private String filmId;
    private int filmType;
    private String imageAddress;
    private String filmName;
    private String filmScore;
    private int expectNum;
    private String showTime;
    private int boxNum;
}
