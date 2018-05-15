package co.ceiba.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {
	 public MvcConfiguration() {
	        super();
	    }

	 /*   @Override
	    public void addViewControllers(final ViewControllerRegistry registry) {
	        super.addViewControllers(registry);
	        registry.addViewController("/").setViewName("forward:/index");
	        registry.addViewController("/index");
	    }*/
}
