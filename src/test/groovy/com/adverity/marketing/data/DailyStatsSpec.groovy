package com.adverity.marketing.data

import com.adverity.marketing.Campaign
import com.adverity.marketing.Datasource
import grails.testing.gorm.DomainUnitTest
import spock.lang.Specification
import spock.lang.Unroll

class DailyStatsSpec extends Specification implements DomainUnitTest<DailyStats> {

	def setup() {
	}

	void "getDatasource: returns proper datasource related with campaign"() {
		given: "create datasource"
			Datasource datasource = new Datasource(name: "ds").save(validate: false)
		and: "create campaign"
			Campaign campaign = new Campaign(datasource: datasource).save(validate: false)
		and: "link campaign with dailyStats"
			DailyStats dailyStats = new DailyStats(campaign: campaign).save(validate: false)
		expect: "proper datasource"
			dailyStats.getDatasource().name == "ds"
	}

	@Unroll
	void "getCTR: returns valid ratio impressions to clicks"() {
		given: "create basic dailyStats"
			DailyStats dailyStats = new DailyStats(clicks: clicks, impressions: impressions).save(validate: false)
		expect:
			dailyStats.getCTR() == expectedCTR

		where:
			clicks | impressions || expectedCTR
			1      | 10          || 0.1
			52     | 100         || 0.52
			0      | 200         || 0
			0      | 0           || 0
	}
}
