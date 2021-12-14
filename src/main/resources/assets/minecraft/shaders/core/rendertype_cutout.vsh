#version 150

#moj_import <light.glsl>
#moj_import <noise.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;
in float Flags;

uniform sampler2D Sampler2;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec3 ChunkOffset;
uniform float wind_time = 0;
uniform vec3 wind_dir = vec3(0, 0, 1);
uniform float wind_strength = 1;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 normal;
out vec4 out_color;
out vec4 vertexLight;

void main() {

	int WIND = int(Flags) & 3;
	
    int wind_flag = 0;
	int wave_flag = 0;
	if (WIND - 2 >= 0) {
		wave_flag = 1;
	} else {
		if (WIND - 1 >= 0) {
			wind_flag = 1;
		}
	}
	
	vec3 cpos = Position + floor(ChunkOffset / 16) * 16;
	vec3 chunk_pos = Position + ChunkOffset;
	
	float wind = noise(vec3(cpos.x + wind_time, cpos.y + wind_time, cpos.z + wind_time));
	float wave_wind = noise(vec3(cpos.x + wind_time * 0.1f, cpos.y + wind_time* 0.1f, cpos.z + wind_time* 0.1f));
	if (wind < 0) {
		wind *= 0.5f;
	}
	if (wave_wind < 0) {
		wave_wind *= 0.5f;
	}
	wave_wind *= 0.1f;
	wind *= 0.1f;
	float wave = wave_wind * wind_strength;
	wind *= float(wind_flag) * wind_strength;
	
	vec3 new_pos = vec3(chunk_pos.x + wind * wind_dir.x, chunk_pos.y - abs(wind) * float(wave_flag), chunk_pos.z + wind * wind_dir.z);
	
    gl_Position = ProjMat * ModelViewMat * vec4(new_pos, 1.0);

    vertexDistance = length((ModelViewMat * vec4(new_pos, 1.0)).xyz);
    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
	vertexLight = minecraft_sample_lightmap(Sampler2, UV2);
    texCoord0 = UV0;
    normal = ProjMat * ModelViewMat * vec4(Normal, 0.0);
	
	out_color = Color;
}
