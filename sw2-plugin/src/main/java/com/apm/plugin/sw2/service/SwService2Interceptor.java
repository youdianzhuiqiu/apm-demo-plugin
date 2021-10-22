package com.apm.plugin.sw2.service;

import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.logging.api.ILog;
import org.apache.skywalking.apm.agent.core.logging.api.LogManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.EnhancedInstance;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.InstanceMethodsAroundInterceptor;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;

import java.lang.reflect.Method;

public class SwService2Interceptor implements InstanceMethodsAroundInterceptor {
    private static final ILog LOGGER = LogManager.getLogger(SwService2Interceptor.class);

    @Override
    public void beforeMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, MethodInterceptResult methodInterceptResult) throws Throwable {
        LOGGER.info("SwService2Interceptor.beforeMethod...");
        // 创建span(监控的开始)，本质上是往ThreadLocal对象里面设值
        AbstractSpan span = ContextManager.createLocalSpan("SW/sw2/getEmail");
        /*
         * 可用ComponentsDefine工具类指定Skywalking官方支持的组件
         * 也可自己new OfficialComponent或者Component
         * 不过在Skywalking的控制台上不会被识别，只会显示N/A
         */
        span.setComponent(ComponentsDefine.TOMCAT);

        span.tag(new StringTag(1000, "params"), objects[0].toString());
        // 指定该调用的layer，layer是个枚举
        span.setLayer(SpanLayer.CACHE);
    }

    @Override
    public Object afterMethod(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) throws Throwable {
        LOGGER.info("SwService2Interceptor.beforeMethod...");
        String retString = (String) o;
        // 激活span，本质上是读取ThreadLocal对象
        AbstractSpan span = ContextManager.activeSpan();
        // 状态码，任意写，Tags也是个Skywalking的工具类，用来比较方便地操作tag
        Tags.STATUS_CODE.set(span, "20000");

        // 停止span(监控的结束)，本质上是清理ThreadLocal对象
        ContextManager.stopSpan();
        LOGGER.debug("afterMethod:" + retString);
        if(retString.contains("laicunli")){
            return "你的邮箱已被篡改！~~";
        }
        return retString;
    }

    @Override
    public void handleMethodException(EnhancedInstance enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {
        LOGGER.info("SwService2Interceptor.handleMethodException...");
        AbstractSpan activeSpan = ContextManager.activeSpan();
        // 记录日志
        activeSpan.log(throwable);
        activeSpan.errorOccurred();
    }
}
