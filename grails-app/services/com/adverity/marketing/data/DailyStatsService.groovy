package com.adverity.marketing.data

import grails.gorm.services.Service

@Service(DailyStats)
interface DailyStatsService {

    DailyStats get(Serializable id)

    List<DailyStats> list(Map args)

    Long count()

    DailyStats delete(Serializable id)

    DailyStats save(DailyStats dailyStats)

}
