package io.maksymdobrynin.snowflakegenerator

data class GeneratorSettings(
	// 00:00:00 on January 1st, 2000 (UTC)
	var startingEpoch: Long = 946684800L,
	var datacenterName: String = "datacenterName",
	var datacenterId: Long = 1,
	var datacenterIP: String = "datacenterIP",
	var workedName: String = "workedName",
	var workedId: Long = 1,
	var workedIP: String = "workedIP",
	var nextTimeSeed: () -> Long = { System.currentTimeMillis() },
	var sequence: Long = 0L,
)
