import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xualvin on 25/2/2020.
 * Java正则表达式解析代码段
 * 主要涉及类： Matcher， Pattern
 */
public class PatternParser {

    private static final String PARAM_PATTERN = "\\$\\{.*?\\}";
    public static void main(String[] args) {
        //根据给定正则表达式字符串构造Pattern对象
        Pattern p = Pattern.compile(PARAM_PATTERN);

        //对给定输入做正则匹配获取匹配对象
        Matcher matcher = p.matcher("testinput${date}###${hour}");

        //获取匹配项
        while (matcher.find()){
            String matchItem = matcher.group();
            System.out.println(matchItem);
        }

        //全部匹配的情况下，matches才为true
        Matcher totalMatch = p.matcher("${date}");
        if(totalMatch.matches()){
            System.out.println(totalMatch.start());
            System.out.println(totalMatch.end());
        }


        //字符串可以调用matches方法来匹配正则表达式, 注意matches调用的是Pattern.matches方法，需要全匹配
        String targetStr = "${targetname}";
        System.out.println(targetStr.matches(PARAM_PATTERN));
    }
}
