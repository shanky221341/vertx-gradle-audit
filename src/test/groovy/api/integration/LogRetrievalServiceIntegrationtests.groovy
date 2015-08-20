package api.integration


import static org.junit.Assert.*;
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder
import org.elasticsearch.common.transport.InetSocketTransportAddress

import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.Ignore

import audit.api.services.LogRetrievalService

class LogRetrievalServiceIntegrationtests {


	Client client
	LogRetrievalService service
	def index = 'logs-tests'
	def type = 'messages'

	@Before
	void before() {
		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build()
		client =new TransportClient(settings)
		client.addTransportAddress(new InetSocketTransportAddress("localhost",9300))
		
		CreateIndexRequestBuilder createIndexRequest = client.admin().indices().prepareCreate(index)
	}
}
