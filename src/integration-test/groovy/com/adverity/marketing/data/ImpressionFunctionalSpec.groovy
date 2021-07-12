package com.adverity.marketing.data

import com.adverity.marketing.CommonIntegrationSpec
import grails.testing.mixin.integration.Integration
import grails.testing.spock.OnceBefore
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpResponse
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import spock.lang.AutoCleanup
import spock.lang.Shared

@Integration
class ImpressionFunctionalSpec extends CommonIntegrationSpec {

	@Shared
	@AutoCleanup
	HttpClient client

	@Shared
	String baseUrl
	@Shared
	DailyStats dailyStats

	@OnceBefore
	void init() {
		baseUrl = "http://localhost:$serverPort"
		this.client = HttpClient.create(new URL(baseUrl))

		dailyStats = createData().dailyStats
	}

	String getResourcePath() {
		return "${baseUrl}/impression"
	}

	void "Test the index action"() {
		when: "The index action is requested"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePath), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 4
			response.body().data.first().id == dailyStats.id
			response.body().data.first().impressions == dailyStats.impressions
	}

	void "Test the index action with filtering by datasource"() {
		when: "The index action is requested"
			String resourcePathWithParams = resourcePath + "?datasource=1"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 3
			response.body().data.first().id == dailyStats.id
			response.body().data.first().impressions == dailyStats.impressions
	}

	void "Test the index action with filtering by campaign"() {
		when: "The index action is requested"
			String resourcePathWithParams = resourcePath + "?campaign=2"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 1
			response.body().data.first().id == 3
			response.body().data.first().impressions == 200
	}

	void "Test the index action with filtering by startDate"() {
		when: "The index action is requested"
			Date fiveDaysAgo = new Date() - 5
			String resourcePathWithParams = resourcePath + "?startDate=" + fiveDaysAgo.format("yyyy-MM-dd")
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 1
			response.body().data.first().id == 4
			response.body().data.first().impressions == 200
	}

	void "Test the index action with filtering by endDate"() {
		when: "The index action is requested"
			Date fiveDaysAgo = new Date() - 5
			String resourcePathWithParams = resourcePath + "?endDate=" + fiveDaysAgo.format("yyyy-MM-dd")
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 3
			response.body().data.first().id == 1
			response.body().data.first().impressions == 100
	}

	void "Test the index action with filtering by startDate and endDate"() {
		when: "The index action is requested"
			Date fiveDaysAgo = new Date() - 5
			Date nineDaysAgo = new Date() - 9
			String resourcePathWithParams = resourcePath + "?startDate=" + nineDaysAgo.format("yyyy-MM-dd") + "&endDate=" + fiveDaysAgo.format("yyyy-MM-dd")
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 2
			response.body().data.first().id == 2
			response.body().data.first().impressions == 100
	}

	void "Test the index action with count"() {
		when: "The index action is requested"
			String resourcePathWithParams = resourcePath + "?count=true"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.impressionsCount == 600
	}

	void "Test the index action with countPerDay and startDate"() {
		when: "The index action is requested"
			Date nineDaysAgo = new Date() - 9
			String resourcePathWithParams = resourcePath + "?countPerDay=true" + "&startDate=" + nineDaysAgo.format("yyyy-MM-dd")
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePathWithParams), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 2
			response.body().data.first().impressionsCount == 300
	}

	void "Test the show action correctly renders an instance"() {
		when: "When the show action is called to retrieve a resource"
			def id = dailyStats.id
			String path = "${resourcePath}/${id}"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(path), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.id == dailyStats.id
			response.body().data.impressions == dailyStats.impressions
			!response.body().data.clicks
	}
}
