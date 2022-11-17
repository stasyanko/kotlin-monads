import arrow.core.Either
import arrow.core.Either.Left
import arrow.core.continuations.either
import kotlinx.coroutines.runBlocking

object NotFound

class Speaker {
    fun getTalk(): Either<NotFound, Talk> = Left(NotFound)
}

class Talk {
    fun getConference(): Either<NotFound, Conference> = Left(NotFound)
}

class City

class Conference {
    fun getCity(): Either<NotFound, City> = Left(NotFound)
}

suspend fun cityToVisit(speaker: Speaker): Either<NotFound, Conference> = either{
    val s = speaker.getTalk().bind()
    s.getConference().bind()
}


fun main() = runBlocking {
    val city = cityToVisit(Speaker())
    when(city) {
        is Left -> {
            println("Error: " + city.value)
        }
        is Either.Right -> {
            println("City:" + city.value)
        }
    }
}