#version 150

#moj_import <fog.glsl>
#moj_import <seasons.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float Season;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;
in vec4 out_color;
in vec4 vertexLight;

out vec4 fragColor;

void main() {
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
	
	if (!(out_color.x == out_color.y && out_color.y == out_color.z) && (out_color.x + out_color.y + out_color.z > 0)) {
		color = seasonColor(color, Season, vertexLight.xyz);
	}
	
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
	//fragColor = mix(fragColor, out_color, 0.99f);
}
