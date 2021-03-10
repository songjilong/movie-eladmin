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
package me.songjilong.modules.hall.rest;

import me.zhengjie.annotation.Log;
import me.songjilong.modules.hall.domain.Hall;
import me.songjilong.modules.hall.service.HallService;
import me.songjilong.modules.hall.service.dto.HallQueryCriteria;
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
@Api(tags = "放映厅管理管理")
@RequestMapping("/api/hall")
public class HallController {

    private final HallService hallService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('hall:list')")
    public void download(HttpServletResponse response, HallQueryCriteria criteria) throws IOException {
        hallService.download(hallService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询放映厅管理")
    @ApiOperation("查询放映厅管理")
    @PreAuthorize("@el.check('hall:list')")
    public ResponseEntity<Object> query(HallQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(hallService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增放映厅管理")
    @ApiOperation("新增放映厅管理")
    @PreAuthorize("@el.check('hall:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Hall resources){
        return new ResponseEntity<>(hallService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改放映厅管理")
    @ApiOperation("修改放映厅管理")
    @PreAuthorize("@el.check('hall:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Hall resources){
        hallService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除放映厅管理")
    @ApiOperation("删除放映厅管理")
    @PreAuthorize("@el.check('hall:del')")
    @DeleteMapping
    public ResponseEntity<Object> delete(@RequestBody Long[] ids) {
        hallService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}