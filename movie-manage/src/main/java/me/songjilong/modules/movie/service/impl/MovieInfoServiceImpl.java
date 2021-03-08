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
package me.songjilong.modules.movie.service.impl;

import lombok.RequiredArgsConstructor;
import me.songjilong.modules.movie.domain.MovieInfo;
import me.songjilong.modules.movie.repository.MovieInfoRepository;
import me.songjilong.modules.movie.service.MovieInfoService;
import me.songjilong.modules.movie.service.dto.MovieInfoDto;
import me.songjilong.modules.movie.service.dto.MovieInfoQueryCriteria;
import me.songjilong.modules.movie.service.mapstruct.MovieInfoMapper;
import me.zhengjie.utils.FileUtil;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import me.zhengjie.utils.ValidationUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
* @website https://el-admin.vip
* @description 服务实现
* @author long
* @date 2021-03-08
**/
@Service
@RequiredArgsConstructor
public class MovieInfoServiceImpl implements MovieInfoService {

    private final MovieInfoRepository movieInfoRepository;
    private final MovieInfoMapper movieInfoMapper;

    @Override
    public Map<String,Object> queryAll(MovieInfoQueryCriteria criteria, Pageable pageable){
        Page<MovieInfo> page = movieInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(movieInfoMapper::toDto));
    }

    @Override
    public List<MovieInfoDto> queryAll(MovieInfoQueryCriteria criteria){
        return movieInfoMapper.toDto(movieInfoRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public MovieInfoDto findById(Long movieInfoId) {
        MovieInfo movieInfo = movieInfoRepository.findById(movieInfoId).orElseGet(MovieInfo::new);
        ValidationUtil.isNull(movieInfo.getMovieInfoId(),"MovieInfo","movieInfoId",movieInfoId);
        return movieInfoMapper.toDto(movieInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public MovieInfoDto create(MovieInfo resources) {
        return movieInfoMapper.toDto(movieInfoRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(MovieInfo resources) {
        MovieInfo movieInfo = movieInfoRepository.findById(resources.getMovieInfoId()).orElseGet(MovieInfo::new);
        ValidationUtil.isNull( movieInfo.getMovieInfoId(),"MovieInfo","id",resources.getMovieInfoId());
        movieInfo.copy(resources);
        movieInfoRepository.save(movieInfo);
    }

    @Override
    public void deleteAll(Long[] ids) {
        for (Long movieInfoId : ids) {
            movieInfoRepository.deleteById(movieInfoId);
        }
    }

    @Override
    public void download(List<MovieInfoDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MovieInfoDto movieInfo : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("名称", movieInfo.getName());
            map.put("英文名称", movieInfo.getNameEn());
            map.put("海报", movieInfo.getImg());
            map.put("类型（英文逗号分隔）", movieInfo.getType());
            map.put("语言", movieInfo.getLanguage());
            map.put("时长", movieInfo.getDuration());
            map.put("上映日期", movieInfo.getReleaseDate());
            map.put("上映地点", movieInfo.getReleaseLocation());
            map.put("综合评分", movieInfo.getScore());
            map.put("评价人数", movieInfo.getEvaluator());
            map.put("剧情简介", movieInfo.getDetails());
            map.put("预告片", movieInfo.getVideo());
            map.put("创建者", movieInfo.getCreateBy());
            map.put("更新者", movieInfo.getUpdateBy());
            map.put("创建日期", movieInfo.getCreateTime());
            map.put("更新时间", movieInfo.getUpdateTime());
            map.put("导演（英文逗号分隔）", movieInfo.getDirector());
            map.put("演员（英文逗号分隔）", movieInfo.getActor());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}