## Main requests to the metrika yandex Api.

 ### Get visits
 https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day&ids=<counterId>&metrics=ym:s:visits&date1=2016-08-01&date2=2016-09-20&oauth_token=<token>
 ### Get goal visits

  https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day&ids=<counterId>&metrics=ym:s:visits&date1=2016-08-01&date2=2016-09-20&dimensions=ym:s:<attribution>TrafficSource&attribution=last&oauth_token=<token>



  https://api-metrika.yandex.ru/stat/v1/data/bytime?group=day
&metrics=ym:s:visits,ym:s:goal13582120visits
&date1=2016-08-01&date2=2016-09-20&dimensions=ym:s:<attribution>TrafficSource&attribution=last
&id=31424723
&oauth_token=AQAAAAAUQvf-AAK_6WmdQtnlGUIekdWX2d6RnU8