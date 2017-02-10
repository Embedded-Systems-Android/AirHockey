package ffhs.ch.airhockey.programs;

import android.content.Context;

import ffhs.ch.airhockey.R;

import static android.opengl.GLES20.glGetAttribLocation;
import static android.opengl.GLES20.glGetUniformLocation;
import static android.opengl.GLES20.glUniformMatrix4fv;
import static ffhs.ch.airhockey.programs.ShaderProgram.A_COLOR;
import static ffhs.ch.airhockey.programs.ShaderProgram.A_POSITION;
import static ffhs.ch.airhockey.programs.ShaderProgram.U_MATRIX;

/**
 * Created by fb on 10.02.2017.
 */

public class ColorShaderProgram extends ShaderProgram {
    // Uniform locations
    private final int uMatrixLocation;

    // Attribute locations
    private final int aPositionLocation;
    private final int aColorLocation;

    public ColorShaderProgram(Context context){
        super(context, R.raw.simple_vertex_shader, R.raw.simple_fragment_shader);
        // Retrieve uniform locations for the shader program
        uMatrixLocation = glGetUniformLocation(program, U_MATRIX);
        // Retrieve attribute locations for the shader program
        aPositionLocation = glGetAttribLocation(program, A_POSITION);
        aColorLocation = glGetAttribLocation(program, A_COLOR);
    }
    public void setUniforms(float[] matrix){
        // Pass the matrix into the shader program
        glUniformMatrix4fv(uMatrixLocation, 1, false, matrix, 0);
    }
    public int getPositionAttributeLocation(){
        return aPositionLocation;
    }
    public int getColorAttributeLocation(){
        return aColorLocation;
    }
}
