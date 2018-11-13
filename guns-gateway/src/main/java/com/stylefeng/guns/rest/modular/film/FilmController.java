package com.stylefeng.guns.rest.modular.film;

import com.alibaba.dubbo.config.annotation.Reference;
import com.stylefeng.guns.api.film.FilmServiceApi;
import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young
 * @description 影片相关请求接口
 * @date 2018/11/11 12:43
 **/
@RestController
@RequestMapping("/film")
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

    @RequestMapping(value = "getConditonList",method = RequestMethod.GET)
    public ResponseVO getConditonList(@RequestParam(name = "catId",required = false,
                                                    defaultValue = "99") String catId,
                                      @RequestParam(name = "sourceId",required = false,
                                              defaultValue = "99") String sourceId,
                                      @RequestParam(name = "yearId",required = false,
                                              defaultValue = "99") String yearId){
        return null;

    }
}
