package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author young
 * @description
 * @date 2018/11/11 14:12
 **/
@Data
public class FilmVO implements Serializable{
    private int fileNum;
    private List<FilmInfo> filmInfos;
}
