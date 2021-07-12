package com.adverity.marketing

import spock.lang.*

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import static org.springframework.http.HttpStatus.OK
import static org.springframework.http.HttpStatus.NOT_FOUND
import grails.testing.web.controllers.ControllerUnitTest
import grails.testing.gorm.DomainUnitTest
import grails.plugin.json.view.JsonViewGrailsPlugin

class CampaignControllerSpec extends Specification implements ControllerUnitTest<CampaignController>, DomainUnitTest<Campaign> {

	void setupSpec() {
		defineBeans(new JsonViewGrailsPlugin(applicationContext: applicationContext))
	}

	void "index: returns the correct response"() {
		given:
			controller.campaignService = Mock(CampaignService) {
				1 * list(_) >> []
			}

		when: "The index action is executed"
			controller.index()

		then: "The response is correct"
			response.status == OK.value()
	}

	void "index: returns proper error if any exception"() {
		given:
			controller.campaignService = Mock(CampaignService) {
				1 * list(_) >> { throw new Exception() }
			}

		when: "The index action is executed"
			controller.index()

		then: "The response is correct"
			response.status == INTERNAL_SERVER_ERROR.value()
	}

	void "show: returns NOT_FOUND status if can not find specific instance"() {
		given:
			controller.campaignService = Mock(CampaignService) {
				1 * get(null) >> null
			}

		when: "The show action is executed with a null domain"
			controller.show()

		then: "A 404 error is returned"
			response.status == NOT_FOUND.value()
	}

	void "show: returns proper object with a valid id"() {
		given:
			controller.campaignService = Mock(CampaignService) {
				1 * get(2) >> new Campaign()
			}

		when: "A domain instance is passed to the show action"
			params.id = 2
			controller.show()

		then: "A model is populated containing the domain instance"
			response.status == OK.value()
	}
}
