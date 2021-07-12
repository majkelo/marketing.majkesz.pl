package com.adverity.marketing

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import org.grails.datastore.mapping.core.Datastore
import org.springframework.beans.factory.annotation.Autowired

@Integration
@Rollback
class CampaignServiceSpec extends CommonIntegrationSpec {

	CampaignService campaignService
	@Autowired
	Datastore datastore

	private Long setupData() {
		createData().campaign.id
	}

	void "test get"() {
		setupData()

		expect:
			campaignService.get(1) != null
	}

	void "test list"() {
		setupData()

		when:
			List<Campaign> campaignList = campaignService.list(max: 2, offset: 2)

		then:
			campaignList.size() == 2
	}

	void "test count and delete"() {
		setupData()

		Datasource datasource = Datasource.findOrCreateWhere(name: "datasourceName").save(flush: true, failOnError: true)
		Campaign campaign = Campaign.findOrCreateWhere(name: "campaignNameX", datasource: datasource).save()
		Long campaignId = campaign.id
		Integer counter = campaignService.count()

		when:
			campaignService.delete(campaignId)
			datastore.currentSession.flush()

		then:
			campaignService.count() == counter - 1
	}

	void "test save"() {
		given:
			Datasource datasource = Datasource.findOrCreateWhere(name: "datasourceName").save(flush: true, failOnError: true)
			Campaign campaign = Campaign.findOrCreateWhere(name: "campaignName", datasource: datasource)

		when:
			campaignService.save(campaign)

		then:
			campaign.id != null
	}
}
