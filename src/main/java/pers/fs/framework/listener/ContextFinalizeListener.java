package pers.fs.framework.listener;

import com.mysql.cj.jdbc.AbandonedConnectionCleanupThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Enumeration;

/**
 * servlet容器销毁时，需要释放一些资源，避免报错
 *
 * @author FS
 */
@WebListener
public class ContextFinalizeListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        // 测试时，代码运行到此，slf4j打印不出日志，但是org.springframework.web.context.ContextCleanupListener中用jcl打印日志
        System.out.println("ServletContextListener.contextDestroyed()");
        // The web application [] registered the JDBC driver [com.alibaba.druid.proxy.DruidDriver] but failed to unregister it when the web application was stopped.
        // 高版本tomcat添加了一些对数据库连接的监听，当tomcat关闭时，若这些连接未关闭，tomcat会提示错误，但tomcat会帮我们关闭掉这些连接
        Enumeration<Driver> drivers = DriverManager.getDrivers();
        Driver driver;
        while (drivers.hasMoreElements()) {
            driver = drivers.nextElement();
            try {
                DriverManager.deregisterDriver(driver);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        // The web application [] appears to have started a thread named [Abandoned connection cleanup thread] com.mysql.jdbc.AbandonedConnectionCleanupThread.
        AbandonedConnectionCleanupThread.uncheckedShutdown();
    }
}
