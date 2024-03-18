package com.bestrookie.springframework.aop.aspectj;

import com.bestrookie.springframework.aop.ClassFilter;
import com.bestrookie.springframework.aop.MethodMatcher;
import com.bestrookie.springframework.aop.Pointcut;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author bestrookie
 * @Date 2024/3/18 14:29
 * @Desc
 */
public class AspectJExpressionPointcut implements Pointcut, ClassFilter, MethodMatcher {
    private static final Set<PointcutPrimitive> SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();
    /**
     * 静态初始化块，用于在类加载时初始化支持的原语。
     * 这段代码特别添加了"EXECUTION"原语到支持的原语集合中。
     *
     * 执行该静态块不需要传入参数，也没有返回值。
     */
    static {
        SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
    }

    private final PointcutExpression pointcutExpression;

    /**
     * 构造函数：使用给定的表达式创建一个AspectJ表达式切点。
     *
     * @param expression 表达式字符串，用于定义切点的条件。
     */
    public AspectJExpressionPointcut(String expression){
        // 获取支持指定基本类型并使用指定类加载器解析的切点解析器
        PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingSpecifiedClassLoaderForResolution(SUPPORTED_PRIMITIVES, this.getClass().getClassLoader());
        // 解析传入的表达式为AspectJ表达式切点
        pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }
    /**
     * 判断给定的类是否匹配切点表达式。
     *
     * @param clazz 需要判断的类，类型为Class<?>。
     * @return 返回一个布尔值，如果类中的连接点能够匹配切点表达式，则返回true；否则返回false。
     */
    @Override
    public boolean matches(Class<?> clazz) {
        // 判断指定的类中是否有连接点能够匹配切点表达式
        return pointcutExpression.couldMatchJoinPointsInType(clazz);
    }


    /**
     * 检查给定的方法是否与切点表达式匹配。
     *
     * @param method 要检查的方法对象。
     * @param targetClass 方法所属的目标类。
     * @return 如果方法与切点表达式完全匹配，则返回true；否则返回false。
     */
    @Override
    public boolean matches(Method method, Class<?> targetClass) {
        // 使用切点表达式检查方法执行的匹配性，并判断是否总是匹配
        return pointcutExpression.matchesMethodExecution(method).alwaysMatches();
    }


    @Override
    public ClassFilter getClassFilter() {
        return this;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
        return this;
    }
}
