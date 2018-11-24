package com.stylefeng.guns.api.film;


import com.stylefeng.guns.api.film.vo.*;

import java.util.List;

public interface FilmServiceApi {

    //获取banners
    List<BannerVO> getBanners();

    //获取热映影片
    FilmVO getHotFilms(FilmSearchVO filmSearchVO);

    //获取即将上映影片
    FilmVO getSoonFilms(FilmSearchVO filmSearchVO);

    //获取经典影片

    FilmVO getClassicFilms(FilmSearchVO filmSearchVO);

    //获取票房排行榜
    List<FilmInfo> getBoxRanking();

    //获取人气排行榜
    List<FilmInfo> getExpectRanking();

    //获取top_100
    List<FilmInfo> getTop();

    // ==== 获取影片条件接口
    // 分类条件
    List<CatVO> getCats();
    // 片源条件
    List<SourceVO> getSources();
    // 获取年代条件
    List<YearVO> getYears();

    // 根据影片ID或者名称获取影片信息
    FilmDetailVO getFilmDetail(int searchType,String searchParam);

    // 获取影片描述信息
    FilmDescVO getFilmDesc(String filmId);

    // 获取图片信息
    ImgVO getImgs(String filmId);

    // 获取导演信息
    ActorVO getDectInfo(String filmId);

    // 获取演员信息
    List<ActorVO> getActors(String filmId);

}
