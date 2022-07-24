package creational.abstractfactory

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

interface DataSource

class DatabaseDataSource: DataSource

class NetworkDataSource: DataSource

abstract class DataSourceFactory {

    abstract fun makeDataSource(): DataSource

    /*

    inline -
    An inline function is one for which the compiler copies the code from the function definition directly into the code
    of the calling function rather than creating a separate set of instructions in memory. This eliminates call-linkage
    overhead and can expose significant optimization opportunities.

    reified -
    fun <T> myGenericFun(c: Class<T>)
    In the body of a generic function like myGenericFun, you can't access the type T because it's only available at
    compile time but erased at runtime. Therefore, if you want to use the generic type as a normal class in the function
    body you need to explicitly pass the class as a parameter as shown in myGenericFun.
    If you create an inline function with a reified T, the type of T can be accessed even at runtime, and thus you
    do not need to pass the Class<T> additionally. You can work with T as if it was a normal class - e.g. you might want
    to check whether a variable is an instance of T, which you can easily do then: myVar is T.
    Such an inline function with reified type T looks as follows:
    inline fun <reified T> myGenericFun()

    Normal functions (not marked as inline) cannot have reified parameters.

     */
    companion object {
        inline fun <reified  T> createFactory(): DataSourceFactory =
            when(T::class) {
                DatabaseDataSource::class -> DatabaseFactory()
                NetworkDataSource::class -> NetworkFactory()
                else -> throw IllegalArgumentException()
            }
    }

}

class DatabaseFactory: DataSourceFactory() {

    override fun makeDataSource(): DataSource = DatabaseDataSource()

}

class NetworkFactory: DataSourceFactory() {

    override fun makeDataSource(): DataSource = NetworkDataSource()

}

class AbstractFactoryTest {

    @Test
    fun abstractFactoryTest() {

        val dataSourceNetworkFactory = DataSourceFactory.createFactory<NetworkDataSource>()
        val dataSourceNetwork = dataSourceNetworkFactory.makeDataSource()
        println("Created datasource $dataSourceNetwork")

        Assertions.assertThat(dataSourceNetwork).isInstanceOf(NetworkDataSource::class.java)

        val dataSourceDatabaseFactory = DataSourceFactory.createFactory<DatabaseDataSource>()
        val dataSourceDatabase = dataSourceDatabaseFactory.makeDataSource()
        println("Created datasource $dataSourceDatabase")

        Assertions.assertThat(dataSourceDatabase).isInstanceOf(DatabaseDataSource::class.java)

    }
}