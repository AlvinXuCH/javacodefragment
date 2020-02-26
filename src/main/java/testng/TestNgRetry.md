TestNg测试框架提供了对失败重试的支持，下面会通过两种方式对测试用例添加失败重试功能：
1. @Test注解中添加
2. TestNg的程序化调用中添加

当然大家也可以通过testng.xml来配置对应的失败重试监听，这里不做说明。

TestNg提供了失败重试接口IRetryAnalyzer，需要实现retry方法：

```java
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * 设置testng用例失败重试次数
 */
public class MyRetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int MAX_RETRY_COUNT = 3;

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (retryCount < MAX_RETRY_COUNT) {
            retryCount++;
            return true;
        }
        return false;
    }
}
```

 
## @Test注解中添加
TestNG中的@Test注解包含参数retryAnalyzer，可以在注解中给定重试分析器，如下：

 

   

```java
 @Test(retryAnalyzer = MyRetryAnalyzer.class)
    public void test01() {
        System.out.println("test01");
        Assert.assertTrue(false, "test failed");
    }
```

 

## TestNg的程序化调用中添加


 

需要实现接口IAnnotationTransformer中的transform方法，对未设置retryanalyzer的@test方法动态添加相应的retryanalyzer，效果同上边的例子中的@Test(**retryAnalyzer = MyRetryAnalyzer.class**)

 

```java

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.IRetryAnalyzer;
import org.testng.annotations.ITestAnnotation;

/**
 * 添加重试监听器，如果测试方法中未添加重试分析器，则默认添加MyRetryAnalyzer作为重试分析器
 */
public class MyRetryListener implements IAnnotationTransformer {
    @Override
    public void transform(ITestAnnotation iTestAnnotation, Class aClass, Constructor constructor, Method method) {
        IRetryAnalyzer retryAnalyzer = iTestAnnotation.getRetryAnalyzer();
        if (null == retryAnalyzer) {
            iTestAnnotation.setRetryAnalyzer(MyRetryAnalyzer.class);
        }
    }
}
 
```

在构造tesng执行对象时，添加响应的listener

 

```java

import java.util.ArrayList;
import java.util.List;
import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlSuite.ParallelMode;
import org.testng.xml.XmlTest;

/**
 */
public class TestNgController {

    /**
     * TestNG测试程序化调用
     */
    public void executeTests() {
        //构建testng.xml内存对象
        try {
            List<XmlSuite> suites = new ArrayList<XmlSuite>();
            XmlSuite suite = new XmlSuite();
            suite.setName("TestNG Retry Test");
            //添加失败重试
            List<String> suiteListeners = new ArrayList<String>();
            //添加重试监听器
            suiteListeners.add("xxx.MyRetryListener");
            suite.setListeners(suiteListeners);

            //类级别的并发
            suite.setParallel(ParallelMode.CLASSES);
            suites.add(suite);

            XmlTest test = new XmlTest(suite);
            test.setName("TestNG Retry Test");

            //添加将要执行的用例
            List<XmlClass> classes = new ArrayList<XmlClass>();

            XmlClass testClass = new XmlClass("xxx.TestClass");

            classes.add(testClass);

            test.setXmlClasses(classes);

            //TestNG程序化调用
            TestNG testNG = new TestNG();
            testNG.setXmlSuites(suites);
            testNG.run();

        } catch (Exception e) {
            System.out.println("测试用例执行失败： " + e.getMessage());
            e.printStackTrace();
        }

    }

    public static void main(String[] args){
        TestNgController testNgController = new TestNgController();
        testNgController.executeTests();
    }
}
```
