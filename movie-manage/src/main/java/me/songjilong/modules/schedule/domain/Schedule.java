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
package me.songjilong.modules.schedule.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import io.swagger.annotations.ApiModelProperty;
import cn.hutool.core.bean.copier.CopyOptions;
import me.songjilong.modules.hall.domain.Hall;
import me.songjilong.modules.movie.domain.MovieInfo;
import me.zhengjie.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-13
**/
@Entity
@Data
@Table(name="biz_schedule")
public class Schedule extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    @ApiModelProperty(value = "主键")
    private Long scheduleId;

    @Column(name = "begin_time",nullable = false)
    @NotNull
    @ApiModelProperty(value = "开始放映时间")
    private Timestamp beginTime;

    @Column(name = "price",nullable = false)
    @NotNull
    @ApiModelProperty(value = "本场价格")
    private BigDecimal price;

    @Column(name = "remain")
    @ApiModelProperty(value = "剩余座位数")
    private Integer remain;

    @OneToOne
    @JoinColumn(name = "hall_id")
    @ApiModelProperty(value = "放映厅")
    private Hall hall;

    @OneToOne
    @JoinColumn(name = "movie_info_id")
    @ApiModelProperty(value = "影片")
    private MovieInfo movieInfo;

    public void copy(Schedule source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}