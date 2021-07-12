package com.adverity.marketing

import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared

@Integration
class CampaignFunctionalSpec extends CommonIntegrationSpec {

	@Shared
	@AutoCleanup
	HttpClient client

	@Shared
	String baseUrl
	@Shared
	Campaign campaign

	@OnceBefore
	void init() {
		baseUrl = "http://localhost:$serverPort"
		this.client = HttpClient.create(new URL(baseUrl))

		campaign = createData().campaign
	}

	String getResourcePath() {
		return "${baseUrl}/campaign"
	}

	void "Test the index action"() {
		when: "The index action is requested"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePath), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 5
			response.body().data.first().id == campaign.id
			response.body().data.first().name == campaign.name
	}

	void "Test the show action correctly renders an instance"() {
		when: "When the show action is called to retrieve a resource"
			def id = campaign.id
			String path = "${resourcePath}/${id}"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(path), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.id == campaign.id
			response.body().data.name == campaign.name
	}

}
