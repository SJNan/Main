package cc.black.initializing.druidConfig;

import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.filter.logging.Log4jFilter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Configuration
public class DruidConfiguration {  
	private Logger logger = LoggerFactory.getLogger(getClass());
  
    @Bean
    public DataSource druidDataSource(@Value("${spring.datasource.driverClassName}") String driver,
                                      @Value("${spring.datasource.url}") String url,
                                      @Value("${spring.datasource.username}") String username,
                                      @Value("${spring.datasource.password}") String password,
                                      @Value("${spring.datasource.maxActive}") int maxActive
                                                              ) {  
        DruidDataSource druidDataSource = new DruidDataSource();  
         Log4jFilter filter = new Log4jFilter();
		 filter.setStatementExecutableSqlLogEnable(true);
		 filter.setResultSetLogEnabled(false);
		 filter.setConnectionLogEnabled(false);
		 filter.setStatementCreateAfterLogEnabled(false);
		 filter.setStatementParameterClearLogEnable(false);
		 filter.setStatementCloseAfterLogEnabled(false);
		 filter.setStatementParameterSetLogEnabled(false);
		 filter.setStatementExecuteAfterLogEnabled(false);
         List list= new ArrayList<Filter>();
         list.add(filter);
         druidDataSource.setProxyFilters(list);
        
        druidDataSource.setDriverClassName(driver);  
        druidDataSource.setUrl(url);  
        druidDataSource.setUsername(username);  
        druidDataSource.setPassword(password);  
        druidDataSource.setMaxActive(maxActive);  
      //  druidDataSource.setDefaultAutoCommit(false);
        logger.info("============================druid config init=========================================");
        logger.info("==========druid url:"+url+"========================================");
        logger.info("==========druid username:"+username+"====================");
        logger.info("=================================================================================");
        
        try {  
            druidDataSource.setFilters("stat, wall");  
        } catch (SQLException e) {
            e.printStackTrace();  
        }  
        return druidDataSource;  
    }  
    
    @Bean
    public ServletRegistrationBean druidServletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean();
        servletRegistrationBean.setServlet(new StatViewServlet());
        servletRegistrationBean.addUrlMappings("/druid/*");
        return servletRegistrationBean;
    }

    /**
     * 注册DruidFilter拦截
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean duridFilterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        Map<String, String> initParams = new HashMap<String, String>();
        //设置忽略请求
        initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.setInitParameters(initParams);
        filterRegistrationBean.addUrlPatterns("/*");
        return filterRegistrationBean;
    }
}  