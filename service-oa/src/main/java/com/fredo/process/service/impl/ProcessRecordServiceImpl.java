package com.fredo.process.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fredo.auth.service.SysUserService;
import com.fredo.custom.LoginUserInfoHelper;
import com.fredo.model.process.ProcessRecord;
import com.fredo.model.system.SysUser;
import com.fredo.process.mapper.ProcessRecordMapper;
import com.fredo.process.service.ProcessRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessRecordServiceImpl extends ServiceImpl<ProcessRecordMapper, ProcessRecord> implements ProcessRecordService {

   @Autowired
   private ProcessRecordMapper processRecordMapper;

   @Autowired
   private SysUserService sysUserService;

   @Override
   public void record(Long processId, Integer status, String description) {
      SysUser sysUser = sysUserService.getById(LoginUserInfoHelper.getUserId());
      ProcessRecord processRecord = new ProcessRecord();
      processRecord.setProcessId(processId);
      processRecord.setStatus(status);
      processRecord.setDescription(description);
      processRecord.setOperateUserId(sysUser.getId());
      processRecord.setOperateUser(sysUser.getName());
      processRecordMapper.insert(processRecord);
   }

}