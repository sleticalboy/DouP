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

- 知乎日报: http://news-at.zhihu.com/api/4/
  - 最新新闻：http://news-at.zhihu.com/api/4/news/latest
  - 老新闻：http://news-at.zhihu.com/api/4/news/before/{data:20171222}
  - 新闻详情：http://news-at.zhihu.com/api/4/news/{id:9662117}

- 干货集中营: http://gank.io/api/
  - 妹子：http://gank.io/api/data/福利/10/{page:2}

- 开眼视频: http://baobab.kaiyanapp.com/api/
  - 首页： http://baobab.kaiyanapp.com/api/v2/feed?num=2&udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
  - 发现： http://baobab.kaiyanapp.com/api/v2/categories?udid=26868b32e808498db32fd51fb422d00175e179df&vc=83
  - 热门： http://baobab.kaiyanapp.com/api/v3/ranklist

- 豆瓣: https://api.douban.com/v2/
  - 图书: https://api.douban.com/v2/book/{id:1000001}
  - 电影: https://api.douban.com/v2/movie/subject/{id:1764789}
  - 音乐: https://api.douban.com/v2/music/{id:10000033}
