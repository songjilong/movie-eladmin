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
package me.songjilong.modules.schedule.service.dto;

import lombok.Data;
import me.songjilong.modules.hall.domain.Hall;
import me.zhengjie.base.BaseDTO;

import java.sql.Timestamp;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-13
**/
@Data
public class ScheduleDto extends BaseDTO implements Serializable {

    /** 主键 */
    private Long scheduleId;

    /** 开始放映时间 */
    private Timestamp beginTime;

    /** 本场价格 */
    private BigDecimal price;

    /** 剩余座位数 */
    private Integer remain;

    /** 放映厅id */
    private Hall hall;

    /** 电影id */
    private MovieInfoSmallDto movieInfo;
}