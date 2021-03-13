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
package me.songjilong.modules.schedule.rest;

import me.zhengjie.annotation.Log;
import me.songjilong.modules.schedule.domain.Schedule;
import me.songjilong.modules.schedule.service.ScheduleService;
import me.songjilong.modules.schedule.service.dto.ScheduleQueryCriteria;
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
@Api(tags = "排片管理管理")
@RequestMapping("/api/schedule")
public class ScheduleController {

    private final ScheduleService scheduleService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('schedule:list')")
    public void download(HttpServletResponse response, ScheduleQueryCriteria criteria) throws IOException {
        scheduleService.download(scheduleService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询排片管理")
    @ApiOperation("查询排片管理")
    @PreAuthorize("@el.check('schedule:list')")
    public ResponseEntity<Object> query(ScheduleQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(scheduleService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增排片管理")
    @ApiOperation("新增排片管理")
    @PreAuthorize("@el.check('schedule:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Schedule resources){
        return new ResponseEntity<>(scheduleService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改排片管理")
    @ApiOperation("修改排片管理")
    @PreAuthorize("@el.check('schedule:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Schedule resources){
        scheduleService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除排片管理")
    @ApiOperation("删除排片管理")
    @PreAuthorize("@el.check('schedule:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        scheduleService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}