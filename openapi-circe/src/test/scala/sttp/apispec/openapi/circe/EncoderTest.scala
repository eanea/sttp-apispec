package sttp.apispec.openapi
package circe

import io.circe.syntax._
import org.scalatest.funsuite.AnyFunSuite

class EncoderTest extends AnyFunSuite with ResourcePlatform {
  val petstore: OpenAPI = OpenAPI(
    info = Info(
      title = "Sample Pet Store App",
      summary = Some("A pet store manager."),
      description = Some("This is a sample server for a pet store."),
      termsOfService = Some("https://example.com/terms/"),
      contact = Some(
        Contact(
          name = Some("API Support"),
          url = Some("https://www.example.com/support"),
          email = Some("support@example.com")
        )
      ),
      license = Some(
        License(
          name = "Apache 2.0",
          url = Some("https://www.apache.org/licenses/LICENSE-2.0.html")
        )
      ),
      version = "1.0.1"
    )
  )

  test("petstore serialize") {
    val withPathItem = petstore.addPathItem(
      "/pets",
      PathItem(
        get = Some(
          Operation(
            operationId = Some("getPets"),
            description = Some("Gets all pets")
          ).addResponse(200, Response(description = "Success"))
        )
      )
    )

    val serialized = withPathItem.asJson
    val Right(json) = readJson("/petstore/basic-petstore.json")

    assert(serialized === json)
  }
}
