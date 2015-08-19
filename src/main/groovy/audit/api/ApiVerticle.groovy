package audit.api

import org.vertx.groovy.platform.Verticle


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
  }
}

