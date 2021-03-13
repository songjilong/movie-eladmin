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
package me.songjilong.modules.seat.service.impl;

import me.songjilong.modules.seat.domain.Seat;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.songjilong.modules.seat.repository.SeatRepository;
import me.songjilong.modules.seat.service.SeatService;
import me.songjilong.modules.seat.service.dto.SeatDto;
import me.songjilong.modules.seat.service.dto.SeatQueryCriteria;
import me.songjilong.modules.seat.service.mapstruct.SeatMapper;
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
public class SeatServiceImpl implements SeatService {

    private final SeatRepository seatRepository;
    private final SeatMapper seatMapper;

    @Override
    public Map<String,Object> queryAll(SeatQueryCriteria criteria, Pageable pageable){
        Page<Seat> page = seatRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(seatMapper::toDto));
    }

    @Override
    public List<SeatDto> queryAll(SeatQueryCriteria criteria){
        return seatMapper.toDto(seatRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SeatDto findById(Long seatId) {
        Seat seat = seatRepository.findById(seatId).orElseGet(Seat::new);
        ValidationUtil.isNull(seat.getSeatId(),"Seat","seatId",seatId);
        return seatMapper.toDto(seat);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SeatDto create(Seat resources) {
        return seatMapper.toDto(seatRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Seat resources) {
        Seat seat = seatRepository.findById(resources.getSeatId()).orElseGet(Seat::new);
        ValidationUtil.isNull( seat.getSeatId(),"Seat","id",resources.getSeatId());
        seat.copy(resources);
        seatRepository.save(seat);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long seatId : ids) {
            seatRepository.deleteById(seatId);
        }
    }

    @Override
    public void download(List<SeatDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SeatDto seat : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("座位号", seat.getNumber());
            map.put("是否损坏", seat.getIsDamage());
            map.put("放映厅id", seat.getHallId());
            map.put("创建者", seat.getCreateBy());
            map.put("更新者", seat.getUpdateBy());
            map.put("创建日期", seat.getCreateTime());
            map.put("更新时间", seat.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}