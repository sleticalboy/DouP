# DouP
一款业余练习的Android app

## 项目简介

一款聚合 APP，自己业余拿来练手的。`目前是传统的 MVC 模式`

### 目前已实现的模块

- 新闻
- 妹子
- 开眼
  - 推荐
  - 发现

### 尚在开发的模块

- 开眼
  - 热门
- 天气
- 地图

### 项目框架

- 网络通讯：`Retrofit`
- 图片加载：`Glide`
- 快速开发：`ButterKnife`
- 响应式编程：`RxAndroid`
- 视频播放： `GSYVideoPlayer`
- 数据库： `LitePal`
- 圆形图片： `CircleImageView`
- 动态权限申请： `RxPermissions`

### api 接口均来源于网络，如有侵权请及时通知

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

- 和风天气：http://guolin.tech/api/weather?cityid={CN101050109}&key={your_key} (需要注册之后获取 appkey)
  - 省市区：http://guolin.tech/api/china

- ~~彩云天气：https://www.caiyunapp.com/ ~~

- 豆瓣: https://api.douban.com/v2/
  - 图书: https://api.douban.com/v2/book/{id:1000001}
  - 电影: https://api.douban.com/v2/movie/subject/{id:1764789}
  - 音乐: https://api.douban.com/v2/music/{id:10000033}

