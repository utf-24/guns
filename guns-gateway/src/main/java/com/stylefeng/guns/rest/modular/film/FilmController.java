package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.api.film.vo.CatVO;
import com.stylefeng.guns.api.film.vo.SourceVO;
import com.stylefeng.guns.api.film.vo.YearVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @author young
 * @description 影片相关请求接口
 * @date 2018/11/11 12:43
 **/
@RestController
@RequestMapping("/film/")
public class FilmController {
    @Reference(interfaceClass = FilmServiceApi.class,check = false)
    FilmServiceApi filmServiceApi;

    private static final String img_pre = "http://img.meetingshop.cn/";

    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO<FilmIndexVo> getIndex(){
        FilmIndexVo filmIndexVo = new FilmIndexVo();
        //获取banner信息
        filmIndexVo.setBanners(filmServiceApi.getBanners());

        //获取热映电影
        filmIndexVo.setHotFilms(filmServiceApi.getHotFilms(true,8));
        //获取即将上映的电影
        filmIndexVo.setExpectRanking(filmServiceApi.getExpectRanking());

        //票房排行榜
        filmIndexVo.setBoxRanking(filmServiceApi.getBoxRanking());

        //获取最受欢迎的榜单
        filmIndexVo.setExpectRanking(filmServiceApi.getExpectRanking());

        //获取前一百
        filmIndexVo.setTop_100(filmServiceApi.getTop());

        return ResponseVO.success(img_pre, filmIndexVo);
    }

    @RequestMapping(value = "getConditionList",method = RequestMethod.GET)
    public ResponseVO getConditonList(@RequestParam(name = "catId",required = false,
                                                    defaultValue = "99") String catId,
                                      @RequestParam(name = "sourceId",required = false,
                                              defaultValue = "99") String sourceId,
                                      @RequestParam(name = "yearId",required = false,
                                              defaultValue = "99") String yearId){

        FilmConditionVO filmConditionVO = new FilmConditionVO();
        //处理分类信息
        boolean flag = false;
        List<CatVO> cats = filmServiceApi.getCats();
        List<CatVO> catResult = new ArrayList<>();
        CatVO cat = new CatVO();

        for(CatVO catVO: cats){
            if(catVO.getCatId().equals("99")){
                cat= catVO;
                continue;
            }
            if(catVO.getCatId().equals(catId)){
                flag = true;
                catVO.setActive(true);
            }else{
                catVO.setActive(false);
            }
            catResult.add(catVO);
        }
        //如果id不存在，显示所有
        if(!flag){
            cat.setActive(true);
            catResult.add(cat);
        }else{
            cat.setActive(false);
            catResult.add(cat);
        }

        // 片源集合
        flag=false;
        List<SourceVO> sources = filmServiceApi.getSources();
        List<SourceVO> sourceResult = new ArrayList<>();
        SourceVO sourceVO = null;
        for(SourceVO source : sources){
            if(source.getSourceId().equals("99")){
                sourceVO = source;
                continue;
            }
            if(source.getSourceId().equals(catId)){
                flag = true;
                source.setActive(true);
            }else{
                source.setActive(false);
            }
            sourceResult.add(source);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flag){
            sourceVO.setActive(true);
            sourceResult.add(sourceVO);
        }else{
            sourceVO.setActive(false);
            sourceResult.add(sourceVO);
        }

        // 年代集合
        flag=false;
        List<YearVO> years = filmServiceApi.getYears();
        List<YearVO> yearResult = new ArrayList<>();
        YearVO yearVO = null;
        for(YearVO year : years){
            if(year.getYearId().equals("99")){
                yearVO = year;
                continue;
            }
            if(year.getYearId().equals(catId)){
                flag = true;
                year.setActive(true);
            }else{
                year.setActive(false);
            }
            yearResult.add(year);
        }
        // 如果不存在，则默认将全部变为Active状态
        if(!flag){
            yearVO.setActive(true);
            yearResult.add(yearVO);
        }else{
            yearVO.setActive(false);
            yearResult.add(yearVO);
        }

        filmConditionVO.setCatInfo(catResult);
        filmConditionVO.setSourceInfo(sourceResult);
        filmConditionVO.setYearInfo(yearResult);

        return ResponseVO.success(filmConditionVO);

    }
}