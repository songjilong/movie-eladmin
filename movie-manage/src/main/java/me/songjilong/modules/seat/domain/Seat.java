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
package me.songjilong.modules.seat.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.songjilong.modules.hall.domain.Hall;
import me.zhengjie.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-13
**/
@Entity
@Data
@Table(name="biz_seat")
public class Seat extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    @ApiModelProperty(value = "主键")
    private Long seatId;

    @Column(name = "number",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "座位号")
    private String number;

    @Column(name = "is_damage",nullable = false)
    @NotNull
    @ApiModelProperty(value = "是否损坏")
    private Boolean isDamage;

    @OneToOne
    @JoinColumn(name = "hall_id")
    @ApiModelProperty("放映厅")
    private Hall hall;

    public void copy(Seat source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}