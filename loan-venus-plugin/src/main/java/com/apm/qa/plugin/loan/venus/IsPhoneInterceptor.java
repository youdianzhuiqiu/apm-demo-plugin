package com.apm.qa.plugin.loan.venus;

import com.alibaba.fastjson.JSON;
import org.apache.skywalking.apm.agent.core.context.ContextManager;
import org.apache.skywalking.apm.agent.core.context.tag.StringTag;
import org.apache.skywalking.apm.agent.core.context.tag.Tags;
import org.apache.skywalking.apm.agent.core.context.trace.AbstractSpan;
import org.apache.skywalking.apm.agent.core.context.trace.SpanLayer;
import org.apache.skywalking.apm.agent.core.logging.api.ILog;
import org.apache.skywalking.apm.agent.core.logging.api.LogManager;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.MethodInterceptResult;
import org.apache.skywalking.apm.agent.core.plugin.interceptor.enhance.StaticMethodsAroundInterceptor;
import org.apache.skywalking.apm.network.trace.component.ComponentsDefine;
import org.apache.skywalking.apm.util.StringUtil;

import java.lang.reflect.Method;

public class IsPhoneInterceptor implements StaticMethodsAroundInterceptor {

    private static final ILog logger = LogManager.getLogger(IsPhoneInterceptor.class);


    @Override
    public void beforeMethod(Class enhancedInstance, Method method, Object[] objects, Class<?>[] classes, MethodInterceptResult methodInterceptResult) {
        // 创建span(监控的开始)，本质上是往ThreadLocal对象里面设值
        logger.info("beforeMethod...");
        AbstractSpan span = ContextManager.createLocalSpan("isPhone");
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
    public Object afterMethod(Class enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Object o) {
        logger.info("beforeMethod...");
        // 激活span，本质上是读取ThreadLocal对象
        AbstractSpan span = ContextManager.activeSpan();
        // 状态码，任意写，Tags也是个Skywalking的工具类，用来比较方便地操作tag
        Tags.STATUS_CODE.set(span, "20000");

        // 停止span(监控的结束)，本质上是清理ThreadLocal对象
        ContextManager.stopSpan();

        try {
            String doGet = HttpUtil.doGet("http://vitamin.daily.vdian.net/node/query?groupId=base-qa&serviceId=loan&nodeKey=loan_venus_phone_check_mock");
            logger.info("beforeMethod..." + doGet);
            if(null != doGet){
                VitaminResponse vitaminResponse = JSON.parseObject(doGet,VitaminResponse.class);
                return "on".equals(vitaminResponse.getResult().getNode().get(0).getNodeValue());
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return o;
    }

    @Override
    public void handleMethodException(Class enhancedInstance, Method method, Object[] objects, Class<?>[] classes, Throwable throwable) {
        AbstractSpan activeSpan = ContextManager.activeSpan();
        // 记录日志
        activeSpan.log(throwable);
        activeSpan.errorOccurred();
    }
}
