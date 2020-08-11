package com.shevelev.photo_editor.open_gl.renderers

import android.content.Context
import android.graphics.Bitmap
import android.opengl.GLES20
import android.opengl.GLSurfaceView
import android.opengl.GLUtils
import androidx.annotation.CallSuper
import androidx.annotation.RawRes
import com.shevelev.my_footprints_remastered.utils.resources.getRawString
import com.shevelev.photo_editor.R
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10


abstract class SurfaceRenderedBase(
    private val context: Context,
    private val bitmap: Bitmap,
    @RawRes
    private val fragmentShaderResId: Int
): GLSurfaceView.Renderer {

    private val vertices = floatArrayOf(
        -1f, -1f,           // Left-bottom
        1f, -1f,            // Right-bottom
        -1f, 1f,            // Left-top
        1f, 1f              // Right-top
    )

    private val textureVertices = floatArrayOf(
        0f, 1f,             // Left-bottom
        1f, 1f,             // Right-bottom
        0f, 0f,             // Left-top
        1f, 0f              // Right-top
    )

    private lateinit var verticesBuffer: FloatBuffer
    private lateinit var textureBuffer: FloatBuffer

    private var vertexShader: Int = 0
    private var fragmentShader: Int = 0
    protected var program: Int = 0

    private val textures = IntArray(2)

    override fun onDrawFrame(gl: GL10?) {
        draw(textures[0])
    }

    override fun onSurfaceChanged(gl: GL10?, width: Int, height: Int) {
        GLES20.glViewport(0, 0, width, height)
        GLES20.glClearColor(0f, 0f, 0f, 1f)

        createTextures()
        createProgram()
    }

    override fun onSurfaceCreated(gl: GL10?, config: EGLConfig?) {
    }

    @CallSuper
    protected open fun createTextures() {
        // Create empty textures
        GLES20.glGenTextures(2, textures, 0)

        // Switch to the texture with index 0 and bind the photo to it
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textures[0])

        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE)
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE)

        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0)

        // Init buffer with polygon vertexes
        var buffer = ByteBuffer.allocateDirect(vertices.size * 4)
        buffer.order(ByteOrder.nativeOrder())
        verticesBuffer = buffer.asFloatBuffer()
        verticesBuffer.put(vertices)
        verticesBuffer.position(0)

        // Init buffer with texture vertexes
        buffer = ByteBuffer.allocateDirect(textureVertices.size * 4)
        buffer.order(ByteOrder.nativeOrder())
        textureBuffer = buffer.asFloatBuffer()
        textureBuffer.put(textureVertices)
        textureBuffer.position(0)
    }

    private fun createProgram() {
        // Init vertex shader code
        vertexShader = GLES20.glCreateShader(GLES20.GL_VERTEX_SHADER)
        GLES20.glShaderSource(vertexShader, context.getRawString(R.raw.vertext))
        GLES20.glCompileShader(vertexShader)

        // Init fragment shader code
        fragmentShader = GLES20.glCreateShader(GLES20.GL_FRAGMENT_SHADER)
        GLES20.glShaderSource(fragmentShader, context.getRawString(fragmentShaderResId))
        GLES20.glCompileShader(fragmentShader)

        // Init program
        program = GLES20.glCreateProgram()
        GLES20.glAttachShader(program, vertexShader)
        GLES20.glAttachShader(program, fragmentShader)

        GLES20.glLinkProgram(program)
    }

    private fun draw(texture: Int) {
        GLES20.glBindFramebuffer(GLES20.GL_FRAMEBUFFER, 0)
        GLES20.glUseProgram(program)
        GLES20.glDisable(GLES20.GL_BLEND)

        val texturePositionHandle = GLES20.glGetAttribLocation(program, "aTexPosition")
        GLES20.glVertexAttribPointer(texturePositionHandle, 2, GLES20.GL_FLOAT, false, 0, textureBuffer)
        GLES20.glEnableVertexAttribArray(texturePositionHandle)

        val textureHandle = GLES20.glGetUniformLocation(program, "uTexture")
        GLES20.glActiveTexture(GLES20.GL_TEXTURE0)
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, texture)
        GLES20.glUniform1i(textureHandle, 0)

        val positionHandle = GLES20.glGetAttribLocation(program, "aPosition")
        GLES20.glVertexAttribPointer(positionHandle, 2, GLES20.GL_FLOAT, false, 0, verticesBuffer)
        GLES20.glEnableVertexAttribArray(positionHandle)

        setFragmentShaderExtParameters()

        GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT)
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_STRIP, 0, 4)
    }

    protected open fun setFragmentShaderExtParameters() {
        // do nothing in the base class
    }
}