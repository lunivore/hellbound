package com.lunivore.hellbound.glue

import com.lunivore.stirry.Stirry
import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import javafx.geometry.Insets
import javafx.scene.Group
import javafx.scene.layout.CornerRadii
import javafx.scene.paint.Color
import javafx.scene.paint.Paint
import javafx.scene.paint.RadialGradient
import javafx.scene.shape.Rectangle
import tornadofx.asBackground


class GridPageObject(world: World) {

    protected val nl = "\n"

    fun should_look_like(expectedGrid: String) {
        val expectedGridMatchingSyntax = nl + expectedGrid.trimIndent() + nl
        val actualGrid = Stirry.findInRoot<Group> { it.id == "gameGrid" }.value
        assertThat(convertGridToString(actualGrid), equalTo(expectedGridMatchingSyntax))
    }

    private fun convertGridToString(actualGrid: Group): String {
        var numOfCols : Int = with (actualGrid.children.last() as Rectangle) {(x / width).toInt()}

        val builder = StringBuilder()
        builder.append(nl)
        for(child in actualGrid.children) {
            val square = child as Rectangle
            appendRepresentationAndNLIfRequired(square, builder, numOfCols)
        }
        return builder.toString()
    }

    private fun appendRepresentationAndNLIfRequired(
        square: Rectangle,
        builder: StringBuilder,
        numOfCols: Int
    ) {
        builder.append(getStringRepresentationOf(square.fill!!))
        val col = (square.x / square.width).toInt()
        if (col == numOfCols) {
            builder.append(nl)
        }
    }

    private fun getStringRepresentationOf(color: Paint): Char {
        val colorToCompare = if (color is RadialGradient) color.stops[0].color else color
        return if(Color.BLACK == colorToCompare) '.' else 'X'
    }
}
