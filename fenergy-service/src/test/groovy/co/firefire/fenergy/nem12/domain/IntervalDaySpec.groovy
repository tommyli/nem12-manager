// Tommy Li (tommy.li@firefire.co), 2017-03-15

package co.firefire.fenergy.nem12.domain

import spock.lang.Specification

import static co.firefire.fenergy.nem12.TestUtils.dt
import static co.firefire.fenergy.nem12.TestUtils.dts

class IntervalDaySpec extends Specification {

  NmiMeterRegister nmiMeterRegister = new NmiMeterRegister()

  IntervalDay underTest

  def 'mergeIntervalValues() correctly determines which IntervalValue overrides'() {
    given:
    underTest = new IntervalDay(nmiMeterRegister, dt('2017-01-01'), new IntervalQuality(Quality.A))
    underTest.updateDateTime = null
    underTest.msatsLoadDateTime = dts('2017-01-01 20:28:28')
    underTest.intervalValues = new TreeMap<>([
      1: new IntervalValue(underTest, 1, 1.8, new IntervalQuality(Quality.A)),
      2: new IntervalValue(underTest, 2, 2.8, new IntervalQuality(Quality.E)),
      3: new IntervalValue(underTest, 3, 3.8, new IntervalQuality(Quality.A))
    ])

    when:
    underTest.mergeNewIntervalValues(new TreeMap<>(
      1: new IntervalValue(underTest, 1, 1.81, new IntervalQuality(Quality.A)),
      2: new IntervalValue(underTest, 2, 2.81, new IntervalQuality(Quality.A)),
      3: new IntervalValue(underTest, 3, 3.81, new IntervalQuality(Quality.E))
    ), null, dts('2017-01-01 20:28:28'))

    then:
    underTest.intervalValues == new TreeMap<>(
      1: new IntervalValue(underTest, 1, 1.81, new IntervalQuality(Quality.A)),
      2: new IntervalValue(underTest, 2, 2.81, new IntervalQuality(Quality.A)),
      3: new IntervalValue(underTest, 3, 3.8, new IntervalQuality(Quality.A))
    )
  }
}
