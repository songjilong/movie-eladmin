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
package me.songjilong.modules.hall.service.dto;

import lombok.Data;
import me.songjilong.modules.cinema.domain.Cinema;
import me.zhengjie.base.BaseDTO;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-10
**/
@Data
public class HallDto extends BaseDTO implements Serializable {

    /** 主键 */
    private Long hallId;

    /** 放映厅名称 */
    private String name;

    /** 放映厅容量 */
    private Integer capacity;

    /** 放映厅类型 */
    private String type;

    /** 电影院 */
    private Cinema cinema;
}