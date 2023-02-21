import mill._, scalalib._

object parsergpt extends ScalaModule {
  override def scalaVersion = "3.2.1"
  override def ammoniteVersion = "2.5.5-17-df243e14"
  override def ivyDeps = Agg(ivy"org.typelevel::cats-core:2.9.0",
    ivy"io.cequence::openai-scala-client:0.1.1",
    ivy"org.typelevel::cats-parse:0.3.9",
    ivy"org.typelevel::fabric-core:1.9.0",
    ivy"org.typelevel::fabric-io:1.9.0",
  )
}
