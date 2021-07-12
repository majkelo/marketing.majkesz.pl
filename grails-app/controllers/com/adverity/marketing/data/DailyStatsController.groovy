package com.adverity.marketing.data

import com.adverity.marketing.DateDimensionFilter
import com.adverity.marketing.RegularDimensionFilter
import org.springframework.http.HttpStatus

abstract class DailyStatsController implements RegularDimensionFilter, DateDimensionFilter {

	static responseFormats = ['json', 'xml']

	DailyStatsService dailyStatsService

	def index(Integer max) {
		try {
			List<DailyStats> dailyStats = dailyStatsService.list(params)
			if (params.campaign) {
				dailyStats = filterByCampaign(dailyStats, params.long("campaign"))
			}
			if (params.datasource) {
				dailyStats = filterByDatasource(dailyStats, params.long("datasource"))
			}
			if (params.startDate && params.endDate) {
				dailyStats = filterByDateBetween(dailyStats, params.date('startDate', ['yyyy-MM-dd', 'yyyyMMdd', 'yyMMdd']), params.date('endDate', ['yyyy-MM-dd', 'yyyyMMdd', 'yyMMdd']))
			} else if (params.startDate) {
				dailyStats = filterByStartDate(dailyStats, params.date('startDate', ['yyyy-MM-dd', 'yyyyMMdd', 'yyMMdd']))
			} else if (params.endDate) {
				dailyStats = filterByEndDate(dailyStats, params.date('endDate', ['yyyy-MM-dd', 'yyyyMMdd', 'yyMMdd']))
			}
			if (params.count) {
				render(view: "count", model: [dailyStatsList: dailyStats])
				return
			} else if (params.countDaily || params.countPerDay) {
				def groupedDailyStatsList = dailyStats.groupBy { it.date }.sort()

				render(view: "grouped", model: [groupedDailyStatsList: groupedDailyStatsList.values()])
				return
			}
			respond dailyStats
		} catch (Exception exception) {
			log.error("General error during index()", exception)
			render status: HttpStatus.INTERNAL_SERVER_ERROR
			return
		}
	}

	def show(Long id) {
		DailyStats dailyStats = dailyStatsService.get(id)
		respond dailyStats
	}
}
