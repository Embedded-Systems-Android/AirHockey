package ffhs.ch.airhockey.objects;

import java.util.List;

import ffhs.ch.airhockey.data.VertexArray;
import ffhs.ch.airhockey.programs.ColorShaderProgram;
import ffhs.ch.airhockey.util.Geometry;
import ffhs.ch.airhockey.objects.ObjectBuilder.GeneratedData;
import ffhs.ch.airhockey.objects.ObjectBuilder.DrawCommand;
/**
 * Created by fb on 09.02.2017.
 *
 * Class which defines the Mallets
 */

public class Mallet {
    private static final int POSITION_COMPONENT_COUNT = 3;

    public final float radius;
    public final float height;

    private final VertexArray vertexArray;
    private final List<ObjectBuilder.DrawCommand> drawList;

    public Mallet(float radius, float height, int numPointsAroundMallet) {
        GeneratedData generatedData = ObjectBuilder.createMallet(new Geometry.Point(0f,
                0f, 0f), radius, height, numPointsAroundMallet);

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
