package io.maksymdobrynin.snowflakegenerator

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableConfigurationProperties(GeneratorSettingsProperties::class)
open class ApplicationConfig {

	@Bean
	open fun generator(settingsProps: GeneratorSettingsProperties): Generator {
		val settings = GeneratorSettings(
			startingEpoch = settingsProps.startingEpoch,
			datacenterName = settingsProps.nodeName,
			datacenterId = extract5Bits(settingsProps.nodeName),
			datacenterIP = settingsProps.nodeIp,
			workedName = settingsProps.podName,
			workedId = settingsProps.workedId,
			workedIP = settingsProps.podIp,
			sequence = settingsProps.sequence
		)
		return Generator(settings)
	}

	private fun extract5Bits(name: String): Long =
		name.hashCode().toLong() and 0x1F
}

@ConfigurationProperties(prefix = "snowflake-settings")
data class GeneratorSettingsProperties(
	var startingEpoch: Long = 946684800L,
	var nodeName: String = "nodeName",
	var nodeIp: String = "nodeIp",
	var podName: String = "podName",
	var podIp: String = "podIp",
	var workedId: Long = 0,
	var sequence: Long = 0L,
)
