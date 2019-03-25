package com.ldz.wechat.interceptor;

import com.ldz.sys.service.YhService;
import com.ldz.util.config.BaseWebConfigure;
import com.ldz.wechat.service.CoachManagementService;
import com.ldz.wechat.service.TraineeInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class ExtendInterceptor extends BaseWebConfigure {

	@Autowired
	private StringRedisTemplate redisDao;

	//学员方法
	@Autowired
	private TraineeInformationService traineeService;
	//教练
	@Autowired
	private CoachManagementService coachService;

	@Autowired
	private YhService yhService;




	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AccessInterceptor(redisDao,traineeService,coachService,yhService))
				.addPathPatterns("/api/**")
				.excludePathPatterns("/pub/**"
						,"/login"
						,"/upload");
		super.addInterceptors(registry);
	}

	/**
	 * 全局跨域处理方法
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
		        .allowedOrigins("*")
		        .allowedMethods("GET", "HEAD", "POST", "PUT", "PATCH", "DELETE", "OPTIONS",  "TRACE")
		        .allowCredentials(true)
		        .maxAge(3600);
	}
}
