package com.stylefeng.guns.api.film.vo;

import lombok.Data;

/**
 * @author young
 * @description 影片查询参数
 * @date 2018/11/19 1:12
 **/
@Data
public class FilmSearchVO {
    private Integer showType=1;
    private Integer sortId=1;
    private Integer sourceId=99;
    private Integer catId=99;
    private Integer yearId=99;
    private Integer nowPage=1;
    private Integer pageSize=18;
    boolean isLimit = false;
}
