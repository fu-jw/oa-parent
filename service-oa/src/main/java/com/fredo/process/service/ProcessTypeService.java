package com.fredo.process.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fredo.model.process.ProcessType;

import java.util.List;

public interface ProcessTypeService extends IService<ProcessType> {

    List<ProcessType> findProcessType();
}