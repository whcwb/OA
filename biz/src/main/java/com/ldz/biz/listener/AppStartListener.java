package com.ldz.biz.listener;

import com.ldz.sys.service.GnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

@Service
public class AppStartListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private GnService gnService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event)  {
        //只在初始化“根上下文”的时候执行
        final ApplicationContext app = event.getApplicationContext();
        if (null == app.getParent()) { // 当存在父子容器时，此判断很有用
            gnService.initPermission();
        }

    }
}