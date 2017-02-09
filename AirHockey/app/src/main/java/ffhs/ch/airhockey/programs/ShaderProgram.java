package ffhs.ch.airhockey.programs;

import android.content.Context;

import ffhs.ch.airhockey.util.ShaderHelper;
import ffhs.ch.airhockey.util.TextResourceReader;

import static android.opengl.GLES20.glUseProgram;

/**
 * Created by fb on 09.02.2017.
 */

public class ShaderProgram {
    // Uniform constants
    protected static final String U_MATRIX = "u_Matrix";
    protected static final String U_TEXTURE_UNIT = "u_TextureUnit";

    // Attribute constants
    protected static final String A_POSITION = "a_Position";
    protected static final String A_COLOR = "a_Color";
    protected static final String A_TEXTURE_COORDINATES = "a_TextureCoordinates";

    // Shader Program
    protected final int program;

    protected ShaderProgram(Context context, int vertexShaderResourceId, int fragmentShaderResourceId){
        // Compile the Shaders and link the program
        program = ShaderHelper.buildProgram(TextResourceReader.readTextFileFromResource(context, vertexShaderResourceId),
                TextResourceReader.readTextFileFromResource(context, fragmentShaderResourceId));
    }

    public void useProgram(){
        // Set the current OpenGL shader program to this program
        glUseProgram(program);
    }
}
