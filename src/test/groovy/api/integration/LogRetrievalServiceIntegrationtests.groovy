package api.integration


import static org.junit.Assert.*;
import org.elasticsearch.client.Client

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
	
	
	
}
