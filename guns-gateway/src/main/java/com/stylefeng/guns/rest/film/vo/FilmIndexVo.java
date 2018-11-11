package com.stylefeng.guns.rest.film.vo;

import lombok.Data;

import java.util.List;

/**
 * @author young
 * @description 影片首页对象
 * @date 2018/11/11 13:07
 **/
@Data
public class FilmIndexVo {

    private List<BannerVO> banners;
    private FilmVO hotFilms;
    private FilmVO soonFilms;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRanking;
    private List<FilmInfo> top_100;

}
