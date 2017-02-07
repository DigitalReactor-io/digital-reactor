## Main requests to the metrika yandex Api.

 ### Get visits
 https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day&ids=<counterId>&metrics=ym:s:users&date1=2016-08-01&date2=2016-09-20&oauth_token=<token>
 ### Get goal visits

  https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day&ids=<counterId>&metrics=ym:s:users&date1=2016-08-01&date2=2016-09-20&dimensions=ym:s:<attribution>TrafficSource&attribution=last&oauth_token=<token>



  https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day
&metrics=ym:s:users,ym:s:goal13582120visits
&date1=2016-08-01&date2=2016-09-20&dimensions=ym:s:<attribution>TrafficSource&attribution=last
&id=31424723
&oauth_token=AQAAAAAUQvf-AAK_6WmdQtnlGUIekdWX2d6RnU8

  https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day
&metrics=ym:s:users
&date1=2016-08-01&date2=2016-09-30&dimensions=ym:s:<attribution>TrafficSource&attribution=last
&id=31424723
&oauth_token=AQAAAAAUQvf-AAK_6WmdQtnlGUIekdWX2d6RnU8

## Terms

OpenMonth - It is interval of time between first and last day of month.
MetricsForThreeMonth - It is interval which consist of two month and openMonth;



https://api-metrika.yandex.ru/stat/v1/data.csv?dimensions=ym:s:directSearchPhrase,ym:s:directClickOrder,ym:s:directBannerGroup,ym:s:directClickBanner,ym:s:directPhraseOrCond,ym:s:UTMSource,ym:s:UTMMedium,ym:s:UTMCampaign,ym:s:UTMContent&metrics=ym:s:visits,ym:s:pageviews,ym:s:bounceRate,ym:s:avgVisitDurationSeconds,ym:s:sumGoalReachesAny&filters=ym:s:directPlatformType=='search'&limit=10000&offset=1&ids=34322390&oauth_token=AQAEA7qgoVoVAAK_6aPQ1lZ_JEQ8tbtwjJcGY7k&include_undefined=true

https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day&dimensions=ym:s:directSearchPhrase,ym:s:directClickOrder,ym:s:directBannerGroup,ym:s:directClickBanner,ym:s:directPhraseOrCond,ym:s:UTMSource,ym:s:UTMMedium,ym:s:UTMCampaign,ym:s:UTMContent&metrics=ym:s:visits,ym:s:pageviews,ym:s:bounceRate,ym:s:avgVisitDurationSeconds,ym:s:sumGoalReachesAny&filters=ym:s:directPlatformType=='search'&limit=10000&offset=1&ids=34322390&oauth_token=AQAEA7qgoVoVAAK_6aPQ1lZ_JEQ8tbtwjJcGY7k&include_undefined=true


  https://api-metrika.yandex.ru/stat/v1/data?date1=2016-08-01
&date2=2016-09-20
&limit=10000&offset=1
&ids=34322390
&oauth_token=AQAEA7qgoVoVAAK_6aPQ1lZ_JEQ8tbtwjJcGY7k
&filters=ym:s:LastDirectClickOrder!n
&metrics=ym:s:visits,ym:s:bounceRate,ym:s:pageDepth,ym:s:avgVisitDurationSeconds
&dimensions=ym:s:lastDirectClickOrder,ym:s:lastDirectClickBanner,ym:s:lastDirectPhraseOrCond,ym:s:lastDirectSearchPhrase
&include_undefined=true