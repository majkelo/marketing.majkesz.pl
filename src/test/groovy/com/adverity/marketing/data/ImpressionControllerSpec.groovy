package com.adverity.marketing.data

import com.adverity.marketing.Campaign
import com.adverity.marketing.Datasource
import spock.lang.*

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_FOUND
import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest
import grails.plugin.json.view.JsonViewGrailsPlugin

class ImpressionControllerSpec extends Specification implements ControllerUnitTest<ImpressionController>, DomainUnitTest<DailyStats> {


	void setupSpec() {
		defineBeans(new JsonViewGrailsPlugin(applicationContext: applicationContext))
	}

	void "index: returns the correct response"() {
		given:
			controller.dailyStatsService = Mock(DailyStatsService) {
				1 * list(_) >> [new DailyStats(clicks: 5, impressions: 10).save(validate: false)]
			}

		when: "The index action is executed"
			controller.index()

		then: "The response is correct"
			response.status == OK.value()
	}

	void "index: returns proper error if any exception"() {
		given:
			controller.dailyStatsService = Mock(DailyStatsService) {
				1 * list(_) >> { throw new Exception() }
			}

		when: "The index action is executed"
			controller.index()

		then: "The response is correct"
			response.status == INTERNAL_SERVER_ERROR.value()
	}

	void "show: returns NOT_FOUND status if can not find specific instance"() {
		given:
			controller.dailyStatsService = Mock(DailyStatsService) {
				1 * get(null) >> null
			}

		when: "The show action is executed with a null domain"
			controller.show()

		then: "A 404 error is returned"
			response.status == NOT_FOUND.value()
	}

	void "show: returns proper object with a valid id"() {
		given:
			controller.dailyStatsService = Mock(DailyStatsService) {
				1 * get(2) >> new DailyStats()
			}

		when: "A domain instance is passed to the show action"
			params.id = 2
			controller.show()

		then: "A model is populated containing the domain instance"
			response.status == OK.value()
	}

	void "filterByDatasource: filter properly by datasource id"() {
		given:
			Datasource datasource = Mock(Datasource)
			datasource.id >> 1
			Datasource datasource2 = Mock(Datasource)
			datasource2.id >> 2
		and: "link datasources with dailyStats"
			DailyStats dailyStats = Mock(DailyStats) {
				getDatasource() >> datasource
			}
			DailyStats dailyStats2 = Mock(DailyStats) {
				getDatasource() >> datasource2
			}
			DailyStats dailyStats3 = Mock(DailyStats) {
				getDatasource() >> datasource2
			}
			def dataBeforeFiltering = [dailyStats, dailyStats2, dailyStats3]

		when:
			def result = controller.filterByDatasource(dataBeforeFiltering, 2L)
		then:
			result.size() == 2
			!result.contains(dailyStats)
			result.contains(dailyStats2)
			result.contains(dailyStats3)
	}

	void "filterByCampaign: filter properly by campaign id"() {
		given:
			Campaign campaign = Mock(Campaign)
			campaign.id >> 1
			Campaign campaign2 = Mock(Campaign)
			campaign2.id >> 2
		and: "link campaigns with dailyStats"
			DailyStats dailyStats = Mock(DailyStats)
			dailyStats.campaign >> campaign
			DailyStats dailyStats2 = Mock(DailyStats)
			dailyStats2.campaign >> campaign2
			DailyStats dailyStats3 = Mock(DailyStats)
			dailyStats3.campaign >> campaign2
			def dataBeforeFiltering = [dailyStats, dailyStats2, dailyStats3]

		when:
			def result = controller.filterByCampaign(dataBeforeFiltering, 2L)
		then:
			result.size() == 2
			!result.contains(dailyStats)
			result.contains(dailyStats2)
			result.contains(dailyStats3)
	}

	def "filterByStartDate: filter by date not less then startDate"() {
		given: "create date"
			Date exactDate = new Date("2021/07/11")
		and: "link dates with dailyStats"
			DailyStats dailyStats = Mock(DailyStats)
			dailyStats.date >> exactDate - 1
			DailyStats dailyStats2 = Mock(DailyStats)
			dailyStats2.date >> exactDate
			DailyStats dailyStats3 = Mock(DailyStats)
			dailyStats3.date >> exactDate + 1
			def dataBeforeFiltering = [dailyStats, dailyStats2, dailyStats3]

		when:
			def result = controller.filterByStartDate(dataBeforeFiltering, exactDate)
		then:
			result.size() == 2
			!result.contains(dailyStats)
			result.contains(dailyStats2)
			result.contains(dailyStats3)
	}

	def "filterByEndDate: filter by date not less then startDate"() {
		given: "create date"
			Date exactDate = new Date("2021/07/11")
		and: "link dates with dailyStats"
			DailyStats dailyStats = Mock(DailyStats)
			dailyStats.date >> exactDate - 1
			DailyStats dailyStats2 = Mock(DailyStats)
			dailyStats2.date >> exactDate
			DailyStats dailyStats3 = Mock(DailyStats)
			dailyStats3.date >> exactDate + 1
			def dataBeforeFiltering = [dailyStats, dailyStats2, dailyStats3]

		when:
			def result = controller.filterByEndDate(dataBeforeFiltering, exactDate)
		then:
			result.size() == 2
			result.contains(dailyStats)
			result.contains(dailyStats2)
			!result.contains(dailyStats3)
	}

	def "filterByDateBetween: filter by date not less then startDate"() {
		given: "create date"
			Date exactDate = new Date("2021/07/11")
		and: "link dates with dailyStats"
			DailyStats dailyStats = Mock(DailyStats)
			dailyStats.date >> exactDate - 1
			DailyStats dailyStats2 = Mock(DailyStats)
			dailyStats2.date >> exactDate
			DailyStats dailyStats3 = Mock(DailyStats)
			dailyStats3.date >> exactDate + 1
			def dataBeforeFiltering = [dailyStats, dailyStats2, dailyStats3]

		when:
			def result = controller.filterByDateBetween(dataBeforeFiltering, exactDate, exactDate)
		then:
			result.size() == 1
			!result.contains(dailyStats)
			result.contains(dailyStats2)
			!result.contains(dailyStats3)
	}
}
