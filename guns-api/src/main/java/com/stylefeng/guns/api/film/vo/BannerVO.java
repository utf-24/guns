package com.stylefeng.guns.api.film.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author young
 * @description
 * @date 2018/11/11 13:46
 **/
@Data
public class BannerVO implements Serializable {
    private String BannerId;
    private String BannerAddress;
    private String BannerUrl;
}
