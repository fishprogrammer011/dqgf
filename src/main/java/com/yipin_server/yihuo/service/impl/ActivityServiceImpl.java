package com.yipin_server.yihuo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yipin_server.yihuo.entity.Activity;
import com.yipin_server.yihuo.mapper.ActivityMapper;
import com.yipin_server.yihuo.service.ActivityService;
import org.springframework.stereotype.Service;

/**
 * @Author: wangTao
 */
@Service
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
}
