// Tommy Li (tommy.li@firefire.co), 2017-02-20

package co.firefire.n12m.api

import java.time.LocalDate
import java.util.*
import javax.persistence.CascadeType
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MapKey
import javax.persistence.OneToMany
import javax.persistence.OrderBy
import org.hibernate.annotations.GenericGenerator as HbmGenericGenerator
import org.hibernate.annotations.Parameter as HbmParameter

@Entity
data class NmiMeterRegister(

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
        var id: Long = -1,

        var nmi: String = "",
        var meterSerial: String = "",
        var registerId: String = "",
        var nmiSuffix: String = "",

        @Enumerated(EnumType.STRING)
        var uom: UnitOfMeasure = UnitOfMeasure.KWH,

        @Convert(converter = IntervalLengthConverter::class)
        var intervalLength: IntervalLength = IntervalLength.IL_30,

        @OneToMany(mappedBy = "nmiMeterRegister", cascade = arrayOf(CascadeType.ALL))
        @MapKey(name = "intervalDate")
        @OrderBy("intervalDate")
        var intervalDays: SortedMap<LocalDate, IntervalDay> = TreeMap()

) {

    var nmiConfig: String? = null
    var mdmDataStreamId: String? = null
    var nextScheduledReadDate: LocalDate = LocalDate.MIN

    fun getDay(localDate: LocalDate): IntervalDay? {
        return intervalDays.get(localDate)
    }

    override fun toString(): String {
        return "NmiMeterRegister(id=$id, nmi='$nmi', meterSerial='$meterSerial', registerId='$registerId', nmiSuffix='$nmiSuffix')"
    }
}
