attribute vec4 aPosition;
attribute vec2 aTexPosition;
varying vec2 vTexPosition;

void main() {
    gl_Position = aPosition;
    vTexPosition = aTexPosition;
}