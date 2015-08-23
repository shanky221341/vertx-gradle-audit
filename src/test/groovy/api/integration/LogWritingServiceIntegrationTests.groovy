package api.integration

import org.junit.Before

import audit.api.services.LogWritingService

class LogWritingServiceIntegrationTests {

	def logDirectory = 'test-logs'
	def fileName = 'test-log.log'
	LogWritingService service

	@Before
	void before() {
		service =new LogWritingService(logDirectory,fileName)
	}
}
