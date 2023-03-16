package com.fredo.process.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fredo.process.mapper.ProcessTypeMapper;
import com.fredo.process.service.ProcessTypeService;
import com.fredo.model.process.ProcessType;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings({"unchecked", "rawtypes"})
public class ProcessTypeServiceImpl extends ServiceImpl<ProcessTypeMapper, ProcessType> implements ProcessTypeService {

}