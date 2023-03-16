package com.fredo.process.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fredo.model.process.ProcessTemplate;
import com.fredo.model.process.ProcessType;
import com.fredo.process.mapper.ProcessTemplateMapper;
import com.fredo.process.service.ProcessTemplateService;
import com.fredo.process.service.ProcessTypeService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessTemplateServiceImpl extends ServiceImpl<ProcessTemplateMapper, ProcessTemplate> implements ProcessTemplateService {

   @Resource
   private ProcessTemplateMapper processTemplateMapper;

   @Resource
   private ProcessTypeService processTypeService;

   @Override
   public IPage<ProcessTemplate> selectPage(Page<ProcessTemplate> pageParam) {
      LambdaQueryWrapper<ProcessTemplate> queryWrapper = new LambdaQueryWrapper<ProcessTemplate>();
      queryWrapper.orderByDesc(ProcessTemplate::getId);
      IPage<ProcessTemplate> page = processTemplateMapper.selectPage(pageParam, queryWrapper);
      List<ProcessTemplate> processTemplateList = page.getRecords();
       
      List<Long> processTypeIdList = processTemplateList.stream().map(processTemplate -> processTemplate.getProcessTypeId()).collect(Collectors.toList());
       
      if(!CollectionUtils.isEmpty(processTypeIdList)) {
         Map<Long, ProcessType> processTypeIdToProcessTypeMap = processTypeService.list(new LambdaQueryWrapper<ProcessType>().in(ProcessType::getId, processTypeIdList)).stream().collect(Collectors.toMap(ProcessType::getId, ProcessType -> ProcessType));
         for(ProcessTemplate processTemplate : processTemplateList) {
            ProcessType processType = processTypeIdToProcessTypeMap.get(processTemplate.getProcessTypeId());
            if(null == processType) continue;
            processTemplate.setProcessTypeName(processType.getName());
         }
      }
      return page;
   }
}