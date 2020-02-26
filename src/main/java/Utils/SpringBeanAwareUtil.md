```java
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by xualvin on 26/2/2020.
 * 详细介绍可以查看"小桶Tobey"的介绍 https://www.jianshu.com/p/4c0723615a52
 */
@Component //注意：需要在Spring中注册，然后通过设置applicationcontext获取容器中的所有bean
public class SpringBeanAwareUtil implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanAwareUtil.applicationContext = applicationContext;
    }

    /**
     * 通过类获取对应的bean
     * @param clazz bean类
     * @param <T> 返回bean对象
     * @return
     */
    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }

    /**
     * 通过bean的名字获取对应的bean
     * @param beanName bean的名字
     * @return bean对象
     */
    public static Object getBeanByName(String beanName) {
        return applicationContext.getBean(beanName);
    }
}

```