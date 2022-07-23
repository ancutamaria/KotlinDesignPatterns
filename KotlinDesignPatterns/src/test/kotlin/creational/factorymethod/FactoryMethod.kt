package creational.factorymethod

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

sealed class Country {

    object Canada: Country()

}

object Spain: Country()
class Greece(val someProperty: String): Country()
data class USA(val someProperty: String): Country()

class Currency(val code: String)


object CurrencyFactory {

    fun currencyForCountry(country: Country): Currency =
        when(country) {
            is Spain -> Currency("EUR")
            is Greece -> Currency("EUR")
            is USA -> Currency("USD")
            is Country.Canada -> Currency("CAD")
        }

}

class FactoryMethodTest {

    @Test
    fun currencyTest() {

        val spainCurrency = CurrencyFactory.currencyForCountry(Spain).code
        println("Spain currency: $spainCurrency")
        Assertions.assertThat(spainCurrency).isEqualTo("EUR")

        val greekCurrency = CurrencyFactory.currencyForCountry(Greece("")).code
        println("Greek currency: $greekCurrency")
        Assertions.assertThat(greekCurrency).isEqualTo("EUR")

        val usaCurrency = CurrencyFactory.currencyForCountry(USA("")).code
        println("USA currency: $usaCurrency")
        Assertions.assertThat(usaCurrency).isEqualTo("USD")

        val canadaCurrency = CurrencyFactory.currencyForCountry(Country.Canada).code
        println("Canada currency: $canadaCurrency")
        Assertions.assertThat(canadaCurrency).isEqualTo("CAD")

    }

}