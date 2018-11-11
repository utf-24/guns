package com.stylefeng.guns.rest.modular.film;

import com.stylefeng.guns.api.film.vo.BannerVO;
import com.stylefeng.guns.rest.modular.film.vo.FilmIndexVo;
import com.stylefeng.guns.rest.modular.vo.ResponseVO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author young
 * @description 影片相关请求接口
 * @date 2018/11/11 12:43
 **/
@RestController
@RequestMapping("/film")
public class FilmController {

    @RequestMapping(value = "getIndex",method = RequestMethod.GET)
    public ResponseVO<FilmIndexVo> getIndex(){
        BannerVO bannerVO = new BannerVO();

        //获取banner信息
        //获取热映电影
        //获取即将上映的电影
        //票房排行榜
        //获取最受欢迎的榜单
        //获取前一百
        return null;
    }
}
