/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.songjilong.modules.movie.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import me.zhengjie.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-08
**/
@Entity
@Data
@Table(name="biz_movie_info")
public class MovieInfo extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "movie_info_id")
    @ApiModelProperty(value = "电影信息-主键")
    private Long movieInfoId;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "名称")
    private String name;

    @Column(name = "name_en")
    @ApiModelProperty(value = "英文名称")
    private String nameEn;

    @Column(name = "img_name")
    @ApiModelProperty(value = "海报名（图片名称）")
    private String imgName;

    @Column(name = "img")
    @ApiModelProperty(value = "海报（图片路径）")
    private String img;

    @Column(name = "type")
    @ApiModelProperty(value = "类型（英文逗号分隔）")
    private String type;

    @Column(name = "language")
    @ApiModelProperty(value = "语言")
    private String language;

    @Column(name = "duration")
    @ApiModelProperty(value = "时长")
    private String duration;

    @Column(name = "release_date")
    @ApiModelProperty(value = "上映日期")
    private Timestamp releaseDate;

    @Column(name = "release_location")
    @ApiModelProperty(value = "上映地点")
    private String releaseLocation;

    @Column(name = "score")
    @ApiModelProperty(value = "综合评分")
    private Double score;

    @Column(name = "evaluator")
    @ApiModelProperty(value = "评价人数")
    private Integer evaluator;

    @Column(name = "details")
    @ApiModelProperty(value = "剧情简介")
    private String details;

    @Column(name = "video")
    @ApiModelProperty(value = "预告片")
    private String video;

    @Column(name = "director")
    @ApiModelProperty(value = "导演（英文逗号分隔）")
    private String director;

    @Column(name = "actor")
    @ApiModelProperty(value = "演员（英文逗号分隔）")
    private String actor;

    public void copy(MovieInfo source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}