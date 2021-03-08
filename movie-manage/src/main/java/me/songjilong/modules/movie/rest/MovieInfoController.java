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
package me.songjilong.modules.movie.rest;

import me.zhengjie.annotation.Log;
import me.songjilong.modules.movie.domain.MovieInfo;
import me.songjilong.modules.movie.service.MovieInfoService;
import me.songjilong.modules.movie.service.dto.MovieInfoQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author long
* @date 2021-03-08
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "电影详情管理")
@RequestMapping("/api/movieInfo")
public class MovieInfoController {

    private final MovieInfoService movieInfoService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('movieInfo:list')")
    public void download(HttpServletResponse response, MovieInfoQueryCriteria criteria) throws IOException {
        movieInfoService.download(movieInfoService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询电影详情")
    @ApiOperation("查询电影详情")
    @PreAuthorize("@el.check('movieInfo:list')")
    public ResponseEntity<Object> query(MovieInfoQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(movieInfoService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增电影详情")
    @ApiOperation("新增电影详情")
    @PreAuthorize("@el.check('movieInfo:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody MovieInfo resources){
        return new ResponseEntity<>(movieInfoService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改电影详情")
    @ApiOperation("修改电影详情")
    @PreAuthorize("@el.check('movieInfo:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody MovieInfo resources){
        movieInfoService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除电影详情")
    @ApiOperation("删除电影详情")
    @PreAuthorize("@el.check('movieInfo:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        movieInfoService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}