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
package me.songjilong.modules.seat.service.dto;

import lombok.Data;
import me.zhengjie.base.BaseDTO;

import java.sql.Timestamp;
import java.io.Serializable;

/**
* @website https://el-admin.vip
* @description /
* @author long
* @date 2021-03-13
**/
@Data
public class SeatDto extends BaseDTO implements Serializable {

    /** 主键 */
    private Long seatId;

    /** 座位号 */
    private String number;

    /** 是否损坏 */
    private Boolean isDamage;

    /** 放映厅id */
    private Long hallId;

}