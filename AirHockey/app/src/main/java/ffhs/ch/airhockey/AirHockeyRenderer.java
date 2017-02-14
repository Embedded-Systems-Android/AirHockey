package ffhs.ch.airhockey;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ffhs.ch.airhockey.objects.Mallet;
import ffhs.ch.airhockey.objects.Puck;
import ffhs.ch.airhockey.objects.Table;
import ffhs.ch.airhockey.programs.ColorShaderProgram;
import ffhs.ch.airhockey.programs.TextureShaderProgram;
import ffhs.ch.airhockey.util.LoggerConfig;
import ffhs.ch.airhockey.util.MatrixHelper;
import ffhs.ch.airhockey.util.ShaderHelper;
import ffhs.ch.airhockey.util.TextResourceReader;
import ffhs.ch.airhockey.util.TextureHelper;

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
import static android.opengl.Matrix.setLookAtM;
import static android.opengl.Matrix.translateM;
import static android.opengl.Matrix.transposeM;

/**
 * Created by felix on 06.02.17.
 */

public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private final Context context;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];
    private final float[] viewMatrix = new float[16];
    private final float[] viewProjectionMatrix = new float[16];
    private final float[] modelViewProjectionMatrix = new float[16];

    private Table table;
    private Mallet mallet;
    private Puck puck;

    private TextureShaderProgram textureProgram;
    private ColorShaderProgram colorPorgram;

    private int texture;

    public AirHockeyRenderer(Context context){
        this.context = context;
    }

    @Override
    public void onSurfaceCreated(GL10 glUnused, EGLConfig config) {
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        table = new Table();
        mallet = new Mallet(0.08f, 0.15f, 32);
        puck = new Puck(0.06f, 0.02f, 32);

        textureProgram = new TextureShaderProgram(context);
        colorPorgram = new ColorShaderProgram(context);

        texture = TextureHelper.loadTexture(context, R.drawable.air_hockey_surface);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Set the OpenGL viewport to fill the entire surface.
        glViewport(0, 0, width, height);

        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        setLookAtM(viewMatrix, 0,0f,1.2f,2.2f,0f,0f,0f,0f,1f,0f);

        setIdentityM(modelMatrix, 0);

        // Perspective Distance
        translateM(modelMatrix, 0, 0f, 0f, -2.5f);
        // Perspective Rotation
        rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
   }

    @Override
    public void onDrawFrame(GL10 glUnused) {
        // Clear the rendering surface
        glClear(GL_COLOR_BUFFER_BIT);

        // Cache the results of multiplying the matrix
        multiplyMM(viewProjectionMatrix, 0, projectionMatrix, 0, viewMatrix, 0);


        positionTableInScene();
        textureProgram.useProgram();
        textureProgram.setUniforms(modelViewProjectionMatrix, texture);
        table.bindData(textureProgram);
        table.draw();

        // Draw the mallets.
        positionObjectInScene(0f, mallet.height / 2f, -0.4f);
        colorPorgram.useProgram();
        colorPorgram.setUniforms(modelViewProjectionMatrix, 1f, 0f, 0f);
        mallet.bindData(colorPorgram);
        mallet.draw();

        positionObjectInScene(0f, mallet.height / 2f, 0.4f);
        colorPorgram.setUniforms(modelViewProjectionMatrix, 0f, 0f, 1f);
        // Note: dont have to define the object twice, just draw the same mallet in different position and color
        mallet.draw();

        // Draw the puck
        positionObjectInScene(0f, puck.height / 2f, 0f);
        colorPorgram.setUniforms(modelViewProjectionMatrix, 0.8f, 0.8f, 1f);
        puck.bindData(colorPorgram);
        puck.draw();
    }

    private void positionObjectInScene(float x, float y, float z) {
        setIdentityM(modelMatrix, 0);
        translateM(modelMatrix, 0, x, y, z);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }

    private void positionTableInScene() {
        // The table is defined in terms of X & Y coordinates, so we rotate it
        // 90 degrees to the lie of flat on the XZ plane
        setIdentityM(modelMatrix, 0);
        rotateM(modelMatrix, 0, -90f, 1f, 0f, 0f);
        multiplyMM(modelViewProjectionMatrix, 0, viewProjectionMatrix, 0, modelMatrix, 0);
    }


}
