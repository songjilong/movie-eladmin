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
package me.songjilong.modules.hall.domain;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import me.songjilong.modules.cinema.domain.Cinema;
import me.zhengjie.base.BaseEntity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-10
**/
@Entity
@Data
@Table(name="biz_hall")
public class Hall extends BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hall_id")
    @ApiModelProperty(value = "主键")
    private Long hallId;

    @Column(name = "name",nullable = false)
    @NotBlank
    @ApiModelProperty(value = "放映厅名称")
    private String name;

    @Column(name = "capacity",nullable = false)
    @NotNull
    @ApiModelProperty(value = "放映厅容量")
    private Integer capacity;

    @OneToOne
    @JoinColumn(name = "cinema_id")
    @ApiModelProperty(value = "电影院")
    private Cinema cinema;

    public void copy(Hall source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}