package api.verticle

import org.vertx.groovy.platform.Verticle


class ApiVerticle extends Verticle {
	
	def auditPort = 9103
	def auditHost = 'localhost'
	def elasticsearchHost = 'localhost'
	def elasticsearchPort = 9300
	
  def start() {
  	
	  println "auditPort is $auditPort"
	  println "auditPort is $auditHost"
  }
}

