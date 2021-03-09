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
package me.songjilong.modules.cinema.rest;

import me.zhengjie.annotation.Log;
import me.songjilong.modules.cinema.domain.Cinema;
import me.songjilong.modules.cinema.service.CinemaService;
import me.songjilong.modules.cinema.service.dto.CinemaQueryCriteria;
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
 * @date 2021-03-10
 **/
@RestController
@RequiredArgsConstructor
@Api(tags = "影院详情管理")
@RequestMapping("/api/cinema")
public class CinemaController {

    private final CinemaService cinemaService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('cinema:list')")
    public void download(HttpServletResponse response, CinemaQueryCriteria criteria) throws IOException {
        cinemaService.download(cinemaService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询影院详情")
    @ApiOperation("查询影院详情")
    @PreAuthorize("@el.check('cinema:list')")
    public ResponseEntity<Object> query(CinemaQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(cinemaService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增影院详情")
    @ApiOperation("新增影院详情")
    @PreAuthorize("@el.check('cinema:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Cinema resources){
        return new ResponseEntity<>(cinemaService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改影院详情")
    @ApiOperation("修改影院详情")
    @PreAuthorize("@el.check('cinema:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Cinema resources){
        cinemaService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除影院详情")
    @ApiOperation("删除影院详情")
    @PreAuthorize("@el.check('cinema:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        cinemaService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}