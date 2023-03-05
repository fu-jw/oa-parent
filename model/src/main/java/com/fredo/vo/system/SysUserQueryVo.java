package com.fredo.vo.system;

import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 用户查询实体
 * </p>
 */
@Data
public class SysUserQueryVo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	// 搜索框的内容
	private String keyword;
	// 开始时间
	private String createTimeBegin;
	// 结束时间
	private String createTimeEnd;

	private Long roleId;
	private Long postId;
	private Long deptId;

}

