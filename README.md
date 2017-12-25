# DouP
一款业余练习的模仿豆瓣的Android app

## 项目简介

仿豆瓣的一款产品，自己业余拿来练手的。

### 项目框架

- 网络通讯框架：`Retrofit`
- 图片加载框架：`Glide`
- 数据库框架：`OrmLite`
- 快速开发框架：`ButterKnife`
- 响应式编程框架：`RxAndroid`

### api 接口来源（网络整理）

- 知乎日报：
  - 最新新闻：http://news-at.zhihu.com/api/4/news/latest
  - 老新闻：http://news-at.zhihu.com/api/4/news/before/{data:20171222}
  - 新闻详情：http://news-at.zhihu.com/api/4/news/{id:9662117}
- 豆瓣
  - 图书
  - 电影
  - 音乐
