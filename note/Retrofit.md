
## 打印 http 请求日志

使用 HttpLoggerInterceptor

## 动态替换 BaseUrl

在接口的方法中使用 @Headers("" + ":" + "") 注解
使用拦截器中解析 Headers 并动态替换掉 baseUrl

## 关于 @Path

/china/{provinceId}/{cityId} 占位符中的字符必须和方法中 @Path("") 的相同

