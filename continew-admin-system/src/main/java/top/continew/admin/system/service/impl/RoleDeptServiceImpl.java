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

package top.continew.admin.system.service.impl;

import cn.crane4j.annotation.ContainerMethod;
import cn.crane4j.annotation.MappingType;
import cn.hutool.core.collection.CollUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.continew.admin.common.constant.ContainerConstants;
import top.continew.admin.system.mapper.RoleDeptMapper;
import top.continew.admin.system.model.entity.RoleDeptDO;
import top.continew.admin.system.service.RoleDeptService;

import java.util.List;

/**
 * 角色和部门业务实现
 *
 * @author Charles7c
 * @since 2023/2/19 10:47
 */
@Service
@RequiredArgsConstructor
public class RoleDeptServiceImpl implements RoleDeptService {

    private final RoleDeptMapper roleDeptMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean add(List<Long> deptIds, Long roleId) {
        // 检查是否有变更
        List<Long> oldDeptIdList = roleDeptMapper.lambdaQuery()
            .select(RoleDeptDO::getDeptId)
            .eq(RoleDeptDO::getRoleId, roleId)
            .list()
            .stream()
            .map(RoleDeptDO::getDeptId)
            .toList();
        if (CollUtil.isEmpty(CollUtil.disjunction(deptIds, oldDeptIdList))) {
            return false;
        }
        // 删除原有关联
        roleDeptMapper.lambdaUpdate().eq(RoleDeptDO::getRoleId, roleId).remove();
        // 保存最新关联
        List<RoleDeptDO> roleDeptList = deptIds.stream().map(deptId -> new RoleDeptDO(roleId, deptId)).toList();
        return roleDeptMapper.insertBatch(roleDeptList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByRoleIds(List<Long> roleIds) {
        roleDeptMapper.lambdaUpdate().in(RoleDeptDO::getRoleId, roleIds).remove();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByDeptIds(List<Long> deptIds) {
        roleDeptMapper.lambdaUpdate().in(RoleDeptDO::getDeptId, deptIds).remove();
    }

    @Override
    @ContainerMethod(namespace = ContainerConstants.ROLE_DEPT_ID_LIST, type = MappingType.ORDER_OF_KEYS)
    public List<Long> listDeptIdByRoleId(Long roleId) {
        return roleDeptMapper.selectDeptIdByRoleId(roleId);
    }
}
