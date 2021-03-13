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
package me.songjilong.modules.seat.rest;

import me.zhengjie.annotation.Log;
import me.songjilong.modules.seat.domain.Seat;
import me.songjilong.modules.seat.service.SeatService;
import me.songjilong.modules.seat.service.dto.SeatQueryCriteria;
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
* @date 2021-03-13
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "座位管理管理")
@RequestMapping("/api/seat")
public class SeatController {

    private final SeatService seatService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('seat:list')")
    public void download(HttpServletResponse response, SeatQueryCriteria criteria) throws IOException {
        seatService.download(seatService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询座位管理")
    @ApiOperation("查询座位管理")
    @PreAuthorize("@el.check('seat:list')")
    public ResponseEntity<Object> query(SeatQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(seatService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增座位管理")
    @ApiOperation("新增座位管理")
    @PreAuthorize("@el.check('seat:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Seat resources){
        return new ResponseEntity<>(seatService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改座位管理")
    @ApiOperation("修改座位管理")
    @PreAuthorize("@el.check('seat:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Seat resources){
        seatService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除座位管理")
    @ApiOperation("删除座位管理")
    @PreAuthorize("@el.check('seat:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        seatService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}