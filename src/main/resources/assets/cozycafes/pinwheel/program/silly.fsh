#version 330 core
#line 0 1
#line 3 0
/* #version 150 */
vec4 linear_fog(vec4 inColor, float vertexDistance, float fogStart, float fogEnd, vec4 fogColor) {
    if(vertexDistance <= fogStart) {
        return inColor;
    }
    float fogValue = vertexDistance < fogEnd ? smoothstep(fogStart, fogEnd, vertexDistance) : 1.0;
    return vec4(mix(inColor.rgb, fogColor.rgb, (fogValue * fogColor.a)), inColor.a);
}
float linear_fog_fade(float vertexDistance, float fogStart, float fogEnd) {
    if(vertexDistance <= fogStart) {
        return 1.0;
    } else {
        if(vertexDistance >= fogEnd) {
            return 0.0;
        }
    }
    return smoothstep(fogEnd, fogStart, vertexDistance);
}
float fog_distance(vec3 pos, int shape) {
    if(shape == 0) {
        return length(pos);
    } else {
        float distXZ = length(pos.xz);
        float distY = abs(pos.y);
        return max(distXZ, distY);
    }
}
uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
in float vertexDistance;
in vec4 vertexColor;
in vec4 lightMapColor;
in vec4 overlayColor;
in vec2 texCoord0;
out vec4 fragColor;
void main() {
    vec4 color = texture(Sampler0, texCoord0);
    if(color.a < 0.1) {
        discard;
    }
    color *= (vertexColor * ColorModulator);
    color.rgb = mix(overlayColor.rgb, color.rgb, overlayColor.a);
    color = vec4(1, 1, 1, 1);x
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
