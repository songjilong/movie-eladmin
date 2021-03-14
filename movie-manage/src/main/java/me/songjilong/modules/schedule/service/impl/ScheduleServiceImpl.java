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
package me.songjilong.modules.schedule.service.impl;

import me.songjilong.modules.schedule.domain.Schedule;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.songjilong.modules.schedule.repository.ScheduleRepository;
import me.songjilong.modules.schedule.service.ScheduleService;
import me.songjilong.modules.schedule.service.dto.ScheduleDto;
import me.songjilong.modules.schedule.service.dto.ScheduleQueryCriteria;
import me.songjilong.modules.schedule.service.mapstruct.ScheduleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author long
* @date 2021-03-13
**/
@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;

    @Override
    public Map<String,Object> queryAll(ScheduleQueryCriteria criteria, Pageable pageable){
        Page<Schedule> page = scheduleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(scheduleMapper::toDto));
    }

    @Override
    public List<ScheduleDto> queryAll(ScheduleQueryCriteria criteria){
        return scheduleMapper.toDto(scheduleRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public ScheduleDto findById(Long scheduleId) {
        Schedule schedule = scheduleRepository.findById(scheduleId).orElseGet(Schedule::new);
        ValidationUtil.isNull(schedule.getScheduleId(),"Schedule","scheduleId",scheduleId);
        return scheduleMapper.toDto(schedule);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ScheduleDto create(Schedule resources) {
        return scheduleMapper.toDto(scheduleRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Schedule resources) {
        Schedule schedule = scheduleRepository.findById(resources.getScheduleId()).orElseGet(Schedule::new);
        ValidationUtil.isNull( schedule.getScheduleId(),"Schedule","id",resources.getScheduleId());
        schedule.copy(resources);
        scheduleRepository.save(schedule);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long scheduleId : ids) {
            scheduleRepository.deleteById(scheduleId);
        }
    }

    @Override
    public void download(List<ScheduleDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (ScheduleDto schedule : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("开始放映时间", schedule.getBeginTime());
            map.put("本场价格", schedule.getPrice());
            map.put("剩余座位数", schedule.getRemain());
            map.put("放映厅", schedule.getHall().getName());
            map.put("电影", schedule.getMovieInfo().getName());
            map.put("创建者", schedule.getCreateBy());
            map.put("更新者", schedule.getUpdateBy());
            map.put("创建日期", schedule.getCreateTime());
            map.put("更新时间", schedule.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}