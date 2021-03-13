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
package me.songjilong.modules.seat.service;

import me.songjilong.modules.seat.domain.Seat;
import me.songjilong.modules.seat.service.dto.SeatDto;
import me.songjilong.modules.seat.service.dto.SeatQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author long
* @date 2021-03-13
**/
public interface SeatService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(SeatQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SeatDto>
    */
    List<SeatDto> queryAll(SeatQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param seatId ID
     * @return SeatDto
     */
    SeatDto findById(Long seatId);

    /**
    * 创建
    * @param resources /
    * @return SeatDto
    */
    SeatDto create(Seat resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Seat resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<SeatDto> all, HttpServletResponse response) throws IOException;
}