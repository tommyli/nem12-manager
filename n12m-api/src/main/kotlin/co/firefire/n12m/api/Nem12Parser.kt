// Tommy Li (tommy.li@firefire.co), 2017-05-27

package co.firefire.n12m.api

import org.slf4j.LoggerFactory
import org.springframework.core.io.Resource
import java.io.InputStreamReader
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.regex.Pattern

interface Nem12Parser {

    fun parseNem12Resource(resource: Resource): Collection<NmiMeterRegister>

}

interface Nem12ParserContext {

    fun getCurrentRecordType(): Nem12RecordType?
    fun updateCurrentRecordType(recordType: Nem12RecordType)
    fun getCurrentNmiMeterRegister(): NmiMeterRegister?
    fun updateCurrentNmiMeterRegister(nmiMeterRegister: NmiMeterRegister)
    fun getCurrentIntervalDay(): IntervalDay?
    fun updateCurrentIntervalDay(intervalDay: IntervalDay)
    fun mergeNmiMeterRegisterResult()
    fun mergeIntervalDayResult()

}

interface ErrorCollector {

    fun addError(error: String)

}

private val NEM12_DELIMITER: Pattern = ",".toPattern()
val DEFAULT_DATE_TIME_FORMATTER: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdHHmmss")

enum class Nem12RecordId(val id: String) {
    ID_100("100"), ID_200("200"), ID_300("300"), ID_400("400"), ID_500("500"), ID_900("600");
}

sealed class Nem12RecordType(val nem12RecordId: Nem12RecordId, val nextValidRecordTypes: Set<Nem12RecordType>) {

    fun isNextRecordTypeValid(recordType: Nem12RecordType): Boolean {
        return nextValidRecordTypes.contains(recordType)
    }

    companion object {
        operator fun invoke(nem12RecordId: Nem12RecordId): Nem12RecordType {
            return when (nem12RecordId) {
                Nem12RecordId.ID_100 -> Nem12RecordType.Record100
                Nem12RecordId.ID_200 -> Nem12RecordType.Record200
                Nem12RecordId.ID_300 -> Nem12RecordType.Record300
                Nem12RecordId.ID_400 -> Nem12RecordType.Record400
                Nem12RecordId.ID_500 -> Nem12RecordType.Record500
                Nem12RecordId.ID_900 -> Nem12RecordType.Record900
            }
        }
    }

    object Record100 : Nem12RecordType(Nem12RecordId.ID_100, setOf(Record200, Record900))

    object Record200 : Nem12RecordType(Nem12RecordId.ID_200, setOf(Record300, Record900))

    object Record300 : Nem12RecordType(Nem12RecordId.ID_300, setOf(Record200, Record300, Record400, Record900))

    object Record400 : Nem12RecordType(Nem12RecordId.ID_400, setOf(Record200, Record300, Record400, Record500, Record900))

    object Record500 : Nem12RecordType(Nem12RecordId.ID_500, setOf(Record200, Record300, Record500, Record900))

    object Record900 : Nem12RecordType(Nem12RecordId.ID_900, setOf())

}

data class Nem12Line(val lineNumber: Int, val recordType: Nem12RecordType, val lineItems: List<String>) {

    companion object {
        var log = LoggerFactory.getLogger(this::class.java)
    }

    fun handleLine(parsingContext: Nem12ParserContext, errorCollector: ErrorCollector): Unit {

        val currentRecordType: Nem12RecordType? = parsingContext.getCurrentRecordType()
        if (currentRecordType != null && currentRecordType.isNextRecordTypeValid(currentRecordType)) {
            throw DomainException("Invalid next record type [${recordType}], current record type [${currentRecordType}]")
        }
        try {
            when (recordType) {
                is Nem12RecordType.Record100 -> {
                    log.info("Ignoring Record Type 100")
                }
                is Nem12RecordType.Record200 -> {
                    parsingContext.mergeIntervalDayResult()
                    parsingContext.mergeNmiMeterRegisterResult()

                    val nmi = parseMandatory("nmi", 1, lineNumber, lineItems, { it })
                    val nmiConfig = parseOptional("nmiConfig", 2, lineNumber, lineItems, { it })
                    val registerId = parseMandatory("registerId", 3, lineNumber, lineItems, { it })
                    val nmiSuffix = parseMandatory("nmiSuffix", 4, lineNumber, lineItems, { it })
                    val mdmDataStreamId = parseOptional("mdmDataStreamId", 5, lineNumber, lineItems, { it })
                    val meterSerial = parseMandatory("meterSerial", 6, lineNumber, lineItems, { it })
                    val uom = parseMandatory("uom", 7, lineNumber, lineItems, { UnitOfMeasure.valueOf(it) })
                    val intervalLength = parseMandatory("intervalLength", 8, lineNumber, lineItems, { IntervalLength.fromMinute(it.toInt()) })
                    val nextScheduledReadDate = parseOptional("nextScheduledReadDate", 9, lineNumber, lineItems, { LocalDate.parse(it, DateTimeFormatter.BASIC_ISO_DATE) })

                    val nmiMeterRegister = NmiMeterRegister(nmi, meterSerial, registerId, nmiSuffix, uom, intervalLength)
                    nmiMeterRegister.nmiConfig = nmiConfig
                    nmiMeterRegister.mdmDataStreamId = mdmDataStreamId
                    nmiMeterRegister.nextScheduledReadDate = nextScheduledReadDate

                    parsingContext.updateCurrentNmiMeterRegister(nmiMeterRegister)
                }
                is Nem12RecordType.Record300 -> {
                    parsingContext.mergeIntervalDayResult()

                    val currNmiMeterRegister = parsingContext.getCurrentNmiMeterRegister()
                    if (currNmiMeterRegister != null) {
                        val intervalDate = parseMandatory("intervalDate", 1, lineNumber, lineItems, { LocalDate.parse(it, DateTimeFormatter.BASIC_ISO_DATE) })
                        val quality = parseMandatory("intervalQuality", 50, lineNumber, lineItems, { Quality.valueOf(it.substring(0, 1)) })
                        val qualityMethod = parseOptional("qualityMethod", 50, lineNumber, lineItems, { it?.substring(1, 3) ?: "" })
                        var interval = 1
                        val values = parseMandatory("values", 2, lineNumber, lineItems, { lineItems.subList(2, 50).associateBy({ interval++ }, { it.toDouble() }) })
                        val reasonCode = parseOptional("reasonCode", 51, lineNumber, lineItems, { it })
                        val reasonDescription = parseOptional("reasonDescription", 52, lineNumber, lineItems, { it })
                        val updateDateTime = parseOptional("updateDateTime", 53, lineNumber, lineItems, { LocalDateTime.parse(it, DEFAULT_DATE_TIME_FORMATTER) })
                        val msatsLoadDateTime = parseOptional("msatsLoadDateTime", 54, lineNumber, lineItems, { LocalDateTime.parse(it, DEFAULT_DATE_TIME_FORMATTER) })
                        val intervalQuality = IntervalQuality(quality, qualityMethod, reasonCode, reasonDescription)

                        val intervalDay = IntervalDay(currNmiMeterRegister, intervalDate, intervalQuality)
                        intervalDay.updateDateTime = updateDateTime
                        intervalDay.msatsLoadDateTime = msatsLoadDateTime
                        intervalDay.mergeNewIntervalValues(values.mapValues { (interval, value) ->
                            IntervalValue(
                                    interval,
                                    value,
                                    IntervalQuality(if (quality == Quality.V) Quality.A else quality))
                        },
                                updateDateTime,
                                msatsLoadDateTime
                        )

                        parsingContext.updateCurrentIntervalDay(intervalDay)
                    } else {
                        throw DomainException("Error, processing record 300 before any associated 200 was processed, line $lineNumber")
                    }
                }
                is Nem12RecordType.Record400 -> {
                    val currIntervalDay = parsingContext.getCurrentIntervalDay()
                    if (currIntervalDay != null) {
                        val startInterval = parseMandatory("startInterval", 1, lineNumber, lineItems, { it.toInt() })
                        val endInterval = parseMandatory("endInterval", 2, lineNumber, lineItems, { it.toInt() })
                        val quality = parseMandatory("intervalQuality", 3, lineNumber, lineItems, { Quality.valueOf(it.substring(0, 1)) })
                        val qualityMethod = parseOptional("qualityMethod", 3, lineNumber, lineItems, { it?.substring(1, 3) ?: "" })
                        val reasonCode = parseOptional("reasonCode", 4, lineNumber, lineItems, { it })
                        val reasonDescription = parseOptional("reasonDescription", 5, lineNumber, lineItems, { it })

                        val intervalEvent = IntervalEvent(startInterval..endInterval, IntervalQuality(quality, qualityMethod, reasonCode, reasonDescription))
                        currIntervalDay.appendIntervalEvent(intervalEvent)
                    } else {
                        throw DomainException("Error, processing record 400 before any associated record 300 was processed, line $lineNumber")
                    }
                }
                is Nem12RecordType.Record500 -> {
                    log.info("Ignoring Record Type 500")
                }
                is Nem12RecordType.Record900 -> {
                    parsingContext.mergeNmiMeterRegisterResult()
                }
            }
        } catch (de: DomainException) {
            errorCollector.addError("$de")
        } catch (e: Exception) {
            val message = "Unexpected exception thrown, line $lineNumber, $lineItems, $e"
            log.error(message, e)
            throw RuntimeException(message, e)
        }
    }

    private fun <R> parseMandatory(propertyName: String, position: Int, lineNumber: Int, lineItems: List<String>, transformer: (str: String) -> R): R {
        return try {
            val result = if (lineItems[position].isNotBlank()) transformer(lineItems[position]) else null
            if (result == null) {
                throw DomainException("$propertyName is mandatory and cannot be blank")
            } else {
                result
            }
        } catch (e: Exception) {
            throw DomainException("Error parsing $propertyName, position $position, line $lineNumber, exception: $e, cause: ${e.cause}")
        }
    }

    private fun <R> parseOptional(propertyName: String, position: Int, lineNumber: Int, lineItems: List<String>, transformer: (str: String?) -> R?): R? {
        return try {
            if (lineItems[position].isNotBlank()) transformer(lineItems[position]) else null
        } catch (e: Exception) {
            log.warn("Warning parsing $propertyName, position $position, line $lineNumber, exception: $e, cause: ${e.cause}")
            null
        }
    }
}

class Nem12ParserImpl : Nem12Parser, Nem12ParserContext, ErrorCollector {

    var currRecordType: Nem12RecordType? = null
    var currNmiMeterRegister: NmiMeterRegister? = null
    var currIntervalDay: IntervalDay? = null
    var result: MutableList<NmiMeterRegister> = arrayListOf()
    var errors: MutableList<String> = arrayListOf()

    fun InputStreamReader.forEachNem12Line(delimiter: Pattern = NEM12_DELIMITER, block: (nem12Line: Nem12Line) -> Unit, finalizer: () -> Unit) {
        try {
            this.use { inputStreamReader: InputStreamReader ->
                var lineNumber: Int = 1

                val distinctRecordTypes = mutableSetOf<Nem12RecordType>()
                inputStreamReader.forEachLine { line: String ->
                    val items = line.split(delimiter)
                    val recordType = Nem12RecordType(Nem12RecordId.valueOf("ID_${items[0]}"))
                    val nem12Line = Nem12Line(lineNumber++, recordType, items)
                    distinctRecordTypes.add(recordType)
                    block(nem12Line)
                }
                if (distinctRecordTypes.none { it == Nem12RecordType.Record900 }) {
                    finalizer()
                }
            }
        } catch (e: Exception) {
            throw DomainException("Error splitting file contents using delimiter [$delimiter]: $e", e)
        }
    }

    override fun parseNem12Resource(resource: Resource): Collection<NmiMeterRegister> {
        try {
            InputStreamReader(resource.inputStream).forEachNem12Line(
                    NEM12_DELIMITER, { it.handleLine(this, this) }, { mergeNmiMeterRegisterResult() })
        } catch (e: Exception) {
            errors.add("Error reading file ${resource.filename}: $e")
        }

        if (errors.isNotEmpty()) {
            throw DomainException("${errors.joinToString("\n", "Found following errors when parsing resource [${resource.filename}]:\n", "\n", 100, "...")}")
        } else {
            return result
        }
    }

    override fun getCurrentRecordType(): Nem12RecordType? {
        return currRecordType
    }

    override fun updateCurrentRecordType(recordType: Nem12RecordType) {
        currRecordType = recordType
    }

    override fun getCurrentNmiMeterRegister(): NmiMeterRegister? {
        return currNmiMeterRegister
    }

    override fun updateCurrentNmiMeterRegister(nmiMeterRegister: NmiMeterRegister) {
        currNmiMeterRegister = nmiMeterRegister
    }

    override fun getCurrentIntervalDay(): IntervalDay? {
        return currIntervalDay
    }

    override fun updateCurrentIntervalDay(intervalDay: IntervalDay) {
        currIntervalDay = intervalDay
    }

    override fun mergeNmiMeterRegisterResult() {
        val currNmiMeterRegister = currNmiMeterRegister
        if (currNmiMeterRegister != null) {
            val existingNmiMeterRegister = result.find { it.nmi == currNmiMeterRegister.nmi && it.meterSerial == currNmiMeterRegister.meterSerial && it.registerId == currNmiMeterRegister.registerId && it.nmiSuffix == currNmiMeterRegister.nmiSuffix }

            if (existingNmiMeterRegister != null) {
                existingNmiMeterRegister.putAllDays(currNmiMeterRegister.intervalDays)
            } else {
                result.add(currNmiMeterRegister)
            }
        }
    }

    override fun mergeIntervalDayResult() {
        val currNmiMeterRegister = currNmiMeterRegister
        val currIntervalDay = currIntervalDay

        if (currNmiMeterRegister != null && currIntervalDay != null) {
            currIntervalDay.applyIntervalEvents()
            currNmiMeterRegister.mergeIntervalDay(currIntervalDay)
        }
    }

    override fun addError(error: String) {
        errors.add(error)
    }
}
