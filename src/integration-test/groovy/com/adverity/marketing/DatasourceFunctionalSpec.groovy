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
class DatasourceFunctionalSpec extends CommonIntegrationSpec {

	@Shared
	@AutoCleanup
	HttpClient client

	@Shared
	String baseUrl
	@Shared
	Datasource datasource

	@OnceBefore
	void init() {
		baseUrl = "http://localhost:$serverPort"
		this.client = HttpClient.create(new URL(baseUrl))

		datasource = createData().datasource
	}

	String getResourcePath() {
		return "${baseUrl}/datasource"
	}

	void "Test the index action"() {
		when: "The index action is requested"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(resourcePath), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.size() == 5
			response.body().data.first().id == datasource.id
			response.body().data.first().name == datasource.name
	}

	void "Test the show action correctly renders an instance"() {
		when: "When the show action is called to retrieve a resource"
			def id = datasource.id
			String path = "${resourcePath}/${id}"
			HttpResponse<List<Map>> response = client.toBlocking().exchange(HttpRequest.GET(path), Map)

		then: "The response is correct"
			response.status == HttpStatus.OK
			response.body().data.id == datasource.id
			response.body().data.name == datasource.name
	}
}
