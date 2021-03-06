package ffhs.ch.airhockey.objects;

import java.util.List;

import ffhs.ch.airhockey.data.VertexArray;
import ffhs.ch.airhockey.programs.ColorShaderProgram;
import ffhs.ch.airhockey.util.Geometry;
import ffhs.ch.airhockey.objects.ObjectBuilder.DrawCommand;
import ffhs.ch.airhockey.objects.ObjectBuilder.GeneratedData;
/**
 * Created by cyborg on 11.02.17.
 *
 * Class which defines the Puck
 */

public class Puck {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius, height;

    private final VertexArray vertexArray;
    private final List<DrawCommand> drawList;

    public Puck(float radius, float height, int numPointsAroundPuck) {
        GeneratedData generatedData = ObjectBuilder.createPuck(new Geometry.Cylinder(
                new Geometry.Point(0f, 0f, 0f), radius, height), numPointsAroundPuck);

        this.radius = radius;
        this.height = height;

        vertexArray = new VertexArray(generatedData.vertexData);
        drawList = generatedData.drawList;
    }

    public void bindData(ColorShaderProgram colorProgram) {
        vertexArray.setVertexAttribPointer(0,
                colorProgram.getPositionAttributeLocation(),
                POSITION_COMPONENT_COUNT, 0);
    }

    public void draw() {
        for (DrawCommand drawCommand : drawList) {
            drawCommand.draw();
        }
    }
}
