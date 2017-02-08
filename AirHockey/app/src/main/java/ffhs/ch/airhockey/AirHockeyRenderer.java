package ffhs.ch.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ffhs.ch.airhockey.util.LoggerConfig;
import ffhs.ch.airhockey.util.MatrixHelper;
import ffhs.ch.airhockey.util.ShaderHelper;
import ffhs.ch.airhockey.util.TextResourceReader;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.GL_TRIANGLE_FAN;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;
import static android.opengl.Matrix.multiplyMM;
import static android.opengl.Matrix.orthoM;
import static android.opengl.Matrix.rotateM;
import static android.opengl.Matrix.setIdentityM;
import static android.opengl.Matrix.translateM;

/**
 * Created by felix on 06.02.17.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private final float[] modelMatrix = new float[16];

    private static final String A_COLOR = "a_Color";
    private static final int COLOR_COMPONENT_COUNT = 3;

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private int program;
    private Context context;
    private static final int POSITION_COMPONENT_COUNT = 4;

    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;

    private int aColorLocation;

    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private static final String U_MATRIX = "u_Matrix";
    private final float[] projectionMatrix = new float[16];
    private int uMatrixLocation;

    public AirHockeyRenderer(Context context) {
        this.context = context;
        float[] tableVerticesWithTriangles = {

                // Order of coordinates: X, Y, Z, W, R, G, B

                // Triangle fan
                0f, 0f, 0f, 1.5f, 1f, 1f, 1f,
                -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
                0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,
                0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
                -0.5f, 0.8f, 0f, 2f, 0.7f, 0.7f, 0.7f,
                -0.5f, -0.8f, 0f, 1f, 0.7f, 0.7f, 0.7f,

                // Line 1
                -0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,
                0.5f, 0f, 0f, 1.5f, 1f, 0f, 0f,

                // Mallets
                0f, -0.4f, 0f, 1.25f, 0f, 0f, 1f,
                0f, 0.4f, 0f, 1.75f, 1f, 0f, 0f,


        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);

        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);

        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);

        if (LoggerConfig.ON) {
            ShaderHelper.validateProgram(program);
        }

        glUseProgram(program);

        aColorLocation = glGetAttribLocation(program, A_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);

        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);

        vertexData.position(POSITION_COMPONENT_COUNT);
        glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GL_FLOAT, false, STRIDE, vertexData);

        glEnableVertexAttribArray(aColorLocation);
        glEnableVertexAttribArray(aPositionLocation);

        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);

        setIdentityM(modelMatrix, 0);

        // Perspective Distance
        translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        // Perspective Rotation
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);

        /*final float aspectRatio = width > height ?
                (float) width / (float) height :
                (float) height / (float) width;

        if (width > height){
            // Landscape
            orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            // Portrait or square
            orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }*/
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);
        // Send Matrix to the shader
        glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix,0);
        // Drawing the Triangles
        glDrawArrays(GL_TRIANGLE_FAN, 0, 6);
        // Drawing the line 1
        glDrawArrays(GL_LINES, 6, 2);
        // Drawing the first Mallets blue
        glDrawArrays(GL_POINTS, 8, 1);
        // Drawing the second Mallets red
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
