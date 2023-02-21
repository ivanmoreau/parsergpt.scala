import mill._, os._, scalalib._, publish._

object parsergpt extends ScalaModule with PublishModule {
  override def scalaVersion = "3.2.1"
  override def ammoniteVersion = "2.5.5-17-df243e14"
  override def ivyDeps = Agg(ivy"org.typelevel::cats-core:2.9.0",
    ivy"io.cequence::openai-scala-client:0.1.1",
    ivy"org.typelevel::cats-parse:0.3.9",
    ivy"org.typelevel::fabric-core:1.9.0",
    ivy"org.typelevel::fabric-io:1.9.0",
  )
  def publishVersion = "0.0.1"
  def pomSettings = PomSettings(
    description = "ParserGPT (for cats-parse) (not a serious library, tho it may work)",
    organization = "com.ivmoreau",
    url = "https://github.com/ivanmoreau/parsergpt.scala",
    licenses = Seq(License.MIT),
    versionControl = VersionControl.github("ivanmoreau", "parsergpt.scala"),
    developers = Seq(
      Developer("ivanmoreau", "Iván Molina Rebolledo", "https://github.com/ivanmoreau")
    )
  )
}
