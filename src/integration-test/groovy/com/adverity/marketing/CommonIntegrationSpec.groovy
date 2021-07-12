package com.adverity.marketing

import com.adverity.marketing.data.DailyStats
import spock.lang.Specification

class CommonIntegrationSpec extends Specification {

	Map createData() {
		DailyStats dailyStats
		Campaign campaign
		Datasource datasource
		DailyStats.withTransaction {
			datasource = Datasource.findOrCreateWhere(name: "datasourceName").save(flush: true, failOnError: true)
			Datasource datasource2 = Datasource.findOrCreateWhere(name: "datasourceName2").save(flush: true, failOnError: true)
			Datasource.findOrCreateWhere(name: "datasourceName3").save(flush: true, failOnError: true)
			Datasource.findOrCreateWhere(name: "datasourceName4").save(flush: true, failOnError: true)
			Datasource.findOrCreateWhere(name: "datasourceName5").save(flush: true, failOnError: true)

			campaign = Campaign.findOrCreateWhere(datasource: datasource, name: "campaignNAme").save(flush: true, failOnError: true)
			Campaign campaign2 = Campaign.findOrCreateWhere(datasource: datasource2, name: "campaignName2").save(flush: true, failOnError: true)
			Campaign.findOrCreateWhere(name: "campaignName3", datasource: datasource).save(flush: true, failOnError: true)
			Campaign.findOrCreateWhere(name: "campaignName4", datasource: datasource).save(flush: true, failOnError: true)
			Campaign.findOrCreateWhere(name: "campaignName5", datasource: datasource).save(flush: true, failOnError: true)

			Date now = new Date().clearTime()
			dailyStats = DailyStats.findOrCreateWhere(campaign: campaign, clicks: 1, impressions: 100, date: now - 10).save()
			DailyStats.findOrCreateWhere(campaign: campaign, clicks: 2, impressions: 100, date: now - 8).save()
			DailyStats.findOrCreateWhere(campaign: campaign2, clicks: 11, impressions: 200, date: now - 8).save()
			DailyStats.findOrCreateWhere(campaign: campaign, clicks: 12, impressions: 200, date: now).save()
		}
		return [dailyStats: dailyStats, campaign: campaign, datasource: datasource]
	}
}
