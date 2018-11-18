package com.stylefeng.guns.rest.modular.film.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.*;
import com.stylefeng.guns.core.util.DateUtil;
import com.stylefeng.guns.rest.common.persistence.dao.*;
import com.stylefeng.guns.rest.common.persistence.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young
 * @description
 * @date 2018/11/11 17:10
 **/
@Component
@Service(interfaceClass = FilmServiceApi.class)
public class DefaultServiceImpl implements FilmServiceApi {
    @Autowired
    MoocBannerTMapper moocBannerTMapper;

    @Autowired
    MoocFilmTMapper moocFilmTMapper;

    @Autowired
    MoocCatDictTMapper moocCatDictTMapper;

    @Autowired
    MoocYearDictTMapper moocYearDictTMapper;

    @Autowired
    MoocSourceDictTMapper moocSourceDictTMapper;

    @Override
    public List<BannerVO> getBanners() {
        List<BannerVO> result = new ArrayList<>();
        //全选
        List<MoocBannerT> moocBannerTS =moocBannerTMapper.selectList(null);
        for(MoocBannerT moocBannerT: moocBannerTS){
            BannerVO bannerVO = new BannerVO();
            bannerVO.setBannerUrl(moocBannerT.getBannerUrl());
            bannerVO.setBannerAddress(moocBannerT.getBannerAddress());
            bannerVO.setBannerId(moocBannerT.getUuid()+"");
            result.add(bannerVO);
        }
        return result;
    }
    //影片状态,1-正在热映，2-即将上映，3-经典影片
    public  FilmVO searchFilmByStatus(int film_status,FilmSearchVO filmSearchVO){
        List<FilmInfo> filmInfos = new ArrayList<>();
        FilmVO filmVO = new FilmVO();

        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        //过滤条件 1->正在热映
        entityWrapper.eq("film_status",film_status);

        //根据isLimit判断是否是首页，限制条数
        if(filmSearchVO.isLimit()){
            // 如果是，则限制条数、限制内容为热映影片
            Page<MoocFilmT> page = new Page<>(1,filmSearchVO.getPageSize());
            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page,entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFileNum(moocFilmTS.size());
            filmVO.setFilmInfos(filmInfos);
        }else {
            // 如果不是，则是列表页，同样需要限制内容为热映影片
            Page<MoocFilmT> page = null;
            //根据sortId的不同封装不同的page对象
            // 1-按热门搜索，2-按时间搜索，3-按评价搜索
            switch (filmSearchVO.getSortId()){
                case 1:
                    page = new Page<>(filmSearchVO.getNowPage(),filmSearchVO.getPageSize(),"film_box_office");
                    break;
                case 2 :
                    page = new Page<>(filmSearchVO.getNowPage(),filmSearchVO.getPageSize(),"film_time");
                    break;
                case 3 :
                    page = new Page<>(filmSearchVO.getNowPage(),filmSearchVO.getPageSize(),"film_score");
                    break;
                default:
                    page = new Page<>(filmSearchVO.getNowPage(),filmSearchVO.getPageSize(),"film_box_office");
                    break;
            }
            // 如果sourceId,yearId,catId 不为99 ,则表示要按照对应的编号进行查询

            if(!(filmSearchVO.getSourceId() ==99)){
                entityWrapper.eq("film_source",filmSearchVO.getSourceId());
            }
            if(!(filmSearchVO.getYearId()==99)){
                entityWrapper.eq("film_date",filmSearchVO.getYearId());
            }
            if(!(filmSearchVO.getCatId() ==99)){
                entityWrapper.eq("film_cats",filmSearchVO.getCatId());
            }

            List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page,entityWrapper);
            filmInfos = getFilmInfos(moocFilmTS);
            filmVO.setFilmInfos(filmInfos);
            filmVO.setFileNum(filmInfos.size());
            int totalNum = moocFilmTMapper.selectCount(entityWrapper);
            int totalPage = (totalNum/filmSearchVO.getPageSize())+1;
            filmVO.setNowPage(filmSearchVO.getNowPage());
            filmVO.setTotalPage(totalPage);
        }

        return filmVO;
    }
    @Override
    public FilmVO getHotFilms(FilmSearchVO filmSearchVO) {
        FilmVO filmVO = searchFilmByStatus(1,filmSearchVO);
        return filmVO;
    }

    @Override
    public FilmVO getSoonFilms(FilmSearchVO filmSearchVO) {
        FilmVO filmVO = searchFilmByStatus(2,filmSearchVO);
        return filmVO;
    }

    @Override
    public FilmVO getClassicFilms(FilmSearchVO filmSearchVO) {
        FilmVO filmVO = searchFilmByStatus(3,filmSearchVO);
        return filmVO;
    }

    @Override
    public List<FilmInfo> getBoxRanking() {
        //条件-》正在上映的票房前十名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MoocFilmT> page = new Page<>(1,10,"film_box_office");
        List<MoocFilmT> filmInfos = moocFilmTMapper.selectPage(page,entityWrapper);
        return getFilmInfos(filmInfos);
    }

    @Override
    public List<FilmInfo> getExpectRanking()
    {
        //条件-》获取即将上映的前十名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status",2);

        Page<MoocFilmT> page = new Page<>(1,10,"film_preSaleNum");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page,entityWrapper);

        return getFilmInfos(moocFilmTS);
    }

    @Override
    public List<FilmInfo> getTop()
    {
        //条件-》正在上映的评分前十名
        EntityWrapper<MoocFilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("film_status","1");

        Page<MoocFilmT> page = new Page<>(1,10,"film_score");
        List<MoocFilmT> moocFilmTS = moocFilmTMapper.selectPage(page,entityWrapper);

        return getFilmInfos(moocFilmTS);
    }

    @Override
    public List<CatVO> getCats() {
        List<CatVO> cats = new ArrayList<>();
        //查询分类实体
        List<MoocCatDictT> moocCatDictTS = moocCatDictTMapper.selectList(null);

        for (MoocCatDictT moocCatDictT:moocCatDictTS){
            CatVO catVO = new CatVO();
            catVO.setCatName(moocCatDictT.getShowName());
            catVO.setCatId(moocCatDictT.getUuid()+"");

            cats.add(catVO);
        }
        return cats;
    }

    @Override
    public List<SourceVO> getSources() {
        List<SourceVO> sourceVOList = new ArrayList<>();
        List<MoocSourceDictT> moocSourceDictTS = moocSourceDictTMapper.selectList(null);

        for (MoocSourceDictT moocSourceDictT:moocSourceDictTS){
            SourceVO sourceVO = new SourceVO();
            sourceVO.setSourceId(moocSourceDictT.getUuid()+"");
            sourceVO.setSourceName(moocSourceDictT.getShowName());

            sourceVOList.add(sourceVO);
        }
        return sourceVOList;
    }

    @Override
    public List<YearVO> getYears() {
        List<YearVO> yearVOList = new ArrayList<>();
        List<MoocYearDictT> moocYearDictTS = moocYearDictTMapper.selectList(null);

        for(MoocYearDictT moocYearDictT: moocYearDictTS){
            YearVO yearVO = new YearVO();
            yearVO.setYearId(moocYearDictT.getUuid()+"");
            yearVO.setYearName(moocYearDictT.getShowName());

            yearVOList.add(yearVO);
        }
        return yearVOList;
    }

    private List<FilmInfo> getFilmInfos(List<MoocFilmT> moocFilms){
        List<FilmInfo> filmInfos = new ArrayList<>();
        for(MoocFilmT moocFilmT : moocFilms){
            FilmInfo filmInfo = new FilmInfo();
            filmInfo.setScore(moocFilmT.getFilmScore());
            filmInfo.setImgAddress(moocFilmT.getImgAddress());
            filmInfo.setFilmType(moocFilmT.getFilmType());
            filmInfo.setFilmScore(moocFilmT.getFilmScore());
            filmInfo.setFilmName(moocFilmT.getFilmName());
            filmInfo.setFilmId(moocFilmT.getUuid()+"");
            filmInfo.setExpectNum(moocFilmT.getFilmPresalenum());
            filmInfo.setBoxNum(moocFilmT.getFilmBoxOffice());
            filmInfo.setShowTime(DateUtil.getDay(moocFilmT.getFilmTime()));

            // 将转换的对象放入结果集
            filmInfos.add(filmInfo);
        }

        return filmInfos;
    }

}
