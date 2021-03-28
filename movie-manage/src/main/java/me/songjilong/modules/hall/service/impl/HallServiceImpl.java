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
package me.songjilong.modules.hall.service.impl;

import me.songjilong.modules.hall.domain.Hall;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.songjilong.modules.hall.repository.HallRepository;
import me.songjilong.modules.hall.service.HallService;
import me.songjilong.modules.hall.service.dto.HallDto;
import me.songjilong.modules.hall.service.dto.HallQueryCriteria;
import me.songjilong.modules.hall.service.mapstruct.HallMapper;
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
* @date 2021-03-10
**/
@Service
@RequiredArgsConstructor
public class HallServiceImpl implements HallService {

    private final HallRepository hallRepository;
    private final HallMapper hallMapper;

    @Override
    public Map<String,Object> queryAll(HallQueryCriteria criteria, Pageable pageable){
        Page<Hall> page = hallRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(hallMapper::toDto));
    }

    @Override
    public List<HallDto> queryAll(HallQueryCriteria criteria){
        return hallMapper.toDto(hallRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public HallDto findById(Long hallId) {
        Hall hall = hallRepository.findById(hallId).orElseGet(Hall::new);
        ValidationUtil.isNull(hall.getHallId(),"Hall","hallId",hallId);
        return hallMapper.toDto(hall);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public HallDto create(Hall resources) {
        return hallMapper.toDto(hallRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Hall resources) {
        Hall hall = hallRepository.findById(resources.getHallId()).orElseGet(Hall::new);
        ValidationUtil.isNull( hall.getHallId(),"Hall","id",resources.getHallId());
        hall.copy(resources);
        hallRepository.save(hall);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long hallId : ids) {
            hallRepository.deleteById(hallId);
        }
    }

    @Override
    public void download(List<HallDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (HallDto hall : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("放映厅名称", hall.getName());
            map.put("放映厅容量", hall.getCapacity());
            map.put("放映厅类型", hall.getType());
            map.put("影院名称", hall.getCinema().getName());
            map.put("创建者", hall.getCreateBy());
            map.put("更新者", hall.getUpdateBy());
            map.put("创建日期", hall.getCreateTime());
            map.put("更新时间", hall.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}