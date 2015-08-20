package audit.api

import org.vertx.groovy.platform.Verticle
import org.elasticsearch.client.Client
import org.elasticsearch.common.settings.ImmutableSettings

import org.elasticsearch.action.admin.indices.exists.indices.IndicesExistsResponse

import org.elasticsearch.common.settings.Settings
import org.elasticsearch.client.transport.TransportClient
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder
import org.elasticsearch.common.transport.InetSocketTransportAddress
import audit.api.services.*
import org.elasticsearch.client.Client	

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
	  client.addTransportAddress(new InetSocketTransportAddress("localhost",9300))
	  
//	  CreateIndexRequestBuilder createIndexRequest = client.admin().indices().prepareCreate(_index)
	  
	  IndicesExistsResponse res =client.admin().indices().prepareCreate(_index).execute().actionGet()
	  println res
	  
	  
  }
}

