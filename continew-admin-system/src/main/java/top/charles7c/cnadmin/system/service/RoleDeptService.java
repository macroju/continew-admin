/*
 * Copyright (c) 2022-present Charles7c Authors. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package top.charles7c.cnadmin.system.service;

import java.util.List;

/**
 * 角色和部门业务接口
 *
 * @author Charles7c
 * @since 2023/2/19 10:40
 */
public interface RoleDeptService {

    /**
     * 保存
     *
     * @param deptIds
     *            部门 ID 列表
     * @param roleId
     *            角色 ID
     */
    void save(List<Long> deptIds, Long roleId);

    /**
     * 根据角色 ID 查询
     *
     * @param roleId
     *            角色 ID
     * @return 部门 ID 列表
     */
    List<Long> listDeptIdByRoleId(Long roleId);

    /**
     * 根据部门 ID 删除
     *
     * @param deptIds
     *            部门 ID 列表
     */
    void deleteByDeptIds(List<Long> deptIds);
}