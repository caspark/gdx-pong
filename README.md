A Pong Game Using [libGDX](http://libgdx.badlogicgames.com/)
============================================================

This is the result of playing around with libGDX with the aim of making a Pong game.

Run with `sbt run`or build a jar which includes all dependencies with `sbt assembly`, then `./target/scala-2.11/gdx-pong-*` to run it.

Somewhat notable things:

- Using a simple SBT build, because of [GRADLE-3023](http://issues.gradle.org/browse/GRADLE-3023).
- Using libgdx's freetype extension to scale fonts properly

