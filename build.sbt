import AssemblyKeys._

name := "gdx-pong"

version := "1.0"

scalaVersion := "2.11.0"

val gdxVersion = "1.1.0"

libraryDependencies ++= Seq(
	"com.badlogicgames.gdx" % "gdx" % gdxVersion,
	"com.badlogicgames.gdx" % "gdx-backend-lwjgl" % gdxVersion,
	"com.badlogicgames.gdx" % "gdx-platform" % gdxVersion classifier "natives-desktop",
	// freetype extension
	"com.badlogicgames.gdx" % "gdx-freetype" % gdxVersion,
	"com.badlogicgames.gdx" % "gdx-freetype-platform" % gdxVersion classifier "natives-desktop"
)

// Eclipse plugin configuration
EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource

EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE16)

EclipseKeys.relativizeLibs := false

EclipseKeys.withSource := true

// Scalariform settings
scalariformSettings

// Assembly plugin configuration
assemblySettings

// make the resulting jar directly runnable
assemblyOption in assembly ~= { _.copy(prependShellScript = Some(defaultShellScript)) }

jarName in assembly := { s"${name.value}-${version.value}" }
