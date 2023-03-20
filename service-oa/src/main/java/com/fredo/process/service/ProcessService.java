package com.fredo.process.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fredo.model.process.Process;
import com.fredo.vo.process.ProcessFormVo;
import com.fredo.vo.process.ProcessQueryVo;
import com.fredo.vo.process.ProcessVo;

public interface ProcessService extends IService<Process> {

    IPage<ProcessVo> selectPage(Page<ProcessVo> pageParam, ProcessQueryVo processQueryVo);
    void deployByZip(String deployPath);

    void startUp(ProcessFormVo processFormVo);
}