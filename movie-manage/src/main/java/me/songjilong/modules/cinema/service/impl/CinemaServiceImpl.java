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
package me.songjilong.modules.cinema.service.impl;

import me.songjilong.modules.cinema.domain.Cinema;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.songjilong.modules.cinema.repository.CinemaRepository;
import me.songjilong.modules.cinema.service.CinemaService;
import me.songjilong.modules.cinema.service.dto.CinemaDto;
import me.songjilong.modules.cinema.service.dto.CinemaQueryCriteria;
import me.songjilong.modules.cinema.service.mapstruct.CinemaMapper;
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
public class CinemaServiceImpl implements CinemaService {

    private final CinemaRepository cinemaRepository;
    private final CinemaMapper cinemaMapper;

    @Override
    public Map<String,Object> queryAll(CinemaQueryCriteria criteria, Pageable pageable){
        Page<Cinema> page = cinemaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(cinemaMapper::toDto));
    }

    @Override
    public List<CinemaDto> queryAll(CinemaQueryCriteria criteria){
        return cinemaMapper.toDto(cinemaRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public CinemaDto findById(Long cinemaId) {
        Cinema cinema = cinemaRepository.findById(cinemaId).orElseGet(Cinema::new);
        ValidationUtil.isNull(cinema.getCinemaId(),"Cinema","cinemaId",cinemaId);
        return cinemaMapper.toDto(cinema);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CinemaDto create(Cinema resources) {
        return cinemaMapper.toDto(cinemaRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Cinema resources) {
        Cinema cinema = cinemaRepository.findById(resources.getCinemaId()).orElseGet(Cinema::new);
        ValidationUtil.isNull( cinema.getCinemaId(),"Cinema","id",resources.getCinemaId());
        cinema.copy(resources);
        cinemaRepository.save(cinema);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long cinemaId : ids) {
            cinemaRepository.deleteById(cinemaId);
        }
    }

    @Override
    public void download(List<CinemaDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CinemaDto cinema : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("电影院名称", cinema.getName());
            map.put("电影院地址", cinema.getAddress());
            map.put("电影院简介", cinema.getIntrod());
            map.put("创建者", cinema.getCreateBy());
            map.put("更新者", cinema.getUpdateBy());
            map.put("创建日期", cinema.getCreateTime());
            map.put("更新时间", cinema.getUpdateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}