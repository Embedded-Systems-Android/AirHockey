package ffhs.ch.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ffhs.ch.airhockey.util.LoggerConfig;
import ffhs.ch.airhockey.util.ShaderHelper;
import ffhs.ch.airhockey.util.TextResourceReader;

import static android.opengl.GLES20.GL_COLOR_BUFFER_BIT;
import static android.opengl.GLES20.GL_FLOAT;
import static android.opengl.GLES20.GL_LINES;
import static android.opengl.GLES20.GL_POINTS;
import static android.opengl.GLES20.GL_TRIANGLES;
import static android.opengl.GLES20.glClear;
import static android.opengl.GLES20.glClearColor;
import static android.opengl.GLES20.glDrawArrays;
import static android.opengl.GLES20.glEnableVertexAttribArray;
import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniform4f;
import static android.opengl.GLES20.glUseProgram;
import static android.opengl.GLES20.glVertexAttribPointer;
import static android.opengl.GLES20.glViewport;

/**
 * Created by felix on 06.02.17.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer{

    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private int program;
    private Context context;
    private static final int POSITION_COMPONENT_COUNT = 2;

    private static final int BYTES_PER_FLOAT = 4;
    private FloatBuffer vertexData;

    public AirHockeyRenderer(Context context){
        this.context = context;
        float[] tableVerticesWithTriangles={
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                // Line 1
                0f, 7f,
                9f, 7f,

                // Mallets
                4.5f, 2f,
                4.5f, 12f
        };
        vertexData = ByteBuffer.allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT).order(ByteOrder.nativeOrder()).asFloatBuffer();
        vertexData.put(tableVerticesWithTriangles);
    }
    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        glClearColor(0.0f,0.0f,0.0f,0.0f);
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_vertex_shader);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(context, R.raw.simple_fragment_shader);
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        program = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        if (LoggerConfig.ON){
            ShaderHelper.validateProgram(program);
        }
        glUseProgram(program);
        uColorLocation = glGetUniformLocation(program, U_COLOR);
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        vertexData.position(0);
        glVertexAttribPointer(aPositionLocation,POSITION_COMPONENT_COUNT,GL_FLOAT,false,0,vertexData);
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
// Set the OpenGL viewport to fill the entire surface.
        glViewport(0,0,width,height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // Clear the rendering surface.
        glClear(GL_COLOR_BUFFER_BIT);
        // Drawing the Triangles
        glUniform4f(uColorLocation,1.0f,1.0f,1.0f,1.0f);
        glDrawArrays(GL_TRIANGLES,0,6);
        // Drawing the line 1
        glUniform4f(uColorLocation, 1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_LINES, 6, 2);
        // Drawing the first Mallets blue
        glUniform4f(uColorLocation, 0.0f,0.0f,1.0f,1.0f);
        glDrawArrays(GL_POINTS, 8,1);
        // Drawing the second Mallets red
        glUniform4f(uColorLocation, 1.0f,0.0f,0.0f,1.0f);
        glDrawArrays(GL_POINTS, 9,1);
    }
}
