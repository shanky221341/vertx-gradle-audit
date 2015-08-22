package audit.api

import static org.elasticsearch.common.xcontent.XContentFactory.*

import org.elasticsearch.action.index.IndexResponse
import org.elasticsearch.client.Client
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.common.settings.ImmutableSettings
import org.elasticsearch.common.settings.Settings
import org.elasticsearch.common.transport.InetSocketTransportAddress
import org.vertx.groovy.platform.Verticle

import audit.api.services.*

class ApiVerticle extends Verticle {

	def auditPort = 9103
	def auditHost = 'localhost'
	def elasticsearchHost = 'localhost'
	def elasticsearchPort = 9300

	def _index = 'index'
	def _type ='entries'

	Client client
	LogRetrievalService retrievalService
	LogWritingService writingService

	def start() {

		println "auditPort is $auditPort"
		println "auditPort is $auditHost"

		Settings settings = ImmutableSettings.settingsBuilder().put("cluster.name", "elasticsearch").build()
		client =new TransportClient(settings)
				.addTransportAddress(new InetSocketTransportAddress("localhost",9300))
		IndexResponse response = client.prepareIndex("twitter", "tweet", "1")
				.setSource(jsonBuilder()
				.startObject()
				.field("user", "kimchy")
				.field("postDate", new Date())
				.field("message", "trying out Elasticsearch")
				.endObject()
				)
				.execute()
				.actionGet();
		//		CreateIndexRequestBuilder createIndexRequest = client.admin().indices().prepareCreate(_index)
				createIndexRequest.addMapping(_type, [
					messages:[
						properties : [
							date: [
								type: 'date',
								format: 'dateOptionalTime'
							],
							message:[
								type : 'string',
								index: 'not_analysed'
							],
							source: [
								type :'string',
								index: 'not_analysed'
		
							]
						]
					]
				])
//		createIndexRequest.execute.actionGet()
				String index = response.getIndex()
				print index
	}
}

