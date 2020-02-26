package selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by xualvin on 26/2/2020.
 * selenium中chromedriver初始化代码段
 */
public class ChromeDriverConfig {

    /**
     * 初始化WebDriver对象
     * chromeoptions添加了很多配置，主要是用例解决部分selenium执行失败问题，详见下方stackoverflow链接
     *
     * @return
     */
    public static WebDriver initDriver() {
        ChromeOptions options = new ChromeOptions();
        //解决各种配置导致的错误 https://stackoverflow.com/questions/48450594/selenium-timed-out-receiving-message-from-renderer
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--disable-gpu");
        //是否无痕模式执行
        options.addArguments("--headless");
        options.addArguments("--test-type");
        options.addArguments("--ignore-certificate-errors");
        options.addArguments("--no-sandbox");

        WebDriver driver = new ChromeDriver(options);
        //设置页面加载超时时间，如超时，页面会停止加载
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        //设置元素获取超时时间
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        return driver;
    }

    /**
     * 通过Chrome的设备模式（device mode）来执行用例，一般用于无线端H5测试
     *
     * @return
     */
    public static WebDriver initDriverWithDeviceMode() {
        ChromeOptions options = new ChromeOptions();

        Map<String, String> mobileEmulation = new HashMap<String, String>();
        mobileEmulation.put("deviceName", "iphone X");
        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        WebDriver driver = new ChromeDriver(options);
        //设置页面加载超时时间，如超时，页面会停止加载
        driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
        //设置元素获取超时时间
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        return driver;
    }

}
