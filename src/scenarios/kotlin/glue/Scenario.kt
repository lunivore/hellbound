package com.lunivore.hellbound.glue

import com.google.inject.Guice
import com.lunivore.hellbound.Hellbound
import com.lunivore.hellbound.InjectorModule
import com.lunivore.hellbound.engine.Heartbeat
import com.lunivore.stirry.Stirry
import javafx.scene.layout.HBox
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.mockito.Mockito.mock


abstract class Scenario {
    private lateinit var hellbound : Hellbound
    protected val world = World()

    protected val When_the_player
            get() = KeyPressPageObject(world)

    protected val Given_a_game
            get() = GamePageObject(world, When_the_player)
    protected val Then_the_game
            get() = Given_a_game
    protected val When_the_shape
            get() = ShapePageObject(world)
    protected val And_the_next_shape
            get() = When_the_shape

    protected val Then_the_score
            get() = ScorePageObject(world)
    protected val And_the_welcome_screen
            get() = WelcomePageObject(world)

    protected val Then_the_grid
            get() = GridPageObject(world)
    protected val When_the_heart
            get() = HeartbeatPageObject(world)

    @Before
    fun `Starting Stirry`() {
        System.out.println("Starting Stirry")
        Stirry.initialize()
        val fixedSeed = 42L
        val unusedBecauseWeFakeTheHeartbeat = mock(Heartbeat::class.java)
        val injector = Guice.createInjector(
            InjectorModule(
                fixedSeed,
                world.events,
                unusedBecauseWeFakeTheHeartbeat
            )
        )
        hellbound = Hellbound(injector)
        Stirry.launchApp(hellbound)
        Stirry.waitForPlatform()
        runBlocking { delay(1000) }
    }

    @After
    fun `Stopping Stirry`() {
        System.out.println("Stopping Stirry")
        // TODO: Add this to Stirry's Stop or find out how to make this work properly in TornadoFX -
        // singleAssign() is not using the app as its scope!
        Stirry.stage?.scene?.root = HBox()
        Stirry.stop()
    }

}
