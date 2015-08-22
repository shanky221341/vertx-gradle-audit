package api.integration


import static org.elasticsearch.common.xcontent.XContentFactory.*
import static org.junit.Assert.*

import java.util.concurrent.CountDownLatch

import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder
import org.elasticsearch.action.admin.indices.mapping.put.PutMappingResponse
import org.elasticsearch.action.bulk.BulkRequestBuilder
import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.junit.Before
import org.junit.Test

import audit.api.services.LogRetrievalService

class LogRetrievalServiceIntegrationtests {

	//Define a client to connect to elasticsearch

	Client client

	LogRetrievalService service
	def index = 'logs-tests'
	def type = 'messages'
	IndexResponse response

	@Before
	void before() {

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build()
		client =new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("localhost",9300))

		CreateIndexRequestBuilder createIndexRequest = client.admin().indices().prepareCreate(index)
		createIndexRequest.execute().actionGet()
		PutMappingResponse putMappingResponse = client.admin().indices()
				.preparePutMapping(index)
				.setType(type)
				.setSource(jsonBuilder().prettyPrint()
				.startObject()
				.startObject(type)
				.startObject("properties")
				.startObject("message").field("type","string").endObject()
				.startObject("user").field("type","string").field("index","not_analyzed").endObject()
				.startObject("source").field("type","string").field("index","not_analyzed").endObject()
				.startObject("date").field("type","date").field("format","dateOptionalTime").endObject()
				.endObject()
				.endObject()
				.endObject())
				.execute()
				.actionGet()
		service = new LogRetrievalService(client,index,type)
	}
	//	@After
	//	void after(){
	//		client.admin().indices().delete(new DeleteIndexRequest(index))
	//	}
	@Test
	void 'should return all logs for tests'() {
		def documents = []
		def today =new Date()
		CountDownLatch latch =new CountDownLatch(1)

		BulkRequestBuilder request = client.prepareBulk().setRefresh(true)

		request.add(client.prepareIndex(index, type, )
				.setSource(jsonBuilder()
				.startObject()
				.field("user", "test-user-1")
				.field("message", "First Message")
				.field("source","test-audit-service")
				.field("date",new Date()-1)
				.endObject()
				)
				)
		request.add(client.prepareIndex(index, type, )
				.setSource(jsonBuilder()
				.startObject()
				.field("user", "test-user-2")
				.field("message", "Second Message")
				.field("source","test-audit-service")
				.field("date",new Date())
				.endObject()
				)
				)
		request.add(client.prepareIndex(index, type, )
				.setSource(jsonBuilder()
				.startObject()
				.field("user", "test-user-3")
				.field("message", "Second Message")
				.field("source","test-audit-service")
				.field("date",new Date()+1)
				.endObject()
				)
				)
		request.execute().actionGet()
		latch.countDown()
		latch.await()

		def userLogs = []
		latch = new CountDownLatch(1)

		service.retrieveAllLogsForUser(){

		}

	}
}
