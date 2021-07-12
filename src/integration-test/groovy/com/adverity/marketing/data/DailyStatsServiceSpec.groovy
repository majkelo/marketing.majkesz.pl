package com.adverity.marketing.data

import com.adverity.marketing.Campaign
import com.adverity.marketing.CommonIntegrationSpec
import com.adverity.marketing.Datasource
import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired

@Integration
@Rollback
class DailyStatsServiceSpec extends CommonIntegrationSpec {

	DailyStatsService dailyStatsService
	@Autowired
	Datastore datastore

	private Long setupData() {
		createData().dailyStats.id
	}

	void "test get"() {
		setupData()

		expect:
			dailyStatsService.get(1) != null
	}

	void "test list"() {
		setupData()

		when:
			List<DailyStats> dailyStatsList = dailyStatsService.list(max: 2, offset: 2)

		then:
			dailyStatsList.size() == 2
	}

	void "test count and delete"() {
		given:
			setupData()
			Datasource datasource = Datasource.findOrCreateWhere(name: "datasourceName").save(flush: true, failOnError: true)
			Campaign campaign = Campaign.findOrCreateWhere(name: "campaignName", datasource: datasource).save(flush: true, failOnError: true)
			DailyStats dailyStats = DailyStats.findOrCreateWhere(campaign: campaign, clicks: 1, impressions: 100, date: new Date() + 100).save()
			Long dailyStatsId = dailyStats.id
			Integer counter = dailyStatsService.count()

		when:
			dailyStatsService.delete(dailyStatsId)
			datastore.currentSession.flush()

		then:
			dailyStatsService.count() == counter - 1
	}

	void "test save"() {
		when:
			Datasource datasource = Datasource.findOrCreateWhere(name: "datasourceName").save(flush: true, failOnError: true)
			Campaign campaign = Campaign.findOrCreateWhere(name: "campaignName", datasource: datasource).save(flush: true, failOnError: true)
			DailyStats dailyStats = DailyStats.findOrCreateWhere(campaign: campaign, clicks: 1, impressions: 100, date: new Date() + 100)
			dailyStatsService.save(dailyStats)

		then:
			dailyStats.id != null
	}
}
