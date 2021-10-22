Apache SkyWalking Plugin
===============

# 踩的坑
1. 写好的 plugin 要放到对应的类所在的服务里（sw2ServiceInterceptor 拦截 sw2服务:service）
1. 拦截的是具体实现类（class），而非接口类（interface）
1. 出现 dubbo 冲突【setSkyWalkingDynamicField(java.lang.Object) not found】拷包到 plugin（dubbo-2.7.x-conflict-patch-8.5.0.jar、dubbo-conflict-patch-8.5.0.jar）解决
1. 拦截替换 DO 需要类实现 Serializable
