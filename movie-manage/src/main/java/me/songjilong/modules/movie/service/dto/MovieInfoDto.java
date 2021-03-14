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
package me.songjilong.modules.movie.service.dto;

import lombok.Data;
import me.zhengjie.base.BaseDTO;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-08
**/
@Data
public class MovieInfoDto extends BaseDTO implements Serializable {

    /** 电影信息-主键 */
    private Long movieInfoId;

    /** 名称 */
    private String name;

    /** 英文名称 */
    private String nameEn;

    /** 海报（图片名称） */
    private String imgName;

    /** 海报（图片路径） */
    private String img;

    /** 类型（英文逗号分隔） */
    private String type;

    /** 语言 */
    private String language;

    /** 时长 */
    private String duration;

    /** 上映日期 */
    private Timestamp releaseDate;

    /** 上映地点 */
    private String releaseLocation;

    /** 综合评分 */
    private Double score;

    /** 评价人数 */
    private Integer evaluator;

    /** 剧情简介 */
    private String details;

    /** 预告片 */
    private String video;

    /** 导演（英文逗号分隔） */
    private String director;

    /** 演员（英文逗号分隔） */
    private String actor;
}