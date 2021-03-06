// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.fenergy.nem12.domain

import co.firefire.fenergy.shared.domain.IntervalLength
import co.firefire.fenergy.shared.domain.LoginNmi
import co.firefire.fenergy.shared.domain.UnitOfMeasure
import co.firefire.fenergy.shared.repository.IntervalLengthConverter
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.MapKey
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import org.hibernate.annotations.GenericGenerator as HbmGenericGenerator
import org.hibernate.annotations.Parameter as HbmParameter

@Entity
data class NmiMeterRegister(

        @ManyToOne(optional = false)
        @JoinColumn(name = "login_nmi", referencedColumnName = "id", nullable = false)
        var loginNmi: LoginNmi = LoginNmi(),

        var meterSerial: String = "",
        var registerId: String = "",
        var nmiSuffix: String = ""

) : Comparable<NmiMeterRegister> {

    @Id
    @HbmGenericGenerator(
            name = "NmiMeterRegisterIdSeq",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = arrayOf(
                    HbmParameter(name = "sequence_name", value = "nmi_meter_register_id_seq"),
                    HbmParameter(name = "initial_value", value = "1000"),
                    HbmParameter(name = "increment_size", value = "1")
            )
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "NmiMeterRegisterIdSeq")
    var id: Long? = null

    var version: Int? = 0

    var nmiConfig: String? = null
    var mdmDataStreamId: String? = null
    var nextScheduledReadDate: LocalDate? = null

    @Enumerated(EnumType.STRING)
    var uom: UnitOfMeasure = UnitOfMeasure.KWH

    @Convert(converter = IntervalLengthConverter::class)
    var intervalLength: IntervalLength = IntervalLength.IL_30

    @OneToMany(mappedBy = "nmiMeterRegister", cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
    @MapKey(name = "intervalDate")
    @OrderBy("intervalDate")
    var intervalDays: SortedMap<LocalDate, IntervalDay> = TreeMap()

    fun getDay(localDate: LocalDate): IntervalDay? {
        return intervalDays.get(localDate)
    }

    fun getVolumeType(): VolumeType {
        return when {
            nmiSuffix.startsWith("E") -> VolumeType.CONSUMPTION
            nmiSuffix.startsWith("B") -> VolumeType.GENERATION
            else -> VolumeType.OTHER
        }
    }

    fun intervalRange(): IntRange {
        return 1..(Duration.ofDays(1).toMinutes().toInt() / intervalLength.minute)
    }

    fun mergeIntervalDays(newIntervalDays: Collection<IntervalDay>) {
        newIntervalDays.forEach({ mergeIntervalDay(it) })
    }

    fun mergeIntervalDay(newIntervalDay: IntervalDay) {
        intervalDays.merge(newIntervalDay.intervalDate, newIntervalDay, { existing: IntervalDay, new: IntervalDay ->
            if (existing.intervalQuality.quality == Quality.V || new.intervalQuality.quality == Quality.V) {
                existing.mergeNewIntervalValues(new.intervalValues, new.updateDateTime, new.msatsLoadDateTime)
                existing
            } else {
                val existingIntervalDayDateTime = existing.updateDateTime ?: existing.msatsLoadDateTime ?: LocalDateTime.now()
                val newIntervalDayDateTime = new.updateDateTime ?: new.msatsLoadDateTime ?: LocalDateTime.now()
                val existingQuality = TimestampedQuality(existing.intervalQuality.quality, existingIntervalDayDateTime)
                val newQuality = TimestampedQuality(new.intervalQuality.quality, newIntervalDayDateTime)

                if (newQuality >= existingQuality) {
                    existing.replaceIntervalValues(new.intervalValues)
                    existing
                } else {
                    existing
                }
            }
        })
    }

    override fun compareTo(other: NmiMeterRegister) = compareValuesBy(this, other, { it.loginNmi }, { it.meterSerial }, { it.registerId }, { it.nmiSuffix })

    override fun toString() = "NmiMeterRegister(id=$id, loginNmi='$loginNmi', meterSerial='$meterSerial', registerId='$registerId', nmiSuffix='$nmiSuffix')"

}
